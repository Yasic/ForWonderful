package com.tesmple.chromeprocessbar.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.StrictMode;
import android.provider.MediaStore;
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
    private int heightStep = 50;

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

    /**
     * 卡片上部margin值
     */
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
     * X轴滑出动画
     */
    private ValueAnimator valueAnimatorLeftOutX;

    /**
     * Y轴滑出动画
     */
    private ValueAnimator valueAnimatorLeftOutY;

    /**
     * 滑动回去的动画ValueAnimotor
     */
    private ValueAnimator valueAnimatorBackX;
    private ValueAnimator valueAnimatorBackY;

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
    private int slideOutTime = 300;

    /**
     * view视图最顶层的下标
     */
    private int CARDNUM = 0;

    /**
     * 滑出view
     */
    private View viewSlideOut;

    /**
     * 初始化图片集最后一张坐标
     */
    private int cardIndex = 0;

    /**
     * 开始动画标记
     */
    private int start = 0;

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
            cardSlideViewItem.setTag(i);
            viewList.add(cardSlideViewItem);
        }
    }
    @Override
    public void computeScroll() {

    }

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
                break;
            case MotionEvent.ACTION_MOVE:
                if(animationFlag == 1){
                    moveEventX = (int)e.getX();
                    moveEventY = (int)e.getY();
                    setNextView(viewList.get(CARDNUM).getLeft());
                    playSlideFingerAnim(moveEventX - downEventX, moveEventY - downEventY);
                    downEventX = moveEventX;
                    downEventY = moveEventY;
                }
                break;
            case MotionEvent.ACTION_UP:
                animationFlag = 0;//恢复flag
                View viewItem = viewList.get(CARDNUM);
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
        //viewList.get((CARDNUM + viewList.size() - 1) % viewList.size()).setAlpha(0.0f);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        int viewListSize = viewList.size();
        for (int i = 0; i < viewListSize; i++) {
            View child = this.getChildAt(i);
            //this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
            child.measure( MeasureSpec.makeMeasureSpec(getWidth()/2 + 100,
                    MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(getHeight(),
                    MeasureSpec.UNSPECIFIED));
            LayoutParams lParams = (LayoutParams) child.getLayoutParams();
            lParams.left = 250;
            lParams.right = maxWidth - 250;
        }
        setMeasuredDimension(maxWidth, maxHeight);
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
            //LayoutParams lParams = (LayoutParams) viewItem.getLayoutParams();
            //viewItem.layout(lParams.left, t, lParams.right, b);
            viewItem.layout(l, t, r, b);
            viewItem.offsetTopAndBottom(cardmarginTop);
            int offset = 0;
            if(i >= 1){
                offset = heightStep;
            }
            //viewItem.offsetTopAndBottom(offset);
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
        if(viewItem.getRight() - initCenterViewRight > longDistance){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 设置第二个view的透明度
     * @param viewOnewX 传入当前X值
     */
    private void setNextView(int viewOnewX){
        View view1 = viewList.get((CARDNUM + 1) % viewList.size());
        View view0 = viewList.get(CARDNUM);
        float percent = Math.abs((float) viewOnewX)/(viewGroupWidth/2);
        if (percent < 1 ){
            view1.setAlpha(percent);
            view0.setAlpha(1.0f - percent);
            int top = cardmarginTop + (int)(((float)heightStep)*(1.0f-percent));
            view1.layout(0, top, view1.getWidth(), top + view1.getHeight());
        }else {
            viewList.get((CARDNUM+1) % viewList.size()).setAlpha(1.0f);
            view0.setAlpha(0f);
        }
    }

    /**
     * 跟随手指滑动
     * @param dx 传入X移动差值
     * @param dy 传入Y移动差值
     */
    private void playSlideFingerAnim(int dx, int dy){
        if(valueAnimatorBackX != null || valueAnimatorBackY != null){
            valueAnimatorBackY.cancel();
            valueAnimatorBackY.cancel();
        }
        View viewItem = viewList.get(CARDNUM);
        int top = viewItem.getTop();
        int left = viewItem.getLeft();
        viewItem.layout(left + dx, top + dy, viewItem.getRight() + dx, viewItem.getBottom() + dy);
    }

    /**
     * 播放滑动回去的动画
     * @param viewsLeft 传入的X开始值
     * @param viewsRght 传入的Y开始值
     */
    private void playSlideBackAnim(final int viewsLeft, final int viewsRght){
        final View viewItem = viewList.get(CARDNUM);
        valueAnimatorBackX = ValueAnimator.ofInt(viewsLeft , initCenterViewLeft);
        valueAnimatorBackX.setDuration(slideAnimationDuration);
        valueAnimatorBackX.start();
        valueAnimatorBackX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                frameValueX = (int) valueAnimatorBackX.getAnimatedValue();
            }
        });
        valueAnimatorBackY = ValueAnimator.ofInt(viewsRght , initCenterViewTop);
        valueAnimatorBackY.setDuration(slideAnimationDuration);
        valueAnimatorBackY.start();
        valueAnimatorBackY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                frameValueY = (int) valueAnimatorBackY.getAnimatedValue();
                setNextView(frameValueX);
                viewItem.layout(frameValueX, frameValueY, frameValueX + viewItem.getWidth(), frameValueY + viewItem.getHeight());
                if (frameValueY == initCenterViewLeft) {
                    animationFlag = 0;
                }
            }
        });
    }

    /**
     * 进行划出动画
     * @param viewItemOut 操纵对象view
     * @param direction 方向，-1为左，1为正
     */
    private void playSlideOutAnimation(final View viewItemOut,int direction){
        /*for (int i = 0; i < viewList.size(); i++){
            if (i != CARDNUM){
                viewList.get(CARDNUM).setAlpha(0.0f);
            }
        }*/
        viewList.get((CARDNUM + viewList.size() - 1) % viewList.size()).setAlpha(0.0f);
        Log.i("playSlideOutAnimation", CARDNUM + ":" +(CARDNUM + viewList.size() - 1) % viewList.size());
        CardSlideViewItem viewItem = viewList.get((CARDNUM + 2) % viewList.size());
        if(CARDNUM == 2 && start == 0){
            start = 1;
        }
        if(start == 1) {
            viewItem.setData(cardSlideDataItemList.get(cardIndex));
            cardIndex = (cardIndex+1)%cardSlideDataItemList.size();
        }
        viewList.get((CARDNUM + 1) % viewList.size()).setAlpha(1);
        viewList.get((CARDNUM + 1) % viewList.size()).layout(0, cardmarginTop, viewList.get((CARDNUM + 1) % viewList.size()).getWidth(), cardmarginTop + viewList.get((CARDNUM + 1) % viewList.size()).getHeight());
        CARDNUM = (CARDNUM + 1) % viewList.size();
        //viewList.get((CARDNUM + 2)% viewList.size()).setData(cardSlideDataItemList.get((++cardIndex)%cardSlideDataItemList.size()));
        //viewList.get((CARDNUM +2)% viewList.size()).setBackgroundResource(cardSlideDataItemList.get((CARDNUM + 2) % viewList.size()).cardslideImgPath);
        viewSlideOut = viewItemOut;
        //viewSlideOutList[CARDNUM] = viewItemOut;
        if(direction == -1){//左
            valueAnimatorLeftOutX = ValueAnimator.ofInt(viewItemOut.getLeft(), -viewItemOut.getWidth()).setDuration(slideOutTime);
            valueAnimatorLeftOutX.start();
            valueAnimatorLeftOutX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutX = (int) valueAnimatorLeftOutX.getAnimatedValue();
                }
            });
            valueAnimatorLeftOutY = ValueAnimator.ofInt(viewItemOut.getTop(), this.getHeight()).setDuration(slideOutTime);
            valueAnimatorLeftOutY.start();
            valueAnimatorLeftOutY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutY = (int) valueAnimatorLeftOutY.getAnimatedValue();
                    setNextView(frameValueX);
                    viewSlideOut.layout(frameSlideOutX, frameSlideOutY, frameSlideOutX + viewSlideOut.getWidth(), frameSlideOutY + viewSlideOut.getHeight());
                }
            });
        } else if (direction == 1){//右
            valueAnimatorLeftOutX = ValueAnimator.ofInt(viewItemOut.getLeft(), this.getRight()).setDuration(slideOutTime);
            valueAnimatorLeftOutX.start();
            valueAnimatorLeftOutX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutX = (int) valueAnimatorLeftOutX.getAnimatedValue();
                }
            });
            valueAnimatorLeftOutY = ValueAnimator.ofInt(viewItemOut.getTop(), this.getHeight()).setDuration(slideOutTime);
            valueAnimatorLeftOutY.start();
            this.valueAnimatorLeftOutY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    frameSlideOutY = (int)valueAnimatorLeftOutY.getAnimatedValue();
                    setNextView(frameValueX);
                    viewSlideOut.layout(frameSlideOutX, frameSlideOutY, frameSlideOutX + viewSlideOut.getWidth(), frameSlideOutY + viewSlideOut.getHeight());
                }
            });
        }
    }

    /**
     * 判断手指按点是否在view中
     * @param downX 手指按点X值
     * @param downY 手指按点Y值
     * @return
     */
    private boolean viewContains(int downX, int downY){
        View viewItem = viewList.get(CARDNUM);
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
        cardIndex = num;
        for (int i = 0; i < num; i++) {
            CardSlideViewItem itemView = viewList.get(i);
            itemView.setData(dataList.get(i));
            if (i != 0){
                itemView.setAlpha(0f);
            }else {
                itemView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 避免由于熄屏后再度打开引起的onMeasure方法调用导致未完全消失的视图显示出错问题
     */
    public void refreshView(){
        viewList.get((CARDNUM + viewList.size() - 1) % viewList.size()).setAlpha(0.0f);
    }


    public static class LayoutParams extends ViewGroup.LayoutParams{

        public int left = 0;
        public int right = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    @Override
    public android.view.ViewGroup.LayoutParams generateLayoutParams(
            AttributeSet attrs) {
        return new CardSlideViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(
            android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof CardSlideViewGroup.LayoutParams;
    }
}

