package com.bigjeon.stickermemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Show_Selected_Memo extends AppCompatActivity {

    private TextView Title;
    private TextView Text;
    private TextView WriteDate;
    private Button Change_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_selected_memo);

        Title = findViewById(R.id.Set_Title);
        Text = findViewById(R.id.Set_Text);
        WriteDate = findViewById(R.id.Selected_WriteDate);
        Text.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        Title.setText(intent.getExtras().getString("title"));
        Text.setText(intent.getExtras().getString("text"));
        WriteDate.setText(intent.getExtras().getString("writeDate"));
        Change_btn = findViewById(R.id.chage_btn);
        Change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getApplicationContext(), Change_Text.class);
                change.putExtra("title", intent.getExtras().getString("title"));
                change.putExtra("text", intent.getExtras().getString("text"));
                change.putExtra("writeDate",intent.getExtras().getString("writeDate"));
                startActivity(change);
                finish();
            }
        });
    }
}
