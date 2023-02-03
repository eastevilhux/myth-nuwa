package com.star.starlight.ui.view.entity.adapter

/**
 * 定义adapter需要展示的基础内容接口,所有使用该框架中的adapter的实体类均需要实现该接口
 * create by Eastevil at 2022/8/19 13:59
 * @author Eastevil
 * @version 1.0.0
 * @since 1.0.0
 */
interface IEastEntity {

    /**
     * 数据唯一标识，一般使用当前实体类的主键作为返回值，在点击事件中，将通过该值进行唯一区分判断
     * create by Eastevil at 2022/8/19 14:00
     * @author Eastevil
     * @return
     *      数据唯一标识
     */
    fun itemDataId() : Long;

    /**
     * 当前列表item是否展示图标
     * create by Eastevil at 2022/8/19 14:01
     * @author Eastevil
     * @return
     *      item是否展示图标
     */
    fun isShowItemIcon() : Boolean;

    /**
     * 当前列表item展示的图标url地址
     * create by Eastevil at 2022/8/19 14:02
     * @author Eastevil
     * @return
     *      图标url地址
     */
    fun itemIconUrl() : String?

    /**
     * 当前列表item展示的标题文本
     * create by Eastevil at 2022/8/19 14:02
     * @author Eastevil
     * @return
     *      标题文本
     */
    fun itemTitleText() : String?

    /**
     * 当前item展示的内容文本
     * create by Eastevil at 2022/8/19 14:04
     * @author Eastevil
     * @return
     *      内容文本
     */
    fun itemContentText() : String?

    /**
     * 当前列表item展示的时间文本
     * create by Eastevil at 2022/8/19 14:04
     * @author Eastevil
     * @return
     *      时间文本
     */
    fun itemTimeText() : String?

    /**
     * 当前列表的item是否需要展示指向箭头
     * create by Eastevil at 2022/8/19 14:05
     * @author Eastevil
     * @return
     *      指向箭头
     */
    fun isShowItemArrow () : Boolean;

    /**
     * 当前列表item的背景资源id
     * create by Eastevil at 2022/8/19 14:05
     * @author Eastevil
     * @return
     *      背景资源id
     */
    fun itemBackgroundId() : Int;

    /**
     * 当前列表item展示的布局类型，通过该方法的返回值来确定列表item的展示布局
     * [com.star.starlight.ui.view.entity.adapter.ItemType]来设定
     * create by Eastevil at 2022/8/19 14:42
     * @author Eastevil
     * @return
     *      item布局类型
     */
    fun itemViewType() : Int;

    /**
     * 当[itemViewType]方法返回为[com.star.starlight.ui.view.entity.adapter.ItemType.UNIVERSAL_CUSTOM]时，
     * 表示当前的item为自定义item类型，需要通过该方法获取item展示的资源id，如果此时该方法返回0，则adapter会抛出异常
     * create by Eastevil at 2022/10/10 13:31
     * @author Eastevil
     * @return
     *      自定义item布局资源id
     */
    fun customItemResourceId() : Int;

    /**
     * item布局宽度，单位：px
     * create by Administrator at 2022/8/23 0:22
     * @author Administrator
     * @return
     *      item布局宽度
     */
    fun itemWidth() : Int;

    /**
     * item布局高度：单位：px
     * create by Administrator at 2022/8/23 0:23
     * @author Administrator
     * @return
     *      item布局高度
     */
    fun itemHeight() : Int;

    /**
     * 是否需要计算列表item view的大小
     * create by Eastevil at 2022/8/23 11:38
     * @author Eastevil
     * @return
     *      是否需要计算列表item view的大小
     */
    fun needCountViewParams() : Boolean;
}
