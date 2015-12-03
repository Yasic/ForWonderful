package com.tesmple.chromeprocessbar.Views;

import android.content.Context;
import android.support.v4.view.ViewCompat;
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
    private int initCenterViewX=0;
    private int initCenterViewY=0;

    /**
     * 子view宽度
     */
    private int childWith=0;

    private int downEventX;
    private int downEventY;
    private int moveEventX;
    private int moveEventY;

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
                break;
            case MotionEvent.ACTION_MOVE:
                moveEventX = (int)e.getX();
                moveEventY = (int)e.getY();
                playSlideAnim(moveEventX-downEventX, moveEventY-downEventY);
                downEventX = moveEventX;
                downEventY = moveEventY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(
                resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
        viewGroupWidth = getMeasuredWidth();
        viewGroupHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int viewListSize = viewList.size();
        for(int i = 0; i < viewListSize; i++){
            View viewItem = viewList.get(i);
            int childHeight = viewItem.getMeasuredHeight();
            viewItem.layout(l, t, r , b);
            int offset = heightStep * i;
            if(i > 2){
                offset = heightStep *2;
            }
            viewItem.offsetTopAndBottom(offset);
            initCenterViewX = viewList.get(0).getLeft();
            initCenterViewY = viewList.get(0).getTop();
            childWith = viewList.get(0).getMeasuredWidth();
        }
    }

    private void playSlideAnim(int dx, int dy){
        View viewItem = viewList.get(0);
        int top = viewItem.getTop();
        int left = viewItem.getLeft();
        viewItem.layout(left + dx, top + dy, viewItem.getRight() + dx, viewItem.getBottom() + dy);
        /*viewItem.setTop(top + dy);
        viewItem.setLeft(left + dx);*/
    }

    public void fillData(List<CardSlideDataItem> dataList) {
        this.cardSlideDataItemList = dataList;
        int num = viewList.size();
        for (int i = 0; i < num; i++) {
            CardSlideViewItem itemView = (CardSlideViewItem) viewList.get(i);
            Log.i("datalist",i+":"+dataList.get(i).cardslideImgPath);
            itemView.setData(dataList.get(i));
            itemView.setVisibility(View.VISIBLE);
        }
    }
}

