package com.example.pcurd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Toolbar t;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = findViewById(R.id.toolbar1);
        setSupportActionBar(t);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.btn11){
            i = new Intent(MainActivity.this, insert.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.btn12) {
            i = new Intent(MainActivity.this, update.class);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.btn13) {
            i = new Intent(MainActivity.this, delete.class);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.btn14) {
            i = new Intent(MainActivity.this, displaytxt.class);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.btn15) {
            i = new Intent(MainActivity.this, displaylist.class);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.btn16) {
            i = new Intent(MainActivity.this, displaysort.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onins(View view){
        i = new Intent(MainActivity.this, insert.class);
        startActivity(i);
    }

    public void onup(View view){
        i = new Intent(MainActivity.this, update.class);
        startActivity(i);
    }

    public void ondel(View view){
        i = new Intent(MainActivity.this, delete.class);
        startActivity(i);
    }

    public void ondis(View view){
        i = new Intent(MainActivity.this, displaytxt.class);
        startActivity(i);
    }

    public void onlist(View view){
        i = new Intent(MainActivity.this, displaylist.class);
        startActivity(i);
    }

    public void onsort(View view){
        i = new Intent(MainActivity.this, displaysort.class);
        startActivity(i);
    }
}