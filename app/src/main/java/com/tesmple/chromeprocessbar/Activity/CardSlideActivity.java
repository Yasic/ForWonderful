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
        dataItemList.add(new CardSlideDataItem(R.drawable.cardslideview_img1, "Tom", "1km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.cardslideview_img2,"Jack","2km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.cardslideview_img3,"Smith","3km"));
        dataItemList.add(new CardSlideDataItem(R.drawable.cardslideview_img4,"John","4km"));
        cardSlideViewGroup = (CardSlideViewGroup)findViewById(R.id.cardslideviewgroup);
        cardSlideViewGroup.fillData(dataItemList);
    }
}
