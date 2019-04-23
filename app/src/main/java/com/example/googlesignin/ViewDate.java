package com.example.googlesignin;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ViewDate extends AppCompatActivity {

    private String email;
    private int date_id; // something to identify date
    private String inviter_id; // inviter google id
    private String inviter_name; // inviter name
    private String invitee_id; // invitee google id
    private String invitee_name; //invittee name
    private String date; // day of year
    private String time; // time of year
    private String location; // location
    private String message; // message
    private String status; // pending-> accept


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_date);

        email=getIntent().getStringExtra("email");

        date_id=getIntent().getIntExtra("date_id",0);
        //TODO: change to pull cur_id however Gabi did it for other pages

        //TODO: query table and get info to set all fields
        date_id=1;
        inviter_id= "sarahganci@gmail.com";
        inviter_name= "Sarah";
        invitee_id="gabi@example.com";
        invitee_name="Gabi";
        date="4/23/2019";
        //time currently in hour:minute am/pm
        time="12:00pm";
        location="211 S Columbia Street";
        message="lets get some scones";
        status="pending";

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
        //TODO: dialog pop up for reject?
        //delete date invite
        //return to dates screen
        Intent dates= new Intent(ViewDate.this, Dates.class);
        dates.putExtra("email", email);
        startActivity(dates);

    }

    protected void handle_cancel_date(View v){

        //TODO: database cancel date and fix popup
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Date Cancellation");
        builder.setMessage("Are you sure you want to cancel this date? It will be deleted forever.");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    protected void handle_view_dates_profile(View v){
        //go to view match but pass in extra to identify where we came from
        Intent viewmatch= new Intent(ViewDate.this, ViewMatch.class);
        //TODO: also need to put matches id by querying date id and getting the other id
        String other_id= this.email.equals(inviter_id)? invitee_id: inviter_id;

        viewmatch.putExtra("other_id", other_id);
        viewmatch.putExtra("from", "ViewDate");
        viewmatch.putExtra("date_id",date_id);
        viewmatch.putExtra("email", email);
        startActivity(viewmatch);


    }

    //date in: (monthOfYear + 1)+"/"+ dayOfMonth + "/" + year
    //time in: hourOfDay + ":" + minute+format
    protected Calendar create_start(String date, String time){

        String [] dateinfo= date.split("/");
        int month= Integer.parseInt(dateinfo[0])-1;
        int day= Integer.parseInt(dateinfo[1]);
        int year= Integer.parseInt(dateinfo[2]);
        String [] timeinfo= time.split(":");
        int hour= Integer.parseInt(timeinfo[0]);
        String format= timeinfo[1].substring(2);
        if(date.contains("pm") &&hour<12){
            hour= hour+ 12;
        }
        int minute= Integer.parseInt(timeinfo[1].substring(0,2));
        Calendar cal= Calendar.getInstance();
        cal.set(year,month, day, hour, minute);
        return cal;
    }

    protected Calendar create_end(String date, String time){

        String [] dateinfo= date.split("/");
        int month= Integer.parseInt(dateinfo[0])-1;
        int day= Integer.parseInt(dateinfo[1]);
        int year= Integer.parseInt(dateinfo[2]);
        String [] timeinfo= time.split(":");
        int hour= Integer.parseInt(timeinfo[0])+1;
        String format= timeinfo[1].substring(2);
        if(date.contains("pm") &&hour<12){
            hour= hour+ 12;
        }
        int minute= Integer.parseInt(timeinfo[1].substring(0,2));
        Calendar cal= Calendar.getInstance();
        cal.set(year,month, day, hour, minute);
        return cal;
    }


    protected void handle_add_event(View v){

        Calendar beginTime = create_start(this.date, this.time);
        Calendar endTime= create_end(this.date, this.time);

       // long temp= beginTime.getTimeInMillis();

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Blind Date: "+inviter_name+" and "+invitee_name)
                .putExtra(CalendarContract.Events.DESCRIPTION, this.message)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, this.location)
                .putExtra(Intent.EXTRA_EMAIL, this.inviter_id+","+this.invitee_id);
        startActivity(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if(resultCode == RESULT_OK){
//           Log.v("RESULT",data.getIntExtra("result",-1)+"");
//        }
//
//    }


}
