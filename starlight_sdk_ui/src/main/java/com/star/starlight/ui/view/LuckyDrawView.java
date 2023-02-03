package com.star.starlight.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.star.starlight.ui.R;
import com.starlight.dot.framework.utils.SLog;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Description：.
 * Author：Created by YJ_Song on 2019-11-25.
 * Email:  warkey1991@gmail.com
 */
public class LuckyDrawView extends View {
    private static final String TAG = "LuckyDrawView";
    //0->1->2->3->5->6->7->8

    //0-1-2-5-8-7-6-3
    private int currentPosition = 0;
    private int stopPosition = -1;
    private final static int LOOP_COUNT = 4;
    private int currentLoopCount = 0;
    private Paint bgPaint;
    private int mWidth, mHeight;
    private int radiusBg;
    private Paint cellPaint;
    private TextPaint cellTextPaint;
    private int innerEachGap = dip2px(6);
    private int innerWidth, innerHeight;
    private int eachWidth, eachHeight;
    private boolean onTouchCenter = false;

    private RectF mCenterButtonRectF;
    private String[] rewardTexts = {"$0.00", "$0.00", "$0.00", "$0.00", "", "$0.00", "$0.00", "$0.00", "$0.00"};
    private String[] unitTexts = {"u","u","u","u","","u","u","u","u"};

    private int[] positions = {0, 1, 2, 5, 8, 7, 6, 3}; //顺时针,逆时针：0 3 6 7 8 5 2 1

    private String start = "Start";
    float scale = 1.0f;
    private boolean isRuning = false;

     //奖品背景颜色
    private int prizeBackgroundColor = Color.WHITE;
    //奖品选中颜色
    private int prizeColor = Color.parseColor("#FFFF35");
    //中间开始按钮背景颜色
    private int middleBackgroundColor = Color.WHITE;
    //中间开始按钮背景颜色
    private int middleButtonColor = Color.parseColor("#FFFF35");
    //中间开始按钮文本颜色
    private int middleTextColor = Color.parseColor("#101010");
    //中间开始按钮文本
    private String middleText;
    //奖品文本颜色
    private int prizeTextColor = Color.parseColor("#101010");
    //奖品被选中的文本颜色
    private int prizeTextCheckedColor = Color.WHITE;
    //奖品旋转时候高亮时候的文本颜色,即：旋转时，正好转动到对应的下标的那个单元格的文本颜色
    private int prizeTextRotatedColor = Color.RED;

    //奖品下标
    private int prizePosition = -1;

    private OnLuckyDrawListener onLuckyDrawListener;

    public LuckyDrawView(Context context) {
        this(context, null);
    }

