package com.example.googlesignin;

import android.content.Intent;
import android.service.autofill.FieldClassification;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Matches extends AppCompatActivity {
    String user;

    DatabaseReference reff;
    LinearLayout matches_container;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        email= getIntent().getStringExtra("email");

       matches_container = (LinearLayout) findViewById(R.id.matches_container);



        // options_container= (LinearLayout) findViewById(R.id.options_container);
        try {
            user = getIntent().getExtras().getString("email").substring(0, getIntent().getExtras().getString("email").indexOf('@'));
        }catch(Exception e){
            user = getIntent().getExtras().getString("email");
        }
        show_matches(user);

        Log.v("MYTAG", "trying to add layout in oncreate  in matches");

    }

    ////////////////////////////
    //Navigation button handlers
    ////////////////////////////

    /**name: handle_back_btn
     * action: takes to previous page
     * */
    protected void handle_back_btn(View v){

        Intent to_home=new Intent(Matches.this, Home.class);
        to_home.putExtra("email", email);
        startActivity(to_home);
    }
    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){
        Intent to_home=new Intent(Matches.this, Home.class);
        to_home.putExtra("email", email);
        startActivity(to_home);
    }

    /**name: handle_view_profile
     * action: takes to view match page
     * */
    protected void handle_view_profile(String id){

        // take to veiew profile -- how to make sure the extra is not email, but is matches id

        Intent view_profile= new Intent(Matches.this, ViewMatch.class );
        view_profile.putExtra("other_id", ""+id);

        view_profile.putExtra("email", user);
        view_profile.putExtra("came_from", "matches");

        startActivity(view_profile);

    }

    /**name: handle_date
     * action: takes to view match page
     * */
    protected void handle_date(String id){
        Log.v("MYTAG", "date id: "+id);
        Intent to_date_input= new Intent(Matches.this, DateInput.class);
        to_date_input.putExtra("from", "Matches");
        to_date_input.putExtra("other_id", ""+id);
        to_date_input.putExtra("email",email);
        startActivity(to_date_input);
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


    public void show_matches(String user_to_find){
        final String final_user = user_to_find;
        reff = FirebaseDatabase.getInstance().getReference().child("Matches");
        reff.child(user).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // passes match list to then get infro from profile to build views of list. System.out.println("match list: " + dataSnapshot.getValue().toString());
                try{
                    getProfile(dataSnapshot.getValue().toString());
                }catch(Exception e){
                    Toast.makeText(Matches.this, "No matches yet!", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void setOption(String id, String name, String age, String score){
        matches_container.addView(this.create_match(id, name, Integer.parseInt(age), Integer.parseInt(score)));
    }





    public void getProfile(String match_list){
        final String final_match_list = match_list;
        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        reff.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // System.out.println(dataSnapshot.getValue().toString());
                if(final_match_list.contains(dataSnapshot.getKey())){
                    String all_vals = dataSnapshot.getValue().toString();
                    System.out.println("all vals string: "+all_vals);

                    String[] all_vals_array = all_vals.split(",");

                    for(int i=0;i<all_vals_array.length;i++){
                        System.out.println("i:"+i+" val: "+ all_vals_array[i]);
                    }
                    String id = all_vals_array[12].substring(all_vals_array[12].indexOf("=") + 1);
                    String name = all_vals_array[10].substring(all_vals_array[10].indexOf("=") + 1);
                    String age = all_vals_array[13].substring(all_vals_array[13].indexOf("=") + 1);
                    setOption(id, name, age,"100");
                }
            }

            @Override
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





}
