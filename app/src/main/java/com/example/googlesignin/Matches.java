package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Matches extends AppCompatActivity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
    }

    protected void handle_home(View v){
        user = getIntent().getExtras().getString("email");
        startActivity(new Intent(Matches.this, Home.class));
    }
}
