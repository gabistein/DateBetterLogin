package com.example.googlesignin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // System.out.println(dataSnapshot.getValue().toString());
                String all_vals = dataSnapshot.getValue().toString();
               //  System.out.println(all_vals);
                if (all_vals.contains(email)) {
                    add_matches(all_vals);


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
        String[] values = to_add.split(",");
        // need to fix to make concatenate of ids
        matches_container.addView(this.create_date(stripExtra(values[5]), stripExtra(values[1]), stripExtraEnd(values[8]), (values[2].substring(values[2].indexOf("=") + 1))+(values[7].substring(values[7].indexOf("=") + 1))));
    }

    public String stripExtra(String extra){
        return extra.substring(extra.indexOf("=") + 1);
    }

    public String stripExtraEnd(String extra){
        extra = extra.substring(extra.indexOf("=") + 1);
        return extra.substring(0,extra.length()-1);
    }







}
