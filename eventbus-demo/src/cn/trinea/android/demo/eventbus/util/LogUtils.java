package cn.trinea.android.demo.eventbus.util;

import android.util.Log;

public class LogUtils {

    private LogUtils() { /* cannot be instantiated */}

    public static int d(Object c, String msg) {
        return Log.d(getTag(c), msg);
    }

    public static int e(Object c, String msg) {
        return Log.e(getTag(c), msg);
    }

    public static int i(Object c, String msg) {
        return Log.i(getTag(c), msg);
    }

    public static int v(Object c, String msg) {
        return Log.v(getTag(c), msg);
    }

    public static int w(Object c, String msg) {
        return Log.w(getTag(c), msg);
    }

    public static int wtf(Object c, String msg) {
        return Log.wtf(getTag(c), msg);
    }

    public static String getTag(Object c) {
        return "Trinea " + c.getClass().getName();
    }
}
