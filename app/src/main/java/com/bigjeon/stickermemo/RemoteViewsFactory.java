package com.bigjeon.stickermemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    public DBHelper dbHelper;
    public Context context = null;
    public ArrayList<Memo_Text> memo_texts;

    public RemoteViewsFactory(Context context){
        this.context  = context;
    }

    //db데이터 리스트 형으로 받아오기기
   public void setData(){
        memo_texts = new ArrayList<>();
        dbHelper = new DBHelper(context);
        memo_texts = dbHelper.getMemoList();
    }

    //실행시 최초로 실행
    @Override
    public void onCreate() {
        setData();
    }

    //항목 추가 및 제거 등 데이터 변경이 발생했을 때 호출되는 함수
    //브로드캐스트 리시버에서 notifyAppWidgetViewDataChanged()가 호출 될 때 자동 호출
    @Override
    public void onDataSetChanged() {
        setData();
    }
    //마지막에 실행
    @Override
    public void onDestroy() {

    }
    //항목 갯수 반환
    @Override
    public int getCount() {
        return memo_texts.size();
    }

    //각 항목을 구현하기위해 호출, 매개변수 값을 참조하여 각 항목을 구성하기 위한 로직이 담김
    //항목 선택 이벤트시 담아야할 내용을 추가
    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews listviewWidget = new RemoteViews(context.getPackageName(), R.layout.listview_memo);
        int Memo_Num = position + 1;
        listviewWidget.setTextViewText(R.id.ListView_Title, Memo_Num + ". " + memo_texts.get(position).getTitle());
        listviewWidget.setTextViewText(R.id.ListView_WriteDate, memo_texts.get(position).getWriteDate());

        Bundle extras = new Bundle();
        extras.putInt(ConfigWidgetProvider.EXTRA_ITEM, position);
        Intent DataIntent = new Intent();
        DataIntent.putExtras(extras);
        DataIntent.putExtra("memo_id", memo_texts.get(position).getId());
        DataIntent.putExtra("memo_title", memo_texts.get(position).getTitle());
        DataIntent.putExtra("memo_text", memo_texts.get(position).getText());
        DataIntent.putExtra("memo_writeDate", memo_texts.get(position).getWriteDate());
        listviewWidget.setOnClickFillInIntent(R.id.ListView_Title, DataIntent);

        //setOnClickFillInIntent 브로드캐스트 리시버에서 항목 선택 이벤트가 발생할 때 실행을 의뢰한 인텐트에 각 항목의 데이터를 추가해주는 함수
        //브로드캐스트 리시버의 인텐트와 Extra 데이터가 담긴 인텐트를 합치는 역할을 한다.

        return listviewWidget;
    }
    //로딩뷰 표현
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    //항목의 타입 갯수 판단, 모두 같은 타입일 경우 1로 반환
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    //각 항목의 식별자 값을 얻기위해(id)
    @Override
    public long getItemId(int position) {
        return 0;
    }
    //같은 id가 항상 같은 개체를 참조하면 true 반환
    @Override
    public boolean hasStableIds() {
        return false;
    }
}
