package com.kisen.plugframelib.view.recyclerhelper.helper.drag;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kisen.plugframelib.view.recyclerhelper.helper.widget.NestingViewHolder;


/**
 * Item拖动ViewHolder
 * Created by huang on 2017/5/4.
 */

public class DragViewHolder extends NestingViewHolder {

    private RecyclerView.ViewHolder mViewHolder;

    public DragViewHolder(DragAdapter.ItemHelper helper) {
        super(helper.viewHolder.itemView);
        mViewHolder = helper.viewHolder;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHolder() {
        return mViewHolder;
    }

    public void onItemSelected() {

    }

    public void onItemClear() {

    }
}
