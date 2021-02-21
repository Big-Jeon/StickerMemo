package com.bigjeon.stickermemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long backKeyPressed = 0;
    private RecyclerView mRCV_Memo;
    private Button mBtn_AddMemo;
    private DBHelper mdbHelper;
    private RCV_Adapter mRCV_Adapter;
    private ArrayList<Memo_Text> mMemo_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInit();
        //매모추가시 위젯 갱신
        updateWidget();
    }

    private void setInit(){
        mdbHelper = new DBHelper(this);
        mRCV_Memo = findViewById(R.id.Memo_RCV_main);
        mBtn_AddMemo = findViewById(R.id.Add_Memo);
        mMemo_List = new ArrayList<>();

        loadRecentDB();

        mBtn_AddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add_Text.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loadRecentDB() {
        //이미 저장된 db메모 불러오기
        mMemo_List = mdbHelper.getMemoList();
        if (mRCV_Adapter == null){
            mRCV_Adapter = new RCV_Adapter(mMemo_List, this);
            mRCV_Memo.setHasFixedSize(true);
            mRCV_Memo.setAdapter(mRCV_Adapter);
        }
    }

    public void onBackPressed(){
        if (System.currentTimeMillis() > backKeyPressed + 2000){
            backKeyPressed = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressed + 2000){
            updateWidget();
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }

    public void updateWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(this, ConfigWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.Memo_List);
    }
}