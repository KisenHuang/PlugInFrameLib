package com.kisen.plugframelib.view.recyclerhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * 适配器基类
 * Created by huang on 2017/4/28.
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final Map<Integer, Class<? extends VH>> vhMap = new HashMap<>();

    public BaseAdapter() {
        initVHMap(vhMap);
    }

    @Override
    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateVHWithClass(parent, vhMap.get(viewType));
    }

    protected abstract VH onCreateVHWithClass(ViewGroup parent, Class<? extends VH> vhClass);

    protected void initVHMap(Map<Integer, Class<? extends VH>> vhMap) {
    }

}
