package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.kisen.plugframelib.view.recyclerhelper.helper.widget.NestingAdapter;


/**
 * 自定义SwipeMenu Adapter
 * Created by huang on 2017/5/2.
 */
class SwipeMenuAdapter extends NestingAdapter<SwipeMenuViewHolder> {

    private final SwipeMenuHelper helper;

    SwipeMenuAdapter(SwipeMenuHelper helper, RecyclerView.Adapter adapter) {
        super(adapter);
        this.helper = helper;
    }

    @Override
    public SwipeMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(parent, viewType);
        SwipeMenuViewHolder swipeVH = helper.createSwipeVH(parent, viewHolder);
        swipeVH.setOnItemClickListener(itemClickListener);
        return swipeVH;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(SwipeMenuViewHolder holder, int position) {
        SwipeMenu menuLeft = new SwipeMenu();
        SwipeMenu menuRight = new SwipeMenu();
        if (helper != null && helper.getCreator() != null) {

            SwipeMenuCreator creator = helper.getCreator();
            if (creator instanceof SwipeMenuCreator.LeftCreator) {
                ((SwipeMenuCreator.LeftCreator) creator).create(menuLeft);
            } else if (creator instanceof SwipeMenuCreator.RightCreator) {
                ((SwipeMenuCreator.RightCreator) creator).create(menuRight);
            } else if (creator instanceof SwipeMenuCreator.DoubleCreator) {
                ((SwipeMenuCreator.DoubleCreator) creator).create(menuLeft, menuRight);
            } else {
                throw new IllegalArgumentException("The creator have to be one of the three" +
                        "LeftCreator,RightCreator and DoubleCreator");
            }
        }
        holder.addMenu(menuLeft, menuRight);

        adapter.onBindViewHolder(holder.getHolder(), position);
    }


}
