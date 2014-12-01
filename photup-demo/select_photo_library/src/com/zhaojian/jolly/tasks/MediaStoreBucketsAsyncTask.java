/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhaojian.jolly.tasks;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.zhaojian.jolly.model.MediaStoreBucket;
import com.zhaojian.jolly.utils.MediaStoreCursorHelper;

public class MediaStoreBucketsAsyncTask extends AsyncTask<Void, Void, List<MediaStoreBucket>> {

    public static interface MediaStoreBucketsResultListener {

        public void onBucketsLoaded(List<MediaStoreBucket> buckets);
    }

    private final WeakReference<Context> mContext;
    private final WeakReference<MediaStoreBucketsResultListener> mListener;

    public static void execute(Context context, MediaStoreBucketsResultListener listener) {
        new MediaStoreBucketsAsyncTask(context, listener).execute();
    }

    private MediaStoreBucketsAsyncTask(Context context, MediaStoreBucketsResultListener listener) {
        mContext = new WeakReference<Context>(context);
        mListener = new WeakReference<MediaStoreBucketsResultListener>(listener);
    }

    @Override
    protected List<MediaStoreBucket> doInBackground(Void... params) {
        ArrayList<MediaStoreBucket> result = null;
        Context context = mContext.get();

        if (null != context) {
            // Add 'All Photos' item
            result = new ArrayList<MediaStoreBucket>();
            result.add(MediaStoreBucket.getAllPhotosBucket(context));

            Cursor cursor = MediaStoreCursorHelper.openPhotosCursor(context,
                    MediaStoreCursorHelper.MEDIA_STORE_CONTENT_URI);
            if (null != cursor) {
                MediaStoreCursorHelper.photosCursorToBucketList(cursor, result);
                cursor.close();
            }
        }else{
 
        }

        return result;
    }

    @Override
    protected void onPostExecute(List<MediaStoreBucket> result) {
        super.onPostExecute(result);

        MediaStoreBucketsResultListener listener = mListener.get();
        if (null != listener) {
            listener.onBucketsLoaded(result);
        }
    }

}