    public LuckyDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(attrs);
    }

    public LuckyDrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    protected void init(AttributeSet attrs){
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LuckyDrawView);
        prizeBackgroundColor = ta.getColor(R.styleable.LuckyDrawView_prize_background_color,prizeBackgroundColor);
        prizeColor = ta.getColor(R.styleable.LuckyDrawView_prize_color,prizeColor);
        middleBackgroundColor = ta.getColor(R.styleable.LuckyDrawView_middle_background_color,middleBackgroundColor);
        middleButtonColor = ta.getColor(R.styleable.LuckyDrawView_middle_button_color,middleButtonColor);
        middleTextColor = ta.getColor(R.styleable.LuckyDrawView_middle_text_color,middleTextColor);
        middleText = ta.getString(R.styleable.LuckyDrawView_middle_text);
        prizeTextColor = ta.getColor(R.styleable.LuckyDrawView_prize_text_color,prizeTextColor);
        prizeTextCheckedColor = ta.getColor(R.styleable.LuckyDrawView_prize_text_checked_color,prizeTextCheckedColor);
        prizeTextRotatedColor = ta.getColor(R.styleable.LuckyDrawView_prize_text_rotated_color,prizeTextRotatedColor);
        prizePosition = ta.getInt(R.styleable.LuckyDrawView_prize_position,-1);
        ta.recycle();

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        bgPaint.setStrokeJoin(Paint.Join.ROUND);
        bgPaint.setAntiAlias(true);
        bgPaint.setDither(true);

        bgPaint.setColor(prizeBackgroundColor);

        cellPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cellPaint.setStyle(Paint.Style.FILL);
        cellPaint.setColor(Color.WHITE);

        cellTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        cellTextPaint.setTextSize(sp2px(getContext(), 26));
        cellTextPaint.setColor(prizeTextColor);
        cellTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        cellTextPaint.setAntiAlias(true);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        onTouchCenter = false;
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        if (mCenterButtonRectF.contains(x, y) && !isRuning) {
                            if (scale != 0.8f) {
                                scale = 0.8f;
                                invalidate();
                            }
                            onTouchCenter = true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (onTouchCenter) {
                            if(onLuckyDrawListener != null){
                                onLuckyDrawListener.onLuckyDrawClick();
                            }
                        }
                        onTouchCenter = false;
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 开始抽奖，动画开始
     * create by Administrator at 2022/12/11 15:52
     * @author Administrator
     * @return
     *      void
     */
    public void startLuckDraw(){
        startPressScaleAnim();
        startLoop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        radiusBg = mWidth / 40;
        innerWidth = mWidth - innerEachGap * 4;
        innerHeight = mHeight - innerEachGap * 4;
        eachWidth = innerWidth / 3;
        eachHeight = innerHeight / 3;
        drawNineCell(canvas);
    }

    /*public void setRewardTexts(String[] rewardTexts){
        this.rewardTexts = rewardTexts;
        invalidate();
    }*/

    /**
     * 设置奖品展示文本，奖品数量必须等同于奖品单位数量
     * create by Eastevil at 2022/12/12 13:49
     * @author Eastevil
     * @param rewardList
     *      奖品列表
     * @param unitList
     *      奖品单位列表
     * @return
     *      void
     */
    public void setRewardTexts(List<String> rewardList,List<String> unitList){
        if(rewardList != null && !rewardList.isEmpty() && unitList != null && rewardList.size() == unitList.size()){
            rewardTexts = new String[rewardList.size()];
            unitTexts = new String[unitList.size()];
            int i = 0;
            for(;i<rewardList.size();i++){
                rewardTexts[i] = rewardList.get(i);
                unitTexts[i] = unitList.get(i);
            }
            invalidate();
        }
    }

    public void setCenterText(String centerText){
        this.start = centerText;
        invalidate();
    }

    /**
     * 设置中将下标
     * create by Eastevil at 2022/12/9 16:42
     * @author Eastevil
     * @param
     * @return
     */
    public void setLuckyDraw(int position){
        this.prizePosition = position;
    }

    private void drawNineCell(Canvas canvas) {
        int nums = 9;
        RectF rectF = new RectF();
        for (int i = 0; i < nums; i++) {
            int startX = innerEachGap + (i % 3) * (eachWidth + innerEachGap);
            int startY = innerEachGap + (i / 3) * (eachHeight + innerEachGap);
            rectF.set(startX, startY, startX + eachWidth, startY + eachHeight);
            if (i == nums / 2) {
                SLog.INSTANCE.d(TAG,"drawNineCell_i_021==>"+i);
                cellPaint.setColor(middleBackgroundColor);
                bgPaint.setColor(middleButtonColor);
                rectF.set(rectF.left + rectF.left * (1 - scale) * 0.08f, rectF.top + rectF.top * (1 - scale) * 0.08f,
                        rectF.right - rectF.right * (1 - scale) * 0.08f, rectF.bottom - rectF.bottom * (1 - scale) * 0.08f);
                //画最中间的外层背景[抽奖按钮背景]
                canvas.drawRoundRect(rectF, radiusBg, radiusBg, cellPaint);

                mCenterButtonRectF = new RectF(rectF);
                rectF.set(rectF.left + dip2px(10), rectF.top + dip2px(10),
                        rectF.right - dip2px(10), rectF.bottom - dip2px(10));
                //画最中间层的最嵌套最里层区域[抽奖按钮]
                canvas.drawRoundRect(rectF, radiusBg, radiusBg, bgPaint);

                cellTextPaint.setColor(middleTextColor);
                canvas.drawText(start, rectF.centerX() - cellTextPaint.measureText(start) / 2,
                        rectF.centerY() + getTextDiffY(cellTextPaint), cellTextPaint);
            } else {
                SLog.INSTANCE.d(TAG,"drawNineCell_i_022_currentPosition=>"+currentPosition+",i=>"+i);
                if (positions[currentPosition] == i) {
                    cellPaint.setColor(prizeColor);
                    cellTextPaint.setColor(prizeTextCheckedColor);
                } else {
                    cellPaint.setColor(prizeBackgroundColor);
                    cellTextPaint.setColor(prizeTextColor);
                    cellTextPaint.setAntiAlias(true);
                }
                canvas.drawRoundRect(rectF, radiusBg, radiusBg, cellPaint);
                SLog.INSTANCE.longD(TAG,"rewardTexts=>"+rewardTexts[i]);

                /*StaticLayout sl = new StaticLayout(rewardTexts[i], cellTextPaint, canvas.getWidth(),
                        Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
                canvas.save();
                canvas.translate(rectF.centerX()/2, rectF.centerY());
                sl.draw(canvas);*/

                float cy = rectF.centerY() - getTextDiffY(cellTextPaint);
                /*canvas.drawText(rewardTexts[i], rectF.centerX() - cellTextPaint.measureText(rewardTexts[i]) / 2,
                        rectF.centerY() + getTextDiffY(cellTextPaint), cellTextPaint);*/
                /*canvas.drawText(unitTexts[i],rectF.centerX() - cellTextPaint.measureText(unitTexts[i]) / 2,
                        rectF.centerY() + getTextDiffY(cellTextPaint),cellTextPaint);*/
                canvas.drawText(rewardTexts[i],rectF.centerX() - cellTextPaint.measureText(rewardTexts[i]) / 2,
                        cy,cellTextPaint);
                float uy = cy + (3.0F * getTextDiffY(cellTextPaint));
                canvas.drawText(unitTexts[i],rectF.centerX() - cellTextPaint.measureText(unitTexts[i]) / 2,
                        uy,cellTextPaint);
                //canvas.restore();
            }
        }
    }


    private float getTextDiffY(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.abs(fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
    }

    private void startLoop() {
        currentLoopCount = 0;
        //Random random = new Random();
        if(prizePosition < 0 || prizePosition > 7){
            stopPosition = 0;
        }else{
            stopPosition = prizePosition;
        }
        currentPosition = 0;
        new Thread(action).start();
    }

    private Runnable action = new Runnable() {
        @Override
        public void run() {
            while (true) {
                isRuning = true;
                if (currentLoopCount >= LOOP_COUNT) {
                    isRuning = false;
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(getContext(), "恭喜你抽中了position=" + stopPosition + "(" + rewardTexts[positions[stopPosition]] + ")", Toast.LENGTH_LONG).show();
                            if(onLuckyDrawListener != null){
                                onLuckyDrawListener.onPrize(stopPosition);
                            }
                        }
                    },0);
                    break;
                }
                currentPosition++;
                if (currentPosition > 7) {
                    currentLoopCount++;
                    currentPosition = 0;
                }

                post(new Runnable() {
                    @Override
                    public void run() {
                        invalidate();
                    }
                });

                if (currentLoopCount == LOOP_COUNT - 1) {
                    if (currentPosition % 7 == stopPosition) {
                        if (currentPosition == stopPosition) {
                            currentLoopCount = LOOP_COUNT;
                        }
                    }
                    SystemClock.sleep(100 * (currentPosition + 1));
                } else {
                    SystemClock.sleep(100);
                }
            }

        }
    };

    private void startPressScaleAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.8f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scale = ((float) animation.getAnimatedValue());
                invalidate();
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public OnLuckyDrawListener getOnLuckyDrawListener() {
        return onLuckyDrawListener;
    }

    public void setOnLuckyDrawListener(OnLuckyDrawListener onLuckyDrawListener) {
        this.onLuckyDrawListener = onLuckyDrawListener;
    }

    public interface OnLuckyDrawListener{

        /**
         * 开始抽奖点击事件
         * create by Administrator at 2022/12/11 14:55
         * @author Administrator
         * @return
         *      void
         */
        void onLuckyDrawClick();

        /**
         * 抽奖结束，中奖事件监听
         * create by Administrator at 2022/12/11 14:55
         * @author Administrator
         * @param position
         *      中奖奖品下标
         * @return
         *      void
         */
        void onPrize(int position);
    }
}
