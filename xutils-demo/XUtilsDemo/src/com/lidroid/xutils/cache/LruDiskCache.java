/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lidroid.xutils.cache;

import com.lidroid.xutils.util.IOUtils;
import com.lidroid.xutils.util.LogUtils;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A cache that uses a bounded amount of space on a filesystem. Each cache
 * entry has a string key and a fixed number of values. Values are byte sequences,
 * accessible as streams or files. Each value must be between {@code 0} and
 * {@code Integer.MAX_VALUE} bytes in length.
 * <p/>
 * <p>The cache stores its data in a directory on the filesystem. This
 * directory must be exclusive to the cache; the cache may delete or overwrite
 * files from its directory. It is an error for multiple processes to use the
 * same cache directory at the same time.
 * <p/>
 * <p>This cache limits the number of bytes that it will store on the
 * filesystem. When the number of stored bytes exceeds the limit, the cache will
 * remove entries in the background until the limit is satisfied. The limit is
 * not strict: the cache may temporarily exceed it while waiting for files to be
 * deleted. The limit does not include filesystem overhead or the cache
 * journal so space-sensitive applications should set a conservative limit.
 * <p/>
 * <p>Clients call {@link #edit} to create or update the values of an entry. An
 * entry may have only one editor at one time; if a value is not available to be
 * edited then {@link #edit} will return null.
 * <ul>
 * <li>When an entry is being <strong>created</strong> it is necessary to
 * supply a full set of values; the empty value should be used as a
 * placeholder if necessary.
 * <li>When an entry is being <strong>edited</strong>, it is not necessary
 * to supply data for every value; values default to their previous
 * value.
 * </ul>
 * Every {@link #edit} call must be matched by a call to {@link Editor#commit}
 * or {@link Editor#abort}. Committing is atomic: a read observes the full set
 * of values as they were before or after the commit, but never a mix of values.
 * <p/>
 * <p>Clients call {@link #get} to read a snapshot of an entry. The read will
 * observe the value at the time that {@link #get} was called. Updates and
 * removals after the call do not impact ongoing reads.
 * <p/>
 * <p>This class is tolerant of some I/O errors. If files are missing from the
 * filesystem, the corresponding entries will be dropped from the cache. If
 * an error occurs while writing a cache value, the edit will fail silently.
 * Callers should handle other problems by catching {@code IOException} and
 * responding appropriately.
 */
public final class LruDiskCache implements Closeable {
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_TEMP = "journal.tmp";
    static final String JOURNAL_FILE_BACKUP = "journal.bkp";
    static final String MAGIC = "libcore.io.DiskLruCache";
    static final String VERSION = "1";
    static final long ANY_SEQUENCE_NUMBER = -1;
    private static final char CLEAN = 'C';
    private static final char UPDATE = 'U';
    private static final char DELETE = 'D';
    private static final char READ = 'R';
    private static final char EXPIRY_PREFIX = 't';

    /*
     * This cache uses a journal file named "journal". A typical journal file
     * looks like this:
     *     libcore.io.DiskLruCache
     *     1
     *     100
     *     2
     *
     *     CLEAN 3400330d1dfc7f3f7f4b8d4d803dfcf6 832 21054
     *     UPDATE 335c4c6028171cfddfbaae1a9c313c52
     *     CLEAN 335c4c6028171cfddfbaae1a9c313c52 3934 2342
     *     DELETE 335c4c6028171cfddfbaae1a9c313c52
     *     UPDATE 1ab96a171faeeee38496d8b330771a7a
     *     CLEAN 1ab96a171faeeee38496d8b330771a7a 1600 234
     *     READ 335c4c6028171cfddfbaae1a9c313c52
     *     READ 3400330d1dfc7f3f7f4b8d4d803dfcf6
     *
     * The first five lines of the journal form its header. They are the
     * constant string "libcore.io.DiskLruCache", the disk cache's version,
     * the application's version, the value count, and a blank line.
     *
     * Each of the subsequent lines in the file is a record of the state of a
     * cache entry. Each line contains space-separated values: a state, a key,
     * and optional state-specific values.
     *   o UPDATE lines track that an entry is actively being created or updated.
     *     Every successful UPDATE action should be followed by a CLEAN or DELETE
     *     action. UPDATE lines without a matching CLEAN or DELETE indicate that
     *     temporary files may need to be deleted.
     *   o CLEAN lines track a cache entry that has been successfully published
     *     and may be read. A publish line is followed by the lengths of each of
     *     its values.
     *   o READ lines track accesses for LRU.
     *   o DELETE lines track entries that have been deleted.
     *
     * The journal file is appended to as cache operations occur. The journal may
     * occasionally be compacted by dropping redundant lines. A temporary file named
     * "journal.tmp" will be used during compaction; that file should be deleted if
     * it exists when the cache is opened.
     */

    private final File directory;
    private final File journalFile;
    private final File journalFileTmp;
    private final File journalFileBackup;
    private final int appVersion;
    private long maxSize;
    private final int valueCount;
    private long size = 0;
    private Writer journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries =
            new LinkedHashMap<String, Entry>(0, 0.75f, true);
    private int redundantOpCount;

    /**
     * To differentiate between old and current snapshots, each entry is given
     * a sequence number each time an edit is committed. A snapshot is stale if
     * its sequence number is not equal to its entry's sequence number.
     */
    private long nextSequenceNumber = 0;

    /**
     * This cache uses a single background thread to evict entries.
     */
    final ThreadPoolExecutor executorService =
            new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private final Callable<Void> cleanupCallable = new Callable<Void>() {
        public Void call() throws Exception {
            synchronized (LruDiskCache.this) {
                if (journalWriter == null) {
                    return null; // Closed.
                }
                trimToSize();
                if (journalRebuildRequired()) {
                    rebuildJournal();
                    redundantOpCount = 0;
                }
            }
            return null;
        }
    };

    private LruDiskCache(File directory, int appVersion, int valueCount, long maxSize) {
        this.directory = directory;
        this.appVersion = appVersion;
        this.journalFile = new File(directory, JOURNAL_FILE);
        this.journalFileTmp = new File(directory, JOURNAL_FILE_TEMP);
        this.journalFileBackup = new File(directory, JOURNAL_FILE_BACKUP);
        this.valueCount = valueCount;
        this.maxSize = maxSize;
    }

    /**
     * Opens the cache in {@code directory}, creating a cache if none exists
     * there.
     *
     * @param directory  a writable directory
     * @param valueCount the number of values per cache entry. Must be positive.
     * @param maxSize    the maximum number of bytes this cache should use to store
     * @throws IOException if reading or writing the cache directory fails
     */
    public static LruDiskCache open(File directory, int appVersion, int valueCount, long maxSize)
            throws IOException {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (valueCount <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        }

        // If a bkp file exists, use it instead.
        File backupFile = new File(directory, JOURNAL_FILE_BACKUP);
        if (backupFile.exists()) {
            File journalFile = new File(directory, JOURNAL_FILE);
            // If journal file also exists just delete backup file.
            if (journalFile.exists()) {
                backupFile.delete();
            } else {
                renameTo(backupFile, journalFile, false);
            }
        }

        // Prefer to pick up where we left off.
        LruDiskCache cache = new LruDiskCache(directory, appVersion, valueCount, maxSize);
        if (cache.journalFile.exists()) {
            try {
                cache.readJournal();
                cache.processJournal();
                cache.journalWriter = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(cache.journalFile, true), HTTP.US_ASCII));
                return cache;
            } catch (Throwable journalIsCorrupt) {
                LogUtils.e("DiskLruCache "
                        + directory
                        + " is corrupt: "
                        + journalIsCorrupt.getMessage()
                        + ", removing", journalIsCorrupt);
                cache.delete();
            }
        }

        // Create a new empty cache.
        if (directory.exists() || directory.mkdirs()) {
            cache = new LruDiskCache(directory, appVersion, valueCount, maxSize);
            cache.rebuildJournal();
        }
        return cache;
    }

    private void readJournal() throws IOException {
        StrictLineReader reader = null;
        try {
            reader = new StrictLineReader(new FileInputStream(journalFile));
            String magic = reader.readLine();
            String version = reader.readLine();
            String appVersionString = reader.readLine();
            String valueCountString = reader.readLine();
            String blank = reader.readLine();
            if (!MAGIC.equals(magic)
                    || !VERSION.equals(version)
                    || !Integer.toString(appVersion).equals(appVersionString)
                    || !Integer.toString(valueCount).equals(valueCountString)
                    || !"".equals(blank)) {
                throw new IOException("unexpected journal header: [" + magic + ", " + version + ", "
                        + valueCountString + ", " + blank + "]");
            }

            int lineCount = 0;
            while (true) {
                try {
                    readJournalLine(reader.readLine());
                    lineCount++;
                } catch (EOFException endOfJournal) {
                    break;
                }
            }
            redundantOpCount = lineCount - lruEntries.size();
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private void readJournalLine(String line) throws IOException {
        int firstSpace = line.indexOf(' ');
        char lineTag = 0;
        if (firstSpace == 1) {
            lineTag = line.charAt(0);
        } else {
            throw new IOException("unexpected journal line: " + line);
        }

        int keyBegin = firstSpace + 1;
        int secondSpace = line.indexOf(' ', keyBegin);
        final String diskKey;
        if (secondSpace == -1) {
            diskKey = line.substring(keyBegin);
            if (lineTag == DELETE) {
                lruEntries.remove(diskKey);
                return;
            }
        } else {
            diskKey = line.substring(keyBegin, secondSpace);
        }

        Entry entry = lruEntries.get(diskKey);
        if (entry == null) {
            entry = new Entry(diskKey);
            lruEntries.put(diskKey, entry);
        }

        switch (lineTag) {
            case CLEAN: {
                entry.readable = true;
                entry.currentEditor = null;
                String[] parts = line.substring(secondSpace + 1).split(" ");
                if (parts.length > 0) {
                    try {
                        if (parts[0].charAt(0) == EXPIRY_PREFIX) {
                            entry.expiryTimestamp = Long.valueOf(parts[0].substring(1));
                            entry.setLengths(parts, 1);
                        } else {
                            entry.expiryTimestamp = Long.MAX_VALUE;
                            entry.setLengths(parts, 0);
                        }
                    } catch (Throwable e) {
                        throw new IOException("unexpected journal line: " + line);
                    }
                }
                break;
            }
            case UPDATE: {
                entry.currentEditor = new Editor(entry);
                break;
            }
            case READ: {
                // This work was already done by calling lruEntries.get().
                break;
            }
            default: {
                throw new IOException("unexpected journal line: " + line);
            }
        }
    }

    /**
     * Computes the initial size and collects garbage as a part of opening the
     * cache. Dirty entries are assumed to be inconsistent and will be deleted.
     */
    private void processJournal() throws IOException {
        deleteIfExists(journalFileTmp);
        for (Iterator<Entry> i = lruEntries.values().iterator(); i.hasNext(); ) {
            Entry entry = i.next();
            if (entry.currentEditor == null) {
                for (int t = 0; t < valueCount; t++) {
                    size += entry.lengths[t];
                }
            } else {
                entry.currentEditor = null;
                for (int t = 0; t < valueCount; t++) {
                    deleteIfExists(entry.getCleanFile(t));
                    deleteIfExists(entry.getDirtyFile(t));
                }
                i.remove();
            }
        }
    }

    /**
     * Creates a new journal that omits redundant information. This replaces the
     * current journal if it exists.
     */
    private synchronized void rebuildJournal() throws IOException {
        if (journalWriter != null) {
            IOUtils.closeQuietly(journalWriter);
        }

        Writer writer = null;
        try {
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(journalFileTmp), HTTP.US_ASCII));
            writer.write(MAGIC);
            writer.write("\n");
            writer.write(VERSION);
            writer.write("\n");
            writer.write(Integer.toString(appVersion));
            writer.write("\n");
            writer.write(Integer.toString(valueCount));
            writer.write("\n");
            writer.write("\n");

            for (Entry entry : lruEntries.values()) {
                if (entry.currentEditor != null) {
                    writer.write(UPDATE + " " + entry.diskKey + '\n');
                } else {
                    writer.write(CLEAN + " " + entry.diskKey + " " + EXPIRY_PREFIX + entry.expiryTimestamp + entry.getLengths() + '\n');
                }
            }
        } finally {
            IOUtils.closeQuietly(writer);
        }

        if (journalFile.exists()) {
            renameTo(journalFile, journalFileBackup, true);
        }
        renameTo(journalFileTmp, journalFile, false);
        journalFileBackup.delete();

        journalWriter = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(journalFile, true), HTTP.US_ASCII));
    }

    private static void deleteIfExists(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void renameTo(File from, File to, boolean deleteDestination) throws IOException {
        if (deleteDestination) {
            deleteIfExists(to);
        }
        if (!from.renameTo(to)) {
            throw new IOException();
        }
    }

    public synchronized long getExpiryTimestamp(String key) throws IOException {
        String diskKey = fileNameGenerator.generate(key);
        checkNotClosed();
        Entry entry = lruEntries.get(diskKey);
        if (entry == null) {
            return 0;
        } else {
            return entry.expiryTimestamp;
        }
    }

    public File getCacheFile(String key, int index) {
        String diskKey = fileNameGenerator.generate(key);
        File result = new File(this.directory, diskKey + "." + index);
        if (result.exists()) {
            return result;
        } else {
            try {
                this.remove(key);
            } catch (IOException ignore) {
            }
            return null;
        }
    }

    public Snapshot get(String key) throws IOException {
        String diskKey = fileNameGenerator.generate(key);
        return getByDiskKey(diskKey);
    }

    /**
     * Returns a snapshot of the entry named {@code diskKey}, or null if it doesn't
     * exist is not currently readable. If a value is returned, it is moved to
     * the head of the LRU queue.
     */
    private synchronized Snapshot getByDiskKey(String diskKey) throws IOException {
        checkNotClosed();
        Entry entry = lruEntries.get(diskKey);
        if (entry == null) {
            return null;
        }

        if (!entry.readable) {
            return null;
        }

        // If expired, delete the entry.
        if (entry.expiryTimestamp < System.currentTimeMillis()) {
            for (int i = 0; i < valueCount; i++) {
                File file = entry.getCleanFile(i);
                if (file.exists() && !file.delete()) {
                    throw new IOException("failed to delete " + file);
                }
                size -= entry.lengths[i];
                entry.lengths[i] = 0;
            }
            redundantOpCount++;
            journalWriter.append(DELETE + " " + diskKey + '\n');
            lruEntries.remove(diskKey);
            if (journalRebuildRequired()) {
                executorService.submit(cleanupCallable);
            }
            return null;
        }

        // Open all streams eagerly to guarantee that we see a single published
        // snapshot. If we opened streams lazily then the streams could come
        // from different edits.
        FileInputStream[] ins = new FileInputStream[valueCount];
        try {
            for (int i = 0; i < valueCount; i++) {
                ins[i] = new FileInputStream(entry.getCleanFile(i));
            }
        } catch (FileNotFoundException e) {
            // A file must have been deleted manually!
            for (int i = 0; i < valueCount; i++) {
                if (ins[i] != null) {
                    IOUtils.closeQuietly(ins[i]);
                } else {
                    break;
                }
            }
            return null;
        }

        redundantOpCount++;
        journalWriter.append(READ + " " + diskKey + '\n');
        if (journalRebuildRequired()) {
            executorService.submit(cleanupCallable);
        }

        return new Snapshot(diskKey, entry.sequenceNumber, ins, entry.lengths);
    }

    /**
     * Returns an editor for the entry named {@code Key}, or null if another
     * edit is in progress.
     */
    public Editor edit(String key) throws IOException {
        String diskKey = fileNameGenerator.generate(key);
        return editByDiskKey(diskKey, ANY_SEQUENCE_NUMBER);
    }

    private synchronized Editor editByDiskKey(String diskKey, long expectedSequenceNumber) throws IOException {
        checkNotClosed();
        Entry entry = lruEntries.get(diskKey);
        if (expectedSequenceNumber != ANY_SEQUENCE_NUMBER &&
                (entry == null || entry.sequenceNumber != expectedSequenceNumber)) {
            return null; // Snapshot is stale.
        }
        if (entry == null) {
            entry = new Entry(diskKey);
            lruEntries.put(diskKey, entry);
        } else if (entry.currentEditor != null) {
            return null; // Another edit is in progress.
        }

        Editor editor = new Editor(entry);
        entry.currentEditor = editor;

        // Flush the journal before creating files to prevent file leaks.
        journalWriter.write(UPDATE + " " + diskKey + '\n');
        journalWriter.flush();
        return editor;
    }

    /**
     * Returns the directory where this cache stores its data.
     */
    public File getDirectory() {
        return directory;
    }

    /**
     * Returns the maximum number of bytes that this cache should use to store
     * its data.
     */
    public synchronized long getMaxSize() {
        return maxSize;
    }

    /**
     * Changes the maximum number of bytes the cache can store and queues a job
     * to trim the existing store, if necessary.
     */
    public synchronized void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
        executorService.submit(cleanupCallable);
    }

    /**
     * Returns the number of bytes currently being used to store the values in
     * this cache. This may be greater than the max size if a background
     * deletion is pending.
     */
    public synchronized long size() {
        return size;
    }

    private synchronized void completeEdit(Editor editor, boolean success) throws IOException {
        Entry entry = editor.entry;
        if (entry.currentEditor != editor) {
            throw new IllegalStateException();
        }

        // If this edit is creating the entry for the first time, every index must have a value.
        if (success && !entry.readable) {
            for (int i = 0; i < valueCount; i++) {
                if (!editor.written[i]) {
                    editor.abort();
                    throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                }
                if (!entry.getDirtyFile(i).exists()) {
                    editor.abort();
                    return;
                }
            }
        }

        for (int i = 0; i < valueCount; i++) {
            File dirty = entry.getDirtyFile(i);
            if (success) {
                if (dirty.exists()) {
                    File clean = entry.getCleanFile(i);
                    dirty.renameTo(clean);
                    long oldLength = entry.lengths[i];
                    long newLength = clean.length();
                    entry.lengths[i] = newLength;
                    size = size - oldLength + newLength;
                }
            } else {
                deleteIfExists(dirty);
            }
        }

        redundantOpCount++;
        entry.currentEditor = null;
        if (entry.readable | success) {
            entry.readable = true;
            journalWriter.write(CLEAN + " " + entry.diskKey + " " + EXPIRY_PREFIX + entry.expiryTimestamp + entry.getLengths() + '\n');
            if (success) {
                entry.sequenceNumber = nextSequenceNumber++;
            }
        } else {
            lruEntries.remove(entry.diskKey);
            journalWriter.write(DELETE + " " + entry.diskKey + '\n');
        }
        journalWriter.flush();

        if (size > maxSize || journalRebuildRequired()) {
            executorService.submit(cleanupCallable);
        }
    }

    /**
     * We only rebuild the journal when it will halve the size of the journal
     * and eliminate at least 2000 ops.
     */
    private boolean journalRebuildRequired() {
        final int redundantOpCompactThreshold = 2000;
        return redundantOpCount >= redundantOpCompactThreshold //
                && redundantOpCount >= lruEntries.size();
    }

    public boolean remove(String key) throws IOException {
        String diskKey = fileNameGenerator.generate(key);
        return removeByDiskKey(diskKey);
    }

    /**
     * Drops the entry for {@code diskKey} if it exists and can be removed. Entries
     * actively being edited cannot be removed.
     *
     * @return true if an entry was removed.
     */
    private synchronized boolean removeByDiskKey(String diskKey) throws IOException {
        checkNotClosed();
        Entry entry = lruEntries.get(diskKey);
        if (entry == null || entry.currentEditor != null) {
            return false;
        }

        for (int i = 0; i < valueCount; i++) {
            File file = entry.getCleanFile(i);
            if (file.exists() && !file.delete()) {
                throw new IOException("failed to delete " + file);
            }
            size -= entry.lengths[i];
            entry.lengths[i] = 0;
        }

        redundantOpCount++;
        journalWriter.append(DELETE + " " + diskKey + '\n');
        lruEntries.remove(diskKey);

        if (journalRebuildRequired()) {
            executorService.submit(cleanupCallable);
        }

        return true;
    }

    /**
     * Returns true if this cache has been closed.
     */
    public synchronized boolean isClosed() {
        return journalWriter == null;
    }

    private void checkNotClosed() {
        if (journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /**
     * Force buffered operations to the filesystem.
     */
    public synchronized void flush() throws IOException {
        checkNotClosed();
        trimToSize();
        journalWriter.flush();
    }

    /**
     * Closes this cache. Stored values will remain on the filesystem.
     */
    @Override
    public synchronized void close() throws IOException {
        if (journalWriter == null) {
            return; // Already closed.
        }
        for (Entry entry : new ArrayList<Entry>(lruEntries.values())) {
            if (entry.currentEditor != null) {
                entry.currentEditor.abort();
            }
        }
        trimToSize();
        journalWriter.close();
        journalWriter = null;
    }

    private void trimToSize() throws IOException {
        while (size > maxSize) {
            Map.Entry<String, Entry> toEvict = lruEntries.entrySet().iterator().next();
            removeByDiskKey(toEvict.getKey());
        }
    }

    /**
     * Closes the cache and deletes all of its stored values. This will delete
     * all files in the cache directory including files that weren't created by
     * the cache.
     */
    public void delete() throws IOException {
        IOUtils.closeQuietly(this);
        deleteContents(directory);
    }

    private static String inputStreamToString(InputStream in) throws IOException {
        return readFully(new InputStreamReader(in, HTTP.UTF_8));
    }

    /**
     * A snapshot of the values for an entry.
     */
    public final class Snapshot implements Closeable {
        private final String diskKey;
        private final long sequenceNumber;
        private final FileInputStream[] ins;
        private final long[] lengths;

        private Snapshot(String diskKey, long sequenceNumber, FileInputStream[] ins, long[] lengths) {
            this.diskKey = diskKey;
            this.sequenceNumber = sequenceNumber;
            this.ins = ins;
            this.lengths = lengths;
        }

        /**
         * Returns an editor for this snapshot's entry, or null if either the
         * entry has changed since this snapshot was created or if another edit
         * is in progress.
         */
        public Editor edit() throws IOException {
            return LruDiskCache.this.editByDiskKey(diskKey, sequenceNumber);
        }

        /**
         * Returns the unbuffered stream with the value for {@code index}.
         */
        public FileInputStream getInputStream(int index) {
            return ins[index];
        }

        /**
         * Returns the string value for {@code index}.
         */
        public String getString(int index) throws IOException {
            return inputStreamToString(getInputStream(index));
        }

        /**
         * Returns the byte length of the value for {@code index}.
         */
        public long getLength(int index) {
            return lengths[index];
        }

        @Override
        public void close() {
            for (InputStream in : ins) {
                IOUtils.closeQuietly(in);
            }
        }
    }

    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            // Eat all writes silently. Nom nom.
        }
    };

    /**
     * Edits the values for an entry.
     */
    public final class Editor {
        private final Entry entry;
        private final boolean[] written;
        private boolean hasErrors;
        private boolean committed;

        private Editor(Entry entry) {
            this.entry = entry;
            this.written = (entry.readable) ? null : new boolean[valueCount];
        }

        public void setEntryExpiryTimestamp(long timestamp) {
            entry.expiryTimestamp = timestamp;
        }

        /**
         * Returns an unbuffered input stream to read the last committed value,
         * or null if no value has been committed.
         */
        public InputStream newInputStream(int index) throws IOException {
            synchronized (LruDiskCache.this) {
                if (entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                if (!entry.readable) {
                    return null;
                }
                try {
                    return new FileInputStream(entry.getCleanFile(index));
                } catch (FileNotFoundException e) {
                    return null;
                }
            }
        }

        /**
         * Returns the last committed value as a string, or null if no value
         * has been committed.
         */
        public String getString(int index) throws IOException {
            InputStream in = newInputStream(index);
            return in != null ? inputStreamToString(in) : null;
        }

        /**
         * Returns a new unbuffered output stream to write the value at
         * {@code index}. If the underlying output stream encounters errors
         * when writing to the filesystem, this edit will be aborted when
         * {@link #commit} is called. The returned output stream does not throw
         * IOExceptions.
         */
        public OutputStream newOutputStream(int index) throws IOException {
            synchronized (LruDiskCache.this) {
                if (entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                if (!entry.readable) {
                    written[index] = true;
                }
                File dirtyFile = entry.getDirtyFile(index);
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(dirtyFile);
                } catch (FileNotFoundException e) {
                    // Attempt to recreate the cache directory.
                    directory.mkdirs();
                    try {
                        outputStream = new FileOutputStream(dirtyFile);
                    } catch (FileNotFoundException e2) {
                        // We are unable to recover. Silently eat the writes.
                        return NULL_OUTPUT_STREAM;
                    }
                }
                return new FaultHidingOutputStream(outputStream);
            }
        }

        /**
         * Sets the value at {@code index} to {@code value}.
         */
        public void set(int index, String value) throws IOException {
            Writer writer = null;
            try {
                writer = new OutputStreamWriter(newOutputStream(index), HTTP.UTF_8);
                writer.write(value);
            } finally {
                IOUtils.closeQuietly(writer);
            }
        }

        /**
         * Commits this edit so it is visible to readers.  This releases the
         * edit lock so another edit may be started on the same key.
         */
        public void commit() throws IOException {
            if (hasErrors) {
                completeEdit(this, false);
                removeByDiskKey(entry.diskKey); // The previous entry is stale.
            } else {
                completeEdit(this, true);
            }
            committed = true;
        }

        /**
         * Aborts this edit. This releases the edit lock so another edit may be
         * started on the same key.
         */
        public void abort() throws IOException {
            completeEdit(this, false);
        }

        public void abortUnlessCommitted() {
            if (!committed) {
                try {
                    abort();
                } catch (Throwable ignored) {
                }
            }
        }

        private class FaultHidingOutputStream extends FilterOutputStream {
            private FaultHidingOutputStream(OutputStream out) {
                super(out);
            }

            @Override
            public void write(int oneByte) {
                try {
                    out.write(oneByte);
                } catch (Throwable e) {
                    hasErrors = true;
                }
            }

            @Override
            public void write(byte[] buffer, int offset, int length) {
                try {
                    out.write(buffer, offset, length);
                    out.flush();
                } catch (Throwable e) {
                    hasErrors = true;
                }
            }

            @Override
            public void close() {
                try {
                    out.close();
                } catch (Throwable e) {
                    hasErrors = true;
                }
            }

            @Override
            public void flush() {
                try {
                    out.flush();
                } catch (Throwable e) {
                    hasErrors = true;
                }
            }
        }
    }

    private final class Entry {
        private final String diskKey;

        private long expiryTimestamp = Long.MAX_VALUE;

        /**
         * Lengths of this entry's files.
         */
        private final long[] lengths;

        /**
         * True if this entry has ever been published.
         */
        private boolean readable;

        /**
         * The ongoing edit or null if this entry is not being edited.
         */
        private Editor currentEditor;

        /**
         * The sequence number of the most recently committed edit to this entry.
         */
        private long sequenceNumber;

        private Entry(String diskKey) {
            this.diskKey = diskKey;
            this.lengths = new long[valueCount];
        }

        public String getLengths() throws IOException {
            StringBuilder result = new StringBuilder();
            for (long size : lengths) {
                result.append(" ").append(size);
            }
            return result.toString();
        }

        /**
         * Set lengths using decimal numbers like "10123".
         */
        private void setLengths(String[] strings, int startIndex) throws IOException {
            if ((strings.length - startIndex) != valueCount) {
                throw invalidLengths(strings);
            }

            try {
                for (int i = 0; i < valueCount; i++) {
                    lengths[i] = Long.parseLong(strings[i + startIndex]);
                }
            } catch (NumberFormatException e) {
                throw invalidLengths(strings);
            }
        }

        private IOException invalidLengths(String[] strings) throws IOException {
            throw new IOException("unexpected journal line: " + java.util.Arrays.toString(strings));
        }

        public File getCleanFile(int i) {
            return new File(directory, diskKey + "." + i);
        }

        public File getDirtyFile(int i) {
            return new File(directory, diskKey + "." + i + ".tmp");
        }
    }

    /////////////////////////////////////// utils //////////////////////////////////////////////////////////////////////
    private static String readFully(Reader reader) throws IOException {
        StringWriter writer = null;
        try {
            writer = new StringWriter();
            char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, count);
            }
            return writer.toString();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(writer);
        }
    }

    /**
     * Deletes the contents of {@code dir}. Throws an IOException if any file
     * could not be deleted, or if {@code dir} is not a readable directory.
     */
    private static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IOException("not a readable directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (file.exists() && !file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }

    /////////////////////////////////////// StrictLineReader /////////////////////////////////////////////
    private class StrictLineReader implements Closeable {
        private static final byte CR = (byte) '\r';
        private static final byte LF = (byte) '\n';

        private final InputStream in;
        private final Charset charset = Charset.forName(HTTP.US_ASCII);

        /*
         * Buffered data is stored in {@code buf}. As long as no exception occurs, 0 <= pos <= end
         * and the data in the range [pos, end) is buffered for reading. At end of input, if there is
         * an unterminated line, we set end == -1, otherwise end == pos. If the underlying
         * {@code InputStream} throws an {@code IOException}, end may remain as either pos or -1.
         */
        private byte[] buf;
        private int pos;
        private int end;

        /**
         * Constructs a new {@code LineReader} with the specified charset and the default capacity.
         *
         * @param in the {@code InputStream} to read data from.
         * @throws NullPointerException     if {@code in} or {@code charset} is null.
         * @throws IllegalArgumentException if the specified charset is not supported.
         */
        public StrictLineReader(InputStream in) {
            this(in, 8192);
        }

        /**
         * Constructs a new {@code LineReader} with the specified capacity and charset.
         *
         * @param in       the {@code InputStream} to read data from.
         * @param capacity the capacity of the buffer.
         * @throws NullPointerException     if {@code in} or {@code charset} is null.
         * @throws IllegalArgumentException if {@code capacity} is negative or zero
         *                                  or the specified charset is not supported.
         */
        public StrictLineReader(InputStream in, int capacity) {
            if (in == null) {
                throw new NullPointerException();
            }
            if (capacity < 0) {
                throw new IllegalArgumentException("capacity <= 0");
            }

            this.in = in;
            buf = new byte[capacity];
        }

        /**
         * Closes the reader by closing the underlying {@code InputStream} and
         * marking this reader as closed.
         *
         * @throws IOException for errors when closing the underlying {@code InputStream}.
         */
        @Override
        public void close() throws IOException {
            synchronized (in) {
                if (buf != null) {
                    buf = null;
                    in.close();
                }
            }
        }

        /**
         * Reads the next line. A line ends with {@code "\n"} or {@code "\r\n"},
         * this end of line marker is not included in the result.
         *
         * @return the next line from the input.
         * @throws IOException  for underlying {@code InputStream} errors.
         * @throws EOFException for the end of source stream.
         */
        public String readLine() throws IOException {
            synchronized (in) {
                if (buf == null) {
                    throw new IOException("LineReader is closed");
                }

                // Read more data if we are at the end of the buffered data.
                // Though it's an error to read after an exception, we will let {@code fillBuf()}
                // throw again if that happens; thus we need to handle end == -1 as well as end == pos.
                if (pos >= end) {
                    fillBuf();
                }
                // Try to find LF in the buffered data and return the line if successful.
                for (int i = pos; i != end; ++i) {
                    if (buf[i] == LF) {
                        int lineEnd = (i != pos && buf[i - 1] == CR) ? i - 1 : i;
                        String res = new String(buf, pos, lineEnd - pos, charset.name());
                        pos = i + 1;
                        return res;
                    }
                }

                // Let's anticipate up to 80 characters on top of those already read.
                ByteArrayOutputStream out = new ByteArrayOutputStream(end - pos + 80) {
                    @Override
                    public String toString() {
                        int length = (count > 0 && buf[count - 1] == CR) ? count - 1 : count;
                        try {
                            return new String(buf, 0, length, charset.name());
                        } catch (UnsupportedEncodingException e) {
                            throw new AssertionError(e); // Since we control the charset this will never happen.
                        }
                    }
                };

                while (true) {
                    out.write(buf, pos, end - pos);
                    // Mark unterminated line in case fillBuf throws EOFException or IOException.
                    end = -1;
                    fillBuf();
                    // Try to find LF in the buffered data and return the line if successful.
                    for (int i = pos; i != end; ++i) {
                        if (buf[i] == LF) {
                            if (i != pos) {
                                out.write(buf, pos, i - pos);
                            }
                            out.flush();
                            pos = i + 1;
                            return out.toString();
                        }
                    }
                }
            }
        }

        /**
         * Reads new input data into the buffer. Call only with pos == end or end == -1,
         * depending on the desired outcome if the function throws.
         */
        private void fillBuf() throws IOException {
            int result = in.read(buf, 0, buf.length);
            if (result == -1) {
                throw new EOFException();
            }
            pos = 0;
            end = result;
        }
    }

    private FileNameGenerator fileNameGenerator = new MD5FileNameGenerator();

    public FileNameGenerator getFileNameGenerator() {
        return fileNameGenerator;
    }

    public void setFileNameGenerator(FileNameGenerator fileNameGenerator) {
        if (fileNameGenerator != null) {
            this.fileNameGenerator = fileNameGenerator;
        }
    }
}