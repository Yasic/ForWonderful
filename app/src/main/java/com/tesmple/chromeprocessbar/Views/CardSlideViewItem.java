package com.tesmple.chromeprocessbar.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tesmple.chromeprocessbar.Objects.CardSlideDataItem;
import com.tesmple.chromeprocessbar.R;

/**
 * Created by ESIR on 2015/12/3.
 */
public class CardSlideViewItem extends LinearLayout {
    /**
     * 目标图片
     */
    private ImageView ivCardslideLogo;
    /**
     * 目标姓名
     */
    private TextView tvCardslideName;
    /**
     * 目标距离
     */
    private TextView tvCardslideDistance;


    public CardSlideViewItem(Context context) {
        super(context);
        inflate(context, R.layout.layout_cardslideviewitem, this);
        ivCardslideLogo = (ImageView)findViewById(R.id.iv_cardslide_logo);
        tvCardslideName = (TextView)findViewById(R.id.tv_cardslide_name);
        tvCardslideDistance = (TextView)findViewById(R.id.tv_cardslide_distance);
    }

    public CardSlideViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_cardslideviewitem, this);
        ivCardslideLogo = (ImageView)findViewById(R.id.iv_cardslide_logo);
        tvCardslideName = (TextView)findViewById(R.id.tv_cardslide_name);
        tvCardslideDistance = (TextView)findViewById(R.id.tv_cardslide_distance);
    }

    public CardSlideViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_cardslideviewitem, this);
        ivCardslideLogo = (ImageView)findViewById(R.id.iv_cardslide_logo);
        tvCardslideName = (TextView)findViewById(R.id.tv_cardslide_name);
        tvCardslideDistance = (TextView)findViewById(R.id.tv_cardslide_distance);
    }

    public void setData(CardSlideDataItem cardSlideDataItem){
        Log.i("datalist", cardSlideDataItem.cardslideImgPath+"");
        ivCardslideLogo.setBackgroundResource(cardSlideDataItem.cardslideImgPath);
        tvCardslideName.setText(cardSlideDataItem.cardslideName);
        tvCardslideDistance.setText(cardSlideDataItem.cardslideDistance);
    }

}
