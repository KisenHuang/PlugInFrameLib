package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kisen.plugframelib.view.recyclerhelper.helper.widget.NestingViewHolder;


/**
 * 自定义SwipeMenu ViewHolder
 * Created by huang on 2017/5/2.
 */
final class SwipeMenuViewHolder extends NestingViewHolder {

    /**
     * 左侧滑动菜单
     */
    private final SwipeMenuGroup menuLeftContainer;
    /**
     * 右侧滑动菜单
     */
    private final SwipeMenuGroup menuRightContainer;
    /**
     * 上下文
     */
    private Context context;
    /**
     * 原来的ViewHolder
     */
    private RecyclerView.ViewHolder mViewHolder;
    /**
     * 滑动菜单的点击事件
     */
    private OnSwipeMenuClickListener menuClickListener;

    SwipeMenuViewHolder(SwipeMenuHelper.ItemHolder helper) {
        super(helper.getSwipeLayout());
        mViewHolder = helper.getViewHolder();
        context = helper.getContext();
        menuLeftContainer = helper.getSwipeLayout().getMenuLeftContainer();
        menuRightContainer = helper.getSwipeLayout().getMenuRightContainer();
        menuClickListener = helper.getMenuClickListener();
        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHolder() {
        return mViewHolder;
    }

    public Context getContext() {
        return context;
    }

    void addMenu(SwipeMenu menuLeft, SwipeMenu menuRight) {

        resetMenu(menuLeft, menuRight);

        for (int i = 0; i < menuLeft.getMenuItems().size(); i++) {
            SwipeMenuItem menuItem = menuLeft.getMenuItem(i);
            if (menuItem.getId() == 0)
                menuItem.setId(i);
            addItem(menuLeftContainer, menuItem);
        }

        for (int i = 0; i < menuRight.getMenuItems().size(); i++) {
            SwipeMenuItem menuItem = menuRight.getMenuItem(i);
            if (menuItem.getId() == 0)
                menuItem.setId(i);
            addItem(menuRightContainer, menuItem);
        }
    }

    private void resetMenu(SwipeMenu menuLeft, SwipeMenu menuRight) {
        menuLeftContainer.removeAllViews();
        menuRightContainer.removeAllViews();

        menuLeftContainer.setOrientation(menuLeft.orientation);
        menuRightContainer.setOrientation(menuRight.orientation);
    }

    private void addItem(final SwipeMenuGroup menuContainer, final SwipeMenuItem item) {
        int width, height;
        float weight = 0;
        if (menuContainer.getOrientation() == LinearLayout.HORIZONTAL) {
            width = item.getWidth() == 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : item.getWidth();
            height = LinearLayout.LayoutParams.MATCH_PARENT;
        } else {
            width = item.getWidth() == 0 ? LinearLayout.LayoutParams.MATCH_PARENT : item.getWidth();
            height = LinearLayout.LayoutParams.WRAP_CONTENT;
            weight = 1;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height, weight);
        LinearLayout parent = new LinearLayout(context);
        parent.setId(item.getId());
        parent.setGravity(Gravity.CENTER);
        parent.setOrientation(LinearLayout.VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            parent.setBackground(item.getBackground());
        } else {
            parent.setBackgroundDrawable(item.getBackground());
        }

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }

        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.onSwipeMenuClick(menuContainer.getDirection(),
                            item.getId(), getAdapterPosition());
                }
            }
        });

        menuContainer.addView(parent, params);
    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView iv = new ImageView(context);
        iv.setImageDrawable(item.getIcon());
        return iv;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView tv = new TextView(context);
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }
}
