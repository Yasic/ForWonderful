package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tesmple.chromeprocessbar.Helpers.ViewDragHelper;
import com.tesmple.chromeprocessbar.Objects.CardSlideDataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESIR on 2015/12/3.
 */
public class CardSlideViewGroup extends ViewGroup {
    /**
     * 存储Views
     */
    private List<CardSlideViewItem> viewList = new ArrayList<CardSlideViewItem>();

    /**
     * 手势处理器
     */
    private ViewDragHelper viewDragHelper;

    /**
     * view之间位置差值
     */
    private int heightStep = 30;

    /**
     * 存储卡片数据列表
     */
    private List<CardSlideDataItem> cardSlideDataItemList;

    /**
     * viewgroup宽度
     */
    private int viewGroupWidth = 0;

    /**
     * viewgroup高度
     */
    private int viewGroupHeight = 0;

    /**
     * 卡片的坐标
     */
    private int initCenterViewLeft = 0;
    private int initCenterViewTop = 0;
    private int initCenterViewRight = 0;
    private int initCenterViewBottom = 0;

    /**
     * 子view宽度
     */
    private int childWith=0;

    private int cardmarginTop = 30;

    private int downEventX;
    private int downEventY;
    private int moveEventX;
    private int moveEventY;

    /**
     * 动画持续时间
     */
    private int slideAnimationDuration = 500;

    /**
     * 动画线程flag，为0时动画可以进行，为1时不可以进行
     */
    private int animationFlag = 0;

    /**
     * 动画中间值
     */
    int frameValueX = 0;
    int frameValueY = 0;

    /**
     * X轴返回动画
     */
    private ValueAnimator valueAnimatorLeftOutX;

    /**
     * Y轴返回动画
     */
    private ValueAnimator valueAnimatorLeftOutY;

    /**
     * 划出动画中间值
     */
    private int frameSlideOutX = 0;
    private int frameSlideOutY = 0;

    /**
     * 足够远距离的判决条件
     */
    private int longDistance = 50;

    /**
     * 滑出动画时间
     */
    private int slideOutTime = 500;

    public CardSlideViewGroup(Context context) {
        super(context);
    }

    public CardSlideViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardSlideViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate(){
        super.onFinishInflate();
        viewList.clear();
        int num = getChildCount();
        for(int i = num -1; i >= 0; i--){
            View childView = getChildAt(i);
            CardSlideViewItem cardSlideViewItem = (CardSlideViewItem)childView;
            cardSlideViewItem.setTag(i + 1);
            viewList.add(cardSlideViewItem);
        }
    }
    @Override
    public void computeScroll() {

    }

