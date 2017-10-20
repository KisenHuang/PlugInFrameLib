package com.kisen.plugframelib;

import android.app.Application;
import com.kisen.plugframelib.config.MvpActivityConfig;

/**
 * 定义Application
 * Created by huang on 2017/4/28.
 */
public class BaseApplication extends Application {

    private static BaseApplication application;
    public String localName = "base_app";

    public static BaseApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        setupCallback();
    }

    private void setupCallback() {
        registerActivityLifecycleCallbacks(new MvpActivityConfig());
    }
}
