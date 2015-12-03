package com.tesmple.chromeprocessbar.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.tesmple.chromeprocessbar.Adapters.MyHeaderAdapter;
import com.tesmple.chromeprocessbar.Adapters.MyListViewHeaderAdapter;
import com.tesmple.chromeprocessbar.R;

import java.util.ArrayList;

/**
 * Created by ESIR on 2015/12/1.
 */
public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyListViewHeaderAdapter mHeaderAdapter;
    private ScalpelFrameLayout scalpelFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_recyclerview);
        mRecyclerView = (RecyclerView)findViewById(R.id.rl_recyclerview);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHeaderAdapter = new MyListViewHeaderAdapter();
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
        mHeaderAdapter.addDatas(datas);
        mRecyclerView.setAdapter(mHeaderAdapter);
        setHeader(mRecyclerView);

        scalpelFrameLayout = (ScalpelFrameLayout)findViewById(R.id.scalpelframelayout);
        scalpelFrameLayout.setLayerInteractionEnabled(true);
        scalpelFrameLayout.setDrawViews(false);
    }

    private void setHeader(RecyclerView view) {
        View header = LayoutInflater.from(this).inflate(R.layout.layout_recyclerviewheader, view, false);
        mHeaderAdapter.setHeaderView(header);
    }
}
