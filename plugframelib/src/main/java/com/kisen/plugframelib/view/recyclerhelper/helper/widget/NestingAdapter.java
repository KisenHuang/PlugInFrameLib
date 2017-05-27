package com.kisen.plugframelib.view.recyclerhelper.helper.widget;

import android.support.v7.widget.RecyclerView;

import com.kisen.plugframelib.view.recyclerhelper.helper.itemclick.OnItemClickListener;


/**
 * 封装Adapter的嵌套容器
 * Created by huang on 2017/5/4.
 */
public abstract class NestingAdapter<VH extends NestingViewHolder> extends RecyclerView.Adapter<VH> {

    protected final RecyclerView.Adapter adapter;
    protected OnItemClickListener itemClickListener;

    public NestingAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return adapter.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        adapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewRecycled(VH holder) {
        adapter.onViewRecycled(holder.getHolder());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onFailedToRecycleView(VH holder) {
        return adapter.onFailedToRecycleView(holder.getHolder());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(VH holder) {
        adapter.onViewAttachedToWindow(holder.getHolder());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewDetachedFromWindow(VH holder) {
        adapter.onViewDetachedFromWindow(holder.getHolder());
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        adapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        adapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        adapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        adapter.onDetachedFromRecyclerView(recyclerView);
    }

}
