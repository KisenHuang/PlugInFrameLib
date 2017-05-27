package com.kisen.plugframelib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kisen.plugframelib.R;


/**
 * 标题：可禁止滑动切换的ViewPager
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/24 09:47.
 */
public class NoSlidePager extends ViewPager {

    private static final boolean DEFAULT_SLIDING = false;
    private boolean mSliding;

    public NoSlidePager(Context context) {
        this(context, null);
    }

    public NoSlidePager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NoSlidePager, 0, 0);
        mSliding = a.getBoolean(R.styleable.NoSlidePager_sliding, DEFAULT_SLIDING);
        a.recycle();
    }

    public void setSliding(boolean mSliding) {
        this.mSliding = mSliding;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mSliding && super.onTouchEvent(event);

    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * 设置在切换时去掉切换动画
     */
    @Override
    public void setCurrentItem(int item) {
        if (mSliding) {
            super.setCurrentItem(item);
        } else {
            super.setCurrentItem(item, false);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mSliding && super.onInterceptTouchEvent(event);
    }
}
