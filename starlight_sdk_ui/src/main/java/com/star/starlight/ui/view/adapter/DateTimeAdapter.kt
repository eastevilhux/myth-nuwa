package com.star.starlight.ui.view.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.star.starlight.ui.view.widget.dialog.DateTimeDialog
import com.star.starlight.ui.R
import com.star.starlight.ui.BR
import com.star.starlight.ui.databinding.RecyclerItemDatetimeBinding
import com.star.starlight.ui.view.presenter.impl.DateTimePresenterImpl
import com.starlight.dot.framework.commons.EastAdapter

class DateTimeAdapter : EastAdapter<DateTimeDialog.DTDayEntity>{
    private var onDateTimeClick : ((dateTime : DateTimeDialog.DTDayEntity)->Unit)? = null;

    constructor(context : Context, list : List<DateTimeDialog.DTDayEntity>?) : super(context,list){

    }

    override fun bindItem(p0: MutableMap<Int, Int>?) {
        p0?.put(R.layout.recycler_item_datetime,BR.dateTime);
    }

    override fun getViewItemType(p0: Int): Int {
        return R.layout.recycler_item_datetime;
    }

    override fun setView(dataBinding: ViewDataBinding?, viewType: Int, position: Int) {
        super.setView(dataBinding, viewType, position)
        if(dataBinding is RecyclerItemDatetimeBinding){
            dataBinding.llDateTime.setBackgroundResource(dataList.get(position).bgResouce);
            dataBinding.tvDateTimeNumber.setTextColor(dataList.get(position).textColor);
        }
    }

    override fun setBean(dataBinding: ViewDataBinding?, varid: Int, position: Int) {
        super.setBean(dataBinding, varid, position)
        dataBinding?.setVariable(BR.presenter,presenter);
    }

    fun onDateTimeClick(onDateTimeClick : ((dateTime : DateTimeDialog.DTDayEntity)->Unit)){
        this.onDateTimeClick = onDateTimeClick;
    }

    private val presenter = object : DateTimePresenterImpl() {

        override fun onItemSelectListener(entity: DateTimeDialog.DTDayEntity) {
            super.onItemSelectListener(entity)
            onDateTimeClick?.invoke(entity);
        }
    }
}
