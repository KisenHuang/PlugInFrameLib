package com.kisen.plugframelib.view.recyclerhelper.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.kisen.plugframelib.R;


/**
 * @author kisen
 * @TIME 2016/8/4 9:56
 */
public class DividerGridItemDecoration extends BaseDividerItemDecoration {

    private Drawable mDivider;

    private int mIntrinsicHeight;

    private int mIntrinsicWidth;

    private int mRecyclerViewLeft = 0;
    private int mRecyclerViewTop = 0;
    private int mRecyclerViewRight = 0;
    private int mRecyclerViewBottom = 0;

    private int mDividerMarginLeft = 0;
    private int mDividerMarginTop = 0;
    private int mDividerMarginRight = 0;
    private int mDividerMarginBottom = 0;

    public DividerGridItemDecoration(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider_bg);
        mIntrinsicHeight = mDivider.getIntrinsicHeight();
        mIntrinsicWidth = mDivider.getIntrinsicWidth();
    }

    public DividerGridItemDecoration setDivider(Drawable divider) {
        this.mDivider = divider;
        return this;
    }

    public DividerGridItemDecoration setDividerSpace(int dividerHeight, int dividerWidth) {
        mIntrinsicHeight = (int) (dividerHeight * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mIntrinsicWidth = (int) (dividerWidth * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        return this;
    }

    @Override
    public DividerGridItemDecoration setRecyclerViewPadding(int left, int top, int right, int bottom) {
        this.mRecyclerViewLeft = (int) (left * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        this.mRecyclerViewTop = (int) (top * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        this.mRecyclerViewRight = (int) (right * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        this.mRecyclerViewBottom = (int) (bottom * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        return this;
    }

    /**
     * 设置 divider 边距
     */
    public DividerGridItemDecoration setDividerMargin(int left, int top, int right, int bottom) {
        this.mDividerMarginLeft = (int) (left * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        this.mDividerMarginTop = (int) (top * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        this.mDividerMarginRight = (int) (right * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        this.mDividerMarginBottom = (int) (bottom * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {

        drawHorizontal(c, parent);
        drawVertical(c, parent);

    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        final RecyclerView.Adapter adapter = parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin
                    + mIntrinsicWidth;
            final int top = child.getBottom() + params.bottomMargin;
            int bottom;
            try {
                if (checkSpecialViewType(adapter.getItemViewType(i)))
                    bottom = top;
                else
                    bottom = top + mIntrinsicHeight;
            } catch (Exception e) {
                e.printStackTrace();
                bottom = top + mIntrinsicHeight;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        final RecyclerView.Adapter adapter = parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            int right;
            try {
                if (checkSpecialViewType(adapter.getItemViewType(i)))
                    right = left;
                else
                    right = left + mIntrinsicWidth;
            } catch (Exception e) {
                e.printStackTrace();
                right = left + mIntrinsicWidth;
            }

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount,
                                  int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 1) {// 如果是第一列，则需要绘制左边
                    return true;
                }
            } else {
                // 如果是第一列，则需要绘制左边
                if (pos < spanCount)
                    return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 1)// 如果是第一列，则需要绘制左边
                {
                    return true;
                }
            } else {
                if (pos < spanCount)// 如果是第一行，则需要绘制左边
                    return true;
            }
        }
        return false;
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount,
                                 int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager).getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) {// 如果是最后一列，则不需要绘制右边
                    return true;
                }
            } else {
                int s = childCount % spanCount;
                childCount = childCount - s == 0 ? spanCount : s;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                int s = childCount % spanCount;
                childCount = childCount - s == 0 ? spanCount : s;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }

    private boolean isFirstRaw(RecyclerView parent, int pos, int spanCount,
                               int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int orientation = ((GridLayoutManager) layoutManager)
                    .getOrientation();
            if (orientation == LinearLayoutManager.VERTICAL) {
                if (pos < spanCount)
                    return true;
            } else {
                if ((pos + 1) % spanCount == 1)
                    return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 如果是第一行，则需要绘制底部
                if (pos < spanCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是第一行，则需要绘制底部
                if ((pos + 1) % spanCount == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int s = childCount % spanCount;
            childCount = childCount - s == 0 ? spanCount : s;
            if (pos >= childCount)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                int s = childCount % spanCount;
                childCount = childCount - s == 0 ? spanCount : s;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        int headerCount = getHeaderCount(parent);
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount() - headerCount;

        boolean firstColumn = isFirstColumn(parent, itemPosition - headerCount, spanCount, childCount);
        boolean firstRaw = isFirstRaw(parent, itemPosition - headerCount, spanCount, childCount);
        boolean lastRaw = isLastRaw(parent, itemPosition - headerCount, spanCount, childCount);
        boolean lastColumn = isLastColumn(parent, itemPosition - headerCount, spanCount, childCount);

        int left = firstColumn ? mRecyclerViewLeft : mIntrinsicWidth / 2 + mDividerMarginRight;
        int right = lastColumn ? mRecyclerViewRight : mIntrinsicWidth / 2 + mDividerMarginLeft;
        int top = firstRaw ? mRecyclerViewTop : mIntrinsicHeight / 2 + mDividerMarginBottom;
        int bottom = lastRaw ? mRecyclerViewBottom : mIntrinsicHeight / 2 + mDividerMarginTop;

        boolean isSpecialView = checkSpecialViewType(parent.getAdapter().getItemViewType(itemPosition));
        if (isSpecialView)
            outRect.set(0, 0, 0, 0);
        else
            outRect.set(left, top, right, bottom);
    }
}
