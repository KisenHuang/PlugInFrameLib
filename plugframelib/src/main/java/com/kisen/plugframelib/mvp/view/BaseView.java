package com.kisen.plugframelib.mvp.view;

import com.kisen.plugframelib.mvp.presenter.BasePresenter;

/**
 * Activity接口
 * Created by huang on 2017/2/7.
 */
public interface BaseView<P extends BasePresenter> extends MvpView<P> {

    /**
     * 打开加载动画
     */
    void openLoadingAnim();

    /**
     * 关闭加载动画
     */
    void closeLoadingAnim();
}
