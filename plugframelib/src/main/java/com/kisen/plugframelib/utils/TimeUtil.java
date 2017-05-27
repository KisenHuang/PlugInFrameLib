package com.kisen.plugframelib.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/24 12:12.
 */

public class TimeUtil {

    private static final String[] MOUNTS = new String[]{"Jun", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private static final SimpleDateFormat DATEFORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATEFORMAT_YMD2 = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat DATEFORMAT_MD = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat DATEFORMAT_YMDHM2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static final SimpleDateFormat DATEFORMAT_YMDHM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat DATEFORMAT_YMDHM3 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    private static final SimpleDateFormat DATEFORMAT_MDHM = new SimpleDateFormat("MM月dd日 HH:mm");
    private static final SimpleDateFormat DATEFORMAT_MDHM2 = new SimpleDateFormat("MM-dd HH:mm");
    private static final SimpleDateFormat IMAGE_DETAIL_CREATE_ON = new SimpleDateFormat("MM月dd日HH:mm");

    public static String convertYMD(long time) {
        return DATEFORMAT_YMD.format(new Date(time));
    }

    public static String convertYMD2(long time) {
        return DATEFORMAT_YMD2.format(new Date(time));
    }

    public static String convertYMDHM(long time) {
        return DATEFORMAT_YMDHM.format(new Date(time));
    }

    public static String convertYMDHM2(long time) {
        return DATEFORMAT_YMDHM2.format(new Date(time));
    }

    public static String convertYMDHM3(long time) {
        return DATEFORMAT_YMDHM3.format(new Date(time));
    }

    public static String convertMDHM(long time) {
        return DATEFORMAT_MDHM.format(new Date(time));
    }

    public static String convertMDHM2(long time) {
        return DATEFORMAT_MDHM2.format(new Date(time));
    }

    public static String convertString(String format, long time) {
        return convertString(format, new Date(time));
    }

    public static String convertString(String format, Date time) {
        try {
            return new SimpleDateFormat(format).format(time);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertSS(long milliSec) {
        Log.v("voiceCal", "src length = " + milliSec);
        return String.valueOf(milliSec / 1000);
    }

    public static String formatForImageDetail(long milliTime) {
        return IMAGE_DETAIL_CREATE_ON.format(new Date(milliTime));
    }

    public static String formatForMD(long milliTime) {
        String format = DATEFORMAT_MD.format(new Date(milliTime));
        String[] split = format.split("/");
        String mount = MOUNTS[Integer.parseInt(split[0]) - 1];
        format = mount + "." + split[1];
        return format;
    }
}
