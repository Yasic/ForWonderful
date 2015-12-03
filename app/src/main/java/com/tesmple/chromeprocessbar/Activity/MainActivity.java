package com.tesmple.chromeprocessbar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tesmple.chromeprocessbar.R;

public class MainActivity extends AppCompatActivity {
    /**
     * 开启跳跃进度条的button
     */
    private Button btnJumpProcessBarActivity;

    /**
     * 开启柱形图的button
     */
    private Button btnHistogramActivity;

    /**
     * 开启折线图的button
     */
    private Button btnBrokenLineGraphView;

    /**
     * 开启旋转圆加载动画的button
     */
    private Button btnRotationProcessBarView;

    /**
     * 开启MIUItimer的button
     */
    private Button btnMIUITimeView;

    /**
     * 开启测试EditText的button
     */
    private Button btnEditTextView;

    /**
     * 开启RecyclerView的button
     */
    private Button btnRecyclerView;

    /**
     * 滑动显示隐藏头部
     */
    private Button btnHeadbarCoordinatorlayout;

    /**
     * 启动滑动卡片button
     */
    private Button btnCardslideViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
    }

    private void initButton(){
        btnJumpProcessBarActivity = (Button)findViewById(R.id.btn_JumpProcessBarActivity);
        btnJumpProcessBarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JumpProcessBarActivity.class));
            }
        });
        btnHistogramActivity = (Button)findViewById(R.id.btn_HistogramActivity);
        btnHistogramActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HistogramActivity.class));
            }
        });
        btnBrokenLineGraphView = (Button)findViewById(R.id.btn_BrokenLineGraphView);
        btnBrokenLineGraphView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BrokenLineGraphActivity.class));
            }
        });
        btnRotationProcessBarView = (Button)findViewById(R.id.btn_RotationProcessBarView);
        btnRotationProcessBarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RotationProcessBarActivity.class));
            }
        });
        btnMIUITimeView = (Button)findViewById(R.id.btn_MIUITimeView);
        btnMIUITimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MIUITimeActivity.class));
            }
        });
        btnEditTextView = (Button)findViewById(R.id.btn_EditTextView);
        btnEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditTextActivity.class));
            }
        });
        btnRecyclerView = (Button)findViewById(R.id.btn_RecyclerView);
        btnRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
            }
        });
        btnHeadbarCoordinatorlayout = (Button)findViewById(R.id.btn_HeadbarCoordinatorlayout);
        btnHeadbarCoordinatorlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HeadBarofCoordinatorLayoutActivity.class));
            }
        });
        btnCardslideViewGroup = (Button)findViewById(R.id.btn_CardslideViewGroup);
        btnCardslideViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CardSlideActivity.class));
            }
        });
    }
}
