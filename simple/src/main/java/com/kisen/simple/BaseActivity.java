package com.kisen.simple;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.plugframelib.mvp.view.BaseView;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 17:01.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView<P> {

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void handleSuccess(String result, int id) {

    }

    @Override
    public void handleFail(Exception e, int id) {

    }

    @Override
    public void handleFinish(int id) {

    }

    @Override
    public void openLoadingAnim() {

    }

    @Override
    public void closeLoadingAnim() {

    }
}
