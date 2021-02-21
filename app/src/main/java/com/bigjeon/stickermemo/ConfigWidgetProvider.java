package com.bigjeon.stickermemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class ConfigWidgetProvider extends AppWidgetProvider {

    private final String ACTION_BTN1 = "ButtonClick1";//리스트
    private final String ACTION_BTN2 = "ButtonClick2";//해당메모
    public static final String TOAST_ACTION = "TOAST";//각항복 타이틀 내용 부여
    public static final String EXTRA_ITEM = "EXTRA_ITEM";

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent serviceintent = new Intent(context, RemoteViewsService.class);
            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_showmemo);
            widget.setRemoteAdapter(R.id.Memo_List, serviceintent);

            Intent GoMain = new Intent(Intent.ACTION_MAIN);
            GoMain.setComponent(new ComponentName(context, Add_Text.class));
            GoMain.setAction("add");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, GoMain, 0);
            widget.setOnClickPendingIntent(R.id.btn_add, pendingIntent);

            Intent ShowList = new Intent(context, ConfigWidgetProvider.class).setAction(ACTION_BTN1);
            PendingIntent PI1 = PendingIntent.getBroadcast(context, 0, ShowList, 0);
            widget.setOnClickPendingIntent(R.id.btn_list, PI1);

            Intent ToastIntent = new Intent(context, ConfigWidgetProvider.class);
            ToastIntent.setAction(ConfigWidgetProvider.TOAST_ACTION);
            ToastIntent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            serviceintent.setData(Uri.parse(serviceintent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPending = PendingIntent.getBroadcast(context, 0, ToastIntent, 0);
            widget.setPendingIntentTemplate(R.id.Memo_List, toastPending);

            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_showmemo);
        ComponentName componentName = new ComponentName(context, ConfigWidgetProvider.class);
        if (action.equals(ACTION_BTN1)){
            remoteViews.setViewVisibility(R.id.Memo_List, 0x00000000);
            remoteViews.setViewVisibility(R.id.memo_View, 0x00000004);
            remoteViews.setViewVisibility(R.id.Widget_AppName, 0x00000000);
            remoteViews.setTextViewText(R.id.Widget_AppName, "포스트잇 메모");
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }else if (action.equals(TOAST_ACTION)){
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            String Title = intent.getStringExtra("memo_title");
            String Text = intent.getStringExtra("memo_text");
            String WriteDate = intent.getStringExtra("memo_writeDate");
            remoteViews.setTextViewText(R.id.Widget_AppName, Title);
            remoteViews.setTextViewText(R.id.Show_Memo_Text, Text);
            remoteViews.setTextViewText(R.id.Show_Memo_WriteDate, WriteDate);
            remoteViews.setViewVisibility(R.id.Memo_List, 0x00000004);
            remoteViews.setViewVisibility(R.id.memo_View, 0x00000000);
            remoteViews.setViewVisibility(R.id.Widget_AppName, 0x00000000);
            appWidgetManager.updateAppWidget(componentName, remoteViews);
        }
    }

}
