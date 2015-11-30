package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by ESIR on 2015/10/29.
 */
public class HistogramView extends View {
    /**
     * 每个矩形块的宽度
     */
    private int RECT_WIDTH = 50;

    /**
     * 矩形块之间的间距
     */
    private int RECT_DISTANCE = 40;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * X轴下方空白高度
     */
    private int bottomMargin = 50;

    /**
     * Y轴左部空白宽度
     */
    private int leftMargin = 30;

    /**
     * 待绘制的矩形块矩阵，left为高度，right为颜色
     */
    private static final int[][] RECT_ARRAY = {
            {380, Color.parseColor("#e51c23"),Color.parseColor("#663399ff")},
            {600, Color.parseColor("#e91e63"),Color.parseColor("#669933ff")},
            {200, Color.parseColor("#9c27b0"),Color.parseColor("#66ff3399")},
            {300, Color.parseColor("#673ab7"),Color.parseColor("#6633ff99")},
            {450, Color.parseColor("#3f51b5"),Color.parseColor("#6699ff33")},
            {500, Color.parseColor("#5677fc"),Color.parseColor("#66ff9933")},
            {420, Color.parseColor("#03a9f4"),Color.parseColor("#66ff9933")}
    };

    /**
     * 当前高度变化比例，从0到1
     */
    private float currentY = 0.0f;

    public HistogramView(Context context,AttributeSet attrs){
        super(context, attrs);
        //targetData = RECT_ARRAY;
        init();
        initialize();
    }

    public HistogramView(Context context, AttributeSet attrs , int[][] targetData) {
        super(context, attrs);
        if(targetData != null){
            //this.targetData = RECT_ARRAY;
        }else {
            //this.targetData = targetData;
        }
        init();
        initialize();
    }

    private void init(){
        //RECT_DISTANCE = getHeight()/(targetData.length);
        //RECT_WIDTH = getHeight()/(targetData.length*2);
    }

    @Override
    protected void onDraw(Canvas canvas){
        init();
        super.onDraw(canvas);
        if(currentY == 0.0f){
            //canvas.drawLine(0,getHeight() - 30,getWidth(),getHeight() - 30,mPaint);
            playAnimation();
        }else {
            for( int i=0; i < RECT_ARRAY.length; i++ ) {
                int paintXPos = i*(RECT_WIDTH + RECT_DISTANCE) + RECT_DISTANCE + leftMargin;
                float paintYPos = RECT_ARRAY[i][0] * currentY;
                Paint textPaint = new Paint();
                textPaint.setTextSize(20f);
                textPaint.setAntiAlias(true);
                textPaint.setColor(Color.parseColor("#545454"));
                mPaint.setColor(Color.parseColor("#545454"));
                canvas.drawLine(leftMargin, getHeight() - bottomMargin, getWidth(), getHeight() - bottomMargin, mPaint);
                canvas.drawLine(leftMargin, getHeight() - bottomMargin - 700, leftMargin, getHeight() - bottomMargin,mPaint);
                mPaint.setColor(Color.parseColor("#009688"));
                canvas.drawRect(paintXPos,
                        getHeight() - bottomMargin - paintYPos,
                        paintXPos + RECT_WIDTH,
                        getHeight() - bottomMargin,
                        mPaint);
                canvas.drawText(String.valueOf((int) (RECT_ARRAY[i][0] * currentY)),
                        (float)paintXPos + 10,
                        getHeight()-paintYPos - 10 - bottomMargin,
                        textPaint);
            }
        }
    }

    protected void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private void playAnimation(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1.0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (Float)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.start();
    }
}
