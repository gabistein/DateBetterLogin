package com.example.googlesignin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class Dates extends AppCompatActivity {

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);

        email=getIntent().getStringExtra("email");

        LinearLayout matches_container= (LinearLayout) findViewById(R.id.dates_container);

        //TODO: for each date, get date id, invitee name, inviter name, status
        //TODO: add each date to matches_container
        matches_container.addView(this.create_date("Sarah", "gabi", "pending", 1));




    }

    protected void handle_back_btn(View v){
        Intent to_home=new Intent(Dates.this, Home.class);
        to_home.putExtra("email",email);

        startActivity(to_home);
    }

    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){

        Intent to_home=new Intent(Dates.this, Home.class);
        to_home.putExtra("email",email);

        startActivity(to_home);

    }


    ///////////////////////
    //Helpers: Date Objects
    ///////////////////////



    /**name: create_date
     * action: creates DateTile object
     * */
    protected DateTile create_date(String inviter_name, String invitee_name, String status, int date_id){
        DateTile date_tile= new DateTile(this);
        //TODO: ideally set name instead of id but need
        date_tile.set_all(invitee_name,inviter_name, status);
        final int f_date_id=date_id;

        date_tile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_view_date(f_date_id);
            }
        });
        return date_tile;
    }


    /**name: handle_view_date
     * action: creates DateTile object
     * */
    protected void handle_view_date( int date_id){

        Intent view_date= new Intent(Dates.this, ViewDate.class );
        view_date.putExtra("date_id", +date_id);
        view_date.putExtra("email",email);
        startActivity(view_date);
    }






}
