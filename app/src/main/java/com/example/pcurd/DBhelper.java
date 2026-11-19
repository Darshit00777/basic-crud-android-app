package com.example.pcurd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    static final String table_name = "p";
    static final String col_id = "id";
    static final String col_name = "name";
    static final String col_gender = "gender";
    static final String col_city = "city";
    static final String col_time = "time";
    public DBhelper(@Nullable Context context) {
        super(context, "pexam.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("create table p(id integer primary key autoincrement, name text not null, gender text, city text, time text);");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists p");
        onCreate(sqLiteDatabase);
    }
}
