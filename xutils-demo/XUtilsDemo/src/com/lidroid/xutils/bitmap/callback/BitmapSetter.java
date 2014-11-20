package com.lidroid.xutils.bitmap.callback;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Author: wyouflf
 * Date: 13-11-1
 * Time: 下午11:05
 */
public interface BitmapSetter<T extends View> {
    void setBitmap(T container, Bitmap bitmap);

    void setDrawable(T container, Drawable drawable);

    Drawable getDrawable(T container);
}
