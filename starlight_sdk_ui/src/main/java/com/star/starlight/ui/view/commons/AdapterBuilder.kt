package com.star.starlight.ui.view.commons

import android.content.Context
import com.star.starlight.ui.view.entity.IRecyclerItem
import com.star.starlight.ui.view.entity.RecyclerItemEntity
import com.star.starlight.ui.view.entity.RichItemResouce
import com.star.starlight.ui.view.presenter.IRecyclerPresenter
import com.star.starlight.ui.view.presenter.IRichPresenter
import java.util.*
import kotlin.collections.HashMap

abstract class AdapterBuilder<I : IRecyclerItem, A : RichBaseAdapter<I>> {
    internal var itemResouceMap: MutableMap<Int, RichItemResouce>;
    internal var beanMap: MutableMap<Int, Any>;
    internal var dataList: List<I>? = null

    internal var onItemClickListener : ((entity : RecyclerItemEntity)->Unit)? = null;
    internal var onItemSelectListener : ((entity : RecyclerItemEntity,position : Int)->Unit)? = null;
    internal var onItemMenuClickListener : ((entity : RecyclerItemEntity)->Unit)? = null;
    internal var onEditListener : ((entity : RecyclerItemEntity)->Unit)? = null;

    internal var context : Context;


    constructor(context: Context){
        this.context = context;
        itemResouceMap = HashMap();
        beanMap = HashMap();
    }

    fun addLayout(itemResouce: RichItemResouce): AdapterBuilder<I, A> {
        itemResouceMap.set(itemResouce.layoutId, itemResouce)
        return this
    }

    fun addLayout(layoutId: Int?, brId: Int?): AdapterBuilder<I, A> {
        val itemResouce = RichItemResouce()
        itemResouce.layoutId = layoutId!!
        itemResouce.brId = brId!!
        itemResouce.itemResouceType = RecyclerItemType.ItemType.TYPE_CUSTOM
        itemResouceMap[layoutId] = itemResouce
        return this
    }

    fun addVariable(key : Int,value : Any): AdapterBuilder<I, A> {
        beanMap.put(key,value);
        return this;
    }

    fun <T : IRichPresenter<I>> addListenter(key : Int,listenter : T): AdapterBuilder<I, A> {
        return addVariable(key,listenter);
    }

    fun dataList(dataList: List<I>?): AdapterBuilder<I, A> {
        this.dataList = dataList
        return this
    }

    fun onItemClickListener(onItemClickListener : ((entity : RecyclerItemEntity)->Unit)): AdapterBuilder<I, A> {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    fun onItemSelectListener(onItemSelectListener : ((entity : RecyclerItemEntity,position : Int)->Unit)): AdapterBuilder<I, A> {
        this.onItemSelectListener = onItemSelectListener;
        return this;
    }

    fun getItemResouceMap(): MutableMap<Int, RichItemResouce> {
        return itemResouceMap
    }

    fun getBeanMap(): Map<Int, Any> {
        return beanMap
    }

    fun getOnItemClickListener(): ((RecyclerItemEntity) -> Unit)? {
        return onItemClickListener;
    }

    fun getOnItemSelectListener(): ((RecyclerItemEntity, Int) -> Unit)? {
        return onItemSelectListener;
    }

    fun getOnItemMenuClickListener(): ((RecyclerItemEntity) -> Unit)? {
        return onItemMenuClickListener;
    }

    fun getOnEditListener(): ((RecyclerItemEntity) -> Unit)? {
        return onEditListener;
    }

    fun getDataList(): List<I>? {
        return dataList;
    }

    abstract fun builder(): A

    fun getContext() : Context{
        return context;
    }

    init {
        itemResouceMap = HashMap()
        beanMap = HashMap()
    }
}
