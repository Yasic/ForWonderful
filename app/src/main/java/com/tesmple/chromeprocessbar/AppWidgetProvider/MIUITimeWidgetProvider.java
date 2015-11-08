package com.tesmple.chromeprocessbar.AppWidgetProvider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.RemoteViews;

import com.tesmple.chromeprocessbar.Activity.MIUITimeActivity;
import com.tesmple.chromeprocessbar.R;

/**
 * Created by ESIR on 2015/11/8.
 */
public class MIUITimeWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context,AppWidgetManager appWidgetManager,int[] appWidgetIds){
        super.onUpdate(context,appWidgetManager,appWidgetIds);
        /*Intent intent=new Intent(context, MIUITimeActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews myRemoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_miuitimeview);

        ComponentName myComponentName = new ComponentName(context, MIUITimeActivity.class);
//负责管理AppWidget，向AppwidgetProvider发送通知。提供了更新AppWidget状态，获取已经安装的Appwidget提供信息和其他的相关状态
        AppWidgetManager myAppWidgetManager = AppWidgetManager.getInstance(context);
        myAppWidgetManager.updateAppWidget(myComponentName, myRemoteViews);*/
    }
}
