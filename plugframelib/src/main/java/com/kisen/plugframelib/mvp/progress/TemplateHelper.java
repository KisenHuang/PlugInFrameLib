package com.kisen.plugframelib.mvp.progress;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题：toolbar模版助手类
 * 作者：kisen
 * 版本：v1.0.0
 * 创建时间：on 2017/5/15 18:28.
 * <p>
 * 主要负责添加加载动画的助手类
 * </p>
 */
public class TemplateHelper {

    private boolean isLoading;
    private int titleHeight;
    private ViewGroup mContentView;
    private LoadingProgress mProgress;
    private List<View> views = new ArrayList<>();


    public TemplateHelper(ViewGroup contentView) {
        this.mContentView = contentView;
        setupLoadView();
    }

    private void setupLoadView() {
        if (mProgress == null)
            return;
        View contentView = mContentView.getChildAt(0);
        if (contentView instanceof ViewGroup) {
            ViewGroup contentGroup = (ViewGroup) contentView;
            for (int i = 0; i < contentGroup.getChildCount(); i++) {
                views.add(contentGroup.getChildAt(i));
            }
        }
        int marginTop = titleHeight;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, marginTop, 0, 0);
        mContentView.addView(mProgress, layoutParams);
    }

    public void openLoadAnim() {
        if (isLoading || mProgress == null)
            return;
        mProgress.show();
        isLoading = true;
    }

    public void closeLoadAnim() {
        if (!isLoading || mProgress == null)
            return;
        mProgress.dismiss();
        isLoading = false;
    }

    /**
     * 父容器获取焦点，禁止子控件自动获取焦点
     * 布局中有EditText时，禁止弹出软键盘
     */
    public void containerFocus() {
        if (mProgress != null)
            mContentView.getChildAt(0).setFocusable(true);
    }

    public void setLoadProgress(LoadingProgress progress) {
        mProgress = progress;
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void destroy() {
        closeLoadAnim();
        mProgress = null;
        views.clear();
        views = null;
        mContentView = null;
    }
}
