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

package com.lidroid.xutils.http.client.multipart;

import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import org.apache.http.util.ByteArrayBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * HttpMultipart represents a collection of MIME multipart encoded content bodies. This class is
 * capable of operating either in the strict (RFC 822, RFC 2045, RFC 2046 compliant) or
 * the browser compatible modes.
 *
 * @since 4.0
 */
class HttpMultipart {

    private static ByteArrayBuffer encode(
            final Charset charset, final String string) {
        ByteBuffer encoded = charset.encode(CharBuffer.wrap(string));
        ByteArrayBuffer bab = new ByteArrayBuffer(encoded.remaining());
        bab.append(encoded.array(), encoded.position(), encoded.remaining());
        return bab;
    }

    private static void writeBytes(
            final ByteArrayBuffer b, final OutputStream out) throws IOException {
        out.write(b.buffer(), 0, b.length());
        out.flush();
    }

    private static void writeBytes(
            final String s, final Charset charset, final OutputStream out) throws IOException {
        ByteArrayBuffer b = encode(charset, s);
        writeBytes(b, out);
    }

    private static void writeBytes(
            final String s, final OutputStream out) throws IOException {
        ByteArrayBuffer b = encode(MIME.DEFAULT_CHARSET, s);
        writeBytes(b, out);
    }

    private static void writeField(
            final MinimalField field, final OutputStream out) throws IOException {
        writeBytes(field.getName(), out);
        writeBytes(FIELD_SEP, out);
        writeBytes(field.getBody(), out);
        writeBytes(CR_LF, out);
    }

    private static void writeField(
            final MinimalField field, final Charset charset, final OutputStream out) throws IOException {
        writeBytes(field.getName(), charset, out);
        writeBytes(FIELD_SEP, out);
        writeBytes(field.getBody(), charset, out);
        writeBytes(CR_LF, out);
    }

    private static final ByteArrayBuffer FIELD_SEP = encode(MIME.DEFAULT_CHARSET, ": ");
    private static final ByteArrayBuffer CR_LF = encode(MIME.DEFAULT_CHARSET, "\r\n");
    private static final ByteArrayBuffer TWO_DASHES = encode(MIME.DEFAULT_CHARSET, "--");


    private String subType;
    private final Charset charset;
    private final String boundary;
    private final List<FormBodyPart> parts;

    private final HttpMultipartMode mode;

    /**
     * Creates an instance with the specified settings.
     *
     * @param subType  mime subtype - must not be {@code null}
     * @param charset  the character set to use. May be {@code null}, in which case {@link MIME#DEFAULT_CHARSET} - i.e. UTF-8 - is used.
     * @param boundary to use  - must not be {@code null}
     * @param mode     the mode to use
     * @throws IllegalArgumentException if charset is null or boundary is null
     */
    public HttpMultipart(final String subType, final Charset charset, final String boundary, HttpMultipartMode mode) {
        super();
        if (subType == null) {
            throw new IllegalArgumentException("Multipart subtype may not be null");
        }
        if (boundary == null) {
            throw new IllegalArgumentException("Multipart boundary may not be null");
        }
        this.subType = subType;
        this.charset = charset != null ? charset : MIME.DEFAULT_CHARSET;
        this.boundary = boundary;
        this.parts = new ArrayList<FormBodyPart>();
        this.mode = mode;
    }

    /**
     * Creates an instance with the specified settings.
     * Mode is set to {@link HttpMultipartMode#STRICT}
     *
     * @param subType  mime subtype - must not be {@code null}
     * @param charset  the character set to use. May be {@code null}, in which case {@link MIME#DEFAULT_CHARSET} - i.e. UTF-8 - is used.
     * @param boundary to use  - must not be {@code null}
     * @throws IllegalArgumentException if charset is null or boundary is null
     */
    public HttpMultipart(final String subType, final Charset charset, final String boundary) {
        this(subType, charset, boundary, HttpMultipartMode.STRICT);
    }

