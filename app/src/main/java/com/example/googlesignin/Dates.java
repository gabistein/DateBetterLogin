package com.example.googlesignin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Dates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);

    }

    protected void handle_back_btn(View v){
        startActivity(new Intent(Dates.this, Home.class));
    }

    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){
        startActivity(new Intent(Dates.this, Home.class));
    }


    ///////////////////////
    //Helpers: Date Objects
    ///////////////////////


}
