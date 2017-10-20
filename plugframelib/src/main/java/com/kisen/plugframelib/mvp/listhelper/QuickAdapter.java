package com.kisen.plugframelib.mvp.listhelper;

import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 适配器将工作转移到{@link IAdapter}的实现类中
 * Created by huang on 2017/2/7.
 */
public abstract class QuickAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    protected AdapterLogic<T> mAdapterLogic;
    protected RecyclerView.AdapterDataObserver oldEmptyObserver;
    private AdapterLogicItemClickListener mLogicItemClickListener;

    public QuickAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public QuickAdapter(@Nullable List<T> data) {
        this(0, data);
    }

    public QuickAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    public void setAdapterLogic(AdapterLogic<T> logic) {
        mAdapterLogic = logic;
    }

    /**
     * 添加新的列表数据，删除之前的列表数据
     *
     * @param data 新的列表数据
     */
    public void addNewData(@Nullable List<T> data) {
        if (mData != null) {
            mData.clear();
        }
        addData(data == null ? new ArrayList<T>() : data);
    }

    /**
     * 清空列表数据
     * 不刷新UI
     */
    public void clear() {
        mData.clear();
    }

    /**
     * 清空列表数据
     * 可以刷新UI
     *
     * @param notify 是否刷新UI
     */
    public void clear(boolean notify) {
        clear();
        if (notify) {
            notifyDataChanged();
        }
    }

    /**
     * 更新加载方法
     * 内部封装了对数据的判断
     * 分别执行了loadMoreComplete()和loadMoreEnd()
     *
     * @param data 加载的数据,本次加载到的数据。
     */
    public void notifyHasLoadMoreData(List<T> data) {
        notifyHasLoadMoreData(data, 0);
    }

    public void notifyHasLoadMoreData(List<T> data, int pageSize) {
        boolean hasData = data != null && data.size() > 0;
        boolean hasMoreData = hasData && data.size() >= pageSize;
        if (hasData) {
            addData(data);
        }
        if (hasMoreData) {
            loadMoreComplete();
        } else {
            loadMoreEnd();
        }
    }

    @Override
    public void addData(@NonNull T data) {
        if (mAdapterLogic != null)
            mAdapterLogic.addData(data);
        super.addData(data);
    }

    @Override
    public void addData(@NonNull Collection<? extends T> newData) {
        if (mAdapterLogic != null)
            mAdapterLogic.addData(newData);
        super.addData(newData);
    }

    @Override
    public void setNewData(@Nullable List<T> data) {
        if (mAdapterLogic != null)
            mAdapterLogic.setNewData(data);
        super.setNewData(data);
    }

    @Override
    public void remove(@IntRange(from = 0L) int position) {
        if (mAdapterLogic != null)
            mAdapterLogic.remove(position);
        super.remove(position);
    }

    /**
     * 刷新页面
     * <p>
     * 会保留状态数据
     * <p>
     * 当使用mData引用操作的时候，可以用这个方法刷新数据
     * 同步AdapterLogic中的data数据
     */
    public void notifyDataChanged() {
        if (mAdapterLogic != null)
            mAdapterLogic.setNewData(mData);
        notifyDataSetChanged();
    }

    /**
     * 注册空试图观察者
     */
    public void registerEmptyViewObserver(RecyclerView.AdapterDataObserver emptyObserver) {
        if (emptyObserver == null)
            return;
        if (oldEmptyObserver == emptyObserver)
            return;
        if (oldEmptyObserver != null) {
            unregisterAdapterDataObserver(oldEmptyObserver);
        }
        registerAdapterDataObserver(emptyObserver);
        oldEmptyObserver = emptyObserver;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);
        int viewType = holder.getItemViewType();
        //排除特定View的操作
        if (viewType == LOADING_VIEW || viewType == HEADER_VIEW
                || viewType == EMPTY_VIEW || viewType == FOOTER_VIEW) {
            return;
        }
        if (mAdapterLogic != null) {
            mAdapterLogic.convert(holder, holder.getLayoutPosition() - getHeaderLayoutCount());
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseViewHolder baseViewHolder;
        //特定类型ViewHolder不作处理
        if (viewType == LOADING_VIEW || viewType == HEADER_VIEW
                || viewType == EMPTY_VIEW || viewType == FOOTER_VIEW) {
            baseViewHolder = super.onCreateViewHolder(parent, viewType);
        } else {//默认类型ViewHolder重新定义Item点击事件,加入AdapterLogic事件拦截器
            baseViewHolder = super.onCreateViewHolder(parent, viewType);
            final View view = baseViewHolder.itemView;
            //重新绑定事件
            if (view != null && mAdapterLogic != null) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = baseViewHolder.getLayoutPosition() - getHeaderLayoutCount();
                        //AdapterLogic事件拦截
                        //默认Item点击事件和AdapterLogic中点击事件只能执行一个
                        if (!mAdapterLogic.interceptItemClick(baseViewHolder, position)) {
                            if (getOnItemClickListener() != null && !interceptItemClick(position))
                                getOnItemClickListener().onItemClick(QuickAdapter.this, v, position);
                        } else if (mLogicItemClickListener != null) {
                            mLogicItemClickListener.onLogicItemClick(QuickAdapter.this, v, position);
                        }
                    }
                });
            }
        }
        return baseViewHolder;
    }

    public void setOnAdapterLogicItemClickListener(AdapterLogicItemClickListener logicItemClickListener) {
        mLogicItemClickListener = logicItemClickListener;
    }

    protected boolean interceptItemClick(int position) {
        return false;
    }

    /**
     * AdapterLogic中的Item点击事件与
     * {@link BaseQuickAdapter.OnItemClickListener}互斥。
     * onLogicItemClicked执行onItemClick就不会执行。
     */
    public interface AdapterLogicItemClickListener {

        /**
         * AdapterLogic拦截Item点击事件后触发的Logic点击事件。
         */
        void onLogicItemClick(BaseQuickAdapter adapter, View view, int position);
    }
}
