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
package com.zhaojian.jolly.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.FloatMath;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.zhaojian.jolly.constant.Constants;
import com.zhaojian.select_photo_library.R;

public class Utils {

    public static Bitmap drawViewOntoBitmap(View view) {
        Bitmap image = Bitmap
                .createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(image);
        view.draw(canvas);
        return image;
    }

    public static Animation createScaleAnimation(View view, int parentWidth, int parentHeight,
            int toX, int toY) {
        // Difference in X and Y
        final int diffX = toX - view.getLeft();
        final int diffY = toY - view.getTop();

        // Calculate actual distance using pythagors
        float diffDistance = FloatMath.sqrt((toX * toX) + (toY * toY));
        float parentDistance = FloatMath
                .sqrt((parentWidth * parentWidth) + (parentHeight * parentHeight));

        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f, Animation.ABSOLUTE,
                diffX,
                Animation.ABSOLUTE, diffY);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        scaleAnimation.setDuration(Math.round(diffDistance / parentDistance
                * Constants.SCALE_ANIMATION_DURATION_FULL_DISTANCE));

        return scaleAnimation;
    }

    // And to convert the image URI to the direct file system path of the image
    // file
    public static String getPathFromContentUri(ContentResolver cr, Uri contentUri) {

        String returnValue = null;

        if (ContentResolver.SCHEME_CONTENT.equals(contentUri.getScheme())) {
            // can post image
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = cr.query(contentUri, proj, null, null, null);

            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    returnValue = cursor
                            .getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
        } else if (ContentResolver.SCHEME_FILE.equals(contentUri.getScheme())) {
            returnValue = contentUri.getPath();
        }

        return returnValue;
    }

    public static int getOrientationFromContentUri(ContentResolver cr, Uri contentUri) {
        int returnValue = 0;

        if (ContentResolver.SCHEME_CONTENT.equals(contentUri.getScheme())) {
            // can post image
            String[] proj = {MediaStore.Images.Media.ORIENTATION};
            Cursor cursor = cr.query(contentUri, proj, null, null, null);

            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    returnValue = cursor.getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION));
                }
                cursor.close();
            }
        } else if (ContentResolver.SCHEME_FILE.equals(contentUri.getScheme())) {
            returnValue = MediaUtils.getExifOrientation(contentUri.getPath());
        }

        return returnValue;
    }

    public static Bitmap decodeImage(final ContentResolver resolver, final Uri uri,
            final int MAX_DIM)
            throws FileNotFoundException {

    	 // Get original dimensions
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(resolver.openInputStream(uri), null, o);
        } catch (SecurityException se) {
            se.printStackTrace();
            return null;
        }

        final int origWidth = o.outWidth;
        final int origHeight = o.outHeight;

        // Holds returned bitmap
        Bitmap bitmap;

        o.inJustDecodeBounds = false;
        o.inScaled = false;
        o.inPurgeable = true;
        o.inInputShareable = true;
        o.inDither = true;
        o.inPreferredConfig = Bitmap.Config.RGB_565;

        if (origWidth > MAX_DIM || origHeight > MAX_DIM) {
            int k = 1;
            int tmpHeight = origHeight, tmpWidth = origWidth;
            while ((tmpWidth / 2) >= MAX_DIM || (tmpHeight / 2) >= MAX_DIM) {
                tmpWidth /= 2;
                tmpHeight /= 2;
                k *= 2;
            }
            o.inSampleSize = k;

            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, o);
        } else {
            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri), null, o);
        }

        if (null != bitmap) {
        }

        return bitmap;
    }

    public static boolean hasCamera(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }

    public static File getCameraPhotoFile() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(dir, "photup_" + System.currentTimeMillis() + ".jpg");
    }

    public static Bitmap rotate(Bitmap original, final int angle) {
        if ((angle % 360) == 0) {
            return original;
        }

        final boolean dimensionsChanged = angle == 90 || angle == 270;
        final int oldWidth = original.getWidth();
        final int oldHeight = original.getHeight();
        final int newWidth = dimensionsChanged ? oldHeight : oldWidth;
        final int newHeight = dimensionsChanged ? oldWidth : oldHeight;

        Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, original.getConfig());
        Canvas canvas = new Canvas(bitmap);

        Matrix matrix = new Matrix();
        matrix.preTranslate((newWidth - oldWidth) / 2f, (newHeight - oldHeight) / 2f);
        matrix.postRotate(angle, bitmap.getWidth() / 2f, bitmap.getHeight() / 2);
        canvas.drawBitmap(original, matrix, null);

        original.recycle();

        return bitmap;
    }

    public static boolean newerThan(long compareTime, int threshold) {
        return compareTime > (System.currentTimeMillis() - threshold);
    }

    public static String formatDistance(final int distance) {
        if (distance < 1000) {
            return distance + "m";
        } else {
            return String.format("%.2fkm", distance / 1000f);
        }
    }

    public static void scanMediaJpegFile(final Context context, final File file,
            final OnScanCompletedListener listener) {
        MediaScannerConnection
                .scanFile(context, new String[]{file.getAbsolutePath()}, new String[]{"image/jpg"},
                        listener);
    }

    public static int getSpinnerItemResId() {
        if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
            return android.R.layout.simple_spinner_item;
        } else {
            return R.layout.layout_spinner_item;
        }
    }

    public static Location getExifLocation(String filepath) {
        Location location = null;

        try {
            final ExifInterface exif = new ExifInterface(filepath);
            final float[] latLong = new float[2];

            if (exif.getLatLong(latLong)) {
                location = new Location("");
                location.setLatitude(latLong[0]);
                location.setLongitude(latLong[1]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return location;
    }
}
