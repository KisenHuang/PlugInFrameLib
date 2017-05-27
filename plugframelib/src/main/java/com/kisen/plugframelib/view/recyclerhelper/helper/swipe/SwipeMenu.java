package com.kisen.plugframelib.view.recyclerhelper.helper.swipe;

import android.content.Context;


import com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;


/**
 * @Title :
 * @Description :
 * @Version :
 * Created by huang on 2016/12/5.
 */

public class SwipeMenu {
    private Context mContext;
    private List<SwipeMenuItem> mItems;
    private int mViewType;

    public SwipeMenu(Context context) {
        mContext = context;
        mItems = new ArrayList<SwipeMenuItem>();
    }

    public Context getContext() {
        return mContext;
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

    public void setViewType(int viewType) {
        this.mViewType = viewType;
    }
}
