package com.star.starlight.ui.view.commons

import android.util.Log
import androidx.databinding.ViewDataBinding
import com.star.starlight.ui.databinding.RecyclerUniversalTextBinding
import com.star.starlight.ui.view.entity.adapter.IUniversal
import com.star.starlight.ui.view.entity.adapter.UniversalLayoutData
import com.star.starlight.ui.view.presenter.UniversalPresenter
import com.star.starlight.ui.view.presenter.impl.UniversalPresenterImpl
import com.star.starlight.ui.view.entity.adapter.ItemType
import com.starlight.dot.framework.utils.SLog
import com.starlight.dot.framework.utils.toJSON

abstract class UniversalAdapter : EastRecyclerAdapter<IUniversal, UniversalPresenter<IUniversal>>{
    private var layoutList : MutableList<UniversalLayoutData>? = null;

    companion object{
        private const val TAG = "UniversalAdapter==>"
    }

    constructor(builder: UniversalBuilder<*>) : super(builder.context,builder.dataList) {
        Log.d(TAG,"constructor init")
        this.layoutList = builder.layoutList;
        if(this.layoutList == null){
            this.layoutList = mutableListOf();
        }
        onItemClick = builder.onItemClick;
        onItemPositionSelect = builder.onItemPositionSelect;
        onEditListener = builder.onEditListener;
        onItemMenuClickListener = builder.onItemMenuClickListener;
        addLayout();
    }

    /**
     * create by Administrator at 2022/8/27 17:46
     * @author Administrator
     * @return
     */
    private fun addLayout(){
        layoutList?.let {
            if(it.isNotEmpty()){
                it.forEach {
                    bindMap.put(it.layoutId,it.brId);
                }
            }
        }
    }

    override fun setBean(dataBinding: ViewDataBinding?, varid: Int, position: Int) {
        super.setBean(dataBinding, varid, position)
        SLog.d(TAG,"setBean==>");
        layoutList?.let {
            for(i in 0 until it.size){
                Log.d(TAG,"i=>${i},brid==>${varid}");
                if(it.get(i).brId == varid){
                    dataBinding?.setVariable(varid,dataList[position]);
                    break;
                }
            }
        }
    }

    override fun setView(dataBinding: ViewDataBinding?, viewType: Int, position: Int) {
        super.setView(dataBinding, viewType, position)
        setItemTheme(dataBinding,position);
    }

    private fun setItemTheme(dataBinding: ViewDataBinding?,position : Int){
        Log.d(TAG,"setItemTheme==>position")
        dataBinding?.let {
            val t = dataList.get(position);
            val needCountParams = t.needCountViewParams();
            if(needCountParams){
                Log.w(TAG,"need count item view params")
                setViewLayoutParams(dataBinding?.root,dataList[position]);
            }
            val viewType = t.itemViewType();
            Log.w(TAG,"setItemTheme viewType=>${viewType}");
            if(ItemType.UNIVERSAL_TEXT == viewType && it is RecyclerUniversalTextBinding){
                setUniversalTextTheme(it,t);
            }else{
                setViewTheme(it,t,position);
            }
        }
    }

    /**
     * 设置横向文本列表展示item样式
     * create by Eastevil at 2022/10/9 13:24
     * @author Eastevil
     * @param dataBinding
     *      [RecyclerUniversalTextBinding]
     * @param iUniversal
     *      [IUniversal]
     * @return
     *      void
     */
    open fun setUniversalTextTheme(dataBinding : RecyclerUniversalTextBinding,iUniversal: IUniversal){

    }


    /**
     * 设置布局样式
     * create by Administrator at 2022/8/28 1:40
     * @author Administrator
     * @return
     */
    open fun setViewTheme(databinding : ViewDataBinding,universal : IUniversal,position:Int){
        Log.w(TAG,"setViewTheme==>${universal.toJSON()}");
    }

    override fun createPresenter(): UniversalPresenter<IUniversal> = presenter;

    /**
     * 如果需要自定义布局类型，需要添加并返回添加后的集合
     * create by Eastevil at 2022/8/19 15:13
     * @author Eastevil
     * @param map
     *      已封装后的列表item数据集合,集合的key为资源id，value为BR资源id
     * @return
     *      自定义布局集合
     */
    override fun bindLayoutMap(map: MutableMap<Int, Int>): MutableMap<Int, Int>? {
        return null;
    }


    private val presenter = object : UniversalPresenterImpl() {
        override fun onEditListener(entity: IUniversal) {
            super.onEditListener(entity)
            onEditListener?.invoke(entity);
        }

        override fun onItemClickListener(entity: IUniversal, position: Int) {
            super.onItemClickListener(entity, position)
            onItemPositionSelect?.invoke(entity,position);
        }

        override fun onItemMenuClickListener(entity: IUniversal) {
            super.onItemMenuClickListener(entity)
            onItemMenuClickListener?.invoke(entity);
        }

        override fun onItemSelectListener(entity: IUniversal) {
            super.onItemSelectListener(entity)
            onItemClick?.invoke(entity);
        }
    }
}
