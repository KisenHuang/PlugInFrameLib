package com.kisen.plugframelib.view.recyclerhelper.helper.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kisen.plugframelib.view.recyclerhelper.helper.itemclick.OnItemClickListener;


/**
 * ViewHolder 嵌套容器
 * Created by huang on 2017/5/4.
 */
public abstract class NestingViewHolder extends RecyclerView.ViewHolder {

    protected OnItemClickListener itemClickListener;

    public NestingViewHolder(View itemView) {
        super(itemView);
    }

    public abstract RecyclerView.ViewHolder getHolder();

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

}
