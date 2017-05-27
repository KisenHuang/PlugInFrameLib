package com.kisen.plugframelib.view.recyclerhelper.helper.drag;


import com.kisen.plugframelib.view.recyclerhelper.helper.itemclick.OnItemClickListener;

import java.util.List;

/**
 * 拖拽效果Adapter接口
 * Created by huang on 2017/5/4.
 */
public interface IDragAdapter {

    /**
     * 简单方式：子类将数据源提供给DragAdapter
     * 并由DragAdapter完成拖拽处理
     */
    interface IDragLazyAdapter {

        List getSource();
    }

    /**
     * 复杂方式：子类自己实现拖拽处理
     */
    interface IDragBusyAdapter {

        boolean onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);

        void setOnItemClickListener(OnItemClickListener listener);
    }

}
