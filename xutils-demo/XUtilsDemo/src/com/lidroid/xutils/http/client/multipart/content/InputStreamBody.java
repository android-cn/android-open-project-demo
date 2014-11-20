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

package com.lidroid.xutils.http.client.multipart.content;

import com.lidroid.xutils.http.client.multipart.MIME;
import com.lidroid.xutils.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;

/**
 * @since 4.0
 */
public class InputStreamBody extends AbstractContentBody {

    private final InputStream in;
    private final String filename;
    private long length;

    public InputStreamBody(final InputStream in, long length, final String filename, final String mimeType) {
        super(mimeType);
        if (in == null) {
            throw new IllegalArgumentException("Input stream may not be null");
        }
        this.in = in;
        this.filename = filename;
        this.length = length;
    }

    public InputStreamBody(final InputStream in, long length, final String filename) {
        this(in, length, filename, "application/octet-stream");
    }

    public InputStreamBody(final InputStream in, long length) {
        this(in, length, "no_name", "application/octet-stream");
    }

    public InputStream getInputStream() {
        return this.in;
    }

    public void writeTo(final OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        try {
            byte[] tmp = new byte[4096];
            int l;
            while ((l = this.in.read(tmp)) != -1) {
                out.write(tmp, 0, l);
                callBackInfo.pos += l;
                if (!callBackInfo.doCallBack(false)) {
                    throw new InterruptedIOException("cancel");
                }
            }
            out.flush();
        } finally {
            IOUtils.closeQuietly(this.in);
        }
    }

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    public String getCharset() {
        return null;
    }

    public long getContentLength() {
        return this.length;
    }

    public String getFilename() {
        return this.filename;
    }

}
