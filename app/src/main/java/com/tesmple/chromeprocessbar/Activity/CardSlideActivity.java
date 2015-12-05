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
        dataItemList = new ArrayList<CardSlideDataItem>();
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg1, "Miku", "1km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg2,"Miku","2km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg3,"Miku","3km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg4,"Miku","4km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg5,"Miku","5km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg6,"Miku","6km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg7,"Miku","7km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg8,"Miku","8km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg9,"Miku","9km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.headimg10,"Miku","10km"));
        cardSlideViewGroup = (CardSlideViewGroup)findViewById(R.id.cardslideviewgroup);
        cardSlideViewGroup.fillData(dataItemList);
    }
}
