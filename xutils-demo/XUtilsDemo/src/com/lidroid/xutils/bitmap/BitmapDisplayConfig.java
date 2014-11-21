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

package com.lidroid.xutils.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.lidroid.xutils.bitmap.factory.BitmapFactory;
import com.lidroid.xutils.task.Priority;

public class BitmapDisplayConfig {

    private BitmapSize bitmapMaxSize;
    private Animation animation;
    private Drawable loadingDrawable;
    private Drawable loadFailedDrawable;
    private boolean autoRotation = false;
    private boolean showOriginal = false;
    private Bitmap.Config bitmapConfig = Bitmap.Config.RGB_565;
    private BitmapFactory bitmapFactory;

    private Priority priority;

    public BitmapDisplayConfig() {
    }

    public BitmapSize getBitmapMaxSize() {
        return bitmapMaxSize == null ? BitmapSize.ZERO : bitmapMaxSize;
    }

    public void setBitmapMaxSize(BitmapSize bitmapMaxSize) {
        this.bitmapMaxSize = bitmapMaxSize;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Drawable getLoadingDrawable() {
        return loadingDrawable;
    }

    public void setLoadingDrawable(Drawable loadingDrawable) {
        this.loadingDrawable = loadingDrawable;
    }

    public Drawable getLoadFailedDrawable() {
        return loadFailedDrawable;
    }

    public void setLoadFailedDrawable(Drawable loadFailedDrawable) {
        this.loadFailedDrawable = loadFailedDrawable;
    }

    public boolean isAutoRotation() {
        return autoRotation;
    }

    public void setAutoRotation(boolean autoRotation) {
        this.autoRotation = autoRotation;
    }

    public boolean isShowOriginal() {
        return showOriginal;
    }

    public void setShowOriginal(boolean showOriginal) {
        this.showOriginal = showOriginal;
    }

    public Bitmap.Config getBitmapConfig() {
        return bitmapConfig;
    }

    public void setBitmapConfig(Bitmap.Config bitmapConfig) {
        this.bitmapConfig = bitmapConfig;
    }

    public BitmapFactory getBitmapFactory() {
        return bitmapFactory;
    }

    public void setBitmapFactory(BitmapFactory bitmapFactory) {
        this.bitmapFactory = bitmapFactory;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return (isShowOriginal() ? "" : bitmapMaxSize.toString()) +
                (bitmapFactory == null ? "" : bitmapFactory.getClass().getName());
    }

    public BitmapDisplayConfig cloneNew() {
        BitmapDisplayConfig config = new BitmapDisplayConfig();
        config.bitmapMaxSize = this.bitmapMaxSize;
        config.animation = this.animation;
        config.loadingDrawable = this.loadingDrawable;
        config.loadFailedDrawable = this.loadFailedDrawable;
        config.autoRotation = this.autoRotation;
        config.showOriginal = this.showOriginal;
        config.bitmapConfig = this.bitmapConfig;
        config.bitmapFactory = this.bitmapFactory;
        config.priority = this.priority;
        return config;
    }
}
