package com.star.starlight.ui.view.commons

import android.widget.HorizontalScrollView
import android.widget.LinearLayout

/**
 * 定义基础Recycler的item展示类型
 * create by Eastevil at 2022/10/28 17:06
 * @since 2.0.0
 * @version 2.0.0
 */
class RecyclerItemType private constructor(){


    object ItemType{
        /**
         * RecyclerView展示的为自定义布局类型
         */
        const val TYPE_CUSTOM = 99;
        /**
         * RecyclerView的item展示的为水平线条
         */
        const val TYPE_LINE_HORIZONTAL = 100;

        /**
         * RecyclerView的item展示的为垂直线条
         */
        const val TYPE_LINE_VERTICLE = 101;

        /**
         * 横向占据列表作为分割线类型
         */
        const val TYPE_SPACE_HORIZONTAL = 102;
    }
}