    public HttpMultipart(final String subType, final String boundary) {
        this(subType, null, boundary);
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getSubType() {
        return this.subType;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public HttpMultipartMode getMode() {
        return this.mode;
    }

    public List<FormBodyPart> getBodyParts() {
        return this.parts;
    }

    public void addBodyPart(final FormBodyPart part) {
        if (part == null) {
            return;
        }
        this.parts.add(part);
    }

    public String getBoundary() {
        return this.boundary;
    }

    private void doWriteTo(
            final HttpMultipartMode mode,
            final OutputStream out,
            boolean writeContent) throws IOException {
        doWriteTo(mode, out, MultipartEntity.CallBackInfo.DEFAULT, writeContent);
    }

    private void doWriteTo(
            final HttpMultipartMode mode,
            final OutputStream out,
            MultipartEntity.CallBackInfo callBackInfo,
            boolean writeContent) throws IOException {

        callBackInfo.pos = 0;

        ByteArrayBuffer boundary = encode(this.charset, getBoundary());
        for (FormBodyPart part : this.parts) {
            if (!callBackInfo.doCallBack(true)) {
                throw new InterruptedIOException("cancel");
            }
            writeBytes(TWO_DASHES, out);
            callBackInfo.pos += TWO_DASHES.length();
            writeBytes(boundary, out);
            callBackInfo.pos += boundary.length();
            writeBytes(CR_LF, out);
            callBackInfo.pos += CR_LF.length();

            MinimalFieldHeader header = part.getHeader();

            switch (mode) {
                case STRICT:
                    for (MinimalField field : header) {
                        writeField(field, out);
                        callBackInfo.pos += encode(MIME.DEFAULT_CHARSET,
                                field.getName() + field.getBody()).length() + FIELD_SEP.length() + CR_LF.length();
                    }
                    break;
                case BROWSER_COMPATIBLE:
                    // Only write Content-Disposition
                    // Use content charset
                    MinimalField cd = header.getField(MIME.CONTENT_DISPOSITION);
                    writeField(cd, this.charset, out);
                    callBackInfo.pos += encode(this.charset,
                            cd.getName() + cd.getBody()).length() + FIELD_SEP.length() + CR_LF.length();
                    String filename = part.getBody().getFilename();
                    if (filename != null) {
                        MinimalField ct = header.getField(MIME.CONTENT_TYPE);
                        writeField(ct, this.charset, out);
                        callBackInfo.pos += encode(this.charset,
                                ct.getName() + ct.getBody()).length() + FIELD_SEP.length() + CR_LF.length();
                    }
                    break;
                default:
                    break;
            }
            writeBytes(CR_LF, out);
            callBackInfo.pos += CR_LF.length();

            if (writeContent) {
                ContentBody body = part.getBody();
                body.setCallBackInfo(callBackInfo);
                body.writeTo(out);
            }
            writeBytes(CR_LF, out);
            callBackInfo.pos += CR_LF.length();
        }
        writeBytes(TWO_DASHES, out);
        callBackInfo.pos += TWO_DASHES.length();
        writeBytes(boundary, out);
        callBackInfo.pos += boundary.length();
        writeBytes(TWO_DASHES, out);
        callBackInfo.pos += TWO_DASHES.length();
        writeBytes(CR_LF, out);
        callBackInfo.pos += CR_LF.length();
        callBackInfo.doCallBack(true);
    }

    /**
     * Writes out the content in the multipart/form encoding. This method
     * produces slightly different formatting depending on its compatibility
     * mode.
     *
     * @see #getMode()
     */
    public void writeTo(final OutputStream out, MultipartEntity.CallBackInfo callBackInfo) throws IOException {
        doWriteTo(this.mode, out, callBackInfo, true);
    }

    /**
     * Determines the total length of the multipart content (content length of
     * individual parts plus that of extra elements required to delimit the parts
     * from one another). If any of the @{link BodyPart}s contained in this object
     * is of a streaming entity of unknown length the total length is also unknown.
     * <p/>
     * This method buffers only a small amount of data in order to determine the
     * total length of the entire entity. The content of individual parts is not
     * buffered.
     *
     * @return total length of the multipart entity if known, <code>-1</code>
     *         otherwise.
     */
    public long getTotalLength() {
        long contentLen = 0;
        for (FormBodyPart part : this.parts) {
            ContentBody body = part.getBody();
            long len = body.getContentLength();
            if (len >= 0) {
                contentLen += len;
            } else {
                return -1;
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            doWriteTo(this.mode, out, false);
            byte[] extra = out.toByteArray();
            return contentLen + extra.length;
        } catch (Throwable ex) {
            // Should never happen
            return -1;
        }
    }

}
