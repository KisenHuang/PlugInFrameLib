package com.kisen.plugframelib.utils;

import android.util.Log;

import com.kisen.plugframelib.BuildConfig;


/**
 * 标题：封装日志输出工具类
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/15 15:36.
 */
public class LogUtil {

    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void setDebug(boolean debug) {
        LogUtil.DEBUG = debug;
    }

    public static String getName(Class clazz) {
        String root = "herbsApp";
        if (clazz == null)
            return root;
        return root + ":" + clazz.getSimpleName();
    }

    public static String getName(Object obj) {
        return getName(obj.getClass());
    }

    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }

}
