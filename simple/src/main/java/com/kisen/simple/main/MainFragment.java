package com.kisen.simple.main;

import com.kisen.simple.BaseListFragment;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 15:50.
 */

public class MainFragment extends BaseListFragment<MainFragPresenter> {

    @Override
    public MainFragPresenter newPresenter() {
        return new MainFragPresenter();
    }

    @Override
    public int getPageSize() {
        return 10;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoad() {
        mPresenter.onLoad();
    }
}
