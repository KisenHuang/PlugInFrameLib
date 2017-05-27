package com.kisen.plugframelib.config;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentHelper;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 11:52.
 */
public class MvpActivityConfig extends ActivityConfig {

    private BasePresenter presenter;
    private QuickAdapter adapter;
    private FragmentConfig fragmentConfig;

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (isBaseView(activity)) {
            BaseView mvpView = (BaseView) activity;
            activity.setContentView(mvpView.getLayoutId());
            ButterKnife.bind(activity);
            presenter = mvpView.newPresenter();
            if (presenter != null)
                presenter.attachView(mvpView);

            mvpView.init(presenter);
            if (isBaseListView(activity)) {
                if (!isBaseListPresenter(presenter))
                    return;
                BaseListView listView = (BaseListView) activity;
                ensureList((BaseListPresenter) presenter, listView);
                listView.loadData();
                setAdapter(listView);
                setListener(listView);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        setupFragmentConfig(activity);
    }

    private void setListener(final BaseListView listView) {
        listView.getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listView.onRefresh();
            }
        });
        if (adapter != null) {
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
    public void onActivityDestroyed(Activity activity) {
        if (fragmentConfig != null) {
            FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
            manager.unregisterFragmentLifecycleCallbacks(fragmentConfig);
        }
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        HttpClient.cancelByTag(activity);
    }

    private boolean isBaseView(Activity activity) {
        return activity instanceof BaseView;
    }

    private boolean isBaseListView(Activity activity) {
        return activity instanceof BaseListView;
    }

    private void setupFragmentConfig(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            fragmentConfig = new FragmentConfig();
            FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
            manager.registerFragmentLifecycleCallbacks(fragmentConfig, false);
        }
    }
}
