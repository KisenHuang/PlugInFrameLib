package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 滑动菜单容器
 * Created by huang on 2017/5/2.
 */
public class SwipeMenuGroup extends LinearLayout {

    public static final int LEFT = 1;
    public static final int RIGHT = -1;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private Checker mChecker;

    @IntDef({LEFT, RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Direction {
    }

    @IntDef({HORIZONTAL, VERTICAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    private int mDirection = LEFT;

    public SwipeMenuGroup(Context context) {
        this(context, null);
    }

    public SwipeMenuGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mChecker = new Checker();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void autoOpenMenu(OverScroller scroller, int scrollX, int duration) {
        scroller.startScroll(Math.abs(scrollX), 0, getWidth() - Math.abs(scrollX), 0, duration);
    }

    public void autoCloseMenu(OverScroller scroller, int scrollX, int duration) {
        scroller.startScroll(-Math.abs(scrollX), 0, Math.abs(scrollX), 0, duration);
    }

    public boolean isMenuOpenNotEqual(int scrollX) {
        int i = -getWidth() * getDirection();
        if (mDirection == RIGHT) {
            return scrollX > i;
        } else {
            return scrollX < i;
        }
    }

    public boolean canSwipe() {
        return getChildCount() > 0;
    }

    public boolean isCompleteOpen(int scrollX){
        int i = -getWidth() * getDirection();
        return scrollX == i;
    }

    public boolean isMenuOpen(int scrollX) {
        int i = -getWidth() * getDirection();
        if (mDirection == RIGHT) {
            return scrollX >= i && i != 0;
        } else {
            return scrollX <= i && i != 0;
        }
    }

    public void setDirection(@Direction int mDirection) {
        this.mDirection = mDirection;
    }

    @Direction
    public int getDirection() {
        return mDirection;
    }

    public Checker checkXY(int x, int y) {
        mChecker.x = x;
        mChecker.y = y;
        mChecker.shouldResetSwipe = mChecker.x == 0;
        if (mDirection == RIGHT) {
            if (mChecker.x < 0) {
                mChecker.x = 0;
            }
            if (mChecker.x > getWidth()) {
                mChecker.x = getWidth();
            }
        } else {
            if (mChecker.x >= 0) {
                mChecker.x = 0;
            }
            if (mChecker.x <= -getWidth()) {
                mChecker.x = -getWidth();
            }
        }
        return mChecker;
    }

    public boolean isClickOnContentView(int contentViewWidth, float x) {
        if (mDirection == RIGHT) {
            return x < (contentViewWidth - getWidth());
        } else {
            return x > getWidth();
        }
    }

    static final class Checker {
        int x;
        int y;
        boolean shouldResetSwipe;
    }
}
