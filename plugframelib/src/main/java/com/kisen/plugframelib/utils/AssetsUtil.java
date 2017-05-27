package com.kisen.plugframelib.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * assets 文件读取类
 *
 * @author kisen
 * @TIME 2016/9/19 16:24
 */
public class AssetsUtil {

    public static String getJson(Context context, String fileName) throws IOException {
        InputStream is = context.getAssets().open(fileName);
        int size = is.available();

        // Read the entire asset into a local byte buffer.
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        // Convert the buffer into a string.
//        String text = new String(buffer, "GB2312");
        String text = new String(buffer);
        return text;
    }
}
