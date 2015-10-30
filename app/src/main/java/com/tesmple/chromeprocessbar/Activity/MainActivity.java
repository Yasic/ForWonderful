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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
    }

    private void initButton(){
        btnJumpProcessBarActivity = (Button)findViewById(R.id.btn_JumpProcessBarActivity);
        btnHistogramActivity = (Button)findViewById(R.id.btn_HistogramActivity);
        btnJumpProcessBarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,JumpProcessBarActivity.class));
            }
        });
        btnHistogramActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,HistogramActivity.class));
            }
        });
    }
}
