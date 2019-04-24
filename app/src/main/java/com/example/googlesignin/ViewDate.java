package com.example.googlesignin;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class ViewDate extends AppCompatActivity {

    private String email;

    private String date_id; // something to identify date

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_date);


        date_id = getIntent().getStringExtra("date_id");
        email=getIntent().getStringExtra("email");
        setExtras(date_id);
        // get values
        // lol why is this happening
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                TextView when_view = (TextView) findViewById(R.id.when);
//                System.out.println(when_view.getText().toString());
//            }
//        }, 2000);

//        String[] when_text = when_view.getText().toString().split(",");
//        time = when_text[1];
//        date = when_text[0];
//        System.out.println(time+", " + date);










        //TODO:
        //determine whether current user invited (inwhich case pass in true
        //or if current user  is invitee but has already accepted date (true_
        //else: pass in false and accept and reject will appear
        this.determine_visibility();




    }


    /////////////////////////////
    //Button Handlers and Helpers
    /////////////////////////////

    /**name: determine_visibility
     * action: determines which buttons to show as visible
     * */
    protected void determine_visibility(){
        //TODO: determine if inviter is current profile
        Button accept= (Button)findViewById(R.id.btn_accept);
        Button reject= (Button)findViewById(R.id.btn_reject);
        Button view_dates_profile= (Button)findViewById(R.id.btn_view_dates_profile);//
        Button calendar_btn= (Button) findViewById(R.id.btn_add_cal);
        TextView accepted_message= (TextView)findViewById(R.id.accepted_msg);

        //TODO: first check if date status is accepted
        boolean hide_accept_reject=true;
        boolean accepted= true;
        if(accepted){
            //if date is accepted, regardless of who is invitee or inviter,
            // we want to display calendar event and viewother profile
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);
            calendar_btn.setVisibility(calendar_btn.VISIBLE);
            accepted_message.setVisibility(accepted_message.VISIBLE);

            //make gone
            accept.setVisibility(accept.GONE);
            reject.setVisibility(reject.GONE);

        }else if(hide_accept_reject){
            //make gone
            accept.setVisibility(accept.GONE);
            reject.setVisibility(reject.GONE);
            calendar_btn.setVisibility(calendar_btn.GONE);
            accepted_message.setVisibility(accepted_message.GONE);

            //make visible
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);
        }else{
            //make visible
            accept.setVisibility(accept.VISIBLE);
            reject.setVisibility(reject.VISIBLE);
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);
            accepted_message.setVisibility(accepted_message.GONE);

            //make gone
            calendar_btn.setVisibility(calendar_btn.GONE);



        }
    }

    protected void handle_accept(View v){
        //TODO: database entry for accepted date
        this.determine_visibility();




    }

    protected void handle_reject(View v){
        //TODO: database entry for rejected date
        //TODO: dialog pop up for reject?
        //TODO:
        //delete date invite
        //return to dates screen
        Intent dates= new Intent(ViewDate.this, Dates.class);
        dates.putExtra("email", email);

      //  startActivity(dates);

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
                        //TODO: CANCEL DATE
                        Intent to_dates=new Intent(ViewDate.this, Dates.class);
                        to_dates.putExtra("email", email);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //DO NOTHING HERE
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    protected void handle_view_dates_profile(View v){
        String inviter_id = "";
        String invitee_id = "";
        if(email.charAt(0)==date_id.charAt(0)){
            inviter_id = email;
            invitee_id = email.substring(email.length());
        }else {
            invitee_id = email;
            inviter_id = email.substring(email.length());
        }
        //go to view match but pass in extra to identify where we came from
        Intent viewmatch= new Intent(ViewDate.this, ViewMatch.class);
        //TODO: also need to put matches id by querying date id and getting the other id
         String other_id= this.email.equals(inviter_id)? invitee_id: inviter_id;

        viewmatch.putExtra("other_id", other_id);
        viewmatch.putExtra("came_from", "view_date");
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
// potentially pull from text when add event is clicked

    protected void handle_add_event(View v){
        TextView when_view = (TextView) findViewById(R.id.when);
       String[] date_time = when_view.getText().toString().split(",");
       String date = date_time[0];
       String time = date_time[1].substring(1);
        TextView inviter_name_view = (TextView)findViewById(R.id.inviter_name);
        String inviter_name = inviter_name_view.getText().toString();


        TextView invitee_name_view = (TextView)findViewById(R.id.invitee_name);
        String invitee_name = invitee_name_view.getText().toString();
        TextView message_view = (TextView)findViewById(R.id.date_message);
        String message = message_view.getText().toString();
        TextView where_view = (TextView)findViewById(R.id.where);
        String location = where_view.getText().toString();
        // hacky stuff to get ids
        // first email is inviter on date_id, second email is invitee on date_id
        // email could be either depending on if inviter and invitee
        String inviter_id = "";
        String invitee_id = "";
        if(email.charAt(0)==date_id.charAt(0)){
            inviter_id = email + "@gmail.com";
            invitee_id = date_id.substring(email.length()) + "@gmail.com";
            System.out.println("inviter_id:" + inviter_id +":");
            System.out.println("invitee_id"+invitee_id+":");
        }else {
            invitee_id = email + "@gmail.com";
            inviter_id = date_id.substring(email.length()) + "@gmail.com";
            System.out.println("inviter_id:" + inviter_id +":");
            System.out.println("invitee_id"+invitee_id+":");
        }
//        System.out.println("date:" + date + ":");
//        System.out.println("time:" + time + ":");
        Calendar beginTime = create_start(date, time);
        Calendar endTime= create_end(date, time);

       // long temp= beginTime.getTimeInMillis();

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Blind Date: "+inviter_name+" and "+invitee_name)
                .putExtra(CalendarContract.Events.DESCRIPTION, message)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(Intent.EXTRA_EMAIL, inviter_id+","+invitee_id);
        startActivity(intent);
    }

    public void setExtras(String id){
        // System.out.println("ID is: "+  id);
        final String final_id = id;
        reff = FirebaseDatabase.getInstance().getReference().child("Dates");
        reff.child(id).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey().toString().equals(final_id)){
                    System.out.println("here");
                    String all_vals = dataSnapshot.getValue().toString();

                    String[] vals = all_vals.split(",");
                    setTime(vals[4].substring(vals[4].indexOf("=") + 1), vals[0].substring(vals[0].indexOf("=") + 1));

                    setLocation(vals[3].substring(vals[3].indexOf("=") + 1));
                    setMessage(vals[6].substring(vals[6].indexOf("=")+1));
                    setNames(vals[5].substring(vals[5].indexOf("=") + 1), vals[1].substring(vals[1].indexOf("=") + 1));

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void setTime(String time, String date){
        TextView when_view = (TextView) findViewById(R.id.when);
        when_view.setText(date + ", " + time);

    }

    public void setLocation(String location){

        TextView where_view = (TextView)findViewById(R.id.where);
        where_view.setText(location);

    }

    public void setMessage(String message){
        TextView message_view = (TextView)findViewById(R.id.date_message);
        message_view.setText(message);
    }

    public void setNames(String invitee, String inviter){
        TextView inviter_name_view = (TextView)findViewById(R.id.inviter_name);
        inviter_name_view.setText(inviter);


        TextView invitee_name_view = (TextView)findViewById(R.id.invitee_name);
        invitee_name_view.setText(invitee);
    }


}
