package com.kisen.plugframelib.view.recyclerhelper.helper.itemclick;

import android.support.v7.widget.RecyclerView;

import com.kisen.plugframelib.view.recyclerhelper.helper.widget.NestingViewHolder;


/**
 * Created by hwyMi on 2017/5/11.
 */
public class ItemClickViewHolder extends NestingViewHolder {

    private RecyclerView.ViewHolder mViewHolder;

    public ItemClickViewHolder(RecyclerView.ViewHolder viewHolder) {
        super(viewHolder.itemView);
        mViewHolder = viewHolder;
    }

    @Override
    public RecyclerView.ViewHolder getHolder() {
        return mViewHolder;
    }
}
