package com.kisen.plugframelib.utils.imageloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

/**
 * 标题：图片加载工具类
 * 作者：kisen
 * 版本：v1.0.0
 * 创建时间：on 2017/5/15 13:17.
 */
public class ImageLoader implements ILoader {

    private static ILoader mILoader;
    private static ImageLoader mInstance;

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null)
                    mInstance = new ImageLoader();
            }
        }
        return mInstance;
    }

    private ImageLoader() {
        mILoader = new GlideUtil();
    }


    @Override
    public void setPlaceHolder(int resId) {
        mILoader.setPlaceHolder(resId);
    }

    @Override
    public void display(Context context, String url, ImageView target) {
        mILoader.display(context, url, target);
    }

    @Override
    public void display(Fragment fragment, String url, ImageView target) {
        mILoader.display(fragment, url, target);
    }

    @Override
    public void display(android.app.Fragment fragment, String url, ImageView target) {
        mILoader.display(fragment, url, target);
    }

    @Override
    public void pause(Object target) {
        mILoader.pause(target);
    }

    @Override
    public void resume(Object target) {
        mILoader.resume(target);
    }

    @Override
    public void stop(Object target) {
        mILoader.stop(target);
    }

    @Override
    public void start(Object target) {
        mILoader.start(target);
    }

    @Override
    public void destroy(Object target) {
        mILoader.destroy(target);
    }
}
