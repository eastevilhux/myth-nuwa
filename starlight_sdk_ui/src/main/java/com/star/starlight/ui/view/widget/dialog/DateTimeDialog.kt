package com.star.starlight.ui.view.widget.dialog

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.GridLayoutManager
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.star.starlight.ui.R
import com.star.starlight.ui.databinding.DialogDatetimeBinding
import com.star.starlight.ui.view.adapter.DateTimeAdapter
import com.starlight.dot.framework.utils.*
import com.starlight.dot.framework.widget.BaseBuilderDialog
import com.starlight.dot.framework.widget.DialogBuilder
import java.util.*

/**
 * 时间选择弹出框
 * @version 1.0.0
 * @since 1.0.0
 * @author EastEvil
 */
class DateTimeDialog : BaseBuilderDialog<DialogDatetimeBinding, DateTimeDialog.Builder>{
    private lateinit var timeFlag : ObservableField<Boolean>;
    private lateinit var secondFlag : ObservableField<Boolean>;
    private lateinit var adapter : DateTimeAdapter;
    private var dateTime : Long = System.currentTimeMillis();

    /**
     * 当前选择得具体时间文本
     */
    private lateinit var timeText : ObservableField<String>;

    /**
     * 当前选择的具体日期文本
     */
    private lateinit var dateTimeText : ObservableField<String>;

    /**
     * 左上角年份月份展示文本
     */
    private lateinit var yearMonthText : ObservableField<String>;

    /**
     * 是否允许选择已经过去的时间
     */
    private var allowPassed : Boolean = false;

    /**
     * 是否允许选择选择未来的时间
     */
    private var allowFuture : Boolean = true;

    private var onDateTimeClick : ((dtDayEntity : DTDayEntity?, dateTime : Long, tag : Int)->Unit)? = null;

    /**
     * 时间显示进制，1：24h，2：12进制
     */
    private var hourHex : Int = 1;

    private var hourList : MutableList<String>? = null;
    private var minuteList : MutableList<String>? = null;
    private var secondList : MutableList<String>? = null;

    /**
     * 上次点击选中的日期下标
     */
    private var lastSelectDayIndex : Int =  -1;

    /**
     * 选择的日期对象
     */
    private var dtDayEntity : DTDayEntity? = null;

    /**
     * 标识
     */
    private var tag : Int = Int.MAX_VALUE;

    private constructor(builder: Builder) : super(builder, R.style.AppDialog) {
        timeFlag = ObservableField();
        timeFlag.set(builder.timeFlag)
        dataBinding.timeFlag = timeFlag;
        dataBinding.titleText = title;
        secondFlag = ObservableField();
        secondFlag.set(builder.secondFlag);
        dataBinding.secondFlag = secondFlag;
        dateTime = builder.dateTime;
        timeText = ObservableField();
        dateTimeText = ObservableField();
        yearMonthText = ObservableField();
        dataBinding.timeText = timeText;
        dataBinding.dateTimeText = dateTimeText;
        dataBinding.yearMonthText = yearMonthText;
        this.allowFuture = builder.allowFuture;
        this.allowPassed = builder.allowPassed;
        this.onDateTimeClick = builder.onDateTimeClick;
        invalidDateTime();
        invalidDateTimeList();
        if(timeFlag.get() == true){
            invalidTimePicker();
        }
    }

    override fun getHeight(): Int {
        if(timeFlag.get() == true){
            return 495.dip2px();
        }
        return 395.dip2px();
    }

    override fun getWidth(): Int {
        return context.defaultWidth();
    }

    override fun initDialog(buider: Builder) {
        dataBinding.dialog = this;
        dataBinding.rvDatetime.layoutManager = GridLayoutManager(context,7);
        adapter = DateTimeAdapter(context,null);
        dataBinding.adapter = adapter;

        adapter.onDateTimeClick {
            //选择日期
            if(it.state == 3 || it.state == 0){
                return@onDateTimeClick;
            }
            val list = adapter.dataList;
            if(lastSelectDayIndex != -1){
                //设置上一次选中的下标的日期为未选中
                if(lastSelectDayIndex < list.size){
                    list.get(lastSelectDayIndex).state = 1;
                    adapter.notifyItemChanged(lastSelectDayIndex);
                }
            }
            //设置本次选中的日期为选中状态
            val itIndex = list.indexOf(it);
            if(itIndex < list.size){
                list[itIndex].state = 2;
            }
            lastSelectDayIndex = itIndex;
            //修改日期
            val c = Calendar.getInstance();
            c.time = Date(dateTime);
            c.set(Calendar.DAY_OF_MONTH,it.day);
            dateTime = c.time.time;
            invalidDateTime();
            //刷新列表
            adapter.notifyItemChanged(itIndex);
            dtDayEntity = it;
        }

        dataBinding.wheelviewHour.setOnItemSelectedListener {
            val hour = hourList?.get(it);
            hour?.let {
                //选择小时
                val c = Calendar.getInstance();
                c.time = Date(dateTime);
                //设置选择的小时值
                c.set(Calendar.HOUR_OF_DAY,it.toInt());
                dateTime = c.time.time;
                invalidDateTime();
            }
        }

        dataBinding.wheelviewMinute.setOnItemSelectedListener {
            val minute = minuteList?.get(it);
            minute?.let {
                //选择分钟
                val c = Calendar.getInstance();
                c.time = Date(dateTime);
                //设置选择的小时值
                c.set(Calendar.MINUTE,it.toInt());
                dateTime = c.time.time;
                invalidDateTime();
            }
        }
        dataBinding.wheelviewSecond.setOnItemSelectedListener {
            val second = secondList?.get(it);
            second?.let {
                //选择秒
                val c = Calendar.getInstance();
                c.time = Date(dateTime);
                //设置选择的小时值
                c.set(Calendar.SECOND,it.toInt());
                dateTime = c.time.time;
                invalidDateTime();
            }
        }
    }

