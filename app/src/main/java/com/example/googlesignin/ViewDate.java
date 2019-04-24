package com.example.googlesignin;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

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
        determine_visibility();


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
        // get status from db
        final String final_id = date_id;

        reff = FirebaseDatabase.getInstance().getReference().child("Dates");
        reff.child(date_id).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey().equals(final_id)){
                    String all_vals = dataSnapshot.getValue().toString();
                    if(all_vals.contains("accept")){
                        // change here
                        changeAccepted();
                    }else if(all_vals.contains("reject")){
                        // change here
                        changeRejected();
                    }else{
                        // change here for pending
                        changePending();
                    }


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void changeAccepted() {
        Button accept = (Button) findViewById(R.id.btn_accept);
        Button reject = (Button) findViewById(R.id.btn_reject);
        Button view_dates_profile = (Button) findViewById(R.id.btn_view_dates_profile);//
        Button calendar_btn = (Button) findViewById(R.id.btn_add_cal);

        //TODO: first check if date status is accepted

            //if date is accepted, regardless of who is invitee or inviter,
            // we want to display calendar event and viewother profile
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);
            calendar_btn.setVisibility(calendar_btn.VISIBLE);
            accept.setVisibility(accept.GONE);
            reject.setVisibility(reject.GONE);


    }

    private void changePending(){
        Button accept = (Button) findViewById(R.id.btn_accept);
        Button reject = (Button) findViewById(R.id.btn_reject);
        Button view_dates_profile = (Button) findViewById(R.id.btn_view_dates_profile);//
        Button calendar_btn = (Button) findViewById(R.id.btn_add_cal);


      if(email.equals(date_id.substring(0, email.length()))) {
            //if you are the inviter
            //make gone: accept, reject, calendar button
            accept.setVisibility(accept.GONE);
            reject.setVisibility(reject.GONE);
            calendar_btn.setVisibility(calendar_btn.GONE);


            //make visible
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);

        }else{

            accept.setVisibility(accept.VISIBLE);
            reject.setVisibility(reject.VISIBLE);
            view_dates_profile.setVisibility(view_dates_profile.VISIBLE);
            calendar_btn.setVisibility(calendar_btn.GONE);

        }

    }


    private void changeRejected() {

        Button accept = (Button) findViewById(R.id.btn_accept);
        Button reject = (Button) findViewById(R.id.btn_reject);
        Button view_dates_profile = (Button) findViewById(R.id.btn_view_dates_profile);//
        Button calendar_btn = (Button) findViewById(R.id.btn_add_cal);


        view_dates_profile.setVisibility(view_dates_profile.VISIBLE);


        //make gone: accept, reject, calendar
        accept.setVisibility(accept.GONE);
        reject.setVisibility(reject.GONE);
        calendar_btn.setVisibility(calendar_btn.GONE);
    }

    protected void handle_accept(View v){
        final String final_id = date_id;
        reff = FirebaseDatabase.getInstance().getReference().child("Dates");
        reff.child(final_id).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey().toString().equals(final_id)){
                    updateStatusAccept(reff.child(final_id));

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        determine_visibility();

    }
    public void updateStatusAccept(DatabaseReference db){
        db.child("status").setValue("accept"); //id

    }
    public void updateStatusReject(DatabaseReference db){
        db.child("status").setValue("reject"); //id


    }

    protected void handle_reject(View v){
        //TODO: database entry for rejected date, update status to reject
        //TODO: determine visibility to say at bottom has been rejected
        final String final_id = date_id;
        reff = FirebaseDatabase.getInstance().getReference().child("Dates");
        reff.child(final_id).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey().toString().equals(final_id)){
                    updateStatusReject(reff.child(final_id));

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //delete date invite
        //return to dates screen
        Intent dates= new Intent(ViewDate.this, Dates.class);
        dates.putExtra("email", email);

      //  startActivity(dates);

    }



    protected void handle_view_dates_profile(View v){
        String inviter_id = "";
        String invitee_id = "";
        if(email.equals(date_id.substring(0, email.length()))){
            inviter_id = email;
            invitee_id = date_id.substring(email.length());
        }else {

            invitee_id = email;
            int difference = date_id.length() - email.length();
            inviter_id = date_id.substring(0,difference);

        }
        System.out.println("in view date profile, invitee id: " + invitee_id);
        System.out.println("in view date profile, inviter id: " + inviter_id);
        //go to view match but pass in extra to identify where we came from
        Intent viewmatch= new Intent(ViewDate.this, ViewMatch.class);
        //TODO: also need to put matches id by querying date id and getting the other id
        // get this to work for both
         String other_id= this.email.equals(inviter_id)? invitee_id: inviter_id;
            // System.out.println("other id in view date " + other_id );
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
        if(email.equals(date_id.substring(0, email.length()))){
            inviter_id = email + "@gmail.com";
            invitee_id = date_id.substring(email.length()) + "@gmail.com";

        }else {

            int difference = date_id.length() - email.length();
            inviter_id = date_id.substring(0,difference) + "gmail.com";
            invitee_id = email + "@gmail.com";


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

    //HELPER to clean array
    private String[] string_to_sort(String s){
        if(s.startsWith("{")){
            s=s.substring(1);
        }

        if(s.endsWith("}")){
            s=s.substring(0, s.length()-1);
        }

        String [] arr=s.split(",");
        for(int i=0;i<arr.length;i++){
            arr[i]=arr[i].trim();
        }
        Arrays.sort(arr);
        System.out.println("Values: "+s);
        System.out.println("SORTED ARRAY VALS:");
        for(int i=0;i<arr.length;i++){
            System.out.println("i: "+i+" value: "+arr[i]);
        }
        return arr;
    }


    public void setExtras(String id){
        // System.out.println("ID is: "+  id);
        final String final_id = id;
        reff = FirebaseDatabase.getInstance().getReference().child("Dates");
        reff.child(id).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //TODO: date order of values
                //TODO: set the pending status at top
                if(dataSnapshot.getKey().toString().equals(final_id)){

                    String all_vals = dataSnapshot.getValue().toString();
                    System.out.println("all_vals for date: "+all_vals);
                    String[] vals = string_to_sort(all_vals);
                    /**key for date vals
                     * date: 0
                     * invitee_id:1
                     * invitee_name:2
                     * inviter_id:3
                     * inviter_name:4
                     * location:5
                     * message:6
                     * status:7
                     * time:8
                     * */


                    for(int i=0;i<vals.length;i++){
                        System.out.println("i: "+i+" val: "+vals[i]);
                    }
                    //TODO: change order
                    //time, date
                    setTime(vals[8].substring(vals[8].indexOf("=") + 1), vals[0].substring(vals[0].indexOf("=") + 1));
                    setLocation(vals[5].substring(vals[5].indexOf("=") + 1));
                    setMessage(vals[6].substring(vals[6].indexOf("=")+1));
                    //invitee, inviter
                    setNames(vals[2].substring(vals[2].indexOf("=") + 1), vals[4].substring(vals[4].indexOf("=") + 1));
                    //set status:
                    setStatus(vals[7].substring(vals[7].indexOf("=")+1));

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

    public void setStatus(String status){
        TextView txt_status= (TextView) findViewById(R.id.txt_status);
        txt_status.setText(status);

    }
    protected void handle_home(View v){
        Intent to_home=new Intent(ViewDate.this, Home.class);
        to_home.putExtra("email", email);
        startActivity(to_home);
    }

    protected void handle_back(View v){
        Intent to_dates= new Intent(ViewDate.this, Dates.class);
        to_dates.putExtra("email", email);
        startActivity(to_dates);
    }



}
