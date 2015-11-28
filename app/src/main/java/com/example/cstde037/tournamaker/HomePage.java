package com.example.cstde037.tournamaker;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_home_page);
    }

    public void onClick(View v) {

        if (v.getId() == R.id.button1) {
            Intent intent = new Intent(HomePage.this, TeamManager.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button) {
            AlertDialog dialog = new AlertDialog.Builder(HomePage.this)
                    .setTitle("Information")
                    .setMessage("Welcome to the TournaMaker android application. This application serves as a tournament creation and management tool for soccer tournaments. On each page of our application you may click on the information button to get instructions as well as full description of the page's different features. ")
                    .show();
        }
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}