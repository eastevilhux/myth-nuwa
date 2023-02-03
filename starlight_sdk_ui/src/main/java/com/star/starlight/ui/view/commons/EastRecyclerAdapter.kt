package com.star.starlight.ui.view.commons

import android.content.Context
import android.util.Log
import android.view.View
import com.star.starlight.ui.R
import com.star.starlight.ui.BR
import com.star.starlight.ui.view.presenter.IAdapterBasePresenter
import com.star.starlight.ui.view.entity.adapter.IEastEntity
import com.star.starlight.ui.view.entity.adapter.ItemType
import java.lang.NullPointerException


abstract class EastRecyclerAdapter<T : IEastEntity,P : IAdapterBasePresenter<T>> : EastBaseAdapter<T,P>{

    constructor(context : Context, list : List<T>?) : super(context,list){

    }

    constructor(context: Context) : super(context){

    }

    override fun bindItem(p0: MutableMap<Int, Int>?) {
        if(p0 != null){
            //封装基础item布局类型
            p0.put(R.layout.recycler_universal_text,BR.textData);
            val m = mutableMapOf<Int,Int>();
            val map = bindLayoutMap(m);
            if(map != null && map.isNotEmpty()){
                //添加自定义的map
                p0.putAll(map);
            }
        }else{
            throw NullPointerException("item map is not allow null");
        }
    }

    override fun getViewItemType(p0: Int): Int {
        val itemViewType = dataList.get(p0).itemViewType();
        Log.d(TAG,"itemViewType=>${itemViewType}");
        if(ItemType.UNIVERSAL_CUSTOM == itemViewType){
            //需要显示自定义资源布局id
            return dataList[p0].customItemResourceId();
        }else {
            return when (itemViewType) {
                ItemType.UNIVERSAL_TEXT->{
                    R.layout.recycler_universal_text;
                }
                /*EastEntity.ItemType.TYPE_CUSTOM->{
                val type = customItemViewType(p0);
                if(type == -1){
                    throw IllegalAccessException("unknow item view type");
                }
                type;
            }
            EastEntity.ItemType.TYPE_BASIC_INFO->{
                R.layout.recycler_eastitem_basicinfo;
            }
            EastEntity.ItemType.TYPE_IMAGE_VERTICAL->{
                R.layout.recycler_eastitem_img_vertical;
            }
            EastEntity.ItemType.TYPE_IMAGE_HORIZONTAL->{
                R.layout.recycler_eastitem_img_horizontal;
            }
            EastEntity.ItemType.TYPE_ITEM_LINE->{
                R.layout.recycler_eastitem_line;
            }
            EastEntity.ItemType.TYPE_FILL_WHITE->{
                R.layout.recycler_eastitem_white;
            }
            EastEntity.ItemType.TYPE_TOP_MENU->{
                R.layout.recycler_eastitem_topmenu;
            }*/
                else -> throw IllegalAccessException("unknow item view type");
            }
        }
    }

    /**
     * 绑定自定义列表item布局
     * create by Eastevil at 2022/8/19 15:13
     * @author Eastevil
     * @param map
     *      已经封装并绑定后的布局集合,集合中的key为资源id，value为BR资源id
     * @return
     *      自定义布局集合
     */
    open fun bindLayoutMap(map : MutableMap<Int, Int>) : MutableMap<Int,Int>?{
        return null;
    }


    /**
     * 设置列表item大小
     * create by Eastevil at 2022/8/23 11:40
     * @author Eastevil
     * @param rootView
     *      item根view
     *@param entity
     *      item显示的数据对象
     * @return
     *      void
     */
    open fun setViewLayoutParams(rootView : View?, entity : T){
        Log.d(TAG,"setViewLayoutParams");
        val params = rootView?.layoutParams;
        params?.width = entity.itemWidth();
        params?.height = entity.itemHeight();
        rootView?.layoutParams = params;
    }

    companion object{
        private const val TAG = "EastRecyclerAdapter==>";
    }
}
