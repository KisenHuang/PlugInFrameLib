package com.kisen.plugframelib.view.recyclerhelper.decoration;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @author kisen
 * @TIME 2016/8/4 11:47
 */
public abstract class BaseDividerItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 基于BaseAdapter的特殊ViewHolder type类型
     */
    public static int[] viewTypes = {BaseQuickAdapter.HEADER_VIEW,
            BaseQuickAdapter.LOADING_VIEW,
            BaseQuickAdapter.EMPTY_VIEW,
            BaseQuickAdapter.FOOTER_VIEW};

    public boolean checkSpecialViewType(int viewType) {
        for (int i = 0; i < viewTypes.length; i++) {
            if (viewTypes[i] == viewType)
                return true;
        }
        return false;
    }

    public int getHeaderCount(RecyclerView recyclerView){
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof BaseQuickAdapter) {
            return  ((BaseQuickAdapter) adapter).getHeaderLayoutCount();
        }
        return 0;
    }

    public abstract BaseDividerItemDecoration setRecyclerViewPadding(int left, int top, int right, int bottom);

}
