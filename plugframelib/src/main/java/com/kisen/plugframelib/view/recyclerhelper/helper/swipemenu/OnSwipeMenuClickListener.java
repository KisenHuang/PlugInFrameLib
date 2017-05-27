package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

/**
 * 滑动菜单点击事件监听
 * Created by huang on 2017/5/2.
 */
public interface OnSwipeMenuClickListener {

    /**
     * @param direction    菜单位置，左菜单，右菜单
     * @param menuId       菜单Id
     * @param itemPosition 点击菜单所在条目的位置
     */
    void onSwipeMenuClick(int direction, int menuId, int itemPosition);

}
