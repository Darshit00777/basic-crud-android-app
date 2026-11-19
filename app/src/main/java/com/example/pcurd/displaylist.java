package com.example.pcurd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class displaylist extends AppCompatActivity {
    ListView l;
    SQLiteDatabase db;
    DBhelper dbh;
    SimpleCursorAdapter sc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaylist);
        l = findViewById(R.id.list1);
        dbh = new DBhelper(this);

        registerForContextMenu(l);

        try{
            db = dbh.getReadableDatabase();

            Cursor c = db.rawQuery("select id as _id,name,gender,city,time from p",null);

            int count = c.getCount();
            if(count < 0){
                Toast.makeText(getApplicationContext(), "No record Found", Toast.LENGTH_SHORT).show();
            }

            String[] s = new String[]{
                c.getColumnName(0),
                c.getColumnName(1),
                c.getColumnName(2),
                c.getColumnName(3),
                c.getColumnName(4)
            };

            int[] from = new int[]{
                R.id.txtid,
                R.id.txtname,
                R.id.txtgender,
                R.id.txtcity,
                R.id.txttime
            };

            sc = new SimpleCursorAdapter(displaylist.this,R.layout.list,c,s,from,0);
            l.setAdapter(sc);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = (int)info.id;
        if (item.getItemId() == R.id.menudel){

            db = dbh.getWritableDatabase();

            long l = db.delete("p","id =" + id,null);

            if(l > 0){
                Toast.makeText(getApplicationContext(),"record deleted",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "record not deleted", Toast.LENGTH_SHORT).show();
            }
            //db.close();

        }
        return super.onContextItemSelected(item);
    }
}