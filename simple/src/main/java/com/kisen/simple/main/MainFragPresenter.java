package com.kisen.simple.main;

import com.alibaba.fastjson.JSON;
import com.kisen.plugframelib.mvp.listhelper.Item;
import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;
import com.kisen.plugframelib.mvp.view.BaseView;
import com.kisen.plugframelib.utils.http.NetWorkCallback;

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
    public void attachView(BaseView view) {
        super.attachView(view);
        mainModel = new MainModel(this);
    }

    @Override
    public Item<MainData> setupItemTemplate() {
        return new MainItem();
    }

    public void onRefresh() {
        mainModel.load(this,1);
    }

    public void onLoad() {
        mainModel.load(this,2);
    }

    @Override
    public void success(String result, int id) {
        if (id == 1)
            getAdapter().clear();
        List<MainData> list = JSON.parseArray(JSON.parseObject(result).getString("data"), MainData.class);
        notifyAfterLoad(list, false);
    }

    @Override
    public void fail(Exception e, int id) {
    }

    @Override
    public void finish(int id) {
    }
}
