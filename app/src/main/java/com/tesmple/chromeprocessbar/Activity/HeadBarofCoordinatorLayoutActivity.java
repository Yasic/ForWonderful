package com.tesmple.chromeprocessbar.Activity;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.tesmple.chromeprocessbar.Adapters.MyListViewHeaderAdapter;
import com.tesmple.chromeprocessbar.Adapters.NormalAdapter;
import com.tesmple.chromeprocessbar.R;

import java.util.ArrayList;

import Listener.FootbarRecyclerViewListener;

/**
 * Created by ESIR on 2015/12/2.
 */
public class HeadBarofCoordinatorLayoutActivity extends Activity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NormalAdapter mNormalAdapter;
    private LinearLayout liFootBar;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headbarcoordinatorlayout);
        mRecyclerView = (RecyclerView)findViewById(R.id.rl_recyclerview);
        liFootBar = (LinearLayout)findViewById(R.id.li_footbar);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<String> datas = new ArrayList<>();
        datas.add("威风堂堂");
        datas.add("玉树临风");
        datas.add("颜值爆表");
        datas.add("智商逆天");
        datas.add("人品绝佳");
        datas.add("品行端正");
        datas.add("外能驱敌于长城之北");
        datas.add("内可安邦于万世千秋");
        datas.add("近能扶忠君出良策");
        datas.add("远能辨是非点忠良");
        datas.add("苟全性命于乱世");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        datas.add("不求闻达于诸侯");
        mNormalAdapter = new NormalAdapter(getApplicationContext(), datas, liFootBar);
        mRecyclerView.setAdapter(mNormalAdapter);
        mRecyclerView.setOnScrollListener(new FootbarRecyclerViewListener() {
            @Override
            public void onHide() {
                hideFootbar(liFootBar);
                liFootBar.setVisibility(View.GONE);
            }

            @Override
            public void onShow() {
                showFootbar(liFootBar);
                liFootBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideFootbar(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(view.getHeight()).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                showFootbar(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }


    private void showFootbar(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hideFootbar(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_recyclerviewheader, view, false);
    }
}
