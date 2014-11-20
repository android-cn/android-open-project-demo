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

package com.lidroid.xutils.bitmap.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.lidroid.xutils.util.LogUtils;

import java.io.FileDescriptor;

public class BitmapDecoder {

    private static final Object lock = new Object();

    private BitmapDecoder() {
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, BitmapSize maxSize, Bitmap.Config config) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeResource(res, resId, options);
            options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
            options.inJustDecodeBounds = false;
            if (config != null) {
                options.inPreferredConfig = config;
            }
            try {
                return BitmapFactory.decodeResource(res, resId, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename, BitmapSize maxSize, Bitmap.Config config) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeFile(filename, options);
            options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
            options.inJustDecodeBounds = false;
            if (config != null) {
                options.inPreferredConfig = config;
            }
            try {
                return BitmapFactory.decodeFile(filename, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, BitmapSize maxSize, Bitmap.Config config) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
            options.inJustDecodeBounds = false;
            if (config != null) {
                options.inPreferredConfig = config;
            }
            try {
                return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] data, BitmapSize maxSize, Bitmap.Config config) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPurgeable = true;
            options.inInputShareable = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, options);
            options.inSampleSize = calculateInSampleSize(options, maxSize.getWidth(), maxSize.getHeight());
            options.inJustDecodeBounds = false;
            if (config != null) {
                options.inPreferredConfig = config;
            }
            try {
                return BitmapFactory.decodeByteArray(data, 0, data.length, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static Bitmap decodeResource(Resources res, int resId) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inInputShareable = true;
            try {
                return BitmapFactory.decodeResource(res, resId, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static Bitmap decodeFile(String filename) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inInputShareable = true;
            try {
                return BitmapFactory.decodeFile(filename, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static Bitmap decodeFileDescriptor(FileDescriptor fileDescriptor) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inInputShareable = true;
            try {
                return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static Bitmap decodeByteArray(byte[] data) {
        synchronized (lock) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPurgeable = true;
            options.inInputShareable = true;
            try {
                return BitmapFactory.decodeByteArray(data, 0, data.length, options);
            } catch (Throwable e) {
                LogUtils.e(e.getMessage(), e);
                return null;
            }
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int maxWidth, int maxHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > maxWidth || height > maxHeight) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) maxHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) maxWidth);
            }

            final float totalPixels = width * height;

            final float maxTotalPixels = maxWidth * maxHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > maxTotalPixels) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
}
