package com.star.starlight.ui.view.commons

import android.content.Context
import com.star.starlight.ui.view.entity.adapter.IUniversal
import com.star.starlight.ui.view.entity.adapter.UniversalLayoutData

abstract class UniversalBuilder<T : UniversalAdapter>{
    internal var context : Context;
    internal var dataList : MutableList<IUniversal>? = null;
    internal var layoutList : MutableList<UniversalLayoutData>;
    internal var onItemClick : ((entity : IUniversal)->Unit)? = null;
    internal var onItemPositionSelect : ((entity : IUniversal, position : Int)-> Unit)? = null;
    internal var onItemMenuClickListener : ((entity : IUniversal)-> Unit)? = null;
    internal var onEditListener : ((entity : IUniversal)-> Unit)? = null;

    constructor(context: Context){
        this.context = context;
        layoutList = mutableListOf();
    }

    constructor(context: Context, dataList : MutableList<IUniversal>?){
        this.context = context;
        this.dataList = dataList;
        layoutList = mutableListOf();
    }

    fun addLayout(layoutId : Int,brId : Int,dataType : Int): UniversalBuilder<T> {
        val layoutData = UniversalLayoutData();
        layoutData.brId = brId;
        layoutData.dataType = dataType;
        layoutData.layoutId = layoutId;
        layoutList.add(layoutData);
        return this;
    }

    fun onItemClick(onItemClick : ((entity : IUniversal)->Unit)): UniversalBuilder<T> {
        this.onItemClick = onItemClick;
        return this;
    }

    fun onItemPositionSelect(onItemPositionSelect : ((entity : IUniversal, position : Int)-> Unit)): UniversalBuilder<T> {
        this.onItemPositionSelect = onItemPositionSelect;
        return this;
    }

    fun onItemMenuClickListener(onItemMenuClickListener : ((entity : IUniversal)-> Unit)): UniversalBuilder<T> {
        this.onItemMenuClickListener = onItemMenuClickListener;
        return this;
    }

    fun onEditListener(onEditListener : ((entity : IUniversal)-> Unit)): UniversalBuilder<T> {
        this.onEditListener = onEditListener;
        return this;
    }

    abstract fun builder() : T;
}
