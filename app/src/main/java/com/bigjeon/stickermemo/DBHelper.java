package com.bigjeon.stickermemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "bigjeon_memo.db";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //데이터베이스가 생성이 될떄 호출
        //데이터베이스 -> 테이블 -> 컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS MemoList (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, text TEXT NOT NULL, writeDate TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onCreate(db);
    }

    //SELECT 문 = 할일목록 조회
    public ArrayList<Memo_Text> getMemoList() {
        ArrayList<Memo_Text> MemoItems = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  MemoList ORDER BY writeDate DESC", null);
        if (cursor.getCount() != 0){
            //조회한 데이터가 반드시 있을때
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title  = cursor.getString(cursor.getColumnIndex("title"));
                String text  = cursor.getString(cursor.getColumnIndex("text"));
                String writeDate  = cursor.getString(cursor.getColumnIndex("writeDate"));

                Memo_Text memo_text = new Memo_Text();
                memo_text.setId(id);
                memo_text.setTitle(title);
                memo_text.setText(text);
                memo_text.setWriteDate(writeDate);
                MemoItems.add(memo_text);
            }
        }
        cursor.close();

        return MemoItems;
    }

    //INSERT 문 액티비티던 어디던 가져오기위해 public으로 만듬, 할일 목록을 db에 넣는 부분
    public void InsertTodo(String _title, String _text, String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MemoList (title, text, writedate) VALUES('" + _title + "', '" + _text + "', '" + _writeDate + "');");
    }

    //UPDATE 문 할일 목록 수정
    public void UpdateTodo(String _title, String _text, String _writeDate, String _beforeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE MemoList SET title = '" + _title + "', text = '" + _text + "', writeDate = '" + _writeDate + "' WHERE writeDate = '" + _beforeDate + "'");
    }
    //DELETE 문 할일 목록 제거
    public void deleteTodo(String _beforeTime){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MemoList WHERE writeDate = '" + _beforeTime + "'");
    }
}