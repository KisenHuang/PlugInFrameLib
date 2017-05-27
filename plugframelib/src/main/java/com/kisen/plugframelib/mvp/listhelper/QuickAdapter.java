package com.kisen.plugframelib.mvp.listhelper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 适配器将工作转移到{@link IAdapter}的实现类中
 * Created by huang on 2017/2/7.
 */
public class QuickAdapter<I extends IAdapter> extends BaseQuickAdapter<I> {

    private int itemPos;
    private boolean isOpenFooterView;
    private View footerView;
    private RecyclerView.AdapterDataObserver oldEmptyObserver;

    public QuickAdapter() {
        this(null);
    }

    public QuickAdapter(int layoutResId, List<I> data) {
        super(layoutResId, data);
    }

    public QuickAdapter(List<I> data) {
        super(data);
    }

    public QuickAdapter(View contentView, List<I> data) {
        super(contentView, data);
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
        isOpenFooterView = true;
    }

    public void closeFooterView() {
        isOpenFooterView = false;
    }

    public void openFooterView() {
        isOpenFooterView = true;
    }

    @Override
    public void notifyDataChangedAfterLoadMore(boolean isNextLoad) {
        super.notifyDataChangedAfterLoadMore(isNextLoad);
        if (isOpenFooterView && !isNextLoad) {
            if (getItemCount() < 1)
                return;
            if (getFooterLayoutCount() > 0)
                return;
            addFooterView(footerView);
        } else {
            if (getFooterLayoutCount() > 0) {
                addFooterView(null);
            }
        }
    }

    /**
     * 注册空试图观察者
     */
    public void registerEmptyViewObserver(RecyclerView.AdapterDataObserver emptyObserver) {
        if (emptyObserver == null)
            return;
        if (oldEmptyObserver != null) {
            unregisterAdapterDataObserver(oldEmptyObserver);
        }
        registerAdapterDataObserver(emptyObserver);
        oldEmptyObserver = emptyObserver;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, I item) {
        item.onBindViewHolder(baseViewHolder, itemPos);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (mData.get(itemPos).getItemResId() != 0)
            mLayoutResId = mData.get(itemPos).getItemResId();
        return createBaseViewHolder(parent, mLayoutResId);
    }

    @Override
    protected int getDefItemViewType(int position) {
        this.itemPos = position;
        return mData.get(position).getItemType();
    }

    /**
     * 清空列表数据，但是不刷新列表
     */
    public void clear() {
        mData.clear();
    }

    /**
     * 清空列表数据，并刷新李列表
     */
    public void clearAndNotify() {
        mData.clear();
        notifyDataSetChanged();
    }
}
