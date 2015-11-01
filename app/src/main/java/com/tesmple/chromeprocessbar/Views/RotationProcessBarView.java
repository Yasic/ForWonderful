package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.jar.Attributes;

/**
 * Created by ESIR on 2015/11/1.
 */
public class RotationProcessBarView extends View{

    /**
     * 使用的画笔
     */
    private Paint mPaint;

    /**
     * 屏幕像素密度
     */
    private float denisty;

    /**
     * 圆心的X坐标
     */
    private int centerX;

    /**
     * 圆心的Y坐标
     */
    private int centerY;

    /**
     * 圆形半径
     */
    private int radius = 15;

    /**
     * 圆圈边缘线条长度
     */
    private int lineLength = 10;

    /**
     * 动画变化时间
     */
    private int animationTime = 1000;

    /**
     * 转动角度值
     */
    private int degreeValue = 0;

    private int animationBeginFlag = 1;

    /**
     * 圆圈颜色渐变列表
     */
    private static final int[][] ROTATIONCOLORARRAY = {
            {0, Color.parseColor("#ffffff")},
            {1, Color.parseColor("#f8f8f8")},
            {2, Color.parseColor("#efefef")},
            {3, Color.parseColor("#e8e8e8")},
            {4, Color.parseColor("#dfdfdf")},
            {5, Color.parseColor("#d8d8d8")},
            {6, Color.parseColor("#cfcfcf")},
            {7, Color.parseColor("#c8c8c8")},
            {8, Color.parseColor("#bfbfbf")},
            {9, Color.parseColor("#b8b8b8")},
            {10, Color.parseColor("#afafaf")},
            {11, Color.parseColor("#a8a8a8")},
            {12, Color.parseColor("#9f9f9f")},
            {13, Color.parseColor("#989898")},
            {14, Color.parseColor("#8f8f8f")},
    };

    public RotationProcessBarView(Context context , AttributeSet attributeSet) {
        super(context, attributeSet);
        initPaint();
    }

    private void initParameter() {
        denisty = getResources().getDisplayMetrics().density;
        centerX = getWidth()/2;
        centerY = getHeight()/2;
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas){
        initParameter();
        canvas.drawColor(Color.parseColor("#000000"));
        if(animationBeginFlag == 1){
            playAnimation();
            animationBeginFlag = 0;
        }
        /*mPaint.setColor(ROTATIONCOLORARRAY[1][1]);
        canvas.drawLine(centerX, centerY - (radius + lineLength) * denisty, centerX, centerY - radius * denisty, mPaint);
        canvas.rotate(90, centerX, centerY);
        canvas.drawLine(centerX,centerY - (radius + lineLength) * denisty , centerX, centerY - radius * denisty,mPaint);
        */
        canvas.rotate(360 * degreeValue/(ROTATIONCOLORARRAY.length * 2),centerX,centerY);
        Log.i("degreeValus2", String.valueOf(degreeValue));
        for (int i = 0;i < ROTATIONCOLORARRAY.length * 2;i++){
            if( i < ROTATIONCOLORARRAY.length){
                mPaint.setColor(ROTATIONCOLORARRAY[ROTATIONCOLORARRAY.length - i - 1][1]);
                canvas.drawLine(centerX,centerY - (radius + lineLength) * denisty , centerX, centerY - radius * denisty,mPaint);
                canvas.rotate(360/(ROTATIONCOLORARRAY.length * 2),centerX,centerY);
            }else {
                canvas.drawLine(centerX,centerY - (radius + lineLength) * denisty , centerX, centerY - radius * denisty,mPaint);
                canvas.rotate(360/(ROTATIONCOLORARRAY.length * 2),centerX,centerY);
            }
        }
    }

    private void playAnimation(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0 , ROTATIONCOLORARRAY.length*2 );
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degreeValue = (Integer)animation.getAnimatedValue();
                Log.i("degreeValus1", String.valueOf(degreeValue));
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(1);
        valueAnimator.setDuration(animationTime);
        valueAnimator.start();
    }
}
