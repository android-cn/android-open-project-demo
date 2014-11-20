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

package com.lidroid.xutils.http;

import android.text.TextUtils;
import com.lidroid.xutils.http.client.entity.BodyParamsEntity;
import com.lidroid.xutils.http.client.multipart.HttpMultipartMode;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.ContentBody;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.InputStreamBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.task.Priority;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class RequestParams {

    private String charset = HTTP.UTF_8;

    private List<HeaderItem> headers;
    private List<NameValuePair> queryStringParams;
    private HttpEntity bodyEntity;
    private List<NameValuePair> bodyParams;
    private HashMap<String, ContentBody> fileParams;

    private Priority priority;

    public RequestParams() {
    }

    public RequestParams(String charset) {
        if (!TextUtils.isEmpty(charset)) {
            this.charset = charset;
        }
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getCharset() {
        return charset;
    }

    public void setContentType(String contentType) {
        this.setHeader("Content-Type", contentType);
    }

    /**
     * Adds a header to this message. The header will be appended to the end of the list.
     *
     * @param header
     */
    public void addHeader(Header header) {
        if (this.headers == null) {
            this.headers = new ArrayList<HeaderItem>();
        }
        this.headers.add(new HeaderItem(header));
    }

    /**
     * Adds a header to this message. The header will be appended to the end of the list.
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        if (this.headers == null) {
            this.headers = new ArrayList<HeaderItem>();
        }
        this.headers.add(new HeaderItem(name, value));
    }

    /**
     * Adds all the headers to this message.
     *
     * @param headers
     */
    public void addHeaders(List<Header> headers) {
        if (this.headers == null) {
            this.headers = new ArrayList<HeaderItem>();
        }
        for (Header header : headers) {
            this.headers.add(new HeaderItem(header));
        }
    }

    /**
     * Overwrites the first header with the same name.
     * The new header will be appended to the end of the list, if no header with the given name can be found.
     *
     * @param header
     */
    public void setHeader(Header header) {
        if (this.headers == null) {
            this.headers = new ArrayList<HeaderItem>();
        }
        this.headers.add(new HeaderItem(header, true));
    }

    /**
     * Overwrites the first header with the same name.
     * The new header will be appended to the end of the list, if no header with the given name can be found.
     *
     * @param name
     * @param value
     */
    public void setHeader(String name, String value) {
        if (this.headers == null) {
            this.headers = new ArrayList<HeaderItem>();
        }
        this.headers.add(new HeaderItem(name, value, true));
    }

    /**
     * Overwrites all the headers in the message.
     *
     * @param headers
     */
    public void setHeaders(List<Header> headers) {
        if (this.headers == null) {
            this.headers = new ArrayList<HeaderItem>();
        }
        for (Header header : headers) {
            this.headers.add(new HeaderItem(header, true));
        }
    }

    public void addQueryStringParameter(String name, String value) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<NameValuePair>();
        }
        queryStringParams.add(new BasicNameValuePair(name, value));
    }

    public void addQueryStringParameter(NameValuePair nameValuePair) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<NameValuePair>();
        }
        queryStringParams.add(nameValuePair);
    }

    public void addQueryStringParameter(List<NameValuePair> nameValuePairs) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<NameValuePair>();
        }
        if (nameValuePairs != null && nameValuePairs.size() > 0) {
            for (NameValuePair pair : nameValuePairs) {
                queryStringParams.add(pair);
            }
        }
    }

    public void addBodyParameter(String name, String value) {
        if (bodyParams == null) {
            bodyParams = new ArrayList<NameValuePair>();
        }
        bodyParams.add(new BasicNameValuePair(name, value));
    }

    public void addBodyParameter(NameValuePair nameValuePair) {
        if (bodyParams == null) {
            bodyParams = new ArrayList<NameValuePair>();
        }
        bodyParams.add(nameValuePair);
    }

    public void addBodyParameter(List<NameValuePair> nameValuePairs) {
        if (bodyParams == null) {
            bodyParams = new ArrayList<NameValuePair>();
        }
        if (nameValuePairs != null && nameValuePairs.size() > 0) {
            for (NameValuePair pair : nameValuePairs) {
                bodyParams.add(pair);
            }
        }
    }

    public void addBodyParameter(String key, File file) {
        if (fileParams == null) {
            fileParams = new HashMap<String, ContentBody>();
        }
        fileParams.put(key, new FileBody(file));
    }

    public void addBodyParameter(String key, File file, String mimeType) {
        if (fileParams == null) {
            fileParams = new HashMap<String, ContentBody>();
        }
        fileParams.put(key, new FileBody(file, mimeType));
    }

    public void addBodyParameter(String key, File file, String mimeType, String charset) {
        if (fileParams == null) {
            fileParams = new HashMap<String, ContentBody>();
        }
        fileParams.put(key, new FileBody(file, mimeType, charset));
    }

    public void addBodyParameter(String key, File file, String fileName, String mimeType, String charset) {
        if (fileParams == null) {
            fileParams = new HashMap<String, ContentBody>();
        }
        fileParams.put(key, new FileBody(file, fileName, mimeType, charset));
    }

    public void addBodyParameter(String key, InputStream stream, long length) {
        if (fileParams == null) {
            fileParams = new HashMap<String, ContentBody>();
        }
        fileParams.put(key, new InputStreamBody(stream, length));
    }

    public void addBodyParameter(String key, InputStream stream, long length, String fileName) {
        if (fileParams == null) {
            fileParams = new HashMap<String, ContentBody>();
        }
        fileParams.put(key, new InputStreamBody(stream, length, fileName));
    }

    public void addBodyParameter(String key, InputStream stream, long length, String fileName, String mimeType) {
        if (fileParams == null) {
            fileParams = new HashMap<String, ContentBody>();
        }
        fileParams.put(key, new InputStreamBody(stream, length, fileName, mimeType));
    }

    public void setBodyEntity(HttpEntity bodyEntity) {
        this.bodyEntity = bodyEntity;
        if (bodyParams != null) {
            bodyParams.clear();
            bodyParams = null;
        }
        if (fileParams != null) {
            fileParams.clear();
            fileParams = null;
        }
    }

    /**
     * Returns an HttpEntity containing all request parameters
     */
    public HttpEntity getEntity() {

        if (bodyEntity != null) {
            return bodyEntity;
        }

        HttpEntity result = null;

        if (fileParams != null && !fileParams.isEmpty()) {

            MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.STRICT, null, Charset.forName(charset));

            if (bodyParams != null && !bodyParams.isEmpty()) {
                for (NameValuePair param : bodyParams) {
                    try {
                        multipartEntity.addPart(param.getName(), new StringBody(param.getValue()));
                    } catch (UnsupportedEncodingException e) {
                        LogUtils.e(e.getMessage(), e);
                    }
                }
            }

            for (ConcurrentHashMap.Entry<String, ContentBody> entry : fileParams.entrySet()) {
                multipartEntity.addPart(entry.getKey(), entry.getValue());
            }

            result = multipartEntity;
        } else if (bodyParams != null && !bodyParams.isEmpty()) {
            result = new BodyParamsEntity(bodyParams, charset);
        }

        return result;
    }

    public List<NameValuePair> getQueryStringParams() {
        return queryStringParams;
    }

    public List<HeaderItem> getHeaders() {
        return headers;
    }

    public class HeaderItem {
        public final boolean overwrite;
        public final Header header;

        public HeaderItem(Header header) {
            this.overwrite = false;
            this.header = header;
        }

        public HeaderItem(Header header, boolean overwrite) {
            this.overwrite = overwrite;
            this.header = header;
        }

        public HeaderItem(String name, String value) {
            this.overwrite = false;
            this.header = new BasicHeader(name, value);
        }

        public HeaderItem(String name, String value, boolean overwrite) {
            this.overwrite = overwrite;
            this.header = new BasicHeader(name, value);
        }
    }
}