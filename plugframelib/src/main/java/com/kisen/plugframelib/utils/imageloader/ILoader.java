package com.kisen.plugframelib.utils.imageloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * 标题：图片加载接口方法
 * 作者：kisen
 * 版本：v1.0.0
 * 创建时间：on 2017/5/15 13:18.
 */
interface ILoader {

    void setPlaceHolder(int resId);

    void display(Context context, String url, ImageView targetView);

    void display(Fragment fragment, String url, ImageView target);

    void display(android.app.Fragment fragment, String url, ImageView target);

    void pause(Object target);

    void resume(Object target);

    void stop(Object target);

    void start(Object target);

    void destroy(Object target);
}
