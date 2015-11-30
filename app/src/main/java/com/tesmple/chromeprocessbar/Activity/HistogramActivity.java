package com.tesmple.chromeprocessbar.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tesmple.chromeprocessbar.R;
import com.tesmple.chromeprocessbar.Views.HistogramView;

/**
 * Created by ESIR on 2015/10/29.
 */
public class HistogramActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);

        HistogramView histogramView = (HistogramView)findViewById(R.id.histogram_View);
        histogramView.setOnTouchListener(new HistogramView.OnInsideTouchListener(){

            @Override
            public void show() {
                Toast.makeText(HistogramActivity.this,"Hello man",Toast.LENGTH_LONG).show();
            }

            @Override
            public void dismiss() {

            }
        });
    }
}
