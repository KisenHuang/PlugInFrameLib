package com.kisen.plugframelib.config;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 11:21.
 */
public abstract class ActivityConfig implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }
}
