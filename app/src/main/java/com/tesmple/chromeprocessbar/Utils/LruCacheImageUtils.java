package com.tesmple.chromeprocessbar.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.util.ConcurrentModificationException;

/**
 * Created by ESIR on 2015/12/16.
 */
public class LruCacheImageUtils {
    /**
     * 缓存图片资源的LruCache
     */
    private LruCache<String,Bitmap> imageMemoryCache;

    /**
     * app上下文引用
     */
    private Context context;

    /**
     * 期望的长与宽
     */
    private int requestWidthPx;
    private int requestHeightPx;

    /**
     * 缩放图片的utils
     */
    private SimpleImageViewUtil simpleImageViewUtil;

    public LruCacheImageUtils(Context context, int requestWidthPx, int requestHeightPx){
        this.context = context;
        this.requestHeightPx = requestHeightPx;
        this.requestWidthPx = requestWidthPx;
        simpleImageViewUtil = new SimpleImageViewUtil(context);
        int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemory/8;
        imageMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap){
                return bitmap.getByteCount()/1024;
            }
        };
    }

    public void loadBitmap(int resId, ImageView imageView){
        String imagekey = String.valueOf(resId);
        Bitmap bitmap = getBitmapFromMemoryCache(imagekey);
        if (bitmap != null) {
            imageView.setImageBitmap(simpleImageViewUtil.getFitSampleBitmap(context.getResources(), resId, requestWidthPx, requestHeightPx));
        }else {
            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask();
            bitmapWorkerTask.execute(resId);
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if (getBitmapFromMemoryCache(key) == null){
            imageMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemoryCache(String key){
        return imageMemoryCache.get(key);
    }

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Integer... params) {
            Bitmap bitmap = simpleImageViewUtil.getFitSampleBitmap(context.getResources(), params[0], requestWidthPx, requestHeightPx);
            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
            return bitmap;
        }
    }
}
