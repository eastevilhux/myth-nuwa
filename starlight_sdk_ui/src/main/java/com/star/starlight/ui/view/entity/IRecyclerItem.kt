package com.star.starlight.ui.view.entity

/**
 * 所有列表展示的item基础接口,通过该接口来定义item展示的数据
 * @since 2.0.0
 * @version 2.0.0
 * @author Eastevil
  */
interface IRecyclerItem {

    /**
     * 获取item展示的icon地址
     * create by Administrator at 2022/10/30 17:48
     * @author Administrator
     * @return
     *      item展示的icon地址
     */
    fun itemIconUrl() : String?;

    /**
     * 获取item详情
     * create by Administrator at 2022/10/30 17:47
     * @author Administrator
     * @return
     *      item详情
     */
    fun getItemDetail() : String?;

    /**
     * 获取当前对象的id，唯一标识
     * create by Administrator at 2022/10/30 17:46
     * @author Administrator
     * @return
     *      当前对象的id
     */
    fun recyclerItemId() : Long;

    /**
     * 获取当前RecyclerView的Item展示类型,返回的值必须为[com.star.starlight.ui.view.commons.RecyclerItemType]定义的类型
     * 列表将根据该类型展示对应的布局文件
     * create by Eastevil at 2022/10/28 17:11
     * @author Eastevil
     * @return
     *      当前RecyclerView的Item展示类型，
     */
    fun recyclerItemType() : Int;

    /**
     * 如果[recyclerItemType]方法返回为[com.star.starlight.ui.view.commons.RecyclerItemType]中定义的自定义布局展示类型，
     * 展示的item资源布局将通过调用次方法获得
     * create by Eastevil at 2022/10/28 17:15
     * @author Eastevil
     * @param
     * @return
     */
    fun customLayoutId() : Int;

}
