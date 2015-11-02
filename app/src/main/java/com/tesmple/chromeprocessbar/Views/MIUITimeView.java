package com.tesmple.chromeprocessbar.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ESIR on 2015/11/1.
 */
public class MIUITimeView extends View {
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
    private int radius = 72;

    /**
     * 圆圈边缘线条长度
     */
    private int lineLength = 16;

    /**
     * 动画变化时间
     */
    private int animationTime = 1000;

    /**
     * 转动角度值
     */
    private int degreeValue = 0;

    /**
     * 开始动画标志位
     */
    private int animationBeginFlag = 1;

    private int second = 0;

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

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    invalidate();
                    break;
            }
        }
    };


    public MIUITimeView(Context context , AttributeSet attributeSet) {
        super(context, attributeSet);
        initPaint();
    }


    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.parseColor("#3399ff"));
        if(animationBeginFlag == 1){
            initParameter();
            startTime();
            animationBeginFlag = 0;
        }
        canvas.rotate(360 * second / 60,centerX,centerY);
        for (int i = 0;i < 60;i++){
            if( i < ROTATIONCOLORARRAY.length){
                mPaint.setColor(ROTATIONCOLORARRAY[ROTATIONCOLORARRAY.length - i - 1][1]);
                canvas.drawLine(centerX,centerY - (radius + lineLength) * denisty , centerX, centerY - radius * denisty,mPaint);
                canvas.rotate(360/60,centerX,centerY);
            }else {
                canvas.drawLine(centerX,centerY - (radius + lineLength) * denisty , centerX, centerY - radius * denisty,mPaint);
                canvas.rotate(360/60,centerX,centerY);
            }
        }
    }

    /**
     * 开始时间计数
     */
    private void startTime(){
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                second = (second+1)%60;
                //invalidate();
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    /**
     * 初始化参数，包括圆心位置
     */
    private void initParameter() {
        denisty = getResources().getDisplayMetrics().density;
        centerX = getWidth()/2;
        centerY = getHeight()/2;
    }
}
