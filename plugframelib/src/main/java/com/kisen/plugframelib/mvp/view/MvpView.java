package com.kisen.plugframelib.mvp.view;

import android.content.Context;

import com.kisen.plugframelib.mvp.presenter.BasePresenter;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 11:58.
 */
interface MvpView<P extends BasePresenter> {

    /**
     * 返回上下文
     */
    Context getContext();

    /**
     * 返回布局
     */
    int getLayoutId();

    /**
     * 初始化操作
     */
    void init(P presenter);

    /**
     * 创建Presenter
     * 该方法不允许被调用
     *
     * @return IPresenter实现类
     */
    P newPresenter();

    /**
     * 网络请求成功结果回调
     *
     * @param result 返回结果
     * @param id     请求识别码，本地识别
     */
    void handleSuccess(String result, int id);

    /**
     * 网络请求失败结果回调
     *
     * @param e 失败异常结果
     * @param id     请求识别码，本地识别
     */
    void handleFail(Exception e, int id);

    /**
     * 网络请求结束回调
     * 在success()方法和fail()方法之后调用
     * @param id     请求识别码，本地识别
     */
    void handleFinish(int id);
}
