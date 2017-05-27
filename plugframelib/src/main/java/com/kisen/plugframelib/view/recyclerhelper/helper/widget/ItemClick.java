package com.kisen.plugframelib.view.recyclerhelper.helper.widget;

import android.support.v7.widget.RecyclerView;

import com.kisen.plugframelib.view.recyclerhelper.helper.itemclick.OnItemClickListener;


/**
 * 设置条目点击事件
 * Created by hwyMi on 2017/5/11.
 */
public interface ItemClick<T> {

    T setOnItemClickListener(OnItemClickListener listener);

    T setAdapter(RecyclerView.Adapter adapter);
}
