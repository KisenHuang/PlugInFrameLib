package com.kisen.simple;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;
import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.plugframelib.mvp.view.BaseListView;
import com.kisen.simple.main.MainFragPresenter;

import butterknife.BindView;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 17:08.
 */
public abstract class BaseListFragment<P extends BaseListPresenter> extends Fragment implements BaseListView<P> {

    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(android.R.id.empty)
    FrameLayout empty;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    protected P presenter;

    @Override
    public void openLoadingAnim() {

    }

    @Override
    public void closeLoadingAnim() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_list;
    }

    @Override
    public void init(P presenter) {
        this.presenter = presenter;
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
        return refreshLayout;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
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
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(refresh);
        }
    }

    @Override
    public void loadData() {
        openLoadingAnim();
        setRefreshing(true);
        onRefresh();
    }

}
