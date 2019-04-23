package com.example.googlesignin;

import android.content.Intent;
import android.service.autofill.FieldClassification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class Matches extends AppCompatActivity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        LinearLayout matches_container= (LinearLayout) findViewById(R.id.matches_container);

        matches_container.addView(this.create_match("sarahganci@gmail.com", "Sarah", 22, 100));

    }

    ////////////////////////////
    //Navigation button handlers
    ////////////////////////////

    /**name: handle_back_btn
     * action: takes to previous page
     * */
    protected void handle_back_btn(View v){
        startActivity(new Intent(Matches.this, Home.class));
    }
    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){
        user = getIntent().getExtras().getString("email");
        startActivity(new Intent(Matches.this, Home.class));
    }

    /**name: handle_view_profile
     * action: takes to view match page
     * */
    protected void handle_view_profile(String id){
        Intent view_profile= new Intent(Matches.this, ViewMatch.class );
        view_profile.putExtra("other_id", ""+id);
        view_profile.putExtra("from", "Matches");
        startActivity(view_profile);
    }

    /**name: handle_date
     * action: takes to view match page
     * */
    protected void handle_date(String id){
        //TODO: open new intent to create a date
        Log.v("MYTAG", "date id: "+id);
        Intent intent= new Intent(Matches.this, DateInput.class);
        intent.putExtra("from", "Matches");
        intent.putExtra("other_id", ""+id);
        startActivity(intent);
    }

    /////////
    //Helpers
    /////////

    /**name: create_match
     * action: creates ProfileMatch object
     * */
    protected ProfileMatch create_match(String id, String name, int age, double score){
        ProfileMatch match= new ProfileMatch(this);
        match.set_all(name, age, score);
        final String btn_id=id;
        match.get_date_button().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_date(btn_id);
            }
        });

        match.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_view_profile(btn_id);
            }
        });
        return match;
    }




}
