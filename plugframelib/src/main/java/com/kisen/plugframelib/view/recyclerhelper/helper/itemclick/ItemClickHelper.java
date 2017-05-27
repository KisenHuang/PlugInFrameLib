package com.kisen.plugframelib.view.recyclerhelper.helper.itemclick;

import android.support.v7.widget.RecyclerView;

import com.kisen.plugframelib.view.recyclerhelper.helper.widget.IAttachRecyclerView;
import com.kisen.plugframelib.view.recyclerhelper.helper.widget.ItemClick;

/**
 * 设置条目点击事件
 * Created by hwyMi on 2017/5/11.
 */
public class ItemClickHelper implements ItemClick<ItemClickHelper>, IAttachRecyclerView {

    private OnItemClickListener mItemClickListener;
    private RecyclerView.Adapter mAdapter;

    @Override
    public ItemClickHelper setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
        return this;
    }

    @Override
    public ItemClickHelper setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        return this;
    }


    @Override
    public void attachRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("The RecyclerView can not be null!");
        }
        if (mAdapter == null) {
            throw new NullPointerException("The Adapter can not be null!");
        }
        ItemClickAdapter clickAdapter = new ItemClickAdapter(mAdapter);
        clickAdapter.setOnItemClickListener(mItemClickListener);
        mAdapter = clickAdapter;

        recyclerView.setAdapter(mAdapter);
    }
}
