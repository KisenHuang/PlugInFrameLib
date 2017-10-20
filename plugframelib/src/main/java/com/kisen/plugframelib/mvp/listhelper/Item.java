package com.kisen.plugframelib.mvp.listhelper;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kisen.plugframelib.mvp.Data;
import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义Item控制类
 * <p>
 * 将Adapter分成Item+Logic：
 * Item表示View，Logic表示Item里的逻辑处理，数据源由Model获取
 * </p>
 * <p>
 * {@link Interact#itemEnable()} 如果开启Item点击事件
 * {@link IAdapter#getItemResId()} Item的布局中最好不要出现Button类控件
 * 可能会出现一些bug
 * </p>
 */
public abstract class Item<D extends Data> implements IAdapter, Interact {

    protected D data;
    protected QuickAdapter adapter;
    protected int mItemPosition;
    protected Context mContext;

    public Item(D data) {
        this.data = data;
    }

    @Override
    public boolean interceptItemClick(QuickMvpAdapter adapter) {
        boolean intercept = itemEnable();
        if (intercept)
            onItemClick();
        return intercept;
    }

    /**
     * 条目点击事件。
     * {@link Interact#itemEnable()}必须返回true，这个方法才会被调用
     */
    protected void onItemClick() {
    }

    @Override
    public final int getItemType(int position) {
        mItemPosition = position;
        return getItemViewType(position);
    }

    /**
     * 创建Builder，由子类实现
     */
    public abstract Builder builder();

    /**
     * 获取ItemType，Item对应类型
     *
     * @param position Item位置
     */
    protected int getItemViewType(int position) {
        return 0;
    }

    public int getItemPosition() {
        return mItemPosition;
    }

    /**
     * 返回Item持有数据
     */
    public D getData() {
        return data;
    }

    public abstract class Builder {
        /**
         * 根据data数据，创建对应BaseItem的子类对象
         *
         * @param data 数据
         */
        public abstract Item<D> create(D data);

        @Nullable
        public final List<Item<D>> buildList(List<D> list, AdapterLogic itemLogic, BaseListPresenter<D> presenter) {
            if (list == null || list.size() == 0)
                return null;
            List<Item<D>> result = new ArrayList<>();
            for (D d : list) {
                Item<D> item = create(d);
                //设置数据
                item.mContext = presenter.getView().getContext();
                item.readyTodo();
                result.add(intercept(item));
            }
            return result;
        }

        protected Item<D> intercept(Item<D> item) {
            return item;
        }
    }
}
