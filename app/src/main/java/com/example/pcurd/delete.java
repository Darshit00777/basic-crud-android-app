package com.example.pcurd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class delete extends AppCompatActivity {
    EditText e1,e2;
    Spinner sp;
    ArrayList<Integer> al;
    SQLiteDatabase db;
    DBhelper dbh;
    AlertDialog ad;
    AlertDialog.Builder ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        e1 = findViewById(R.id.ediddel);
        e2 = findViewById(R.id.ednamedel);
        sp = findViewById(R.id.spiddel);
        dbh = new DBhelper(this);

        load();

//        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    public void load(){
        al = new ArrayList<>();

        db = dbh.getReadableDatabase();

        Cursor c = db.rawQuery("select * from p",null);

        while (c.moveToNext()){
            al.add(c.getInt(0));
        }

        ArrayAdapter ad = new ArrayAdapter<>(delete.this, android.R.layout.simple_spinner_item,al);
        sp.setAdapter(ad);
    }

//    public void ondelete(View view){
//        int id = Integer.parseInt(sp.getSelectedItem().toString());
//
//        db = dbh.getWritableDatabase();
//
//        long l = db.delete("p","id =" + id,null);
//
//        if(l > 0){
//            Toast.makeText(getApplicationContext(),"record deleted",Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(getApplicationContext(), "record not deleted", Toast.LENGTH_SHORT).show();
//        }
//        //db.close();
//        e1.setText("");
//        e2.setText("");
//        sp.setSelection(0);
//    }

    public void ondelete(View view){
        int id = Integer.parseInt(sp.getSelectedItem().toString());

        ab = new AlertDialog.Builder(delete.this);
        ab.setTitle("delete the record");
        ab.setMessage("are you sure to delete this record");
        ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = dbh.getWritableDatabase();

                long l = db.delete("p","id =" + id,null);

                if(l > 0){
                    Toast.makeText(getApplicationContext(),"record deleted",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "record not deleted", Toast.LENGTH_SHORT).show();
                }
                //db.close();
                e1.setText("");
                e2.setText("");
                sp.setSelection(0);
            }
        });
        ab.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad = ab.create();
        ad.show();
    }

    public void oncancel(View view){
        dbh.close();
        finish();
    }
}