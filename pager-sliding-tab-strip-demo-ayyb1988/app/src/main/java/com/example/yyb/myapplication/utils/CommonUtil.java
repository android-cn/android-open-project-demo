package com.example.yyb.myapplication.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by yyb on 15-3-7.
 */
public class CommonUtil {

    public static int dp2px(Context context, int dp) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);

        return (int) (dp * displaymetrics.density + 0.5f);
    }
}
