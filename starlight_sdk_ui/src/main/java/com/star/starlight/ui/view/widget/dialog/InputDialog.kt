package com.star.starlight.ui.view.widget.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ObservableField
import com.star.starlight.ui.R
import com.star.starlight.ui.databinding.DialogInputBinding
import com.starlight.dot.framework.utils.defaultWidth
import com.starlight.dot.framework.utils.dip2px
import com.starlight.dot.framework.utils.getScreenSize
import com.starlight.dot.framework.widget.BaseBuilderDialog
import com.starlight.dot.framework.widget.BaseDialog
import com.starlight.dot.framework.widget.DialogBuilder

class InputDialog : BaseBuilderDialog<DialogInputBinding,InputDialog.Builder>{
    private var bottomMarginLeft : Int = 0;
    private var bottomMarginRight : Int = 0;
    private var bottomMarginBottom : Int = 0;
    private lateinit var tipsText : ObservableField<String>;
    private lateinit var haveTips : ObservableField<Boolean>;
    private lateinit var hintText : ObservableField<String>;
    private lateinit var showSpace : ObservableField<Boolean>;
    private var editText : String? = null;
    private var submitHeight : Int = 45.dip2px();
    private var cancelHeight : Int = 45.dip2px();
    private var bottomSpaceWidth : Int = 0;
    private var submitResId : Int = 0;
    private var cancelResId : Int = 0;

    /**
     * 定义标记
     */
    private var tag : Int = 0;

    private var onInputCallBack : ((tag : Int,text : String)->Unit)? = null;
    private var submitTextColor : Int = Color.WHITE;
    private var cancelTextColor : Int = Color.BLACK;

    private constructor(builder: Builder) : super(builder, R.style.AppDialog) {
        dataBinding.haveSubmit = haveSubmit;
        dataBinding.haveCancel = haveCancel;
        dataBinding.titleText = title;
        dataBinding.submitText = submitText;
        dataBinding.cancelText = cancelText;
        tipsText = ObservableField();
        tipsText.set(builder.tipsText);
        haveTips = ObservableField();
        haveTips.set(builder.haveHintTips);
        hintText = ObservableField();
        hintText.set(builder.hintText);
        this.editText = builder.editText;
        if(editText != null && editText?.isNotBlank() == true){
            dataBinding.editInput.setText(editText);
        }
        showSpace = ObservableField();
        showSpace.set(false);
        this.submitResId = builder.submitResId;
        this.cancelResId = builder.cancelResId;
        this.onInputCallBack = builder.onInputCallBack;
        dataBinding.hintText = hintText;
        dataBinding.haveSubmit = haveSubmit;
        dataBinding.haveCancel = haveCancel;
        dataBinding.haveTips = haveTips;
        dataBinding.hintTipsText = tipsText;
        if(submitResId != 0 && true == haveSubmit.get()){
            dataBinding.tvSlDialogSubmit.setBackgroundResource(submitResId);
        }
        if(cancelResId != 0 && true == haveCancel.get()){
            dataBinding.tvSlDialogCancel.setBackgroundResource(cancelResId);
        }
        if(builder.showSpace){
            if(true == haveSubmit.get() && true == haveCancel.get()){
                showSpace.set(true);
            }
        }
    }

    override fun initDialog(buider: Builder) {
        this.bottomMarginBottom = buider.bottomMarginBottom;
        this.bottomMarginLeft = buider.bottomMarginLeft;
        this.bottomMarginRight = buider.bottomMarginRight;
        this.submitHeight = buider.submitHeight;
        this.cancelHeight = buider.cancelHeight;
        this.bottomSpaceWidth = buider.bottomSpaceWidth;
        val llParams = dataBinding.llDialogButtom.layoutParams;
        if(llParams is ConstraintLayout.LayoutParams){
            llParams.setMargins(bottomMarginLeft,0,bottomMarginRight,bottomMarginBottom);
        }
        var llBottomHeight = 50.dip2px();
        if(haveSubmit.get() == true){
            val submitParams = dataBinding.tvSlDialogSubmit.layoutParams;
            submitParams.height = submitHeight;
            dataBinding.tvSlDialogSubmit.layoutParams = submitParams;
            llBottomHeight = submitHeight;
        }
        if(haveCancel.get() == true){
            val cancelParams = dataBinding.tvSlDialogCancel.layoutParams;
            cancelParams.height = cancelHeight;
            dataBinding.tvSlDialogCancel.layoutParams = cancelParams;
            if(cancelHeight > submitHeight){
                llBottomHeight = cancelHeight;
            }
        }
        llParams.height = llBottomHeight;
        dataBinding.llDialogButtom.layoutParams = llParams;
        dataBinding.dialog = this;
    }

