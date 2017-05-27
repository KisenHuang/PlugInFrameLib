package com.kisen.plugframelib.view.recyclerhelper.helper.itemclick;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.kisen.plugframelib.view.recyclerhelper.helper.widget.NestingAdapter;


/**
 * 条目点击事件adapter
 * Created by hwyMi on 2017/5/11.
 */
public class ItemClickAdapter extends NestingAdapter<ItemClickViewHolder> {

    public ItemClickAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public ItemClickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, viewType);
        ItemClickViewHolder itemClickViewHolder = new ItemClickViewHolder(viewHolder);
        itemClickViewHolder.setOnItemClickListener(itemClickListener);
        return itemClickViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemClickViewHolder holder, int position) {
        adapter.onBindViewHolder(holder.getHolder(), position);
    }
}
