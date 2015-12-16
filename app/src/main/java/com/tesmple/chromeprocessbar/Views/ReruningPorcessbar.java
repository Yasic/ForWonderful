package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by ESIR on 2015/12/7.
 */
public class ReruningPorcessbar extends View {
    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 颜色列表
     */
    private List<String> colorList = new ArrayList<>();

    private int starAnimation = 0;

    /**
     * 运动方向，1向右，0向左
     */
    private int directions = 1;

    private float circleX = 0;
    private float circleY = 0;

    public ReruningPorcessbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor(colorList.get(0)));
        if (starAnimation == 0){
            playAnimations();
            starAnimation = 1;
        }
        canvas.drawCircle(circleX, circleY, 15, mPaint);
    }

    private void playAnimations(){
        ValueAnimator valueAnimator1 = new ValueAnimator();
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(-100,100);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(2);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Double framValue = Math.toRadians((Double) animation.getAnimatedValue());
                if (directions == 1){
                    circleY = getHeight()/2 + 50*(float) Math.sin(framValue);
                    circleX = (float) (getWidth()/2 - 30 * framValue);
                } else if (directions == -1){
                    circleY = getHeight()/2 - 50*(float) Math.sin(framValue);
                    circleX = (float) (getWidth()/2 - 30 * framValue);
                }
                Log.i("sinvalue", String.valueOf((float)Math.sin(framValue)) + "+" + framValue);
                if((float)animation.getAnimatedValue() == 180 || (float)animation.getAnimatedValue() == -180){
                    directions = -directions;
                }
                invalidate();
            }
        });
    }

    private void initialize(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);
        colorList.add("#3399ff");
        colorList.add("#9933ff");
        colorList.add("#ff3399");
    }
}
