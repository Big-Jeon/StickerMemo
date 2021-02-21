package com.bigjeon.stickermemo;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Text extends AppCompatActivity {

    private long backKeyPressed = 0;
    private EditText set_Title;
    private EditText set_Text;
    private Button Complete_btn;
    private DBHelper mdbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);

        mdbHelper = new DBHelper(this);

        set_Title = findViewById(R.id.Set_Title);
        set_Text = findViewById(R.id.Set_Text);
        Complete_btn = findViewById(R.id.Add_Complete);

        Complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (set_Title.getText().toString().getBytes().length > 0){
                    String CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    mdbHelper.InsertTodo(set_Title.getText().toString(), set_Text.getText().toString(), CurrentTime);
                    Toast.makeText(Add_Text.this, "메모가 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                    goMain();
                    finish();
                    //서비스에 인텐트로 실행
                }else {
                    Toast.makeText(Add_Text.this, "제목을 입력하십시오.", Toast.LENGTH_SHORT).show();
                    goMain();
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (set_Title.getText().toString().getBytes().length > 0){
            String CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            mdbHelper.InsertTodo(set_Title.getText().toString(), set_Text.getText().toString(), CurrentTime);
            Toast.makeText(Add_Text.this, "메모가 추가 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void goMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

}
