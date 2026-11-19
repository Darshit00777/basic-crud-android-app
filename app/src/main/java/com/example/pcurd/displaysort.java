package com.example.pcurd;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class displaysort extends AppCompatActivity {
    TextView t;
    SQLiteDatabase db;
    DBhelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaysort);
        t = findViewById(R.id.txtsortdisplay);
        dbh = new DBhelper(this);
    }
    public void onsort(View view){
        db = dbh.getReadableDatabase();

        Cursor c = db.rawQuery("select * from p order by name desc",null);

        while(c.moveToNext()){
            t.append("\n\n" + c.getInt(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(3) + " " + c.getString(4));

        }
    }
    public void oncancel(View view){
        dbh.close();
        finish();
    }
}