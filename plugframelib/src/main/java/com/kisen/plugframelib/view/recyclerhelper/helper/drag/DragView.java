package com.kisen.plugframelib.view.recyclerhelper.helper.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 拖拽View
 * Created by huang on 2017/5/5.
 */
public class DragView extends FrameLayout {

    private FrameLayout mContentView;
    private FrameLayout mDragView;

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mContentView = new FrameLayout(getContext());
        mDragView = new FrameLayout(getContext());

        addView(mContentView);
        addView(mDragView);
    }

    void setContentView(View view) {
        mContentView.addView(view);
    }

    void setDragViewEntity(DragHelper.DragViewEntity dragViewEntity) {
        mDragView.removeAllViews();
        if (dragViewEntity == null)
            return;
        View drag = dragViewEntity.view;
        if (dragViewEntity.resId != 0) {
            drag = LayoutInflater.from(getContext()).inflate(dragViewEntity.resId, mDragView, false);
        }
        if (drag != null) {
            LayoutParams params = new LayoutParams(drag.getLayoutParams());
            params.gravity = dragViewEntity.gravity;
            mDragView.addView(drag, params);
        }
    }

    void hintDrag(){
        mDragView.setVisibility(GONE);
    }

    void showDrag(){
        mDragView.setVisibility(VISIBLE);
    }
}