    override fun layoutRes(): Int {
        return R.layout.dialog_datetime;
    }

    override fun onViewClick(view: View) {
        super.onViewClick(view)
        when(view.id){
            R.id.iv_dt_last->{
                lastMonth();
            }
            R.id.iv_dt_next->{
                nextMonth();
            }
            R.id.tv_date_time_enter->{
                dismiss();
                onDateTimeClick?.invoke(dtDayEntity,dateTime,tag);
            }
        }
    }

    fun setTag(tag : Int){
        this.tag = tag;
    }

    fun getTag(): Int {
        return tag;
    }

    fun setAllowPassed(allowPassed : Boolean){
        this.allowPassed = allowPassed;
    }

    fun setAllowFuture(allowFuture : Boolean){
        this.allowFuture = allowFuture;
    }

    /**
     * 初始化时间列表
     * create by Eastevil at 2022/9/19 10:47
     * @author Eastevil
     * @param
     * @return
     */
    private fun invalidDateTimeList(){
        var list = adapter.dataList;
        if(list == null){
            list = mutableListOf();
        }else{
            list.removeAll(list);
        }
        //添加星期类型数据
        for(w in 1..7){
            val weekDT = DTDayEntity();
            weekDT.type = 0;
            weekDT.week = w;
            weekDT.state = 0;
            list.add(weekDT);
        }
        val c = Calendar.getInstance();
        c.time = Date(dateTime);
        //获取本月第一天是星期几
        val monthFristDayWeek = c.monthFirstDayWeek();
        if(1 != monthFristDayWeek){
            //本月第一天不是星期一,则需要填充本月第一天周一之前上月的末尾天数
            val lastMonthMaxDay = monthFristDayWeek-1;
            val lastMonthLastDay = c.addMonth(-1).getMaxDay();
            for(u in lastMonthMaxDay downTo 1){
                val uDay = DTDayEntity();
                uDay.type = 1;
                uDay.state = 3;
                uDay.year = c.getYear();
                uDay.month = c.getMonth();
                uDay.day = lastMonthLastDay - (u-1);
                list.add(uDay);
            }
        }
        //重新设置Calendar时间为dateTime
        c.time = Date(dateTime);
        val minDay = c.getMinDay();
        val maxDay = c.getMaxDay();
        for(d in minDay..maxDay){
            val day = DTDayEntity();
            day.type = 1;
            day.year = c.getYear();
            day.month = c.getMonth();
            day.day = d;
            if(day.isSameTodayDay(1)){
                day.state = 2;
                lastSelectDayIndex = list.size;
                dtDayEntity = day;
            }else{
                if(day.isBeforeToday()){
                    if(allowPassed){
                        day.state = 1;
                    }else{
                        day.state = 3;
                    }
                }
                if(day.isAfterToday()){
                    if(allowFuture){
                        day.state = 1;
                    }else{
                        day.state = 3;
                    }
                }
            }
            list.add(day);
        }
        val monthLastDayWeek = c.monthLastDayWeek();
        val totalSurplusDay = 7 - monthLastDayWeek;
        for(i in 1 .. totalSurplusDay){
            val uDay = DTDayEntity();
            uDay.type = 1;
            uDay.state = 3;
            uDay.year = c.getYear();
            uDay.month = c.getMonth();
            uDay.day = i;
            list.add(uDay);
        }
        adapter.dataList = list;
        adapter.notifyDataSetChanged();

    }

