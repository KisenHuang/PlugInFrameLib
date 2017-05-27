package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kisen.plugframelib.R;
import com.kisen.plugframelib.view.recyclerhelper.helper.itemclick.OnItemClickListener;
import com.kisen.plugframelib.view.recyclerhelper.helper.widget.IAttachRecyclerView;
import com.kisen.plugframelib.view.recyclerhelper.helper.widget.ItemClick;

import java.lang.ref.WeakReference;

/**
 * 滑动菜单助手类
 * Created by huang on 2017/5/2.
 */
public class SwipeMenuHelper implements ItemClick<SwipeMenuHelper>, IAttachRecyclerView {

    /**
     * 是否开启侧滑菜单
     */
    private boolean isOpen = false;
    /**
     * 侧滑菜单生成器
     */
    private SwipeMenuCreator creator;
    /**
     * 完善侧滑菜单功能的OnItemTouchListener
     */
    private SwipeTouchListener touchListener;
    private final WeakReference<Context> context;
    /**
     * 侧滑菜单点击事件
     */
    private OnSwipeMenuClickListener menuClickListener;
    private OnItemClickListener itemClickListener;
    /**
     * 侧滑菜单动画时间
     */
    private int swipeDuration = SwipeLayout.DEFAULT_SCROLLER_DURATION;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public SwipeMenuHelper(WeakReference<Context> context) {
        this.context = context;
    }

    /**
     * 打开Item的侧滑菜单，初始化方法
     *
     * @param creator  菜单生成器
     * @param listener 滑动菜单点击事件
     */
    public SwipeMenuHelper openSwipeMenu(SwipeMenuCreator creator, OnSwipeMenuClickListener listener) {
        this.creator = creator;
        menuClickListener = listener;
        isOpen = true;
        return this;
    }

    /**
     * 设置适配器，初始化方法
     */
    @Override
    public SwipeMenuHelper setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        return this;
    }

    /**
     * 设置布局管理器，初始化方法
     */
    public SwipeMenuHelper setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        return this;
    }

    /**
     * 设置侧滑菜单动画时间，初始化方法
     */
    public SwipeMenuHelper setSwipeDuration(int duration) {
        swipeDuration = duration;
        return this;
    }

    @Override
    public SwipeMenuHelper setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
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
                SwipeMenuAdapter swipeMenuAdapter = new SwipeMenuAdapter(this, mAdapter);
                swipeMenuAdapter.setOnItemClickListener(itemClickListener);
                mAdapter = swipeMenuAdapter;
            }
        }
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        }
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        if (isOpen) {
            touchListener = new SwipeTouchListener(context.get());
            recyclerView.addOnItemTouchListener(touchListener);
        }
    }

    /**
     * 该方法不能再初始化时调用
     * <p>该方法在{@link Activity#onResume()}之后调用</p>
     *
     * @param position 开启的位置
     */
    public void openMenuAt(int position) {
        SwipeLayout childAt = (SwipeLayout) mLayoutManager.getChildAt(position);
        if (childAt == null)
            return;
        if (touchListener == null)
            return;
        touchListener.manualOpenSwipe(position, childAt);
        if (childAt.hasLeftMenu()) {
            childAt.smoothOpenLeftMenu();
        } else {
            childAt.smoothOpenRightMenu();
        }
    }

    /*-------------------------------------------私有方法------------------------------------------*/

    SwipeMenuCreator getCreator() {
        return creator;
    }

    /**
     * 创建可滑动的ViewHolder
     */
    SwipeMenuViewHolder createSwipeVH(ViewGroup parent, RecyclerView.ViewHolder viewHolder) {
        ItemHolder helper = createItemHolder(parent, viewHolder);
        return new SwipeMenuViewHolder(helper);
    }

    Context getContext() {
        return context.get();
    }

    private ItemHolder createItemHolder(ViewGroup parent, RecyclerView.ViewHolder viewHolder) {
        Context context = parent.getContext();
        SwipeLayout layout = (SwipeLayout) LayoutInflater.from(context).inflate(R.layout.swipe_menu, parent, false);
        layout.setContentView(viewHolder.itemView);
        layout.setScrollerDuration(swipeDuration);

        ItemHolder helper = new ItemHolder();

        helper.setContext(context);
        helper.setSwipeLayout(layout);
        helper.setViewHolder(viewHolder);
        helper.setMenuClickListener(menuClickListener);
        return helper;
    }
    /**
     * ViewHolder相关数据类
     */
    static class ItemHolder {

        private RecyclerView.ViewHolder viewHolder;
        private SwipeLayout swipeLayout;
        private Context context;
        private OnSwipeMenuClickListener menuClickListener;

        OnSwipeMenuClickListener getMenuClickListener() {
            return menuClickListener;
        }

        void setMenuClickListener(OnSwipeMenuClickListener menuClickListener) {
            this.menuClickListener = menuClickListener;
        }

        Context getContext() {
            return context;
        }

        void setContext(Context context) {
            this.context = context;
        }

        RecyclerView.ViewHolder getViewHolder() {
            return viewHolder;
        }

        void setViewHolder(RecyclerView.ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        SwipeLayout getSwipeLayout() {
            return swipeLayout;
        }

        void setSwipeLayout(SwipeLayout swipeLayout) {
            this.swipeLayout = swipeLayout;
        }
    }
}
