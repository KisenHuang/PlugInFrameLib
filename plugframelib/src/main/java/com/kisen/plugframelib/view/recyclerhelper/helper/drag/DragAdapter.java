package com.kisen.plugframelib.view.recyclerhelper.helper.drag;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


import com.kisen.plugframelib.view.recyclerhelper.helper.widget.NestingAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 拖拽效果适配器嵌套容器
 * Created by huang on 2017/5/4.
 */
class DragAdapter extends NestingAdapter<DragViewHolder> implements IDragAdapter.IDragBusyAdapter {

    private final List mSource;
    private IDragAdapter.IDragBusyAdapter busyAdapter;
    private DragHelper.DragViewEntity dragViewEntity;

    @SuppressWarnings("unchecked")
    DragAdapter(RecyclerView.Adapter adapter) {
        super(adapter);
        mSource = new ArrayList();
        if (adapter instanceof IDragAdapter.IDragLazyAdapter) {
            mSource.addAll(((IDragAdapter.IDragLazyAdapter) adapter).getSource());
        } else if (adapter instanceof IDragAdapter.IDragBusyAdapter) {
            busyAdapter = (IDragAdapter.IDragBusyAdapter) adapter;
        }
    }

    void setDragViewEntity(DragHelper.DragViewEntity dragViewEntity) {
        this.dragViewEntity = dragViewEntity;
    }

    @Override
    public DragViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (busyAdapter != null) {
            busyAdapter.setOnItemClickListener(itemClickListener);
        }
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, viewType);
        DragView dragView = new DragView(parent.getContext());
        dragView.setContentView(viewHolder.itemView);
        dragView.setDragViewEntity(dragViewEntity);
        ItemHelper helper = new ItemHelper();
        helper.dragView = dragView;
        helper.viewHolder = viewHolder;
        DragViewHolder dragViewHolder = new DragViewHolder(helper);
        if (mSource.size() > 0) {
            dragViewHolder.setOnItemClickListener(itemClickListener);
        }
        return dragViewHolder;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(DragViewHolder holder, int position) {
        adapter.onBindViewHolder(holder.getHolder(), position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (busyAdapter != null) {
            busyAdapter.onItemMove(fromPosition, toPosition);
            return true;
        } else if (mSource.size() > 0) {
            Collections.swap(mSource, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        if (busyAdapter != null) {
            busyAdapter.onItemDismiss(position);
        } else if (mSource.size() > 0) {
            mSource.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class ItemHelper{
        public DragView dragView;
        public RecyclerView.ViewHolder viewHolder;
    }
}
