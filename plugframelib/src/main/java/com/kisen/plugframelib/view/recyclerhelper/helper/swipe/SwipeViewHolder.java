package com.kisen.plugframelib.view.recyclerhelper.helper.swipe;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kisen.plugframelib.R;
import com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu.SwipeMenuItem;

/**
 * @Title : 侧滑
 * @Description :
 * @Version :
 * Created by huang on 2016/12/5.
 */

public class SwipeViewHolder extends BaseViewHolder {

    private final SparseArray<View> views = new SparseArray();
    private LinearLayout actionContainer;
    private Context context;
    private final View item;

    SwipeViewHolder(LinearLayout actionContainer, View view) {
        super(view);
        context = view.getContext();
        this.actionContainer = actionContainer;
        item = convertView.findViewById(R.id.container_item);
    }

    @Override
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = item.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public View getConvertView() {
        return getItemView();
    }

    public View getItemView() {
        return item;
    }

    void addMenu(SwipeMenu menu) {
        actionContainer.removeAllViews();
        for (int i = 0; i < menu.getMenuItems().size(); i++) {
            SwipeMenuItem menuItem = menu.getMenuItem(i);
            menuItem.setId(i);
            addItem(menuItem, menuItem.getId());
        }
    }

    private void addItem(SwipeMenuItem item, int id) {
        int width = item.getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width == 0 ? LinearLayout.LayoutParams.WRAP_CONTENT : width,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout parent = new LinearLayout(context);
        parent.setId(id);
        parent.setGravity(Gravity.CENTER);
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setLayoutParams(params);
        parent.setBackgroundDrawable(item.getBackground());
        actionContainer.addView(parent);

        if (item.getIcon() != null) {
            parent.addView(createIcon(item));
        }
        if (!TextUtils.isEmpty(item.getTitle())) {
            parent.addView(createTitle(item));
        }

    }

    private ImageView createIcon(SwipeMenuItem item) {
        ImageView iv = new ImageView(context);
        iv.setImageDrawable(item.getIcon());
        return iv;
    }

    private TextView createTitle(SwipeMenuItem item) {
        TextView tv = new TextView(context);
        tv.setText(item.getTitle());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(item.getTitleSize());
        tv.setTextColor(item.getTitleColor());
        return tv;
    }
}
