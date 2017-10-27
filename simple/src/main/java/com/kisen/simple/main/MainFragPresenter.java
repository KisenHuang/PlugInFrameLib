package com.kisen.simple.main;

import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.kisen.plugframelib.mvp.listhelper.Item;
import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;
import com.kisen.plugframelib.mvp.view.BaseListView;
import com.kisen.plugframelib.mvp.view.BaseView;
import com.kisen.plugframelib.utils.http.NetWorkCallback;
import com.kisen.simple.Constract;

import java.util.List;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 16:01.
 */

public class MainFragPresenter extends BaseListPresenter<MainData> implements NetWorkCallback<String> {

    private MainModel mainModel;

    @Override
    public void attachView(BaseListView view) {
        super.attachView(view);
        mainModel = new MainModel(this);
    }

    @NonNull
    @Override
    protected Item<MainData>.Builder createItemBuilder() {
        return new MainItem(null).builder();
    }

    public void onRefresh() {
        mainModel.load(this, Constract.REFRESH_CODE);
    }

    public void onLoad() {
        mainModel.load(this, Constract.LOAD_CODE);
    }

    @Override
    public void success(String result, int id) {
        List<MainData> list = JSON.parseArray(JSON.parseObject(result).getString("data"), MainData.class);
        notifyAfterLoad(list);
    }

    @Override
    public void fail(Exception e, int id) {
    }

    @Override
    public void finish(int id) {
        if (id == 1 || id == 2)
            getView().setRefreshing(false);
    }
}
