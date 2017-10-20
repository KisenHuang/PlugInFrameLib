package com.kisen.plugframelib.mvp.presenter;


import android.support.annotation.NonNull;

import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.kisen.plugframelib.mvp.Data;
import com.kisen.plugframelib.mvp.listhelper.AdapterLogic;
import com.kisen.plugframelib.mvp.listhelper.Item;
import com.kisen.plugframelib.mvp.listhelper.Logic;
import com.kisen.plugframelib.mvp.listhelper.QuickAdapter;
import com.kisen.plugframelib.mvp.listhelper.QuickMvpAdapter;
import com.kisen.plugframelib.mvp.view.BaseListView;

import java.util.List;


/**
 * 列表Presenter
 * <p>
 * 绑定{@link Logic}列表逻辑处理
 * 生成默认BaseAdapter
 * 使用ItemFactory生成Item列表
 * </p>
 * Created by huang on 2017/2/7.
 */
public abstract class BaseListPresenter<D extends Data> extends BasePresenter<BaseListView> {

    private Item<D>.Builder mItemBuilder;
    private AdapterLogic itemLogic;
    private QuickMvpAdapter<Item<D>> adapter;

    @Override
    public void attachView(BaseListView view) {
        super.attachView(view);
        mItemBuilder = createItemBuilder();
        adapter = new QuickMvpAdapter<>();
    }

    public void setAdapter(QuickMvpAdapter<Item<D>> adapter) {
        this.adapter = adapter;
    }

    /**
     * 在父类中注册ItemLogic，
     * 主要是在创建Item时传给所有Item，保持所有Item都持有一个ItemLogic对象
     *
     * @param logic 在父类中注册的ItemLogic
     */
    protected void setItemLogic(AdapterLogic logic) {
        itemLogic = logic;
    }

    @Override
    public void detachView() {
        super.detachView();
        if (itemLogic != null) {
            itemLogic.clear();
            itemLogic = null;
        }
        mItemBuilder = null;
        adapter.clear();
    }

    /**
     * 通过list生产出Item列表
     *
     * @param list 生产Item所需数据源
     */
    public void notifyAfterLoad(List<D> list) {
        List<Item<D>> items = mItemBuilder.buildList(list, itemLogic, this);
        adapter.notifyHasLoadMoreData(items, getView().getPageSize());
    }

    public QuickAdapter<Item<D>> getAdapter() {
        return adapter;
    }

    /**
     * 该方法只能被调用一次，用于创建ItemBuilder
     */
    @NonNull
    protected abstract Item<D>.Builder createItemBuilder();
}
