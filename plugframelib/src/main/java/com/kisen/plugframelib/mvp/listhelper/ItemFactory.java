package com.kisen.plugframelib.mvp.listhelper;

import android.content.Context;
import android.support.annotation.Nullable;


import com.kisen.plugframelib.mvp.Data;
import com.kisen.plugframelib.mvp.presenter.BaseListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * item工厂类
 * Created by huang on 2017/3/3.
 */

public class ItemFactory<D extends Data> {

    private Item<D> template;
    private QuickAdapter<Item<D>> adapter;
    private Context context;

    public ItemFactory(Context context, Item<D> template, QuickAdapter<Item<D>> adapter) {
        this.template = template;
        this.adapter = adapter;
        this.context = context;
    }

    @Nullable
    public List<Item<D>> makeItems(List<D> list, Logic logic, BaseListPresenter<D> presenter) {
        List<Item<D>> items = new ArrayList<>();
        if (list == null)
            return items;
        for (D d : list) {
            Item<D> item = presenter.setupItemTemplate();
            if (item instanceof SetLogic) {
                ((SetLogic) item).setLogic(logic);
            }
            item.setContext(context);
            item.setAdapter(adapter);
            item.setData(d);
            item.readyTodo();
            items.add(item);
        }
        return items;
    }
}
