package com.kisen.plugframelib.mvp.listhelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by huangwy on 2017/9/5.
 * email: kisenhuang@163.com.
 */

public abstract class AdapterLogic<T> implements Logic{

    protected List<Entity<T>> mData;
    protected RecyclerView.Adapter mAdapter;

    public AdapterLogic(RecyclerView.Adapter adapter) {
        mData = new ArrayList<>();
        mAdapter = adapter;
    }

    void addData(@NonNull T item) {
        mData.add(new Entity<>(item));
    }

    void addData(@NonNull Collection<? extends T> newData) {
        for (T t : newData) {
            mData.add(new Entity<>(t));
        }
    }

    void setNewData(@Nullable List<T> data) {
        mData.clear();
        if (data != null)
            addData(data);
    }

    void remove(int position) {
        mData.remove(position);
    }

    @Nullable
    public Entity<T> getLogicEntity(int position) {
        return mData == null ? null : mData.get(position);
    }

    /**
     * 刷新状态
     * 状态改变会调用该方法
     *
     * @param holder   ViewHolder
     * @param position 位置
     */
    public abstract void convert(BaseViewHolder holder, int position);

    /**
     * 设置是否拦截BaseQuickAdapter中的点击事件。
     * 在里面可以进行逻辑处理和UI操作
     *
     * @param position 位置
     */
    public boolean interceptItemClick(BaseViewHolder holder, int position) {
        return false;
    }


    public static class Entity<T> {

        public T itemData;
        public boolean state = false;
        public int[] tag = null;

        Entity(T itemData) {
            this.itemData = itemData;
        }
    }

}
