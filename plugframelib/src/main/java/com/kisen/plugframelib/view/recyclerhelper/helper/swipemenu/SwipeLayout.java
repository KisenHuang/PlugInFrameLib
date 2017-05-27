package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import com.kisen.plugframelib.R;


/**
 * 带有滑动菜单的Item布局
 * Created by huang on 2017/4/28.
 */
public class SwipeLayout extends FrameLayout {

    public static final int DEFAULT_SCROLLER_DURATION = 200;
    private int mScrollerDuration = DEFAULT_SCROLLER_DURATION;
    private float mOpenPercent = 0.5f;
    private SwipeMenuGroup mSwipeMenuLayoutLeft;
    private SwipeMenuGroup mSwipeMenuLayoutRight;
    private SwipeMenuGroup mCurrentSwipeMenuLayout;
    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mScaledTouchSlop;
    private int mScaledMinimumFlingVelocity;
    private int mScaledMaximumFlingVelocity;
    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;
    private boolean mDragging;
    private boolean shouldResetSwipe;
    private FrameLayout mContentView;

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mScaledTouchSlop = configuration.getScaledTouchSlop();
        mScaledMinimumFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mScaledMaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();

        mScroller = new OverScroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mSwipeMenuLayoutLeft == null) {
            mSwipeMenuLayoutLeft = (SwipeMenuGroup) findViewById(R.id.swipe_menu_left);
        }
        if (mSwipeMenuLayoutRight == null) {
            mSwipeMenuLayoutRight = (SwipeMenuGroup) findViewById(R.id.swipe_menu_right);
        }
        if (mContentView == null) {
            mContentView = (FrameLayout) findViewById(R.id.container_item);
        }

        mSwipeMenuLayoutLeft.setDirection(SwipeMenuGroup.LEFT);
        mSwipeMenuLayoutRight.setDirection(SwipeMenuGroup.RIGHT);
    }

    public void setContentView(View item) {
        if (mContentView == null)
            return;
        mContentView.addView(item);
    }

    /**
     * Set open percentage.
     *
     * @param openPercent such as 0.5F.
     */
    public void setOpenPercent(float openPercent) {
        this.mOpenPercent = openPercent;
    }

    /**
     * Get open percentage.
     *
     * @return such as 0.5F.
     */
    public float getOpenPercent() {
        return mOpenPercent;
    }

    public boolean hasRightMenu() {
        return mSwipeMenuLayoutRight.canSwipe();
    }

    public boolean hasLeftMenu() {
        return mSwipeMenuLayoutLeft.canSwipe();
    }

    public boolean isMenuOpen() {
        return isLeftMenuOpen() || isRightMenuOpen();
    }

    private boolean isLeftMenuOpen() {
        return mSwipeMenuLayoutLeft.isMenuOpen(getScrollX());
    }

    private boolean isRightMenuOpen() {
        return mSwipeMenuLayoutRight.isMenuOpen(getScrollX());
    }

    public boolean isCompleteOpen() {
        return isLeftCompleteOpen() || isRightCompleteOpen();
    }

    public boolean isLeftCompleteOpen() {
        return hasLeftMenu() && mSwipeMenuLayoutLeft.isCompleteOpen(getScrollX());
    }

    public boolean isRightCompleteOpen() {
        return hasRightMenu() && mSwipeMenuLayoutRight.isCompleteOpen(getScrollX());
    }

    public boolean isMenuOpenNotEqual() {
        return isLeftMenuOpenNotEqual() || isRightMenuOpenNotEqual();
    }

    private boolean isLeftMenuOpenNotEqual() {
        return mSwipeMenuLayoutLeft.isMenuOpenNotEqual(getScrollX());
    }

    private boolean isRightMenuOpenNotEqual() {
        return mSwipeMenuLayoutRight.isMenuOpenNotEqual(getScrollX());
    }

    private void smoothOpenMenu() {
        smoothOpenMenu(mScrollerDuration);
    }

    public void smoothOpenLeftMenu() {
        smoothOpenLeftMenu(mScrollerDuration);
    }

    public void smoothOpenRightMenu() {
        smoothOpenRightMenu(mScrollerDuration);
    }

    public void smoothOpenLeftMenu(int duration) {
        if (mSwipeMenuLayoutLeft != null) {
            mCurrentSwipeMenuLayout = mSwipeMenuLayoutLeft;
            smoothOpenMenu(duration);
        }
    }

    public void smoothOpenRightMenu(int duration) {
        if (mSwipeMenuLayoutRight != null) {
            mCurrentSwipeMenuLayout = mSwipeMenuLayoutRight;
            smoothOpenMenu(duration);
        }
    }

    public void setScrollerDuration(int scrollerDuration) {
        this.mScrollerDuration = scrollerDuration;
    }

    /**
     * compute finish duration.
     *
     * @param ev       up event.
     * @param velocity velocity x.
     * @return finish duration.
     */
    private int getSwipeDuration(MotionEvent ev, int velocity) {
        int sx = getScrollX();
        int dx = (int) (ev.getX() - sx);
        final int width = mCurrentSwipeMenuLayout.getWidth();
        final int halfWidth = width / 2;
        final float distanceRatio = Math.min(1f, 1.0f * Math.abs(dx) / width);
        final float distance = halfWidth + halfWidth * distanceInfluenceForSnapDuration(distanceRatio);
        int duration;
        if (velocity > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            final float pageDelta = (float) Math.abs(dx) / width;
            duration = (int) ((pageDelta + 1) * 100);
        }
        duration = Math.min(duration, mScrollerDuration);
        return duration;
    }

    private float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * Math.PI / 2.0f;
        return (float) Math.sin(f);
    }

    private void smoothOpenMenu(int duration) {
        if (mCurrentSwipeMenuLayout != null) {
            mCurrentSwipeMenuLayout.autoOpenMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    public void smoothCloseMenu() {
        smoothCloseMenu(mScrollerDuration);
    }

    public void smoothCloseLeftMenu() {
        if (mSwipeMenuLayoutLeft != null) {
            mCurrentSwipeMenuLayout = mSwipeMenuLayoutLeft;
            smoothCloseMenu();
        }
    }

    public void smoothCloseRightMenu() {
        if (mSwipeMenuLayoutRight != null) {
            mCurrentSwipeMenuLayout = mSwipeMenuLayoutRight;
            smoothCloseMenu();
        }
    }

    public void smoothCloseMenu(int duration) {
        if (mCurrentSwipeMenuLayout != null) {
            mCurrentSwipeMenuLayout.autoCloseMenu(mScroller, getScrollX(), duration);
            invalidate();
        }
    }

    private boolean isSwipeEnable() {
        return mSwipeMenuLayoutLeft.canSwipe() || mSwipeMenuLayoutRight.canSwipe();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercepted = super.onInterceptTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = mLastX = (int) ev.getX();
                mDownY = (int) ev.getY();
                return false;
            }
            case MotionEvent.ACTION_MOVE: {
                int disX = (int) (ev.getX() - mDownX);
                int disY = (int) (ev.getY() - mDownY);
                return Math.abs(disX) > mScaledTouchSlop && Math.abs(disX) > Math.abs(disY);
            }
            case MotionEvent.ACTION_UP: {
                boolean isClick = mCurrentSwipeMenuLayout != null
                        && mCurrentSwipeMenuLayout.isClickOnContentView(getWidth(), ev.getX());
                if (isMenuOpen() && isClick) {
                    smoothCloseMenu();
                    return true;
                }
                return false;
            }
            case MotionEvent.ACTION_CANCEL: {
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                return false;
            }
        }
        return isIntercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(ev);
        int dx;
        int dy;
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = (int) ev.getX();
                mLastY = (int) ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!isSwipeEnable()) break;
                int disX = (int) (mLastX - ev.getX());
                int disY = (int) (mLastY - ev.getY());
                if (!mDragging && Math.abs(disX) > mScaledTouchSlop && Math.abs(disX) > Math.abs(disY)) {
                    mDragging = true;
                }
                if (mDragging) {
                    if (mCurrentSwipeMenuLayout == null || shouldResetSwipe) {
                        if (disX < 0) {
                            mCurrentSwipeMenuLayout = mSwipeMenuLayoutLeft;
                        } else {
                            mCurrentSwipeMenuLayout = mSwipeMenuLayoutRight;
                        }
                    }
                    scrollBy(disX, 0);
                    mLastX = (int) ev.getX();
                    mLastY = (int) ev.getY();
                    shouldResetSwipe = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                dx = (int) (mDownX - ev.getX());
                dy = (int) (mDownY - ev.getY());
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mScaledMaximumFlingVelocity);
                int velocityX = (int) mVelocityTracker.getXVelocity();
                int velocity = Math.abs(velocityX);
                if (velocity > mScaledMinimumFlingVelocity) {
                    if (mCurrentSwipeMenuLayout != null) {
                        int duration = getSwipeDuration(ev, velocity);
                        if (mCurrentSwipeMenuLayout.getDirection() == SwipeMenuGroup.RIGHT) {
                            if (velocityX < 0) {
                                smoothOpenMenu(duration);
                            } else {
                                smoothCloseMenu(duration);
                            }
                        } else {
                            if (velocityX > 0) {
                                smoothOpenMenu(duration);
                            } else {
                                smoothCloseMenu(duration);
                            }
                        }
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                } else {
                    judgeOpenClose(dx, dy);
                }
                mVelocityTracker.clear();
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                if (Math.abs(mDownX - ev.getX()) > mScaledTouchSlop
                        || Math.abs(mDownY - ev.getY()) > mScaledTouchSlop
                        || isLeftMenuOpen()
                        || isRightMenuOpen()) {
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                    super.onTouchEvent(ev);
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                mDragging = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                } else {
                    dx = (int) (mDownX - ev.getX());
                    dy = (int) (mDownY - ev.getY());
                    judgeOpenClose(dx, dy);
                }
                break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset() && mCurrentSwipeMenuLayout != null) {
            if (mCurrentSwipeMenuLayout.getDirection() == SwipeMenuGroup.RIGHT) {
                scrollTo(Math.abs(mScroller.getCurrX()), 0);
                invalidate();
            } else {
                scrollTo(-Math.abs(mScroller.getCurrX()), 0);
                invalidate();
            }
        }
    }

    public SwipeMenuGroup getMenuLeftContainer() {
        return mSwipeMenuLayoutLeft;
    }

    public SwipeMenuGroup getMenuRightContainer() {
        return mSwipeMenuLayoutRight;
    }

    private void judgeOpenClose(int dx, int dy) {
        if (mCurrentSwipeMenuLayout != null) {
            if (Math.abs(getScrollX()) >= (mCurrentSwipeMenuLayout.getWidth() * mOpenPercent)) { // auto open
                if (Math.abs(dx) > mScaledTouchSlop || Math.abs(dy) > mScaledTouchSlop) { // swipe up
                    if (isMenuOpenNotEqual()) {
                        smoothCloseMenu();
                    } else {
                        smoothOpenMenu();
                    }
                } else { // normal up
                    if (isMenuOpen()) {
                        smoothCloseMenu();
                    } else {
                        smoothOpenMenu();
                    }
                }
            } else { // auto close
                smoothCloseMenu();
            }
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mCurrentSwipeMenuLayout == null) {
            super.scrollTo(x, y);
        } else {
            SwipeMenuGroup.Checker checker = mCurrentSwipeMenuLayout.checkXY(x, y);
            shouldResetSwipe = checker.shouldResetSwipe;
            if (checker.x != getScrollX()) {
                super.scrollTo(checker.x, checker.y);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int contentViewHeight;
        if (mContentView != null) {
            int contentViewWidth = mContentView.getMeasuredWidthAndState();
            contentViewHeight = mContentView.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
            int start = getPaddingLeft();
            int top = getPaddingTop() + lp.topMargin;
            mContentView.layout(start, top, start + contentViewWidth, top + contentViewHeight);
        }

        if (mSwipeMenuLayoutLeft != null) {
            View leftMenu = mSwipeMenuLayoutLeft;
            int menuViewWidth = leftMenu.getMeasuredWidthAndState();
            int menuViewHeight = leftMenu.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) leftMenu.getLayoutParams();
            int top = getPaddingTop() + lp.topMargin;
            leftMenu.layout(-menuViewWidth, top, 0, top + menuViewHeight);
        }

        if (mSwipeMenuLayoutRight != null) {
            View rightMenu = mSwipeMenuLayoutRight;
            int menuViewWidth = rightMenu.getMeasuredWidthAndState();
            int menuViewHeight = rightMenu.getMeasuredHeightAndState();
            LayoutParams lp = (LayoutParams) rightMenu.getLayoutParams();
            int top = getPaddingTop() + lp.topMargin;

            int parentViewWidth = getMeasuredWidthAndState();
            rightMenu.layout(parentViewWidth, top, parentViewWidth + menuViewWidth, top + menuViewHeight);
        }
    }
}
