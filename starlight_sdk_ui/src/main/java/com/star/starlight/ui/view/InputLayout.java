package com.star.starlight.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;

import com.star.starlight.ui.R;
import com.star.starlight.ui.databinding.ViewInputBinding;
import com.star.starlight.ui.view.commons.SpaceTextWatcher;

public class InputLayout extends FrameLayout {
    private static final String TAG = "InputLayout==>";

    private static final int GRAVITY_CENTER = 0;
    private static final int GRAVITY_CENTER_VERTICAL_LEFT = 1;
    private static final int GRAVITY_CENTER_VERTICAL_RIGHT = 2;
    private ViewInputBinding dataBinding;
    private ObservableField<String> navText;
    private ObservableField<String> hintText;
    private ObservableField<String> inputText;
    private ObservableField<Boolean> lineState;
    private ObservableField<Boolean> rightImageState;
    /**
     * 是否显示指示图标
     */
    private boolean guideFlag;
    private ObservableField<Boolean> inputEnable;

    private OnInputLayoutListener onInputLayoutListener;

    public final static int TYPE_PHONE = 1;
    public final static int TYPE_EMAIL = 2;
    public final static int TEXT_PASSWORD = 3;
    public final static int NUMBER_PASSWORD = 4;
    public final static int FOUR_DIGITS_SPACE = 5;
    public final static int NUMBER = 6;
    public final static int ID_CARD = 7;

    private int inputType;

    private SpaceTextWatcher spaceTextWatcher;

    public InputLayout(@NonNull Context context) {
        super(context);
    }

    public InputLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public InputLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public OnInputLayoutListener getOnInputLayoutListener() {
        return onInputLayoutListener;
    }

    public void setOnInputLayoutListener(OnInputLayoutListener onInputLayoutListener) {
        this.onInputLayoutListener = onInputLayoutListener;
    }

    public String getInput(){
        return spaceTextWatcher.getTextNotSpace();
    }

