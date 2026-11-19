package com.example.pcurd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class insert extends AppCompatActivity {
    EditText e1,e2;
    RadioGroup rg;
    RadioButton r1,r2;
    Spinner scity;
    String[] city;
    ArrayAdapter<String> ad;
    TimePickerDialog tp;
    SQLiteDatabase db;
    DBhelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        e1 = findViewById(R.id.edinsid);
        e2 = findViewById(R.id.edinsdate);
        rg = findViewById(R.id.rggender);
        r1 = findViewById(R.id.rgmale);
        r2 = findViewById(R.id.rgfemale);
        scity = findViewById(R.id.spcity);
        city = getResources().getStringArray(R.array.city);
        dbh = new DBhelper(this);

        ad = new ArrayAdapter<>(insert.this, android.R.layout.simple_spinner_item,city);
        scity.setAdapter(ad);

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR);
                int m = c.get(Calendar.MINUTE);

                tp = new TimePickerDialog(insert.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        e2.setText(i + " : " + i1);
                    }
                },h,m,true);
                tp.show();
            }
        });

    }
    public void oninsert(View view){
        String name = e1.getText().toString();

        String gender = "";
        if(r1.isChecked()){
            gender = "male";
        } else if (r2.isChecked()) {
            gender = "female";
        }

        String city = scity.getSelectedItem().toString();
        String time = e2.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("gender",gender);
        cv.put("city",city);
        cv.put("time",time);

        try {
            db = dbh.getWritableDatabase();

            long l = db.insert("p",null,cv);

            if(l != -1){
                Toast.makeText(getApplicationContext(), "Record inserted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Record not inserte", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //db.close();
        e1.setText("");
        e2.setText("");
        scity.setSelection(0);
    }

    public void oncancel(View view){
        dbh.close();
        finish();
    }
}