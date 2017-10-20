package com.kisen.simple.main;

import com.kisen.plugframelib.mvp.model.MvpModel;
import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.plugframelib.utils.AssetsUtil;
import com.kisen.plugframelib.utils.http.NetWorkCallback;

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

    public void load(NetWorkCallback<String> callback, int code) {
        callback = getCallback(callback);
        try {
            String json = AssetsUtil.getJson(context, "mall.json");
            callback.success(json, code);
            callback.finish(code);
        } catch (IOException e) {
            e.printStackTrace();
            callback.fail(e, code);
        }
    }

}