    /**
     * 根据当前指定的时间戳初始化时间
     * create by Eastevil at 2022/9/19 10:48
     * @author Eastevil
     * @param
     * @return
     */
    private fun invalidDateTime(){
        val c = Calendar.getInstance();
        val dt = Date(dateTime);
        c.time = dt;
        var dtTimeText = "${c.getHourText()}:${c.getMinuteText()}";
        if(secondFlag.get() == true){
            dtTimeText = "${dtTimeText}:${c.getSecondText()}"
        }
        timeText.set(dtTimeText)
        val dtTime = c.time.time;
        dateTimeText.set(dtTime.format(getString(R.string.sl_date_format_with_week)));
        yearMonthText.set(dtTime.format(getString(R.string.sl_date_year_month_format)));
    }

    private fun invalidTimePicker(){
        hourList = mutableListOf<String>();
        if(hourHex == 1){
            for(h in 0 until 24){
                if(h < 10){
                    hourList?.add("0${h}");
                }else{
                    hourList?.add(h.toString());
                }
            }
        }else{
            for(h in 0 until 12){
                if(h < 10){
                    hourList?.add("0${h}");
                }else{
                    hourList?.add(h.toString());
                }
            }
        }
        dataBinding.wheelviewHour.visibility = View.VISIBLE;
        dataBinding.wheelviewHour.adapter = ArrayWheelAdapter(hourList);
        minuteList = mutableListOf<String>();
        for(m in 0 until 60){
            if(m < 10){
                minuteList?.add("0${m}");
            }else{
                minuteList?.add(m.toString());
            }
        }
        dataBinding.wheelviewMinute.visibility = View.VISIBLE;
        dataBinding.wheelviewMinute.adapter = ArrayWheelAdapter(minuteList);
        if(secondFlag.get() == true){
            secondList = mutableListOf<String>();
            for(s in 0 until 60){
                if(s < 10){
                    secondList?.add("0${s}");
                }else{
                    secondList?.add(s.toString());
                }
            }
            dataBinding.wheelviewSecond.visibility = View.VISIBLE;
            dataBinding.wheelviewSecond.adapter = ArrayWheelAdapter(secondList);
        }
    }

    /**
     * 显示下个月
     */
    fun nextMonth(){
        val c = Calendar.getInstance();
        c.time = Date(dateTime);
        if(c.beforNow()){
            c.add(Calendar.MONTH,1);
            dateTime = c.time.time;
            lastSelectDayIndex = -1;
            invalidDateTime();
            invalidDateTimeList();
        }else{
            if(!allowFuture){
                return;
            }
            c.add(Calendar.MONTH,1);
            dateTime = c.time.time;
            lastSelectDayIndex = -1;
            invalidDateTime();
            invalidDateTimeList();
        }

    }

    /**
     * 显示上个月
     * create by Eastevil at 2022/9/19 11:42
     * @author Eastevil
     * @param
     * @return
     */
    private fun lastMonth(){
        val c = Calendar.getInstance();
        c.time = Date(dateTime);
        if(c.afterNow()){
            c.add(Calendar.MONTH,-1);
            dateTime = c.time.time;
            lastSelectDayIndex = -1;
            invalidDateTime();
            invalidDateTimeList();
        }else {
            if (!allowPassed) {
                return;
            }
            c.add(Calendar.MONTH,-1);
            dateTime = c.time.time;
            lastSelectDayIndex = -1;
            invalidDateTime();
            invalidDateTimeList();
        }
    }


    class Builder(context: Context) : DialogBuilder<DateTimeDialog>(context) {
        /**
         * 是否显示时间选择
         */
        internal var timeFlag : Boolean = false;
        internal var secondFlag : Boolean = false;

        /**
         * 当前时间
         */
        internal var dateTime : Long = System.currentTimeMillis();



        /**
         * 是否允许选择已经过去的时间
         */
        internal var allowPassed : Boolean = false;

        /**
         * 是否允许选择选择未来的时间
         */
        internal var allowFuture : Boolean = true;

        /**
         * 时间显示进制，1：24h，2：12进制
         */
        internal var hourHex : Int = 1;

        internal var onDateTimeClick : ((dtDayEntity : DTDayEntity?, dateTime : Long, tag : Int)->Unit)? = null;

        /**
         * 是否显示时间选择
         * create by Administrator at 2022/9/18 22:08
         * @author Administrator
         * @param timeFlag
         *      是否显示时间选择
         * @return
         *      [com.star.light.brand.commons.widget.dialog.Builder]
         */
        fun timeFlag(timeFlag : Boolean): Builder {
            this.timeFlag = timeFlag;
            return this;
        }

        /**
         * 是否显示秒选择控件
         * create by Administrator at 2022/9/18 22:06
         * @author Administrator
         * @param secondFlag
         *      是否选择
         * @return
         *      [com.star.light.brand.commons.widget.dialog.Builder]
         */
        fun secondFlag(secondFlag : Boolean): Builder {
            this.secondFlag = secondFlag;
            return this;
        }

        fun dateTime(dateTime : Long): Builder {
            this.dateTime = dateTime;
            return this;
        }

