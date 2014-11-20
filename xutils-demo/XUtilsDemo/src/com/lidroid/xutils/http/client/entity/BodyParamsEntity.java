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

package com.lidroid.xutils.http.client.entity;

import com.lidroid.xutils.http.client.util.URLEncodedUtils;
import com.lidroid.xutils.util.LogUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: wyouflf
 * Date: 13-7-26
 * Time: 下午4:21
 */
public class BodyParamsEntity extends AbstractHttpEntity implements Cloneable {

    protected byte[] content;

    private boolean dirty = true;

    private String charset = HTTP.UTF_8;

    private List<NameValuePair> params;

    public BodyParamsEntity() {
        this((String) null);
    }

    public BodyParamsEntity(String charset) {
        super();
        if (charset != null) {
            this.charset = charset;
        }
        setContentType(URLEncodedUtils.CONTENT_TYPE);
        params = new ArrayList<NameValuePair>();
    }

    public BodyParamsEntity(List<NameValuePair> params) {
        this(params, null);
    }

    public BodyParamsEntity(List<NameValuePair> params, String charset) {
        super();
        if (charset != null) {
            this.charset = charset;
        }
        setContentType(URLEncodedUtils.CONTENT_TYPE);
        this.params = params;
        refreshContent();
    }

    public BodyParamsEntity addParameter(String name, String value) {
        this.params.add(new BasicNameValuePair(name, value));
        this.dirty = true;
        return this;
    }

    public BodyParamsEntity addParams(List<NameValuePair> params) {
        this.params.addAll(params);
        this.dirty = true;
        return this;
    }

    private void refreshContent() {
        if (dirty) {
            try {
                this.content = URLEncodedUtils.format(params, charset).getBytes(charset);
            } catch (UnsupportedEncodingException e) {
                LogUtils.e(e.getMessage(), e);
            }
            dirty = false;
        }
    }

    public boolean isRepeatable() {
        return true;
    }

    public long getContentLength() {
        refreshContent();
        return this.content.length;
    }

    public InputStream getContent() throws IOException {
        refreshContent();
        return new ByteArrayInputStream(this.content);
    }

    public void writeTo(final OutputStream outStream) throws IOException {
        if (outStream == null) {
            throw new IllegalArgumentException("Output stream may not be null");
        }
        refreshContent();
        outStream.write(this.content);
        outStream.flush();
    }

    /**
     * Tells that this entity is not streaming.
     *
     * @return <code>false</code>
     */
    public boolean isStreaming() {
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
