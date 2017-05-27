package com.kisen.simple;

import android.support.v4.app.Fragment;

import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.plugframelib.mvp.view.BaseView;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 17:07.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView<P> {

    @Override
    public void openLoadingAnim() {

    }

    @Override
    public void closeLoadingAnim() {

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
}
