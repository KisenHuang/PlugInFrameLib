package com.kisen.plugframelib.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/18 16:45.
 */

public class PermissionUtil {

    /**
     * 检测是否获取权限
     *
     * @param activity 活动界面
     * @return 是否获取权限
     */
    public static boolean checkPermission(Activity activity, String permission) {
        //没有获得权限，请求
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void requestPermission(Activity activity, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, 1);
    }

}
