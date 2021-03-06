package com.tesmple.chromeprocessbar.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tesmple.chromeprocessbar.Objects.CardSlideDataItem;
import com.tesmple.chromeprocessbar.R;
import com.tesmple.chromeprocessbar.Views.CardSlideViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESIR on 2015/12/3.
 */
public class CardSlideActivity extends AppCompatActivity {
    private List<CardSlideDataItem> dataItemList;
    private CardSlideViewGroup cardSlideViewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardslideview);
        initViewBind();
        setDatas();
    }

    /**
     * 视图绑定
     */
    private void initViewBind(){
        cardSlideViewGroup = (CardSlideViewGroup)findViewById(R.id.cardslideviewgroup);
    }

    /**
     * 数据加载
     */
    private void setDatas(){
        dataItemList = new ArrayList<CardSlideDataItem>();
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg1, "Miku", "0km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg2,"Miku","1km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg3,"Miku","2km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg4,"Miku","3km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg5,"Miku","4km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg6,"Miku","5km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg7,"Miku","6km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg8,"Miku","7km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg9,"Miku","8km"));
        cardSlideViewGroup.fillData(dataItemList);
    }

    /**
     * 在这里进行对视图的检查和更新
     */
    @Override
    protected void onResume(){
        cardSlideViewGroup.refreshView();
        super.onResume();
    }

}
