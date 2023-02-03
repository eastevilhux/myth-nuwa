package com.star.starlight.ui.view.entity.adapter

import com.star.starlight.ui.view.entity.adapter.IEastEntity

/**
 * 列表item展示数据实体类接口
 * @since 2.1.0
 * @version 2.1.0
 * @author EastEvil
 */
interface IUniversal : IEastEntity {

    /**
     * 列表内容距离左边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离左边边距
     */
    fun paddingLeft() : Int;

    /**
     * 列表内容距离右边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离右边边距
     */
    fun paddingRight() : Int;

    /**
     * 列表内容距离上边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离上边边距
     */
    fun paddingTop() : Int;

    /**
     * 列表内容距离下边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离下边边距
     */
    fun paddingBottom() : Int;
}
