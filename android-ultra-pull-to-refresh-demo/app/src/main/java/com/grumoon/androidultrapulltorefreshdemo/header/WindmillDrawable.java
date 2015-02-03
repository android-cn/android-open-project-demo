package com.grumoon.androidultrapulltorefreshdemo.header;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.grumoon.androidultrapulltorefreshdemo.R;


/**
 * Created by grumoon on 2015/1/20.
 */
public class WindmillDrawable extends Drawable implements Animatable {

    private static final String TAG = WindmillDrawable.class.getSimpleName();

    private Resources resources;

    private Bitmap windmill;

    private Matrix matrix;

    private View parent;


    private Animation animation;

    private boolean isFirstDraw = true;

    private boolean isAnimating;

    public WindmillDrawable(Context context, View parent) {
        resources = context.getResources();
        windmill = BitmapFactory.decodeResource(resources, R.drawable.windmill);

        matrix = new Matrix();

        this.parent = parent;


        animation = new RotateAnimation(360, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(800);
        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setFillAfter(true);

    }


    @Override
    public void draw(Canvas canvas) {
        if (isFirstDraw) {
            isFirstDraw = false;
            matrix.setTranslate((getBounds().width() - windmill.getWidth()) / 2, (getBounds().height() - windmill.getHeight()) / 2);
        }

        Paint p = new Paint();
        canvas.drawBitmap(windmill, matrix, p);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter cf) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public void postRotation(int degree) {

        matrix.postRotate(degree, getBounds().exactCenterX(), getBounds().exactCenterY());
        invalidateSelf();
    }

    @Override
    public void start() {
        parent.startAnimation(animation);
        isAnimating = true;
    }

    @Override
    public void stop() {
        parent.clearAnimation();
        isAnimating = false;
    }

    @Override
    public boolean isRunning() {
        return isAnimating;
    }
}
