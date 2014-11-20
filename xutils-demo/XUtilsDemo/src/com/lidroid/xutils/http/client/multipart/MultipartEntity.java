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

import com.lidroid.xutils.http.callback.RequestCallBackHandler;
import com.lidroid.xutils.http.client.entity.UploadEntity;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Multipart/form coded HTTP entity consisting of multiple body parts.
 *
 * @since 4.0
 */
public class MultipartEntity implements HttpEntity, UploadEntity {

    private CallBackInfo callBackInfo = new CallBackInfo();

    @Override
    public void setCallBackHandler(RequestCallBackHandler callBackHandler) {
        callBackInfo.callBackHandler = callBackHandler;
    }

    // wyouflf add： upload callBackHandler
    public static class CallBackInfo {
        public final static CallBackInfo DEFAULT = new CallBackInfo();
        public RequestCallBackHandler callBackHandler = null;
        public long totalLength = 0;
        public long pos = 0;

        /**
         * @param forceUpdateUI
         * @return Whether continue.
         */
        public boolean doCallBack(boolean forceUpdateUI) {
            if (callBackHandler != null) {
                return callBackHandler.updateProgress(totalLength, pos, forceUpdateUI);
            }
            return true;
        }
    }

    /**
     * The pool of ASCII chars to be used for generating a multipart boundary.
     */
    private final static char[] MULTIPART_CHARS =
            "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    .toCharArray();

    private final HttpMultipart multipart;
    private Header contentType;

    // @GuardedBy("dirty") // we always read dirty before accessing length
    private long length;
    private volatile boolean dirty; // used to decide whether to recalculate length

    // wyouflf add
    private final String boundary;
    private final Charset charset;

    /**
     * Creates an instance using the specified parameters
     *
     * @param mode     the mode to use, may be {@code null}, in which case {@link HttpMultipartMode#STRICT} is used
     * @param boundary the boundary string, may be {@code null}, in which case {@link #generateBoundary()} is invoked to create the string
     * @param charset  the character set to use, may be {@code null}, in which case {@link MIME#DEFAULT_CHARSET} - i.e. UTF-8 - is used.
     */
    public MultipartEntity(
            HttpMultipartMode mode,
            String boundary,
            Charset charset) {
        super();
        if (boundary == null) {
            boundary = generateBoundary();
        }
        this.boundary = boundary;
        if (mode == null) {
            mode = HttpMultipartMode.STRICT;
        }
        this.charset = charset != null ? charset : MIME.DEFAULT_CHARSET;
        this.multipart = new HttpMultipart(multipartSubtype, this.charset, this.boundary, mode);
        this.contentType = new BasicHeader(
                HTTP.CONTENT_TYPE,
                generateContentType(this.boundary, this.charset));
        this.dirty = true;
    }

    /**
     * Creates an instance using the specified {@link HttpMultipartMode} mode.
     * Boundary and charset are set to {@code null}.
     *
     * @param mode the desired mode
     */
    public MultipartEntity(final HttpMultipartMode mode) {
        this(mode, null, null);
    }

    /**
     * Creates an instance using mode {@link HttpMultipartMode#STRICT}
     */
    public MultipartEntity() {
        this(HttpMultipartMode.STRICT, null, null);
    }

    // wyouflf add
    private String multipartSubtype = "form-data";

    /**
     * @param multipartSubtype default "form-data"
     */
    public void setMultipartSubtype(String multipartSubtype) {
        this.multipartSubtype = multipartSubtype;
        this.multipart.setSubType(multipartSubtype);
        this.contentType = new BasicHeader(
                HTTP.CONTENT_TYPE,
                generateContentType(this.boundary, this.charset));
    }

    protected String generateContentType(
            final String boundary,
            final Charset charset) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("multipart/" + multipartSubtype + "; boundary=");
        buffer.append(boundary);
        /*if (charset != null) {
            buffer.append("; charset=");
            buffer.append(charset.name());
        }*/
        return buffer.toString();
    }

    protected String generateBoundary() {
        StringBuilder buffer = new StringBuilder();
        Random rand = new Random();
        int count = rand.nextInt(11) + 30; // a random size from 30 to 40
        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
    }

    public void addPart(final FormBodyPart bodyPart) {
        this.multipart.addBodyPart(bodyPart);
        this.dirty = true;
    }

    public void addPart(final String name, final ContentBody contentBody) {
        addPart(new FormBodyPart(name, contentBody));
    }

    public void addPart(final String name, final ContentBody contentBody, final String contentDisposition) {
        addPart(new FormBodyPart(name, contentBody, contentDisposition));
    }

    public boolean isRepeatable() {
        for (FormBodyPart part : this.multipart.getBodyParts()) {
            ContentBody body = part.getBody();
            if (body.getContentLength() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isChunked() {
        return !isRepeatable();
    }

    public boolean isStreaming() {
        return !isRepeatable();
    }

    public long getContentLength() {
        if (this.dirty) {
            this.length = this.multipart.getTotalLength();
            this.dirty = false;
        }
        return this.length;
    }

    public Header getContentType() {
        return this.contentType;
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent()
            throws IOException, UnsupportedOperationException {
        if (isStreaming()) {
            throw new UnsupportedOperationException(
                    "Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Multipart form entity does not implement #getContent()");
    }

    public void writeTo(final OutputStream outStream) throws IOException {
        callBackInfo.totalLength = getContentLength();
        this.multipart.writeTo(outStream, callBackInfo);
    }
}
