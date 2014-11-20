package com.lidroid.xutils.bitmap.factory;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: wyouflf
 * Date: 14-05-20
 * Time: 下午4:26
 */
public interface BitmapFactory {

    BitmapFactory cloneNew();

    Bitmap createBitmap(Bitmap rawBitmap);
}
