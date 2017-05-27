package com.kisen.plugframelib.view.recyclerhelper.helper.drag;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 拖拽事件处理
 * Created by huang on 2017/5/4.
 */
class DragItemTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;
    private int dragMode;
    private DragGestureListener gestureListener;

    void setup(RecyclerView recyclerView, OnItemDragListener dragListener) {
        gestureListener = new DragGestureListener(dragListener, recyclerView, dragMode);
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), gestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    void setDragMode(int dragMode) {
        this.dragMode = dragMode;
        if (gestureListener != null)
            gestureListener.setDragMode(dragMode);
    }

    private static class DragGestureListener extends GestureDetector.SimpleOnGestureListener {

        private OnItemDragListener dragListener;
        private RecyclerView recyclerView;
        private int dragMode;

        DragGestureListener(OnItemDragListener dragListener, RecyclerView recyclerView, int dragMode) {
            this.recyclerView = recyclerView;
            this.dragListener = dragListener;
            this.dragMode = dragMode;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            dragListener.onItemClick(computeVH(e));
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return dragListener.onMove(computeVH(e1), dragMode);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            dragListener.onLongPress(computeVH(e));
        }

        private RecyclerView.ViewHolder computeVH(MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                return recyclerView.getChildViewHolder(child);
            }
            return null;
        }

        void setDragMode(int dragMode) {
            this.dragMode = dragMode;
        }
    }
}
