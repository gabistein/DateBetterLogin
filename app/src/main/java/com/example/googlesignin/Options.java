package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        LinearLayout options_container= (LinearLayout) findViewById(R.id.options_container);

      //  options_container.addView(new ProfileOption(this));
        options_container.addView(this.create_option("emailsarah","Sarah", 22, 100));
        options_container.addView(this.create_option("emailbob","Bob", 22, 100));
        options_container.addView(this.create_option("emailsue","Sue", 22, 100));



        Log.v("MYTAG", "trying to add layout in oncreate");
    }



    ///////////////////////////
    //Navigation Button Handlers
    ///////////////////////////

    /**name: handle_view_profile
     * action: opens ViewOption for specific id
     * */
    protected void handle_view_profile(String id){
        Intent view_profile= new Intent(Options.this, ViewOption.class );
        view_profile.putExtra("other_id", ""+id);
        startActivity(view_profile);
    }


    /**name: handle_like
     * action: adds a like to the database for id to id
     * */
    protected void handle_like(String id){
        //TODO: insert like into like table
        Log.v("MYTAG", "liked id: "+id);
    }

    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){
        startActivity(new Intent(Options.this, Home.class));
    }

    /**name: handle_home
     * action: takes to previous page
     * */
    protected void handle_back_btn(View v){
        startActivity(new Intent(Options.this, Home.class));
    }




    ///////////////////////////
    //////Helpers
    ///////////////////////////
    /**name: handle_home
     * action: clears options
     * */
    protected void clear_options(){
        LinearLayout options_container= (LinearLayout) findViewById(R.id.options_container);
        //TODO: clear all options

    }

    /**name: create_option
     * action: creates a profile option object
     * */
    protected ProfileOption create_option(String id, String name, int age, double score){
        ProfileOption option= new ProfileOption(this);
        option.set_all(name, age, score);
        final String btn_id=id;
        option.get_like_button().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_like(btn_id);
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_view_profile(btn_id);
            }
        });
        return option;
    }
}

