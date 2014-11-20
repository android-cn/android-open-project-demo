package com.lidroid.xutils.http;

import org.apache.http.*;

import java.util.Locale;

/**
 * Author: wyouflf
 * Date: 13-10-26
 * Time: 下午3:20
 */
public final class ResponseInfo<T> {

    private final HttpResponse response;
    public T result;
    public final boolean resultFormCache;

    public final Locale locale;

    // status line
    public final int statusCode;
    public final ProtocolVersion protocolVersion;
    public final String reasonPhrase;

    // entity
    public final long contentLength;
    public final Header contentType;
    public final Header contentEncoding;

    public Header[] getAllHeaders() {
        if (response == null) return null;
        return response.getAllHeaders();
    }

    public Header[] getHeaders(String name) {
        if (response == null) return null;
        return response.getHeaders(name);
    }

    public Header getFirstHeader(String name) {
        if (response == null) return null;
        return response.getFirstHeader(name);
    }

    public Header getLastHeader(String name) {
        if (response == null) return null;
        return response.getLastHeader(name);
    }

    public ResponseInfo(final HttpResponse response, T result, boolean resultFormCache) {
        this.response = response;
        this.result = result;
        this.resultFormCache = resultFormCache;

        if (response != null) {
            locale = response.getLocale();

            // status line
            StatusLine statusLine = response.getStatusLine();
            if (statusLine != null) {
                statusCode = statusLine.getStatusCode();
                protocolVersion = statusLine.getProtocolVersion();
                reasonPhrase = statusLine.getReasonPhrase();
            } else {
                statusCode = 0;
                protocolVersion = null;
                reasonPhrase = null;
            }

            // entity
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                contentLength = entity.getContentLength();
                contentType = entity.getContentType();
                contentEncoding = entity.getContentEncoding();
            } else {
                contentLength = 0;
                contentType = null;
                contentEncoding = null;
            }
        } else {
            locale = null;

            // status line
            statusCode = 0;
            protocolVersion = null;
            reasonPhrase = null;

            // entity
            contentLength = 0;
            contentType = null;
            contentEncoding = null;
        }
    }
}
