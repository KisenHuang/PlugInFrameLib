package com.kisen.plugframelib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UI相关的工具类
 * Created by huang on 2017/5/3.
 */

public class UIUtil {
    /**
     * 格式化字符串中的数字
     */
    public static SpannableStringBuilder formatStringWithNumber(String value, int color) {

        SpannableStringBuilder builder = new SpannableStringBuilder(value);
        Pattern pattern = Pattern.compile("[\\d]+?");
        Matcher isNum = pattern.matcher(value);
        while (isNum.find()) {
            int start = isNum.start();
            int end = isNum.end();
            builder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return builder;
    }

    public static float dpToPx(Context context, final float dp) {
        return dp * (context.getResources().getDisplayMetrics().densityDpi / 160f);
    }

    public static float pxToDp(Context context, final float px) {
        return px / (context.getResources().getDisplayMetrics().densityDpi / 160f);
    }

    public static float spToPx(Context context, final float sp) {
        return sp * (context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static float pxToSp(Context context, final float px) {
        return px / (context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static float dpToPx(final float dp) {
        return dp * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f);
    }

    public static float pxToDp(final float px) {
        return px / (Resources.getSystem().getDisplayMetrics().densityDpi / 160f);
    }

    public static float spToPx(final float sp) {
        return sp * (Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    public static float pxToSp(final float px) {
        return px / (Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int getDisplayScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getDisplayScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 根据图片名称获取图片资源id
     */
    public static int getImageResourceId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    /**
     * 格式化金额
     */
    public static String formatPrice(String price) {
        try {
            double tmp = Double.parseDouble(price);
            if (tmp - (int) tmp != 0) {
                return new DecimalFormat("¥###0.00").format(tmp);
            } else {
                return new DecimalFormat("¥##0").format(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("¥%s", price);
        }
    }

    /**
     * 格式化金额
     */
    public static String formatPrice(double price) {
        int tmp = (int) price;
        if (price - tmp != 0) {
            return new DecimalFormat("¥###0.00").format(price);
        } else {
            return new DecimalFormat("¥##0").format(price);
        }
    }

    /**
     * 从AndroidMenifest.xml获取配置信息
     *
     * @param TAG_NAME name 配置项
     */
    public static String getConfig(Context context, final String TAG_NAME) {
        String v = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(TAG_NAME); // manifest里面的name值
            if (value != null) {
                v = value.toString();
            }
        } catch (Exception e) {
            //
        }
        return v;
    }

    /**
     * 打开指定网页地址
     */
    public static void openBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * string转list
     */
    public static List<String> stringToList(String string) {
        if (TextUtils.isEmpty(string))
            return null;
        return stringToList(string, getSplits(string));
    }

    public static List<String> stringToList(String string, String split) {
        if (TextUtils.isEmpty(string))
            return null;
        String[] splits = string.split(split);
        return arrayToList(splits);
    }

    public static List<String> intToString(List<Integer> list) {
        if (list == null || list.isEmpty())
            return null;
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            strings.add(String.valueOf(list.get(i)));
        }
        return strings;
    }

    public static List<Integer> stringToInt(List<String> list) {
        if (list == null || list.isEmpty())
            return null;
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            integers.add(Integer.parseInt(list.get(i)));
        }
        return integers;
    }

    /**
     * string转list
     */
    public static List<Integer> stringToIntList(String string, String split) {
        if (TextUtils.isEmpty(string))
            return null;
        String[] splits = string.split(split);
        List<Integer> list = new ArrayList<>();
        for (String str : splits) {
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public static String getSplits(String s) {
        for (String split : splits) {
            if (s.contains(split))
                return split;
        }
        return " ";
    }

    private static String[] splits = new String[]{",", "，", "\\.", "。"};

    /**
     * list转string
     */
    public static String listToString(List<String> list) {
        return listToString(list, ',');
    }

    /**
     * list转string
     * @param split 分隔符
     */
    public static String listToString(List<String> list, char split) {
        if (list == null || list.isEmpty())
            return "";
        StringBuffer buffer = new StringBuffer();
        for (String str : list) {
            buffer.append(str).append(split);
        }
        if (buffer.length() > 0)
            buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    /**
     * list转string
     */
    public static String intListToString(List<Integer> list, char split) {
        if (list == null || list.isEmpty())
            return "";
        StringBuffer buffer = new StringBuffer();
        for (int i : list) {
            buffer.append(String.valueOf(i)).append(split);
        }
        if (buffer.length() > 0)
            buffer.deleteCharAt(buffer.length() - 1);
        return buffer.toString();
    }

    /**
     * array转list
     */
    public static List<String> arrayToList(String[] array) {
        List<String> list = new ArrayList<>();
        for (String str : array) {
            list.add(str);
        }
        return list;
    }

    /**
     * 检测Url是否合法
     */
    public static String checkUrl(String url) {
        if (TextUtils.isEmpty(url))
            return "";
        if (url.startsWith("http://") || url.startsWith("https://"))
            return url;
        else return "http://" + url;
    }

    /**
     * 判断是否为静态网页
     */
    public static boolean checkHtml(String content) {
        if (content.contains("<") && content.contains("/>"))
            return true;
        return false;
    }

    /**
     * 手机号正则表达
     */
    public static final String MOBILE = "^(13[0-9]|15[0-35-9]|17[06-8]|18[0-9]|14[57])[0-9]{8}$";

    public static boolean checkPhone(String phone) {
        return phone.matches(MOBILE);
    }
}
