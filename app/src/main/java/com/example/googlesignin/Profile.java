package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    protected void populate_profile(){
        //query based on user

        //get and set image

        //get all properties,
        //loop and create text views for each
        //add all to about view

    }

    protected void handle_edit_profile(View v){

        startActivity(new Intent(Profile.this, EditProfile.class));

    }

    protected void handle_home(View v){
        startActivity(new Intent(Profile.this, Home.class));
    }

    protected void handle_back_btn(View v){
        startActivity(new Intent(Profile.this, Home.class));
    }


}


