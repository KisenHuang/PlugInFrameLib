package com.kisen.plugframelib.view.recyclerhelper.helper.drag;

import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.kisen.plugframelib.view.recyclerhelper.helper.itemclick.OnItemClickListener;
import com.kisen.plugframelib.view.recyclerhelper.helper.widget.IAttachRecyclerView;
import com.kisen.plugframelib.view.recyclerhelper.helper.widget.ItemClick;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * RecyclerView item拖动助手类
 * Created by huang on 2017/5/4.
 */
public class DragHelper implements ItemClick<DragHelper>, IAttachRecyclerView {

    public static final int NORMAL = 0;
    public static final int CUSTOM = 1;
    public static final int NONE = 2;
    private boolean isOpen = true;
    private RecyclerView.Adapter mAdapter;
    private DragCallback dragCallback;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnItemDragListener dragListener;
    private OnItemClickListener itemClickListener;
    private DragItemTouchListener dragItemTouchListener;
    private DragViewEntity dragViewEntity;

    @Override
    public DragHelper setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
        return this;
    }

    @IntDef({CUSTOM, NONE, NORMAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DragMode {
    }

    public DragHelper() {
        dragViewEntity = new DragViewEntity();
        dragItemTouchListener = new DragItemTouchListener();
    }

    /**
     * 开启Item的拖拽效果
     *
     * @param dragListener 拖拽监听
     * @return DragHelper对象
     */
    public DragHelper openItemDrag(OnItemDragListener dragListener) {
        this.dragListener = dragListener;
        this.isOpen = true;
        return this;
    }

    /**
     * 设置适配器，初始化方法
     */
    @Override
    public DragHelper setAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        return this;
    }

    /**
     * 设置拖拽时显示View
     */
    public DragHelper setDragingView(int resId, int gravity) {
        dragViewEntity.view = null;
        dragViewEntity.resId = resId;
        dragViewEntity.gravity = gravity;
        return this;
    }

    /**
     * 设置拖拽时显示View
     */
    public DragHelper setDragingView(View view, int gravity) {
        dragViewEntity.resId = 0;
        dragViewEntity.view = view;
        dragViewEntity.gravity = gravity;
        notifyAdapter();
        return this;
    }

    /**
     * 设置布局管理器，初始化方法
     */
    public DragHelper setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        return this;
    }

    /**
     * 设置拖拽模式
     */
    public DragHelper setDragMode(@DragMode int dragMode) {
        dragItemTouchListener.setDragMode(dragMode);
        return this;
    }

    /**
     * 初始化结束方法
     */
    @Override
    public void attachRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("The RecyclerView can not be null!");
        }
        if (mAdapter == null) {
            throw new NullPointerException("The Adapter can not be null!");
        } else {
            if (isOpen) {
                DragAdapter dragAdapter = new DragAdapter(mAdapter);
                dragAdapter.setDragViewEntity(dragViewEntity);
                dragAdapter.setOnItemClickListener(itemClickListener);
                dragCallback = new DragCallback(dragAdapter);
                mAdapter = dragAdapter;
            }
        }
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        }
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        if (isOpen) {
            dragItemTouchListener.setup(recyclerView, dragListener);
            recyclerView.addOnItemTouchListener(dragItemTouchListener);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    private void notifyAdapter() {
        if (mAdapter instanceof DragAdapter) {
            ((DragAdapter) mAdapter).setDragViewEntity(dragViewEntity);
        }
    }

    static class DragViewEntity {
        int resId;
        View view;
        int gravity;
    }

}
