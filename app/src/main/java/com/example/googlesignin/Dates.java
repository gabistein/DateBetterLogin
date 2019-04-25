package com.example.googlesignin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;


public class Dates extends AppCompatActivity {

    private String email;
    DatabaseReference reff;
    LinearLayout matches_container;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);

        email=getIntent().getStringExtra("email");
        if(email.contains("@")){
            email = email.substring(0, email.indexOf("@"));
        }

        matches_container= (LinearLayout) findViewById(R.id.dates_container);
        show_dates(email);




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
    protected DateTile create_date(String inviter_name, String invitee_name, String status, String date_id){
        if(status.equals("accept")){
            status="Accepted";
        }else if(status.equals("reject")){
            status="Rejected";
        }else{
            status="Pending";
        }
        DateTile date_tile= new DateTile(this);
        //TODO: ideally set name instead of id but need
        date_tile.set_all(invitee_name,inviter_name, status);
        final String f_date_id=date_id;

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
    protected void handle_view_date( String date_id){

        Intent view_date= new Intent(Dates.this, ViewDate.class );
        view_date.putExtra("email",email );
        view_date.putExtra("date_id", date_id);

        startActivity(view_date);
    }

    public void show_dates(String user_to_find){
        reff = FirebaseDatabase.getInstance().getReference().child("Dates");
        reff.orderByKey().addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // System.out.println(dataSnapshot.getValue().toString());
                String all_vals = dataSnapshot.getValue().toString();
               //  System.out.println(all_vals);
                if (all_vals.contains(email)) {

                    String[] find_date = string_to_sort(all_vals);
                    // String date = find_date[0];


                    String date_only = find_date[0];
                    int month;
                    String month_string = "";
                    int day;
                    String day_string = "";
                    int year;

                    String[] date_numbers = date_only.split("/");
                    month = Integer.parseInt(date_numbers[0].substring(date_numbers[0].indexOf("=")+1));
                    day = Integer.parseInt(date_numbers[1]);
                    year = Integer.parseInt(date_numbers[2]);
                    if(month < 10){
                       month_string = "0" + month;
                       month = Integer.parseInt(month_string);
                    }else{
                        month_string = Integer.toString(month);
                    }
                    if(day<10){
                        day_string = "0" + day;
                        day = Integer.parseInt(day_string);
                    }else{
                        day_string = Integer.toString(day);
                    }
                    String whole_date = year + "-" + month_string + "-" + day_string;
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate now = LocalDate.now();
                    dtf.format(now);
                    LocalDate check = LocalDate.parse(whole_date, dtf);
                    if(check.isBefore(now)){
                        dataSnapshot.getRef().removeValue();
                    }else {
                        add_matches(all_vals);

                    }




                }
            }
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void add_matches(String to_add){
        String[] values = string_to_sort(to_add);
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

        //create_date(String inviter_name, String invitee_name, String status, String date_id)
        String date= stripExtra(values[0]);
        String inviter_name= stripExtra(values[4]);
        String invitee_name=stripExtra(values[2]);
        String status= stripExtra(values[7]);
        String inviter_id= stripExtra(values[3]);
        String invitee_id=stripExtra(values[1]);
        String date_id= inviter_id+invitee_id;


        // need to fix to make concatenate of ids
        matches_container.addView(this.create_date(inviter_name, invitee_name, status, date_id));
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

    public String stripExtra(String extra){
        return extra.substring(extra.indexOf("=") + 1);
    }

    public String stripExtraEnd(String extra){
        extra = extra.substring(extra.indexOf("=") + 1);
        return extra.substring(0,extra.length()-1);
    }







}
