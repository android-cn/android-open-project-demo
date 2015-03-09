package com.example.yyb.myapplication.utils;

/**
 * @author yyb
 */
public class LogUtil {
    public final static boolean DEBUG = true;

    public static String makeLogTag(Class cls) {
        return "PagerSlidingTabStrip" + cls.getSimpleName();

    }
}
