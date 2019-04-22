package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewDate extends AppCompatActivity {

    private int date_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_date);

        date_id=getIntent().getIntExtra("date_id",0);

        //TODO:
        //determine whether current user invited (inwhich case pass in true
        //or if current user  is invitee but has already accepted date (true_
        //else: pass in false and accept and reject will appear
        this.determine_visibility(true);


    }


    /////////////////////////////
    //Button Handlers and Helpers
    /////////////////////////////

    /**name: determine_visibility
     * action: determines which buttons to show as visible
     * */
    protected void determine_visibility(boolean hide_accept_reject){
        //TODO: determine if inviter is current profile
        Button accept= (Button)findViewById(R.id.btn_accept);
        Button reject= (Button)findViewById(R.id.btn_reject);
        Button view_dates_profile= (Button)findViewById(R.id.btn_view_dates_profile);
        Button cancel= (Button)findViewById(R.id.btn_cancel);


        if(hide_accept_reject){
            accept.setVisibility(accept.GONE);
            reject.setVisibility(reject.GONE);
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);
            cancel.setVisibility(cancel.VISIBLE);
        }else{
            accept.setVisibility(accept.VISIBLE);
            reject.setVisibility(reject.VISIBLE);
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);
            cancel.setVisibility(cancel.GONE);

        }
    }

    protected void handle_accept(View v){
        //TODO: database entry for accepted date
        //TODO: google calendar invite
        this.determine_visibility(true);

    }

    protected void handle_reject(View v){
        //TODO: database entry for rejected date
        //delete date invite
        //return to dates screen
        Intent dates= new Intent(ViewDate.this, Dates.class);
        startActivity(dates);

    }

    protected void handle_cancel_date(View v){
        //TODO: database deletes date
        //return to dates screen
        Intent dates= new Intent(ViewDate.this, Dates.class);
        startActivity(dates);
    }

    protected void handle_view_dates_profile(View v){
        //go to view match but pass in extra to identify where we came from
        Intent viewmatch= new Intent(ViewDate.this, ViewMatch.class);
        //TODO: also need to put matches id by querying date id and getting the other id
        viewmatch.putExtra("from", "ViewDate");
        viewmatch.putExtra("date_id",date_id);


    }
}
