package com.tesmple.chromeprocessbar.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by ESIR on 2015/12/15.
 */
public class SimpleImageViewUtil {

    private Context context;

    public SimpleImageViewUtil(Context context){
        this.context = context;
    }

    public Bitmap getFitSampleBitmap(Resources resources, int resId, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = getFitInSampleSize(width, height, options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources,resId, options);
    }

    public int getFitInSampleSize(int requestWidth, int requestHeight, BitmapFactory.Options options){
        int inSampleSize = 1;
        if (options.outWidth > requestWidth || options.outHeight > requestHeight){
            int widthRatio = Math.round((float)options.outWidth / (float) requestWidth);
            int heightRatio = Math.round((float)options.outHeight / (float)requestHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }
        Log.i("inSample", String.valueOf(inSampleSize) + " " + options.outWidth + " " + options.outHeight + " " + requestWidth + " " + requestHeight);
        return inSampleSize;
    }

    public int getViewWidthByMeasure(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return width;
    }

    public int dp2pix(int dp){
        float scale = context.getResources().getDisplayMetrics().density;
        int pix = (int) (dp * scale + 0.5f);
        return pix;
    }

    public int pix2dp(int pix){
        float scale = context.getResources().getDisplayMetrics().density;
        int dp = (int) (pix/scale + 0.5f);
        return dp;
    }

}
