package com.kisen.simple;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;
import com.kisen.plugframelib.mvp.view.BaseListView;

import butterknife.BindView;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 17:05.
 */
public abstract class BaseListActivity<P extends BaseListPresenter> extends BaseActivity<P>
        implements BaseListView<P> {

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
    protected P mPresenter;

    @Override
    public void init(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_fragment_list;
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
    public void handleSuccess(String result, int id) {
        super.handleSuccess(result, id);
        if (id == Constract.REFRESH_CODE && mPresenter != null) {
            mPresenter.getAdapter().clear();
        }
    }

    @Override
    public void handleFail(Exception e, int id) {
        super.handleFail(e, id);
    }

    @Override
    public void handleFinish(int id) {
        super.handleFinish(id);
        setRefreshing(false);
    }

    @Override
    public View getEmptyView() {
        return empty;
    }

    @Override
    public void setEmptyImage(Drawable drawable) {
        if (ivEmpty != null) {
            ivEmpty.setImageDrawable(drawable);
        }
    }

    @Override
    public void setEmptyImage(int resId) {
        if (ivEmpty != null) {
            ivEmpty.setImageResource(resId);
        }
    }

    @Override
    public void setEmptyText(CharSequence empty) {
        if (tvEmpty != null)
            tvEmpty.setText(empty);
    }

    @Override
    public void setEmptyText(int resId) {
        if (tvEmpty != null)
            tvEmpty.setText(resId);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        if (refreshLayout != null)
            refreshLayout.setBackgroundColor(color);
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