    /**
     * 指定当前Dialog使用得布局资源id
     * create by Administrator at 2021/4/25 12:01
     * @author Administrator
     * @since 1.0.0
     * @see onCreate
     * @return
     *      当前Dialog使用得布局资源ID
     */
    override fun layoutRes(): Int {
        return R.layout.dialog_input;
    }

    /**
     * 设置当前弹框的高度
     * create by Administrator at 2021/4/25 12:03
     * @author Administrator
     * @since 1.0.0
     * @see initDialog
     * @return
     *      当前弹框的高度
     */
    override fun getHeight(): Int {
        return 220.dip2px();
    }

    fun setTag(tag : Int){
        this.tag = tag;
    }

    fun setTipsText(tipsText: String){
        this.tipsText.set(tipsText);
        this.haveTips.set(true);
    }

    fun setHint(hint : String){
        this.hintText.set(hint);
    }

    fun setText(text : String){
        editText = text;
        if(text.isNotBlank()){
            dataBinding.editInput.setText(text);
        }
    }

    override fun onSubmit() {
        super.onSubmit()
        val text = dataBinding.editInput.text.toString();
        onInputCallBack?.invoke(tag,text);
        dismiss();
    }

    override fun onCancel() {
        super.onCancel()
        dismiss();
    }

    class Builder(context: Context) : DialogBuilder<InputDialog>(context) {
        internal var tipsText : String? = null;
        internal var haveHintTips : Boolean = false;
        internal var hintText : String? = null;
        internal var editText : String? = null;
        internal var bottomMarginLeft : Int = 0;
        internal var bottomMarginRight : Int = 0;
        internal var bottomMarginBottom : Int = 0;
        internal var submitHeight : Int = 45.dip2px();
        internal var cancelHeight : Int = 45.dip2px();
        internal var bottomSpaceWidth : Int = 0;
        internal var submitResId : Int = R.drawable.shape_border_grey_fillet_white_15;
        internal var cancelResId : Int = R.drawable.shape_border_grey_fillet_white_15;
        internal var onInputCallBack : ((tag : Int,text : String)->Unit)? = null;
        internal var showSpace : Boolean = false;
        internal var submitTextColor : Int = context.getColor(R.color.colorWhite);
        internal var cancelTextColor : Int = context.getColor(R.color.colorAppText);

        fun tipsText(tipsText : String): Builder {
            this.tipsText = tipsText;
            return this;
        }

        fun haveHintTips(haveHintTips : Boolean): Builder {
            this.haveHintTips = haveHintTips;
            return this;
        }

        fun hintText(hintText : String): Builder {
            this.hintText = hintText;
            return this;
        }

        fun text(text : String): Builder {
            this.editText = text;
            return this;
        }

        fun bottomMargin(bottomMarginLeft : Int,bottomMarginRight : Int,bottomMarginBottom :  Int): Builder {
            this.bottomMarginBottom = bottomMarginBottom;
            this.bottomMarginLeft = bottomMarginLeft;
            this.bottomMarginRight = bottomMarginRight;
            return this;
        }

        fun submitHeight(submitHeight : Int): Builder {
            this.submitHeight = submitHeight;
            return this;
        }

        fun cancelHeight(cancelHeight : Int): Builder {
            this.cancelHeight = cancelHeight;
            return this;
        }

        fun bottomSpaceWidth(bottomSpaceWidth : Int): Builder {
            this.bottomSpaceWidth = bottomSpaceWidth;
            return this;
        }

        fun submitResId(submitResId : Int): Builder {
            this.submitResId = submitResId;
            return this;
        }

        fun cancelResId(cancelResId : Int): Builder {
            this.cancelResId = cancelResId;
            return this;
        }

        fun onInputCallBack(onInputCallBack : ((tag : Int,text : String)->Unit)): Builder {
            this.onInputCallBack = onInputCallBack;
            return this;
        }

        fun showSpace(showSpace : Boolean): Builder {
            this.showSpace = showSpace;
            return this;
        }

        fun submitTextColor(submitTextColor : Int): Builder {
            this.submitTextColor = submitTextColor;
            return this;
        }

        fun cancelTextColor(cancelTextColor : Int): Builder {
            this.cancelTextColor = cancelTextColor;
            return this;
        }

        override fun builder(): InputDialog {
            return InputDialog(this);
        }
    }
}
