package com.kisen.plugframelib.mvp.presenter;

import android.widget.Toast;

import com.kisen.plugframelib.mvp.view.BaseView;


/**
 * 普通页面使用的Presenter
 * Created by huang on 2017/2/7.
 */
public class BasePresenter<V extends BaseView> implements IPresenter<V> {

    private V view;

    public V getView() {
        return view;
    }

    protected void showMsg(String msg) {
        Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    protected void showMsg(int resId) {
        Toast.makeText(view.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }
}