    private void init(AttributeSet attrs){
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),R.layout.view_input,
                this,true);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.InputLayout);
        navText = new ObservableField(ta.getString(R.styleable.InputLayout_nav_text));
        hintText = new ObservableField<>(ta.getString(R.styleable.InputLayout_hint_text));
        dataBinding.setNavText(navText);
        dataBinding.setHintText(hintText);
        int navMarginLeft = ta.getDimensionPixelOffset(R.styleable.InputLayout_nav_margin_left,0);
        int navMarginRight = ta.getDimensionPixelOffset(R.styleable.InputLayout_nav_margin_right,0);
        int navTextSize = (int) ta.getDimensionPixelOffset(R.styleable.InputLayout_nav_text_size,15);
        int navTextColor = ta.getColor(R.styleable.InputLayout_nav_text_color,getContext().getColor(R.color.colorAppText));
        int navTextGravity = ta.getInt(R.styleable.InputLayout_nav_text_gravity,0);
        int navTextWidth = ta.getDimensionPixelOffset(R.styleable.InputLayout_nav_text_width,0);

        dataBinding.tvInputText.setTextSize(TypedValue.COMPLEX_UNIT_PX,navTextSize);
        ConstraintLayout.LayoutParams navParams = (ConstraintLayout.LayoutParams) dataBinding.tvInputText.getLayoutParams();
        navParams.width = navTextWidth;
        navParams.setMargins(navMarginLeft,0,navMarginRight,0);
        float navWegith = ta.getFloat(R.styleable.InputLayout_navWeight,0.0f);
        navParams.horizontalWeight = navWegith;

        dataBinding.tvInputText.setLayoutParams(navParams);
        dataBinding.tvInputText.setTextColor(navTextColor);
        switch (navTextGravity){
            case GRAVITY_CENTER:
                dataBinding.tvInputText.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_CENTER_VERTICAL_LEFT:
                dataBinding.tvInputText.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                break;
            case GRAVITY_CENTER_VERTICAL_RIGHT:
                dataBinding.tvInputText.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
                break;
        }
        inputEnable = new ObservableField<>(ta.getBoolean(R.styleable.InputLayout_input_enable,true));
        dataBinding.setInputEable(inputEnable);

        int hintColor = ta.getColor(R.styleable.InputLayout_hint_color,getContext().getColor(R.color.colorAppText));
        dataBinding.etInputText.setHintTextColor(hintColor);
        int input_edit_margin_left = ta.getDimensionPixelOffset(R.styleable.InputLayout_input_edit_margin_left,0);
        int input_edit_margin_right = ta.getDimensionPixelOffset(R.styleable.InputLayout_input_edit_margin_right,0);
        int input_text_size = ta.getDimensionPixelOffset(R.styleable.InputLayout_input_text_size,15);
        int input_text_color = ta.getColor(R.styleable.InputLayout_input_text_color,getContext().getColor(R.color.colorAppText));
        int input_gravity = ta.getInt(R.styleable.InputLayout_input_gravity,GRAVITY_CENTER_VERTICAL_LEFT);
        int max_line = ta.getInt(R.styleable.InputLayout_max_line,1);
        String inputText = ta.getString(R.styleable.InputLayout_input_text);
        this.inputText = new ObservableField<String>(inputText);
        dataBinding.setInputText(this.inputText);

        ConstraintLayout.LayoutParams inputParams = null;
        if(inputEnable.get()){
            inputParams = (ConstraintLayout.LayoutParams) dataBinding.etInputText.getLayoutParams();
        }else{
            inputParams = (ConstraintLayout.LayoutParams) dataBinding.tvInputContent.getLayoutParams();
        }
        inputParams.setMargins(input_edit_margin_left,0,input_edit_margin_right,0);
        float inputWeight = ta.getFloat(R.styleable.InputLayout_inputWeight,1.0f);
        inputParams.horizontalWeight = inputWeight;


        switch (input_gravity){
            case GRAVITY_CENTER:
                dataBinding.etInputText.setGravity(Gravity.CENTER);
                dataBinding.tvInputContent.setGravity(Gravity.CENTER);
                break;
            case GRAVITY_CENTER_VERTICAL_LEFT:
                dataBinding.etInputText.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                dataBinding.tvInputContent.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                break;
            case GRAVITY_CENTER_VERTICAL_RIGHT:
                dataBinding.etInputText.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
                dataBinding.tvInputContent.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                break;
        }
        if(inputEnable.get()){
            dataBinding.etInputText.setMaxLines(1);
            dataBinding.etInputText.setEllipsize(TextUtils.TruncateAt.END);
            dataBinding.etInputText.setLayoutParams(inputParams);
        }else{
            dataBinding.tvInputContent.setMaxLines(1);
            dataBinding.tvInputContent.setEllipsize(TextUtils.TruncateAt.END);
            dataBinding.tvInputContent.setLayoutParams(inputParams);
        }
        dataBinding.etInputText.setTextSize(TypedValue.COMPLEX_UNIT_PX,input_text_size);
        dataBinding.etInputText.setTextColor(input_text_color);
        dataBinding.tvInputContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,input_text_size);
        dataBinding.tvInputContent.setTextColor(input_text_color);

        boolean line_state = ta.getBoolean(R.styleable.InputLayout_line_state,true);
        lineState = new ObservableField<>(line_state);
        dataBinding.setLineState(lineState);
        if(line_state){
            int line_color = ta.getColor(R.styleable.InputLayout_line_color,getContext().getColor(R.color.slColorLine));
            int line_height = ta.getDimensionPixelOffset(R.styleable.InputLayout_line_height,1);
            int line_margin_left = ta.getDimensionPixelOffset(R.styleable.InputLayout_line_margin_left,0);
            int line_margin_right = ta.getDimensionPixelOffset(R.styleable.InputLayout_line_margin_right,0);
            ConstraintLayout.LayoutParams lineParams = (ConstraintLayout.LayoutParams) dataBinding.tvInputLine.getLayoutParams();
            lineParams.height = line_height;
            lineParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
            lineParams.setMargins(line_margin_left,0,line_margin_right,0);
            dataBinding.tvInputLine.setLayoutParams(lineParams);
            dataBinding.tvInputLine.setBackgroundColor(line_color);
        }
        guideFlag = ta.getBoolean(R.styleable.InputLayout_guide_falg,false);
        rightImageState = new ObservableField<>(guideFlag);
        if(guideFlag){
            if(inputEnable.get()){
                //输入状态下，判断输入框是否已经展示数据
                if(TextUtils.isEmpty(inputText)){
                    //输入内容未空，则不显示删除按钮
                    rightImageState.set(false);
                }else{
                    //显示删除按钮
                    rightImageState.set(true);
                }
            }
        }
        dataBinding.setRightImageState(rightImageState);

        int guide_width = ta.getDimensionPixelOffset(R.styleable.InputLayout_guide_width,15);
        int guide_height = ta.getDimensionPixelOffset(R.styleable.InputLayout_guide_height,15);
        int guide_margin_left = ta.getDimensionPixelOffset(R.styleable.InputLayout_guide_margin_left,0);
        int guide_margin_right = ta.getDimensionPixelOffset(R.styleable.InputLayout_guide_margin_right,0);
        if(guideFlag){
            Drawable guideDrawable = ta.getDrawable(R.styleable.InputLayout_guide_src);
            if(guideDrawable != null){
                dataBinding.ivInputRight.setImageDrawable(guideDrawable);
            }
            ConstraintLayout.LayoutParams guideParams = (ConstraintLayout.LayoutParams) dataBinding.ivInputRight.getLayoutParams();
            guideParams.width = guide_width;
            guideParams.height = guide_height;
            guideParams.setMargins(guide_margin_left,0,guide_margin_right,0);
            dataBinding.ivInputRight.setLayoutParams(guideParams);
        }

        inputType = ta.getInt(R.styleable.InputLayout_input_type,0);
        ta.recycle();
        int maxLength = Integer.MAX_VALUE;
        SpaceTextWatcher.SpaceType spaceType = SpaceTextWatcher.SpaceType.DEFAULTTYPE;
        switch (inputType){
            case TYPE_PHONE:
                spaceType = SpaceTextWatcher.SpaceType.PHONE_TYPE;
                break;
            case TYPE_EMAIL:
                dataBinding.etInputText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case TEXT_PASSWORD:
                dataBinding.etInputText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case NUMBER_PASSWORD:
                dataBinding.etInputText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                break;
            case FOUR_DIGITS_SPACE:
                spaceType = SpaceTextWatcher.SpaceType.BANK_CARD_NUMBERTYPE;
                break;
            case NUMBER:
                dataBinding.etInputText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case ID_CARD:
                spaceType = SpaceTextWatcher.SpaceType.IDCARD_NUMBER_TYPE;
                break;
            default:
                break;
        }

        spaceTextWatcher = new SpaceTextWatcher(dataBinding.etInputText,maxLength);
        spaceTextWatcher.setSpaceType(spaceType);
        spaceTextWatcher.setSpaceWatcherListener(new SpaceTextWatcher.SpaceWatcherListener() {
            @Override
            public void afterTextChanged(String s) {
                if(inputEnable.get() && guideFlag){
                    if(s != null && !TextUtils.isEmpty(s)){
                        rightImageState.set(true);
                    }else{
                        rightImageState.set(false);
                    }
                }
            }
        });
        dataBinding.etInputText.addTextChangedListener(spaceTextWatcher);

        dataBinding.tvInputContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onInputLayoutListener != null){
                    onInputLayoutListener.onViewClickListener(InputLayout.this);
                }
            }
        });

        dataBinding.ivInputRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputEnable.get()){
                    dataBinding.etInputText.setText("");
                    if(onInputLayoutListener != null){
                        onInputLayoutListener.onClear(InputLayout.this);
                    }
                }
            }
        });
    }

    public void setInputText(String inputText) {
        this.inputText.set(inputText);
    }

    public void setInputText(int textResId){
        String s = getContext().getString(textResId);
        setInputText(s);
    }

    public void setInputText(int textResId,Object... s){
        String str = getContext().getString(textResId,s);
        setInputText(str);
    }

    public void setInputTextColor(int textColor){
        Boolean enable = inputEnable.get();
        if(enable == null){
            dataBinding.tvInputText.setTextColor(textColor);
        }else{
            if(enable){
                dataBinding.etInputText.setTextColor(textColor);
            }else{
                dataBinding.tvInputText.setTextColor(textColor);
            }
        }
    }

    public void setNavText(String text){
        navText.set(text);
    }

    public void setNavTextColor(int textColor){
        dataBinding.tvInputText.setTextColor(textColor);
    }

    public void setHint(String hintText){
        this.hintText.set(hintText);
    }

    public void setLineColor(int lineColor){
        dataBinding.tvInputLine.setBackgroundColor(lineColor);
    }

    public interface OnInputLayoutListener{

        /**
         * 清除收入事件监听
         * create by Eastevil at 2022/9/26 14:44
         * @author Eastevil
         * @param view
         *      {@link InputLayout}
         * @return
         *      void
         */
        void onClear(InputLayout view);

        /**
         * 点击事件监听
         * create by Eastevil at 2022/9/26 14:45
         * @author Eastevil
         * @param view
         *      {@link InputLayout}
         * @return
         *      void
         */
        void onViewClickListener(InputLayout view);
    }


}
