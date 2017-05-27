//package com.kisen.plugframelib.mvp;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.kisen.plugframelib.mvp.presenter.BasePresenter;
//import com.kisen.plugframelib.mvp.view.BaseView;
//
//
///**
// * 标题：Mvp模式View模板Fragment基类
// * 作者：kisen
// * 版本：
// * 创建时间：on 2017/5/15 15:45.
// */
//public abstract class MvpFragment<P extends BasePresenter> extends Fragment implements BaseView<P> {
//
//    private P presenter;
//    protected Context mContext;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(getLayoutId(), container, false);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        init(getPresenter());
//    }
//
//    @Override
//    public Context getContext() {
//        return mContext;
//    }
//
//    /**
//     * 初始化Presenter并关联Activity
//     *
//     * @return presenter
//     */
//    protected P getPresenter() {
//        if (presenter == null) {
//            presenter = newPresenter();
//            if (presenter != null) {
//                presenter.attachView(this);
//            }
//        }
//        return presenter;
//    }
//
//    @Override
//    public void success(String result, int id) {
//        handleSuccess(id, result);
//    }
//
//    @Override
//    public void fail(Exception e, int id) {
//        handleError(id, e);
//    }
//
//    @Override
//    public void finish(int id) {
//        handleFinish(id);
//    }
//
//    @Override
//    public void openLoadingAnim() {
//
//    }
//
//    @Override
//    public void closeLoadingAnim() {
//
//    }
//
//    /**
//     * Model错误处理
//     *
//     * @param reqCode 请求码
//     * @param args    返回数据
//     */
//    protected void handleSuccess(int reqCode, String args) {
//    }
//
//    /**
//     * Model错误处理
//     *
//     * @param reqCode 请求码
//     * @param e       异常
//     */
//    protected void handleError(int reqCode, Exception e) {
//    }
//
//    /**
//     * Model错误处理
//     *
//     * @param reqCode 请求码
//     */
//    protected void handleFinish(int reqCode) {
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mContext = null;
//        if (presenter != null) {
//            presenter.detachView();
//            presenter = null;
//        }
//    }
//}
