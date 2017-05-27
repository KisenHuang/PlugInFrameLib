package com.kisen.plugframelib.view.recyclerhelper.helper;

import android.content.Context;


import com.kisen.plugframelib.view.recyclerhelper.helper.drag.DragHelper;
import com.kisen.plugframelib.view.recyclerhelper.helper.itemclick.ItemClickHelper;
import com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu.SwipeMenuHelper;
import com.kisen.plugframelib.view.recyclerhelper.helper.widget.SwipeLog;

import java.lang.ref.WeakReference;

/**
 * 提供对RecyclerView的个性化设置
 * Created by huang on 2017/4/28.
 */
public class RecyclerHelper {

    private final WeakReference<Context> context;
    private SwipeMenuHelper mSwipeMenuHelper;
    private DragHelper mDragHelper;
    private ItemClickHelper mItemClickListener;
    private static RecyclerHelper helper;

    /**
     * 获取Helper对象
     *
     * @param context 上下文对象，并作为标记使用
     * @return 如果传入context和本类context相同，返回静态实例,否则创建新的实例
     */
    public static RecyclerHelper get(Context context) {
        if (helper != null && helper.context.get() == context) {
            return helper;
        } else {
            helper = new RecyclerHelper(context);
            return helper;
        }
    }

    private RecyclerHelper(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public SwipeMenuHelper getSwipeMenuHelper() {
        if (mSwipeMenuHelper == null) {
            mSwipeMenuHelper = new SwipeMenuHelper(context);
        }
        return mSwipeMenuHelper;
    }

    public ItemClickHelper getItemClickHelper() {
        if (mItemClickListener == null) {
            mItemClickListener = new ItemClickHelper();
        }
        return mItemClickListener;
    }

    public DragHelper getDragHelper() {
        if (mDragHelper == null) {
            mDragHelper = new DragHelper();
        }
        return mDragHelper;
    }

    /**
     * 设置调试模式
     *
     * @param debug 是否调试
     */
    public RecyclerHelper setDebug(boolean debug) {
        SwipeLog.setDebug(debug);
        return this;
    }

}
