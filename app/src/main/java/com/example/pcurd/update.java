package com.example.pcurd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class update extends AppCompatActivity {
    EditText e1,e2;
    RadioGroup rg;
    RadioButton r1,r2;
    Spinner scity,sid;
    String[] s;
    ArrayAdapter<String> ad;
    ArrayList<Integer> al;
    TimePickerDialog tp;
    SQLiteDatabase db;
    DBhelper dbh;

    AlertDialog adl;
    AlertDialog.Builder ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        e1 = findViewById(R.id.ednameup);
        e2 = findViewById(R.id.eddateup);
        rg = findViewById(R.id.rggenderup);
        r1 = findViewById(R.id.rgmaleup);
        r2 = findViewById(R.id.rgfemaleup);
        scity = findViewById(R.id.spcityup);
        sid = findViewById(R.id.spidup);
        dbh = new DBhelper(this);
        s = getResources().getStringArray(R.array.city);

        ad = new ArrayAdapter<>(update.this, android.R.layout.simple_spinner_item,s);
        scity.setAdapter(ad);

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR);
                int m = c.get(Calendar.MINUTE);

                tp = new TimePickerDialog(update.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        e2.setText(i + " : " + i1);
                    }
                },h,m,true);
                tp.show();
            }
        });

        load_id();

        sid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int id = Integer.parseInt(sid.getSelectedItem().toString());

                db = dbh.getReadableDatabase();

                Cursor cid = db.rawQuery("select * from p where id = "+id,null);

                if(cid.moveToNext()){
                    e1.setText(cid.getString(1));

                    String gender = cid.getString(2);

                    if(gender.equals("male")){
                        r1.setChecked(true);
                        r2.setChecked(false);
                    } else if (gender.equals("female")) {
                        r1.setChecked(false);
                        r2.setChecked(true);
                    }

                    String city = cid.getString(3);
                    int pos = ad.getPosition(city);
                    scity.setSelection(pos);

                    e2.setText(cid.getString(4));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void load_id()
    {
        al = new ArrayList<>();

        db = dbh.getReadableDatabase();

        Cursor c = db.rawQuery("select * from p",null);

        while (c.moveToNext()){
            al.add(c.getInt(0));
        }

        ArrayAdapter add = new ArrayAdapter<>(update.this, android.R.layout.simple_spinner_item,al);
        sid.setAdapter(add);
    }

    public void onupdate(View view){
        int rowid = Integer.parseInt(sid.getSelectedItem().toString());
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

        ab = new AlertDialog.Builder(update.this);
        ab.setTitle("Update Alert");
        ab.setMessage("Are you Sure to Delete This record ");
        ab.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db = dbh.getWritableDatabase();

                long l = db.update("p",cv,"id = " + rowid,null);

                if(l > 0){
                    Toast.makeText(getApplicationContext(),"record updated",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "record not updated", Toast.LENGTH_SHORT).show();
                }
                //db.close();
                e1.setText("");
                e2.setText("");
                sid.setSelection(0);
                scity.setSelection(0);
            }
        });
        ab.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        adl = ab.create();
        adl.show();
    }

    public void oncancel(View view){
        dbh.close();
        finish();
    }
}