package com.kisen.plugframelib.config;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHelper;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kisen.plugframelib.mvp.listhelper.QuickAdapter;
import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;
import com.kisen.plugframelib.mvp.presenter.BasePresenter;
import com.kisen.plugframelib.mvp.view.BaseListView;
import com.kisen.plugframelib.mvp.view.BaseView;
import com.kisen.plugframelib.utils.http.HttpClient;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 14:11.
 */
public class FragmentConfig extends FragmentManager.FragmentLifecycleCallbacks {

    private Unbinder unbinder;
    private BasePresenter presenter;
    private QuickAdapter adapter;

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        if (isBaseView(f)) {
            BaseView mvpView = (BaseView) f;
            FragmentHelper.FragmentViewHolder vh = FragmentHelper.getFragmentContainerId(mvpView.getLayoutId(), f);
            if (vh.mContainer != null && vh.mContainerId > 0 && vh.mContainerId != View.NO_ID) {
                vh.mContainer.removeAllViews();
                vh.mContainer.addView(vh.mView);
                unbinder = ButterKnife.bind(f, vh.mView);
            }
            presenter = mvpView.newPresenter();
            if (presenter != null)
                presenter.attachView(mvpView);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        if (isBaseView(f)) {
            BaseView baseView = (BaseView) f;
            baseView.init(presenter);
            if (isBaseListView(f)) {
                if (!isBaseListPresenter(presenter))
                    return;
                BaseListView listView = (BaseListView) f;
                ensureList((BaseListPresenter) presenter, listView);
                listView.loadData();
                setAdapter(listView);
                setListener(listView);
            }
        }
    }

    private void setAdapter(final BaseListView listView) {
        if (adapter == null)
            return;
        adapter.registerEmptyViewObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if (adapter != null && listView.getEmptyView() != null) {
                    if (adapter.getItemCount() == 0) {
                        listView.getEmptyView().setVisibility(View.VISIBLE);
                        listView.getRecyclerView().setVisibility(View.GONE);
                    } else {
                        listView.getEmptyView().setVisibility(View.GONE);
                        listView.getRecyclerView().setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        listView.getRecyclerView().setAdapter(adapter);
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        if (unbinder != null)
            unbinder.unbind();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        HttpClient.cancelByTag(f);
    }

    private void setListener(final BaseListView listView) {
        listView.getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listView.onRefresh();
            }
        });
        if (adapter!= null){
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    listView.onLoad();
                }
            });
        }
    }

    private boolean isBaseListPresenter(BasePresenter presenter) {
        return presenter != null && presenter instanceof BaseListPresenter;
    }

    private void ensureList(BaseListPresenter presenter, BaseListView activity) {
        if (adapter != null)
            return;
        if (activity.getRefreshLayout() == null)
            return;
        if (activity.getRecyclerView() == null)
            return;
        View emptyView = activity.getEmptyView();
        if (emptyView != null)
            emptyView.setVisibility(View.GONE);
        activity.getRecyclerView().setLayoutManager(new LinearLayoutManager(activity.getContext()));
        adapter = presenter.getAdapter();
        adapter.openLoadMore(activity.getPageSize(), true);
    }

    private boolean isBaseView(Fragment fragment) {
        return fragment instanceof BaseView;
    }

    private boolean isBaseListView(Fragment fragment) {
        return fragment instanceof BaseListView;
    }

}
