package com.kisen.plugframelib.view.recyclerhelper.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

import com.kisen.plugframelib.R;


/**
 * @author kisen
 * @TIME 2016/8/4 9:59
 */
public class DividerItemDecoration extends BaseDividerItemDecoration {

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

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider_bg);
        mIntrinsicHeight = mDivider.getIntrinsicHeight();
        mIntrinsicWidth = mDivider.getIntrinsicWidth();
        setOrientation(orientation);
    }

    public DividerItemDecoration setDivider(Drawable divider) {
        this.mDivider = divider;
        return this;
    }

    public DividerItemDecoration setDividerSpace(int dividerSpace) {
        mIntrinsicHeight = (int) (dividerSpace * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mIntrinsicWidth = (int) (dividerSpace * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        return this;
    }

    /**
     * 设置 divider 边距
     */
    public DividerItemDecoration setDividerMargin(int left, int top, int right, int bottom) {
        mDividerMarginLeft = (int) (left * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mDividerMarginTop = (int) (top * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mDividerMarginRight = (int) (right * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mDividerMarginBottom = (int) (bottom * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        return this;
    }

    @Override
    public DividerItemDecoration setRecyclerViewPadding(int left, int top, int right, int bottom) {
        mRecyclerViewLeft = (int) (left * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mRecyclerViewTop = (int) (top * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mRecyclerViewRight = (int) (right * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        mRecyclerViewBottom = (int) (bottom * (Resources.getSystem().getDisplayMetrics().densityDpi / 160f));
        return this;
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft() + mDividerMarginLeft;
        final int right = parent.getWidth() - parent.getPaddingRight() - mDividerMarginRight;

        final int childCount = parent.getChildCount();
        final RecyclerView.Adapter adapter = parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int adapterPosition = parent.getChildViewHolder(child).getAdapterPosition();
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin + mDividerMarginTop;
            int bottom;
            try {
                boolean lastShown = adapter.getItemCount() - 1 == adapterPosition;
                boolean firstShown = 0 == adapterPosition;
                if ((checkSpecialViewType(adapter.getItemViewType(i)) && firstShown) || lastShown)
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

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop() + mDividerMarginTop;
        final int bottom = parent.getHeight() - parent.getPaddingBottom() - mDividerMarginBottom;

        final int childCount = parent.getChildCount();
        final RecyclerView.Adapter adapter = parent.getAdapter();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int adapterPosition = parent.getChildViewHolder(child).getAdapterPosition();
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin + mDividerMarginLeft;
            int right;
            try {
                boolean lastShown = adapter.getItemCount() - 1 == adapterPosition;
                boolean firstShown = 0 == adapterPosition;
                if ((checkSpecialViewType(adapter.getItemViewType(i)) && firstShown) || lastShown)
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

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();
        boolean isFirstItem = itemPosition == 0;
        boolean isLastItem = childCount == itemPosition + 1;
        boolean isSpecialView = checkSpecialViewType(parent.getAdapter().getItemViewType(itemPosition));
        if (isSpecialView) {
            outRect.set(0, 0, 0, 0);
            return;
        }
        if (mOrientation == VERTICAL_LIST) {
            int top = isFirstItem ? mRecyclerViewTop : mDividerMarginBottom;
            int bottom = isLastItem ? mRecyclerViewBottom : mIntrinsicHeight;
            outRect.set(mRecyclerViewLeft, top, mRecyclerViewRight, mDividerMarginBottom + bottom);
        } else {
            int left = isFirstItem ? mRecyclerViewLeft : mDividerMarginRight;
            int right = (isLastItem ? mRecyclerViewRight : mIntrinsicWidth);
            outRect.set(left, mRecyclerViewTop, mDividerMarginRight + right, mRecyclerViewBottom);
        }
    }
}
