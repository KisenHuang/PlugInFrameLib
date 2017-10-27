package com.kisen.simple.main;

import android.os.Handler;

import com.kisen.plugframelib.mvp.model.MvpModel;
import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.plugframelib.utils.AssetsUtil;
import com.kisen.plugframelib.utils.http.NetWorkCallback;
import com.kisen.simple.Constract;

import java.io.IOException;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 16:47.
 */

public class MainModel extends MvpModel {

    public MainModel(BasePresenter presenter) {
        super(presenter);
    }

    public void load(NetWorkCallback<String> callback, final int code) {
        callback = getCallback(callback);
        final NetWorkCallback<String> finalCallback = callback;
        if (code == Constract.REFRESH_CODE) {//刷新
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        String json = AssetsUtil.getJson(context, "mall.json");
                        finalCallback.success(json, code);
                        finalCallback.finish(code);
                    } catch (IOException e) {
                        e.printStackTrace();
                        finalCallback.fail(e, code);
                    }
                }
            }, 1000);
        } else {//加载
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        String json = AssetsUtil.getJson(context, "mall_more.json");
                        finalCallback.success(json, code);
                        finalCallback.finish(code);
                    } catch (IOException e) {
                        e.printStackTrace();
                        finalCallback.fail(e, code);
                    }
                }
            }, 1000);
        }

    }

}
