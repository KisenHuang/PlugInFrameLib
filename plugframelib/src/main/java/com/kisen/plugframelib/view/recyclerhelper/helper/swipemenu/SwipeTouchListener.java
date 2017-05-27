package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义OnItemTouchListener
 * <p>
 * 主要作用是保证只能打开一个滑动菜单
 * 原理：
 * 将RecyclerView 中的Touch事件转移到TouchListener中进行处理
 * </p>
 * Created by huang on 2017/5/2.
 */
class SwipeTouchListener implements RecyclerView.OnItemTouchListener {

    private static final int INVALID_POSITION = -1;
    private int mDownX;
    private int mDownY;
    private int mOldTouchedPosition = INVALID_POSITION;
    private SwipeLayout mOldSwipedLayout;
    private int mScaledTouchSlop;

    SwipeTouchListener(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mScaledTouchSlop = configuration.getScaledTouchSlop();
    }

    /**
     * 通过代码打开滑动菜单时，需要调用该方法
     *
     * @param position    滑动的位置
     * @param swipeLayout 滑动的布局
     */
    void manualOpenSwipe(int position, SwipeLayout swipeLayout) {
        mOldTouchedPosition = position;
        mOldSwipedLayout = swipeLayout;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        boolean isIntercepted = false;
        if (e.getPointerCount() > 1)
            return true;
        int action = e.getAction();
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = x;
                mDownY = y;
                isIntercepted = false;

                int touchingPosition = rv.getChildAdapterPosition(rv.findChildViewUnder(x, y));
                if (touchingPosition != mOldTouchedPosition && mOldSwipedLayout != null
                        && mOldSwipedLayout.isMenuOpen()) {
                    mOldSwipedLayout.smoothCloseMenu();
                    isIntercepted = true;
                }

                if (isIntercepted) {
                    mOldSwipedLayout = null;
                    mOldTouchedPosition = INVALID_POSITION;
                } else {
                    RecyclerView.ViewHolder vh = rv.findViewHolderForAdapterPosition(touchingPosition);
                    if (vh != null) {
                        View itemView = getSwipeMenuView(vh.itemView);
                        if (itemView instanceof SwipeLayout) {
                            mOldSwipedLayout = (SwipeLayout) itemView;
                            mOldTouchedPosition = touchingPosition;
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mOldSwipedLayout == null) break;
                ViewParent viewParent = rv.getParent();
                if (viewParent == null) break;

                int disX = mDownX - x;
                int disY = mDownY - y;
                // 向左滑，显示右侧菜单，或者关闭左侧菜单。
                boolean showRightCloseLeft = disX > 0 && (mOldSwipedLayout.hasRightMenu() || mOldSwipedLayout
                        .isLeftCompleteOpen());
                // 向右滑，显示左侧菜单，或者关闭右侧菜单。
                boolean showLeftCloseRight = disX < 0 && (mOldSwipedLayout.hasLeftMenu() || mOldSwipedLayout
                        .isRightCompleteOpen());
                viewParent.requestDisallowInterceptTouchEvent(showRightCloseLeft || showLeftCloseRight);

//                //禁止按着打开菜单的Item上下滑动
                int touchPos = rv.getChildAdapterPosition(rv.findChildViewUnder(x, y));
                if (touchPos == mOldTouchedPosition && mOldSwipedLayout != null
                        && mOldSwipedLayout.isCompleteOpen() && Math.abs(disY) > 0) {
//                    mOldSwipedLayout.smoothCloseMenu();
                    isIntercepted = true;
                }
                break;
            }
        }
        return isIntercepted;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        int action = e.getAction();
        int y = (int) e.getY();
        int x = (int) e.getX();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_MOVE:
                int touchPos = rv.getChildAdapterPosition(rv.findChildViewUnder(x, y));
                if (mOldSwipedLayout != null) {
                        if (touchPos == mOldTouchedPosition) {
                            mOldSwipedLayout.onTouchEvent(e);
                    } else if (mOldSwipedLayout.isCompleteOpen()) {
                            mOldSwipedLayout.smoothCloseMenu();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private View getSwipeMenuView(View itemView) {
        if (itemView instanceof SwipeLayout)
            return itemView;
        List<View> unvisited = new ArrayList<>();
        unvisited.add(itemView);
        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            if (!(child instanceof ViewGroup)) { // view
                continue;
            }
            if (child instanceof SwipeLayout)
                return child;
            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                unvisited.add(group.getChildAt(i));
            }
        }
        return itemView;
    }

}
