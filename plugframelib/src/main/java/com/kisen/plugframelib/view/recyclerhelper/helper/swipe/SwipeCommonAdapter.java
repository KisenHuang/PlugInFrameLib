package com.kisen.plugframelib.view.recyclerhelper.helper.swipe;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kisen.plugframelib.R;
import com.kisen.plugframelib.mvp.listhelper.IAdapter;
import com.kisen.plugframelib.mvp.listhelper.QuickAdapter;

/**
 * @Title : 带侧滑按钮适配器
 * @Description :
 * @Version : v2.1.5
 * Created by huang on 2016/12/5.
 */
public class SwipeCommonAdapter extends QuickAdapter {

    private SwipeMenuCreator creator;
    private Context context;

    public SwipeCommonAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, IAdapter item) {
        if (helper instanceof SwipeViewHolder && creator != null) {
            SwipeViewHolder swipeViewHolder = (SwipeViewHolder) helper;
            SwipeMenu menu = new SwipeMenu(context);
            creator.create(menu);
            swipeViewHolder.addMenu(menu);
        }
        super.convert(helper, item);
    }

    public void setMenuCreator(SwipeMenuCreator creator) {
        this.creator = creator;
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        View root = getItemView(R.layout.item_swipe_view, parent);
        ViewGroup itemParent = (ViewGroup) root.findViewById(R.id.container_item);
        LinearLayout actionContainer = (LinearLayout) root.findViewById(R.id.container_action);
        View itemView = getItemView(layoutResId, itemParent);
        itemParent.addView(itemView);
        return new SwipeViewHolder(actionContainer, root);
    }
}
