package com.kisen.plugframelib.view.recyclerhelper.helper.swipemenu;

/**
 * 侧滑菜单生成器
 * Created by huang on 2017/5/2.
 */
public interface SwipeMenuCreator {

    interface LeftCreator extends SwipeMenuCreator {

        /**
         * 创建菜单
         *
         * @param menuLeft 左侧菜单
         */
        void create(SwipeMenu menuLeft);
    }

    interface RightCreator extends SwipeMenuCreator {

        /**
         * 创建菜单
         *
         * @param menuRight 右侧的菜单
         */
        void create(SwipeMenu menuRight);
    }

    interface DoubleCreator extends SwipeMenuCreator {

        /**
         * 创建菜单
         *
         * @param menuLeft  左侧菜单
         * @param menuRight 右侧的菜单
         */
        void create(SwipeMenu menuLeft, SwipeMenu menuRight);
    }
}
