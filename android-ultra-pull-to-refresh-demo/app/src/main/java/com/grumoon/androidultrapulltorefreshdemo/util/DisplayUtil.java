package com.grumoon.androidultrapulltorefreshdemo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2015/1/15.
 */
public class DisplayUtil {

    public static int SCREEN_WIDTH_PIXELS;
    public static int SCREEN_HEIGHT_PIXELS;
    public static float SCREEN_DENSITY;
    public static int SCREEN_WIDTH_DP;
    public static int SCREEN_HEIGHT_DP;
    private static boolean sInitialed;

    public static void init(Context context) {
        if (sInitialed || context == null) {
            return;
        }
        sInitialed = true;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);
    }

    public static int dp2px(float dp) {
        final float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5f);
    }

    public static int designedDP2px(float designedDp) {
        if (SCREEN_WIDTH_DP != 320) {
            designedDp = designedDp * SCREEN_WIDTH_DP / 320f;
        }
        return dp2px(designedDp);
    }

}
