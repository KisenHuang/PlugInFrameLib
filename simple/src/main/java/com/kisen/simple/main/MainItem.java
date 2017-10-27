package com.kisen.simple.main;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kisen.plugframelib.mvp.listhelper.Item;
import com.kisen.plugframelib.utils.FormatUtil;
import com.kisen.plugframelib.utils.imageloader.ImageLoader;
import com.kisen.simple.R;

/**
 * 标题：
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/5/27 16:05.
 */
public class MainItem extends Item<MainData> {

    public MainItem(MainData data) {
        super(data);
    }

    @Override
    public int getItemResId() {
        return R.layout.item_product_grid;
    }

    @Override
    public Builder builder() {
        return new Builder() {
            @Override
            public Item<MainData> create(MainData data) {
                return new MainItem(data);
            }
        };
    }

    @Override
    public void convert(BaseViewHolder helper) {
        ImageLoader.getInstance().display(mContext, data.getImageUrl(), (ImageView) helper.getView(R.id.product_image));
        helper.setText(R.id.product_title, data.getProductName() + "/" + data.getBrandName());
        TextView price = helper.getView(R.id.product_price);
        TextView oldPrice = helper.getView(R.id.product_old_price);
        View layoutOldPrice = helper.getView(R.id.layout_old_price);
        if (data.getListPrice() > 0) {
            layoutOldPrice.setVisibility(View.VISIBLE);
            price.setTextColor(Color.RED);
            oldPrice.setText(FormatUtil.formatPrice(data.getListPrice()));
        } else {
            layoutOldPrice.setVisibility(View.GONE);
            price.setTextColor(Color.GRAY);
        }
        price.setText(FormatUtil.formatPrice(data.getPrice()));
    }

    @Override
    public boolean itemEnable() {
        return false;
    }

    @Override
    public void readyTodo() {

    }
}
