package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ViewOption extends AppCompatActivity {

    boolean has_liked;
    String other_id;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_option);

        /**Set Like Button message
         * query in match table: has current user liked this user before?
         *  if yes=> set button text to say "Unlike"
         *  if no=> set button text to say "Like"
         *
         **/
        Button like_btn= (Button) findViewById(R.id.btn_like);
        //do some querying and if statements
        has_liked=false;
        //set text based on liked value
        this.like_btn_text();


        //testing if info is being passed
        other_id= getIntent().getStringExtra("other_id");
        email= getIntent().getStringExtra("email");
        Log.v("MYTAG", other_id);
    }

    protected void handle_like(View v){
        //insert a like into the like table where the liker is the current user and likee is the profile viewd

        //flip value of has_liked boolean
        has_liked=!has_liked;

        //update the like buttons text
        this.like_btn_text();

    }

    protected void like_btn_text(){
        Button like_btn= (Button) findViewById(R.id.btn_like);
        if(has_liked){
            like_btn.setText("Unlike");
        }else{
            like_btn.setText("Like");
        }

    }


    protected void handle_back_btn(View v){
        Intent to_options=new Intent(ViewOption.this, Options.class);
        to_options.putExtra("email", email);
        startActivity(to_options);
    }

    protected void handle_home(View v){
        Intent to_home=new Intent(ViewOption.this, Home.class);
        to_home.putExtra("email", email);
        startActivity(to_home);
    }
}
