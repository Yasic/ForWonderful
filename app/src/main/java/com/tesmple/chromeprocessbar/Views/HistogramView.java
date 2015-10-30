package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;

/**
 * Created by ESIR on 2015/10/29.
 */
public class HistogramView extends View {
    /**
     * 每个矩形块的宽度
     */
    private int RECT_WIDTH = 60;

    /**
     * 矩形块之间的间距
     */
    private int RECT_DISTANCE = 40;

    /**
     * 画笔
     */
    private Paint mPaint;

    private int[][] targetData;

    /**
     * 待绘制的矩形块矩阵，left为高度，right为颜色
     */
    private static final int[][] RECT_ARRAY = {
            {380, Color.parseColor("#3399ff")},
            {600, Color.parseColor("#9933ff")},
            {200, Color.parseColor("#ff3399")},
            {450, Color.parseColor("#33ff99")},
            {300, Color.parseColor("#99ff33")},
            {500, Color.parseColor("#ff9933")}
    };

    /**
     * 当前高度变化比例，从0到1
     */
    private float currentY = 0.0f;

    public HistogramView(Context context,AttributeSet attrs){
        super(context, attrs);
        targetData = RECT_ARRAY;
        init();
        initialize();
    }

    public HistogramView(Context context, AttributeSet attrs , int[][] targetData) {
        super(context, attrs);
        if(targetData != null){
            this.targetData = RECT_ARRAY;
        }else {
            this.targetData = targetData;
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
            playAnimation();
        }else {
            for( int i=0; i < RECT_ARRAY.length; i++ ) {
                int paintXPos = i*(RECT_WIDTH + RECT_DISTANCE) + RECT_DISTANCE;
                float paintYPos = RECT_ARRAY[i][0] * currentY;
                Paint textPaint = new Paint();
                textPaint.setTextSize(20f);
                textPaint.setAntiAlias(true);
                textPaint.setColor(Color.parseColor("#545454"));
                Shader shader = new LinearGradient(paintXPos, getHeight() - paintYPos, paintXPos + RECT_WIDTH, getHeight(),RECT_ARRAY[i][1], Color.parseColor("#f7f8f3"), Shader.TileMode.REPEAT);
                //mPaint.setColor(RECT_ARRAY[i][1]);
                mPaint.setShader(shader);
                canvas.drawRect(paintXPos, getHeight() - paintYPos, paintXPos + RECT_WIDTH, getHeight(), mPaint);
                canvas.drawText(String.valueOf((int) (RECT_ARRAY[i][0] * currentY)),(float)paintXPos + 10,getHeight()-paintYPos - 10,textPaint);
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
