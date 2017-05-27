package com.kisen.simple;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;
import com.kisen.plugframelib.mvp.view.BaseListView;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 17:05.
 */
public abstract class BaseListActivity<P extends BaseListPresenter> extends AppCompatActivity implements BaseListView<P> {

    @Override
    public void openLoadingAnim() {

    }

    @Override
    public void closeLoadingAnim() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    public void init(P presenter) {

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
    public SwipeRefreshLayout getRefreshLayout() {
        return null;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return null;
    }

    @Override
    public View getEmptyView() {
        return null;
    }

    @Override
    public void setEmptyImage(Drawable drawable) {

    }

    @Override
    public void setEmptyImage(int resId) {

    }

    @Override
    public void setEmptyText(CharSequence c) {

    }

    @Override
    public void setEmptyText(int resId) {

    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {

    }

    @Override
    public void setRefreshing(boolean refresh) {

    }

    @Override
    public void loadData() {

    }
}
