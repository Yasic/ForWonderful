package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.tesmple.chromeprocessbar.R;

import java.security.PrivateKey;
import java.sql.Time;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.jar.Attributes;

/**
 * Created by ESIR on 2015/10/27.
 */
public class Processbar_one extends View {

    /**
     * 屏幕分辨率
     */
    private float denisty;

    /**
     * 灰色画笔
     */
    private Paint greyPaint;

    /**
     * 蓝色画笔
     */
    private Paint bluePaint;

    /**
     * 绿色画笔
     */
    private Paint greenPaint;

    /**
     * 红色画笔
     */
    private Paint redPaint;

    /**
     * 黄色画笔
     */
    private Paint yellowPaint;

    /**
     * X轴起点
     */
    private int startX;

    /**
     * Y轴起点
     */
    private int startY;

    /**
     * Y轴终点
     */
    private int endY;

    /**
     * Y轴当前坐标
     */
    private int currentY;

    /**
     * 小球半径
     */
    private int radius = 10;

    public Processbar_one(Context context,AttributeSet attrs) {
        super(context,attrs);
        init();
        initPaint();
    }

    private void init(){
        denisty = getResources().getDisplayMetrics().density;
    }

    private void initPaint(){
        bluePaint = new Paint();
        bluePaint.setAntiAlias(true);
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.STROKE);
        bluePaint.setStrokeWidth(3);

        redPaint = new Paint();
        redPaint.setAntiAlias(true);
        redPaint.setColor(Color.RED);
        redPaint.setStrokeWidth(3);
        redPaint.setStyle(Paint.Style.STROKE);

        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setColor(Color.GREEN);
        greenPaint.setStrokeWidth(3);
        greenPaint.setStyle(Paint.Style.STROKE);

        yellowPaint = new Paint();
        yellowPaint.setAntiAlias(true);
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStrokeWidth(3);
        yellowPaint.setStyle(Paint.Style.STROKE);

        greyPaint = new Paint();
        greyPaint.setColor(Color.parseColor(getContext().getString(R.string.greymidle)));
        greyPaint.setAntiAlias(true);
        greyPaint.setStrokeWidth(3);
        greyPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startX = getWidth()/2;
        endY = getHeight()/2;
        startY = endY * 6/7;
        if(currentY == 0){
            playAnimation();
        }else {
            drawCircle(canvas);
            drawShader(canvas);
        }
    }

    private void drawShader(Canvas canvas) {
        int dx = endY - startY;
        int dx1 = currentY - startY;
        float ratio = (float) (dx1 *1.0 / dx);
        if(ratio <= 0.3){//当高度比例小于0.3，所在比较高的时候就不进行绘制影子
            return;
        }
        int ovalRadius = (int) (radius * ratio * denisty);
        RectF rectF = new RectF(startX-ovalRadius,endY + 5,startX+ovalRadius,endY + 15);
        canvas.drawOval(rectF,greyPaint);
    }

    private void drawCircle(Canvas canvas) {
        if(endY - currentY > 30){
            canvas.drawCircle(startX,currentY,radius * denisty,bluePaint);
        }else {
            RectF rectF = new RectF(startX - radius * denisty - 2,currentY - radius * denisty + 5,startX + radius * denisty + 2,currentY + radius * denisty);
            canvas.drawOval(rectF,bluePaint);
        }
    }

    private void playAnimation(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startY, endY);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (Integer)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator(1.2f));
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(2);
        valueAnimator.setDuration(700);
        valueAnimator.start();
    }
}
