package com.kisen.plugframelib.utils;

import android.content.Context;
import android.util.TypedValue;

import java.text.DecimalFormat;

/**
 * 标题：价格格式化工具
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/19 15:06.
 */
public class FormatUtil {

    /**
     * 格式化金额
     */
    public static String formatPrice(String price) {
        try {
            double tmp = Double.parseDouble(price);
            return formatPrice(tmp);
        } catch (Exception e) {
            e.printStackTrace();
            return String.format("¥%s", price);
        }
    }

    public static String formatPrice(double price) {
        int tmp = (int) price;
        if (price - tmp != 0) {
            return new DecimalFormat("¥###0.00").format(price);
        } else {
            return new DecimalFormat("¥##.00").format(price);
        }
    }

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
