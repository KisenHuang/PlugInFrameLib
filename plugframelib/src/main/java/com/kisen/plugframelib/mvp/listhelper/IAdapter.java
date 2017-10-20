package com.kisen.plugframelib.mvp.listhelper;


import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Item衔接Adapter的接口
 * Created by huang on 2017/3/21.
 */
public interface IAdapter {

    /**
     * 需要实现，返回对应Item的布局文件Id 如果返回0，则使用适配器默认布局
     *
     * @return 返回当前数据类对应布局
     */
    int getItemResId();

    /**
     * 必须实现，在数据类中直接将数据适配到通过BaseViewHolder获取到的视图中
     *
     * @param helper          用来获取Item的控件
     */
    void convert(BaseViewHolder helper);

    /**
     * 需要实现，默认返回0，同一列表中出现多种不同的布局时，必须返回不同的类型，
     * 如果返回相同的值，会因BaseViewHolder复用出现布局错乱，处理数据时异常
     * 在{@link IAdapter#getItemResId()}中已经把对应的布局返回给适配器
     *
     * @param position Item对应的position
     * @return 返回当前自定义Item类型
     * {@link android.widget.BaseAdapter#getItemViewType(int)}
     */
    int getItemType(int position);

    /**
     * Item点击事件
     */
    boolean interceptItemClick(QuickMvpAdapter adapter);

}
