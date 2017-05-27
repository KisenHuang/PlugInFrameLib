//package com.kisen.plugframelib.mvp;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.view.ViewGroup;
//
//import com.kisen.plugframelib.R;
//import com.kisen.plugframelib.mvp.presenter.BasePresenter;
//import com.kisen.plugframelib.mvp.progress.LoadingProgress;
//import com.kisen.plugframelib.mvp.progress.TemplateHelper;
//import com.kisen.plugframelib.mvp.view.BaseView;
//
//
///**
// * Mvp模式View模板Activity基类
// * Created by huang on 2017/2/7.
// */
//public abstract class MvpActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView<P> {
//
//    private P presenter;
//    protected Context mContext;
//    protected TemplateHelper templateHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(getLayoutId());
//        ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
//        setupTemplate(contentView);
//        mContext = this;
//    }
//
//    private void setupTemplate(ViewGroup contentView) {
//        templateHelper = new TemplateHelper(contentView);
//        templateHelper.setTitleHeight(getTitleHeight());
//        templateHelper.setLoadProgress(getLoadProgress());
//    }
//
//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        initView();
//        initData();
//        initListener();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        templateHelper.containerFocus();
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
//        templateHelper.openLoadAnim();
//    }
//
//    @Override
//    public void closeLoadingAnim() {
//        templateHelper.closeLoadAnim();
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
//    /**
//     * 设置Toolbar高度
//     * 显示加载动画时不遮挡Toolbar
//     */
//    protected int getTitleHeight() {
//        return (int) getResources().getDimension(R.dimen.title_height);
//    }
//
//    protected LoadingProgress getLoadProgress() {
//        return null;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        templateHelper.destroy();
//        mContext = null;
//        if (presenter != null) {
//            presenter.detachView();
//            presenter = null;
//        }
//    }
//}
