package com.tesmple.chromeprocessbar.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
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
    private int radius = 100;

    /**
     * 圆圈边缘线条长度
     */
    private int lineLength = 13;

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

    /**
     * 秒钟数
     */
    private float second = 0.0f;

    /**
     * 间隔线个数
     */
    private int septalLineNum = 180;

    /**
     * 圆圈颜色渐变列表
     */
    private static final int[][] ROTATIONCOLORARRAY = {
            {0, Color.parseColor("#fff7f8f3")},
            {0, Color.parseColor("#fff7f8f3")},
            {0, Color.parseColor("#fff7f8f3")},
            {0, Color.parseColor("#fff7f8f3")},
            {0, Color.parseColor("#fff7f8f3")},
            {1, Color.parseColor("#eff7f8f3")},
            {1, Color.parseColor("#eff7f8f3")},
            {1, Color.parseColor("#eff7f8f3")},
            {1, Color.parseColor("#eef7f8f3")},
            {1, Color.parseColor("#eef7f8f3")},
            {2, Color.parseColor("#eef7f8f3")},
            {2, Color.parseColor("#e9f7f8f3")},
            {2, Color.parseColor("#e9f7f8f3")},
            {2, Color.parseColor("#e9f7f8f3")},
            {2, Color.parseColor("#dff7f8f3")},
            {3, Color.parseColor("#dff7f8f3")},
            {3, Color.parseColor("#dff7f8f3")},
            {3, Color.parseColor("#cff7f8f3")},
            {3, Color.parseColor("#cff7f8f3")},
            {3, Color.parseColor("#cff7f8f3")},
            {4, Color.parseColor("#bff7f8f3")},
            {4, Color.parseColor("#bff7f8f3")},
            {4, Color.parseColor("#aff7f8f3")},
            {4, Color.parseColor("#aff7f8f3")},
            {5, Color.parseColor("#9ff7f8f3")},
            {5, Color.parseColor("#9ff7f8f3")},
            {5, Color.parseColor("#97f7f8f3")},
            {6, Color.parseColor("#8ff7f8f3")},
            {6, Color.parseColor("#87f7f8f3")},
            {7, Color.parseColor("#80f7f8f3")},
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
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.parseColor("#3399ff"));
        if(animationBeginFlag == 1){
            initParameter();
            startTime();
            animationBeginFlag = 0;
        }
        mPaint.setColor(Color.parseColor("#f7f8f3"));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX, centerY, (radius - 20) * denisty, mPaint);
        mPaint.setStrokeWidth(6);
        canvas.drawCircle(centerX,centerY,5 * denisty , mPaint);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.rotate(90,centerX,centerY);
        canvas.rotate(360-90,centerX,centerY);
        canvas.rotate(2f * second, centerX, centerY);
        for (int i = 0;i < septalLineNum;i++){
            if( i > septalLineNum - ROTATIONCOLORARRAY.length){
                mPaint.setColor(ROTATIONCOLORARRAY[septalLineNum - i][1]);
                canvas.drawLine(centerX, centerY - (radius + lineLength) * denisty, centerX, centerY - radius * denisty, mPaint);
                canvas.rotate(2f, centerX, centerY);
            }else {
                if(i == 0){
                    mPaint.setColor(ROTATIONCOLORARRAY[0][1]);
                    Path path = new Path();
                    path.moveTo(centerX, centerY - (radius - 3)*denisty);
                    path.lineTo(centerX - 7, centerY - (radius - 10)*denisty);
                    path.lineTo(centerX + 7, centerY - (radius - 10)*denisty);
                    path.close();
                    canvas.drawPath(path, mPaint);
                    canvas.drawLine(centerX, centerY - (radius + lineLength) * denisty, centerX, centerY - radius * denisty, mPaint);
                    canvas.rotate(2f, centerX, centerY);
                    continue;
                }
                mPaint.setColor(ROTATIONCOLORARRAY[ROTATIONCOLORARRAY.length - 1][1]);
                canvas.drawLine(centerX, centerY - (radius + lineLength) * denisty, centerX, centerY - radius * denisty, mPaint);
                canvas.rotate(2f, centerX, centerY);
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
                if(second != septalLineNum){
                    second = second + 1;
                }else {
                    second = 0;
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask,0,1000/3);
    }

    /**
     * 初始化参数，包括圆心位置
     */
    private void initParameter() {
        denisty = getResources().getDisplayMetrics().density;
        centerX = getWidth()/2;
        centerY = getHeight()/2 * 2/3;
    }
}
