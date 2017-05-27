package com.kisen.simple.main;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

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
    public int getPageSize() {
        return 10;
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
    }

    @Override
    public void onLoad() {
        presenter.onLoad();
    }
}
