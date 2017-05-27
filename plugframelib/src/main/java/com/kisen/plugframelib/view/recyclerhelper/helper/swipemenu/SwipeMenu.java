package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动菜单集合
 * Created by huang on 2017/5/2.
 */
public class SwipeMenu {

    private List<SwipeMenuItem> mItems;
    private int mViewType;
    public int orientation = LinearLayout.HORIZONTAL;

    public SwipeMenu() {
        mItems = new ArrayList<>();
    }

    public void addMenuItem(SwipeMenuItem item) {
        mItems.add(item);
    }

    public void removeMenuItem(SwipeMenuItem item) {
        mItems.remove(item);
    }

    public List<SwipeMenuItem> getMenuItems() {
        return mItems;
    }

    public SwipeMenuItem getMenuItem(int index) {
        return mItems.get(index);
    }

    public int getViewType() {
        return mViewType;
    }

    public void setOrientation(@SwipeMenuGroup.OrientationMode int orientation) {
        if (orientation == SwipeMenuGroup.VERTICAL) {
            this.orientation = LinearLayout.VERTICAL;
        } else {
            this.orientation = LinearLayout.HORIZONTAL;
        }
    }

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }
}
