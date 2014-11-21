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

package com.lidroid.xutils.http.callback;


import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

public abstract class RequestCallBack<T> {

    private static final int DEFAULT_RATE = 1000;

    private static final int MIN_RATE = 200;

    private String requestUrl;

    protected Object userTag;

    public RequestCallBack() {
        this.rate = DEFAULT_RATE;
    }

    public RequestCallBack(int rate) {
        this.rate = rate;
    }

    public RequestCallBack(Object userTag) {
        this.rate = DEFAULT_RATE;
        this.userTag = userTag;
    }

    public RequestCallBack(int rate, Object userTag) {
        this.rate = rate;
        this.userTag = userTag;
    }

    private int rate;

    public final int getRate() {
        if (rate < MIN_RATE) {
            return MIN_RATE;
        }
        return rate;
    }

    public final void setRate(int rate) {
        this.rate = rate;
    }

    public Object getUserTag() {
        return userTag;
    }

    public void setUserTag(Object userTag) {
        this.userTag = userTag;
    }

    public final String getRequestUrl() {
        return requestUrl;
    }

    public final void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void onStart() {
    }

    public void onCancelled() {
    }

    public void onLoading(long total, long current, boolean isUploading) {
    }

    public abstract void onSuccess(ResponseInfo<T> responseInfo);

    public abstract void onFailure(HttpException error, String msg);
}
