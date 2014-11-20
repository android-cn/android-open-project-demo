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

package com.lidroid.xutils.bitmap.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

public abstract class BitmapLoadCallBack<T extends View> {

    /**
     * Call back when start loading.
     *
     * @param container
     * @param uri
     * @param config
     */
    public void onPreLoad(T container, String uri, BitmapDisplayConfig config) {
    }

    /**
     * Call back when start loading.
     *
     * @param container
     * @param uri
     * @param config
     */
    public void onLoadStarted(T container, String uri, BitmapDisplayConfig config) {
    }

    /**
     * Call back when loading.
     *
     * @param container
     * @param uri
     * @param config
     * @param total
     * @param current
     */
    public void onLoading(T container, String uri, BitmapDisplayConfig config, long total, long current) {
    }

    /**
     * Call back when bitmap has loaded.
     *
     * @param container
     * @param uri
     * @param bitmap
     * @param config
     */
    public abstract void onLoadCompleted(T container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from);

    /**
     * Call back when bitmap failed to load.
     *
     * @param container
     * @param uri
     * @param drawable
     */
    public abstract void onLoadFailed(T container, String uri, Drawable drawable);

    private BitmapSetter<T> bitmapSetter;

    public void setBitmapSetter(BitmapSetter<T> bitmapSetter) {
        this.bitmapSetter = bitmapSetter;
    }

    public void setBitmap(T container, Bitmap bitmap) {
        if (bitmapSetter != null) {
            bitmapSetter.setBitmap(container, bitmap);
        } else if (container instanceof ImageView) {
            ((ImageView) container).setImageBitmap(bitmap);
        } else {
            container.setBackgroundDrawable(new BitmapDrawable(container.getResources(), bitmap));
        }
    }

    public void setDrawable(T container, Drawable drawable) {
        if (bitmapSetter != null) {
            bitmapSetter.setDrawable(container, drawable);
        } else if (container instanceof ImageView) {
            ((ImageView) container).setImageDrawable(drawable);
        } else {
            container.setBackgroundDrawable(drawable);
        }
    }

    public Drawable getDrawable(T container) {
        if (bitmapSetter != null) {
            return bitmapSetter.getDrawable(container);
        } else if (container instanceof ImageView) {
            return ((ImageView) container).getDrawable();
        } else {
            return container.getBackground();
        }
    }
}