    /* touch事件的拦截与处理都交给mDraghelper来处理 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downEventX = (int)e.getX();
                downEventY = (int)e.getY();
                if(viewContains(downEventX, downEventY)){
                    animationFlag = 1;
                }else {
                    animationFlag = 0;
                }
                Log.i("animationFlag",viewContains(downEventX, downEventY)+"");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("move_animationFlag",animationFlag+"");
                if(animationFlag == 1){
                    moveEventX = (int)e.getX();
                    moveEventY = (int)e.getY();
                    playSlideAnim(moveEventX-downEventX, moveEventY-downEventY);
                    downEventX = moveEventX;
                    downEventY = moveEventY;
                }
                break;
            case MotionEvent.ACTION_UP:
                animationFlag = 0;//恢复flag
                View viewItem = viewList.get(0);
                if(viewMoveLongLeft(viewItem)){
                    playSlideOutAnimation(viewItem,-1);
                } else if (viewMoveLongRight(viewItem)){
                    playSlideOutAnimation(viewItem,1);
                } else {
                    playSlideBackAnim(viewItem.getLeft(),viewItem.getTop());
                }
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(maxWidth,maxHeight);
        /*setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));*/
        viewGroupWidth = getMeasuredWidth();
        viewGroupHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int viewListSize = viewList.size();
        for(int i = 0; i < viewListSize; i++){
            View viewItem = viewList.get(i);
            int childHeight = viewItem.getMeasuredHeight();
            int childWidth = viewItem.getMeasuredWidth();
            viewItem.layout(l, t, r, b);
            viewItem.offsetTopAndBottom(cardmarginTop);
            int offset = heightStep * i;
            if(i > 2){
                offset = heightStep *2;
            }
            viewItem.offsetTopAndBottom(offset);
            initCenterViewLeft = viewList.get(0).getLeft();
            initCenterViewTop = viewList.get(0).getTop();
            initCenterViewRight = viewList.get(0).getRight();
            initCenterViewBottom = viewList.get(0).getBottom();
            childWith = viewList.get(0).getMeasuredWidth();
        }
    }

    /**
     * 判断是否足够向左
     * @param viewItem 传入目标view
     * @return 返回true则足够远可以消失，flase则不够远不能消失
     */
    private boolean viewMoveLongLeft(View viewItem){
        if(initCenterViewLeft - viewItem.getLeft() > longDistance){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 判断是否足够向右
     * @param viewItem 传入目标view
     * @return 返回true则足够远可以消失，flase则不够远不能消失
     */
    private boolean viewMoveLongRight(View viewItem){
        //Log.i("right",viewItem.getRight() + "-" + initCenterViewRight + ":" +(viewItem.getRight() - initCenterViewRight));
        if(viewItem.getRight() - initCenterViewRight > longDistance){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 进行划出动画
     * @param viewItemOut 操纵对象view
     * @param direction 方向，-1为左，1为正
     */
    private void playSlideOutAnimation(final View viewItemOut,int direction){
        if(direction == -1){//左
            valueAnimatorLeftOutX = ValueAnimator.ofInt(viewItemOut.getLeft(), -viewItemOut.getWidth()).setDuration(slideAnimationDuration);
            valueAnimatorLeftOutX.start();
            valueAnimatorLeftOutX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutX = (int) valueAnimatorLeftOutX.getAnimatedValue();
                }
            });
            valueAnimatorLeftOutY = ValueAnimator.ofInt(viewItemOut.getTop(), this.getHeight()).setDuration(slideAnimationDuration);
            valueAnimatorLeftOutY.start();
            valueAnimatorLeftOutY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutY = (int) valueAnimatorLeftOutY.getAnimatedValue();
                    viewItemOut.layout(frameSlideOutX, frameSlideOutY, frameSlideOutX + viewItemOut.getWidth(), frameSlideOutY + viewItemOut.getHeight());
                }
            });
        } else if (direction == 1){//右
            valueAnimatorLeftOutX = ValueAnimator.ofInt(viewItemOut.getLeft(), this.getRight()).setDuration(slideAnimationDuration);
            valueAnimatorLeftOutX.start();
            valueAnimatorLeftOutX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutX = (int) valueAnimatorLeftOutX.getAnimatedValue();
                }
            });
            valueAnimatorLeftOutY = ValueAnimator.ofInt(viewItemOut.getTop(), this.getHeight()).setDuration(slideAnimationDuration);
            valueAnimatorLeftOutY.start();
            this.valueAnimatorLeftOutY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutY = (int)valueAnimatorLeftOutY.getAnimatedValue();
                    viewItemOut.layout(frameSlideOutX, frameSlideOutY, frameSlideOutX + viewItemOut.getWidth(), frameSlideOutY + viewItemOut.getHeight());
                }
            });
        }
    }

    /**
     * 跟随手指滑动
     * @param dx 传入X移动差值
     * @param dy 传入Y移动差值
     */
    private void playSlideAnim(int dx, int dy){
        if(valueAnimatorLeftOutY != null || valueAnimatorLeftOutX != null){
            valueAnimatorLeftOutX.cancel();
            valueAnimatorLeftOutY.cancel();
        }
        View viewItem = viewList.get(0);
        int top = viewItem.getTop();
        int left = viewItem.getLeft();
        viewItem.layout(left + dx, top + dy, viewItem.getRight() + dx, viewItem.getBottom() + dy);
    }

    /**
     * 播放滑动回去的动画
     * @param upX 传入的X开始值
     * @param upY 传入的Y开始值
     */
    private void playSlideBackAnim(final int upX, final int upY){
            final View viewItem = viewList.get(0);
            valueAnimatorLeftOutX = ValueAnimator.ofInt(upX , initCenterViewLeft);
            valueAnimatorLeftOutX.setDuration(slideAnimationDuration);
            valueAnimatorLeftOutX.start();
            valueAnimatorLeftOutX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameValueX = (int) valueAnimatorLeftOutX.getAnimatedValue();
                }
            });
            valueAnimatorLeftOutY = ValueAnimator.ofInt(upY , initCenterViewTop);
            valueAnimatorLeftOutY.setDuration(slideAnimationDuration);
            valueAnimatorLeftOutY.start();
            valueAnimatorLeftOutY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameValueY = (int) valueAnimatorLeftOutY.getAnimatedValue();
                    viewItem.layout(frameValueX, frameValueY, frameValueX + viewItem.getWidth(), frameValueY + viewItem.getHeight());
                    if (frameValueY == initCenterViewLeft) {
                        animationFlag = 0;
                    }
                }
            });
    }

    /**
     * 判断手指按点是否在view中
     * @param downX 手指按点X值
     * @param downY 手指按点Y值
     * @return
     */
    private boolean viewContains(int downX, int downY){
        View viewItem = viewList.get(0);
        if(downX > viewItem.getLeft() && downY < viewItem.getRight() && downY > viewItem.getTop() && downY < viewItem.getBottom()){
            return true;
        }
        return false;
    }

    /**
     * 装填数据
     * @param dataList 装数据的列表
     */
    public void fillData(List<CardSlideDataItem> dataList) {
        this.cardSlideDataItemList = dataList;
        int num = viewList.size();
        for (int i = 0; i < num; i++) {
            CardSlideViewItem itemView = viewList.get(i);
            Log.i("datalist",i+":"+dataList.get(i).cardslideImgPath);
            itemView.setData(dataList.get(i));
            itemView.setVisibility(View.VISIBLE);
        }
    }
}

