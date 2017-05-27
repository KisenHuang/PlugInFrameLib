package com.kisen.plugframelib.utils.imageloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.IllegalFormatConversionException;

/**
 * Glide图片加载框架封装工具类
 * Created by huang on 2017/4/17.
 */
class GlideUtil implements ILoader {

    private int resId = -1;

    @Override
    public void setPlaceHolder(int resId) {
        this.resId = resId;
    }

    @Override
    public void display(Context context, String url, ImageView target) {
        getRequestManager(context)
                .load(url)
                .placeholder(resId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(getListener(target))
                .into(target);
    }

    @Override
    public void display(Fragment fragment, String url, ImageView target) {
        getRequestManager(fragment)
                .load(url)
                .placeholder(resId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(getListener(target))
                .into(target);
    }

    @Override
    public void display(android.app.Fragment fragment, String url, ImageView target) {
        getRequestManager(fragment)
                .load(url)
                .placeholder(resId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(getListener(target))
                .into(target);
    }

    @Override
    public void pause(Object target) {
        getRequestManager(target).pauseRequests();
    }

    @Override
    public void resume(Object target) {
        getRequestManager(target).resumeRequests();
    }

    @Override
    public void stop(Object target) {
        getRequestManager(target).onStop();
    }

    @Override
    public void start(Object target) {
        getRequestManager(target).onStart();
    }

    @Override
    public void destroy(Object target) {
        getRequestManager(target).onDestroy();
    }

    private static RequestListener<? super String, GlideDrawable> getListener(final ImageView target) {
        return new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                // 如果加载失败，进行失败处理
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                // 图片资源下载成功，可以在这里获取到图片的参数
                return false;
            }
        };
    }

    private static RequestManager getRequestManager(Object target) {
        if (target instanceof Context) {
            return Glide.with((Context) target);
        } else if (target instanceof Fragment) {
            return Glide.with((Fragment) target);
        } else if (target instanceof android.app.Fragment) {
            return Glide.with((android.app.Fragment) target);
        } else {
            throw new IllegalFormatConversionException('e', target.getClass());
        }
    }
}
