package com.bigjeon.stickermemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Change_Text extends AppCompatActivity {

    private long backKeyPressed = 0;
    private EditText set_Title;
    private EditText set_Text;
    private Button Complete_btn;
    private DBHelper mdbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);

        mdbHelper = new DBHelper(this);

        set_Title = findViewById(R.id.Set_Title);
        set_Text = findViewById(R.id.Set_Text);
        Complete_btn = findViewById(R.id.Add_Complete);
        set_Text.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        set_Title.setText(intent.getExtras().getString("title"));
        set_Text.setText(intent.getExtras().getString("text"));

        set_Title.setSelection(set_Title.length());
        set_Text.setSelection(set_Text.length());

        Complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = set_Title.getText().toString();
                String Text = set_Text.getText().toString();
                String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String beforeTime = intent.getExtras().getString("writeDate");
                mdbHelper.UpdateTodo(Title, Text, currentTime, beforeTime);
                Intent gomain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(gomain);
                finish();
            }
        });
    }
}
