package com.kisen.plugframelib.mvp.listhelper;

import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Item的交互与逻辑接口
 * Created by huang on 2017/4/26.
 */
public interface Interact {

    /**
     * 设置Item是否可以点击
     */
    boolean itemEnable();

    /**
     * Item被创建时，设置完数据后调用，
     * 在{@link IAdapter#convert(BaseViewHolder)}之前被调用，
     * 只被调用一次
     * 用于Item准备
     */
    void readyTodo();
}
