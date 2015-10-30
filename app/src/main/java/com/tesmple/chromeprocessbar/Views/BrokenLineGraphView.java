package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by ESIR on 2015/10/30.
 */
public class BrokenLineGraphView extends View {
    /**
     * 矩形块之间的间距
     */
    private int LINE_DISTANCE = 80;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 待绘制的矩形块矩阵，left为高度，right为颜色
     */
    private static final int[][] LINE_ARRAY = {
            {380, Color.parseColor("#3399ff")},
            {600, Color.parseColor("#9933ff")},
            {200, Color.parseColor("#ff3399")},
            {450, Color.parseColor("#33ff99")},
            {250, Color.parseColor("#99ff33")},
            {300, Color.parseColor("#ff9933")},
            {500, Color.parseColor("#ff9933")},
            {510, Color.parseColor("#ff9933")}
    };

    /**
     * 当前高度变化比例，从0到1
     */
    private float currentY = 0.0f;

    /**
     * 点半径
     */
    private int radius = 5;

    public BrokenLineGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(currentY == 0.0f){
            playAnimation();
        }else {
            for( int i=0; i < LINE_ARRAY.length; i++ ) {
                int paintXPos = i*(LINE_DISTANCE) + LINE_DISTANCE;
                float paintYPos = LINE_ARRAY[i][0] * currentY;
                Paint textPaint = new Paint();
                textPaint.setTextSize(20f);
                textPaint.setAntiAlias(true);
                textPaint.setColor(Color.parseColor("#545454"));
                //Shader shader = new LinearGradient(paintXPos, getHeight() - paintYPos, paintXPos, getHeight(),LINE_ARRAY[i][1], Color.parseColor("#f7f8f3"), Shader.TileMode.REPEAT);
                //mPaint.setColor(RECT_ARRAY[i][1]);
                //mPaint.setShader(shader);
                canvas.drawCircle(paintXPos, getHeight() - paintYPos,radius,mPaint);
                if(i != LINE_ARRAY.length -1){
                    canvas.drawLine(paintXPos,getHeight() - paintYPos,paintXPos + LINE_DISTANCE,getHeight() - (LINE_ARRAY[i+1][0] * currentY),mPaint);
                }
                //canvas.drawRect(paintXPos, getHeight() - paintYPos, paintXPos + RECT_WIDTH, getHeight(), mPaint);
                canvas.drawText(String.valueOf((int) (LINE_ARRAY[i][0] * currentY)),(float)paintXPos - 15,getHeight()-paintYPos - 10,textPaint);
            }
        }
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

    private void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(LINE_ARRAY[0][1]);
    }
}
