package com.kisen.plugframelib.mvp.listhelper;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kisen.plugframelib.R;
import com.kisen.plugframelib.view.recyclerhelper.helper.swipe.SwipeViewHolder;

/**
 * Created by huangwy on 2017/10/20.
 * email: kisenhuang@163.com.
 */

public class QuickMvpAdapter<I extends IAdapter> extends QuickAdapter<I> {

    private int position;

    public QuickMvpAdapter() {
        super(0, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, I item) {
        item.convert(helper);
    }

    @Override
    protected int getDefItemViewType(int position) {
        this.position = position;
        int itemType = mData.get(position).getItemType(position);
        int defItemViewType = super.getDefItemViewType(position);
        return defItemViewType == -0xff ? itemType : defItemViewType;
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (mData != null) {
            int layoutId = mData.get(position).getItemResId();
            if (layoutId > 0)
                layoutResId = layoutId;
        }
        return super.createBaseViewHolder(parent, layoutResId);
    }

    @Override
    protected boolean interceptItemClick(int position) {
        return mData.get(position).interceptItemClick(this);
    }
}