        fun allowPassed(allowPassed : Boolean): Builder {
            this.allowPassed = allowPassed;
            return this;
        }

        fun allowFuture(allowFuture : Boolean): Builder {
            this.allowFuture = allowFuture;
            return this;
        }

        /**
         * 设置小时显示进制
         * create by Eastevil at 2022/9/20 15:33
         * @author Eastevil
         * @param hourHex
         *      1:24进制，1：12进制
         * @return
         *      [com.star.light.brand.commons.widget.dialog.Builder]
         */
        fun hourHex(hourHex : Int): Builder {
            this.hourHex = hourHex;
            return this;
        }

        fun onDateTimeClick(onDateTimeClick : ((dtDayEntity : DTDayEntity?, dateTime : Long, tag : Int)->Unit)): Builder {
            this.onDateTimeClick = onDateTimeClick;
            return this;
        }


        override fun builder(): DateTimeDialog {
            return DateTimeDialog(this);
        }
    }

    /**
     * 日期时间对象
     * create by Administrator at 2022/9/18 22:13
     * @author Administrator
     */
    class DTDayEntity{

        val dtId : String
        get() {
            val s = "${type}${year}${month}${day}${week}${state}${bgResouce}${textColor}";
            return MD5Util.encryptDate(s);
        }

        /**
         * 0:星期
         * 1:日期
         */
        var type : Int = 1;

        var year : Int = Calendar.getInstance().getYear();
        var month : Int = Calendar.getInstance().getMonth();
        var day : Int = Calendar.getInstance().getDay();
        var week : Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        val showText : String
        get() {
            if(type == 0){
                return when(week){
                    1-> "一"
                    2-> "二"
                    3-> "三"
                    4-> "四"
                    5-> "五"
                    6-> "六"
                    7-> "日"
                    else-> "U"
                }
            }
            return day.toString();
        }

        /**
         * 当前日期状态
         * 0:不可点击
         * 1:非选中
         * 2:选中
         * 3:不可用，只显示
         */
        var state : Int = 0;

        val bgResouce : Int
        get() {
            return when(state){
                0-> R.drawable.sl_shape_fill_white;
                1-> R.drawable.sl_shape_fill_white;
                3-> R.drawable.sl_shape_fill_white;
                2-> R.drawable.bg_dialog_dt_select;
                else -> R.drawable.sl_shape_fill_white;
            }
        }

        val textColor : Int
        get() {
            return when(state){
                0-> Color.parseColor("#101010");
                1-> Color.parseColor("#101010");
                2-> Color.parseColor("#F0AF46");
                3-> Color.parseColor("#cdcdcd");
                else-> Color.parseColor("#cdcdcd");
            }
        }

        fun isSameDay(dt : DTDayEntity) : Boolean {
            if(type == 0){
                return this.week == dt.week;
            }else{
                val isSameYear = this.year == dt.year;
                if(!isSameYear){
                    return false;
                }
                val isSameMonth = this.month == dt.month;
                if(!isSameMonth){
                    return false;
                }
                val isSameDay = this.day == dt.day;
                if(!isSameDay){
                    return false;
                }
                return true;
            }
        }

        /**
         * 比较是否与今天相同
         * create by Eastevil at 2022/9/20 13:37
         * @author Eastevil
         * @param type
         *      需要比较的对象类型，0：星期类型，1：日期类型
         * @return
         *      是否与今天相同
         */
        fun isSameTodayDay(type : Int): Boolean {
            val today = DTDayEntity();
            val c = Calendar.getInstance();
            today.type = type;
            today.week = c.getWeek();
            today.year = c.getYear();
            today.month = c.getMonth();
            today.day = c.getDay();
            return isSameDay(today);
        }

        fun isBeforeToday(): Boolean {
            val c = Calendar.getInstance();
            c.set(Calendar.YEAR,year);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.DAY_OF_MONTH,day);
            val nowC = Calendar.getInstance();
            nowC.time = Date();
            return c.before(nowC);
        }

        fun isAfterToday(): Boolean {
            val c = Calendar.getInstance();
            c.set(Calendar.YEAR,year);
            c.set(Calendar.MONTH,month);
            c.set(Calendar.DAY_OF_MONTH,day);
            val nowC = Calendar.getInstance();
            nowC.time = Date();
            return c.after(nowC);
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DTDayEntity

            if (dtId != other.dtId) return false
            if (type != other.type) return false
            if (year != other.year) return false
            if (month != other.month) return false
            if (day != other.day) return false
            if (week != other.week) return false
            if (state != other.state) return false

            return true
        }

        override fun hashCode(): Int {
            var result = dtId.hashCode()
            result = 31 * result + type
            result = 31 * result + year
            result = 31 * result + month
            result = 31 * result + day
            result = 31 * result + week
            result = 31 * result + state
            return result
        }


    }

}
