package com.star.starlight.ui.view.commons

import android.content.Context
import android.graphics.Color
import androidx.databinding.ViewDataBinding
import com.star.starlight.ui.view.presenter.EastBasePresenter
import com.starlight.dot.framework.BR
import com.starlight.dot.framework.commons.EastAdapter
import com.starlight.dot.framework.utils.SLog
import java.lang.Exception

abstract class EastBaseAdapter<T,P : EastBasePresenter<T>> : EastAdapter<T>{
    var onItemClick : ((entity : T)->Unit)? = null;
    var onItemPositionSelect : ((entity : T,position : Int)-> Unit)? = null;
    var onItemMenuClickListener : ((entity : T)-> Unit)? = null;
    var onEditListener : ((entity : T)-> Unit)? = null;

    constructor(context : Context, list : List<T>?) : super(context,list){

    }

    constructor(context: Context) : super(context){

    }

    override fun setBean(dataBinding: ViewDataBinding?, varid: Int, position: Int) {
        super.setBean(dataBinding, varid, position)
        try {
            dataBinding?.setVariable(BR.presenter,createPresenter());
        }catch (e : Exception){
            SLog.e(TAG,"set presenter error");
        }
    }

    open fun addItemClick(onItemClick : ((entity : T)->Unit)){
        this.onItemClick = onItemClick;
    }

    open fun addOnItemPositionSelect(onItemPositionSelect : ((entity : T,position : Int)-> Unit)){
        this.onItemPositionSelect = onItemPositionSelect;
    }

    open fun onItemMenuClickListener(onItemMenuClickListener : ((entity : T)-> Unit)){
        this.onItemMenuClickListener = onItemMenuClickListener;
    }

    open fun onEditListener(onEditListener : ((entity : T)-> Unit)){
        this.onEditListener = onEditListener;
    }

    abstract fun createPresenter() : P;

    fun getColor(id : Int): Int {
        return context.getColor(id);
    }

    fun parseColor(color : String): Int {
        return Color.parseColor(color);
    }

    fun getString(resId : Int): String {
        return context.getString(resId);
    }

    fun getString(resId : Int,vararg formatArgs : String): String {
        return context.getString(resId,formatArgs);
    }

    companion object{
        private const val TAG = "SL_EastBaseAdapter==>"
    }
}
