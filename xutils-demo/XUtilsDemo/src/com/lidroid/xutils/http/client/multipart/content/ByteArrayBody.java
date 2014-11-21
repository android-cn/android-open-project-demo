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

import java.io.IOException;
import java.io.OutputStream;

/**
 * Body part that is built using a byte array containing a file.
 *
 * @since 4.1
 */
public class ByteArrayBody extends AbstractContentBody {

    /**
     * The contents of the file contained in this part.
     */
    private final byte[] data;

    /**
     * The name of the file contained in this part.
     */
    private final String filename;

    /**
     * Creates a new ByteArrayBody.
     *
     * @param data     The contents of the file contained in this part.
     * @param mimeType The mime type of the file contained in this part.
     * @param filename The name of the file contained in this part.
     */
    public ByteArrayBody(final byte[] data, final String mimeType, final String filename) {
        super(mimeType);
        if (data == null) {
            throw new IllegalArgumentException("byte[] may not be null");
        }
        this.data = data;
        this.filename = filename;
    }

    /**
     * Creates a new ByteArrayBody.
     *
     * @param data     The contents of the file contained in this part.
     * @param filename The name of the file contained in this part.
     */
    public ByteArrayBody(final byte[] data, final String filename) {
        this(data, "application/octet-stream", filename);
    }

    public String getFilename() {
        return filename;
    }

    public void writeTo(final OutputStream out) throws IOException {
        out.write(data);
        callBackInfo.pos += data.length;
        callBackInfo.doCallBack(false);
    }

    public String getCharset() {
        return null;
    }

    public String getTransferEncoding() {
        return MIME.ENC_BINARY;
    }

    public long getContentLength() {
        return data.length;
    }

}
