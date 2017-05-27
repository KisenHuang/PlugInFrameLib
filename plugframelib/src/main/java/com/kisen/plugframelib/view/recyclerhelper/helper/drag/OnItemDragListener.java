package com.kisen.plugframelib.view.recyclerhelper.helper.drag;

import android.support.v7.widget.RecyclerView;

/**
 * 拖拽监听
 * Created by huang on 2017/5/4.
 */

public interface OnItemDragListener {

    boolean onItemClick(RecyclerView.ViewHolder viewHolder);

    boolean onMove(RecyclerView.ViewHolder viewHolder, @DragHelper.DragMode int dragMode);

    void onLongPress(RecyclerView.ViewHolder viewHolder);

    public abstract class SimpleDragListener implements OnItemDragListener {

        @Override
        public boolean onItemClick(RecyclerView.ViewHolder viewHolder) {
            return false;
        }

        @Override
        public boolean onMove(RecyclerView.ViewHolder viewHolder, @DragHelper.DragMode int dragMode) {
            return false;
        }

        @Override
        public void onLongPress(RecyclerView.ViewHolder viewHolder) {

        }
    }
}
