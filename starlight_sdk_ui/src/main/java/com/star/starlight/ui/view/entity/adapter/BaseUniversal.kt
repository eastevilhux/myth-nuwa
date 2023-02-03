package com.star.starlight.ui.view.entity.adapter

import com.star.starlight.ui.R
import com.starlight.dot.framework.utils.format

abstract class BaseUniversal : IUniversal{
    var id : Long = 0;
    var iconUrl : String? = null;
    var title : String? = null;
    var details : String? = null;
    var createTime : Long = 0;
    var appId : Long = 0;
    var appName : String? = null;

    val timeText : String
    get() {
        return createTime.format("yyyy-MM-dd HH:mm:ss")
    }

    /**
     * 列表内容距离左边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离左边边距
     */
    override fun paddingLeft(): Int {
        return 0;
    }

    /**
     * 列表内容距离右边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离右边边距
     */
    override fun paddingRight(): Int {
        return 0;
    }

    /**
     * 列表内容距离上边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离上边边距
     */
    override fun paddingTop(): Int {
        return 0;
    }

    /**
     * 列表内容距离下边边距
     * create by Eastevil at 2022/10/9 11:38
     * @author Eastevil
     * @return
     *      列表内容距离下边边距
     */
    override fun paddingBottom(): Int {
        return 0;
    }

    /**
     * 数据唯一标识，一般使用当前实体类的主键作为返回值，在点击事件中，将通过该值进行唯一区分判断
     * create by Eastevil at 2022/8/19 14:00
     * @author Eastevil
     * @return
     *      数据唯一标识
     */
    override fun itemDataId(): Long {
        return id;
    }

    /**
     * 当前列表item是否展示图标
     * create by Eastevil at 2022/8/19 14:01
     * @author Eastevil
     * @return
     *      item是否展示图标
     */
    override fun isShowItemIcon(): Boolean {
        return false;
    }

    /**
     * 当前列表item展示的图标url地址
     * create by Eastevil at 2022/8/19 14:02
     * @author Eastevil
     * @return
     *      图标url地址
     */
    override fun itemIconUrl(): String? {
        return iconUrl;
    }

    /**
     * 当前列表item展示的标题文本
     * create by Eastevil at 2022/8/19 14:02
     * @author Eastevil
     * @return
     *      标题文本
     */
    override fun itemTitleText(): String? {
        return title;
    }

    /**
     * 当前item展示的内容文本
     * create by Eastevil at 2022/8/19 14:04
     * @author Eastevil
     * @return
     *      内容文本
     */
    override fun itemContentText(): String? {
        return details;
    }

    /**
     * 当前列表item展示的时间文本
     * create by Eastevil at 2022/8/19 14:04
     * @author Eastevil
     * @return
     *      时间文本
     */
    override fun itemTimeText(): String? {
        return timeText;
    }

    /**
     * 当前列表的item是否需要展示指向箭头
     * create by Eastevil at 2022/8/19 14:05
     * @author Eastevil
     * @return
     *      指向箭头
     */
    override fun isShowItemArrow(): Boolean {
        return false;
    }

    /**
     * 当前列表item的背景资源id
     * create by Eastevil at 2022/8/19 14:05
     * @author Eastevil
     * @return
     *      背景资源id
     */
    override fun itemBackgroundId(): Int {
        return R.drawable.selecter_list_item_default;
    }

    /**
     * item布局宽度，单位：px
     * create by Administrator at 2022/8/23 0:22
     * @author Administrator
     * @return
     *      item布局宽度
     */
    override fun itemWidth(): Int {
        return 0;
    }

    /**
     * item布局高度：单位：px
     * create by Administrator at 2022/8/23 0:23
     * @author Administrator
     * @return
     *      item布局高度
     */
    override fun itemHeight(): Int {
        return 0;
    }

    /**
     * 是否需要计算列表item view的大小
     * create by Eastevil at 2022/8/23 11:38
     * @author Eastevil
     * @return
     *      是否需要计算列表item view的大小
     */
    override fun needCountViewParams(): Boolean {
        return false;
    }
}
