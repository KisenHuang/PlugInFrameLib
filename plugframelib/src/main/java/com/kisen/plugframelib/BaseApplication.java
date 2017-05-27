package com.kisen.plugframelib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.kisen.plugframelib.config.ActivityConfig;
import com.kisen.plugframelib.config.MvpActivityConfig;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, ActivityConfig> activityConfigs;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        activityConfigs = new HashMap<>();
        setupCallback();
    }

    public void addActivityConfigs(String tag, ActivityConfig config) {
        removeActivityConfig(tag);
        activityConfigs.put(tag, config);
    }

    public void removeActivityConfig(String tag) {
        if (activityConfigs.containsKey(tag)) {
            activityConfigs.remove(tag);
        }
    }

    private void setupCallback() {
        activityConfigs.put("mvp", new MvpActivityConfig());
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                for (ActivityConfig config : activityConfigs.values()) {
                    config.onActivityCreated(activity, savedInstanceState);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                for (ActivityConfig config : activityConfigs.values()) {
                    config.onActivityStarted(activity);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {
                for (ActivityConfig config : activityConfigs.values()) {
                    config.onActivityResumed(activity);
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
                for (ActivityConfig config : activityConfigs.values()) {
                    config.onActivityPaused(activity);
                }
            }

            @Override
            public void onActivityStopped(Activity activity) {
                for (ActivityConfig config : activityConfigs.values()) {
                    config.onActivityStopped(activity);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                for (ActivityConfig config : activityConfigs.values()) {
                    config.onActivitySaveInstanceState(activity, outState);
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                for (ActivityConfig config : activityConfigs.values()) {
                    config.onActivityDestroyed(activity);
                }
            }
        });
    }
}
