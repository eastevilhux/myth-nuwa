package com.star.starlight.ui.view.entity

import com.star.starlight.ui.R
import com.star.starlight.ui.view.commons.RecyclerItemType
import com.starlight.dot.framework.utils.dip2px

abstract class RichLineEntity : RecyclerItemEntity() {
    var lineHeight : Int = 10.dip2px();
    var lineType : Int = 0
    var lineRes : Int = R.drawable.selecter_list_item_default;

    /**
     * 获取item展示的icon地址
     * create by Administrator at 2022/10/30 17:48
     * @author Administrator
     * @return
     *      item展示的icon地址
     */
    override fun itemIconUrl(): String? {
        return null;
    }

    /**
     * 获取item详情
     * create by Administrator at 2022/10/30 17:47
     * @author Administrator
     * @return
     *      item详情
     */
    override fun getItemDetail(): String? {
        return null;
    }

    /**
     * 获取当前对象的id，唯一标识
     * create by Administrator at 2022/10/30 17:46
     * @author Administrator
     * @return
     *      当前对象的id
     */
    override fun recyclerItemId(): Long {
        return 0;
    }

    /**
     * 获取当前RecyclerView的Item展示类型,返回的值必须为[com.star.starlight.ui.view.commons.RecyclerItemType]定义的类型
     * 列表将根据该类型展示对应的布局文件
     * create by Eastevil at 2022/10/28 17:11
     * @author Eastevil
     * @return
     *      当前RecyclerView的Item展示类型，
     */
    override fun recyclerItemType(): Int {
        return RecyclerItemType.ItemType.TYPE_LINE_HORIZONTAL;
    }

    /**
     * 如果[recyclerItemType]方法返回为[com.star.starlight.ui.view.commons.RecyclerItemType]中定义的自定义布局展示类型，
     * 展示的item资源布局将通过调用次方法获得
     * create by Eastevil at 2022/10/28 17:15
     * @author Eastevil
     * @param
     * @return
     */
    override fun customLayoutId(): Int {
        return R.layout.recycler_viewitem_line_horizontal;
    }
}
