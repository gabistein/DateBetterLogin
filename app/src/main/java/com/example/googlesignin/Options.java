package com.example.googlesignin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Options extends AppCompatActivity {

    DatabaseReference reff;
    LinearLayout options_container;
    int options_num = 1;
    String currentList;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Set current like list of user


        // pull all options
        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        reff.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                parseValues(dataSnapshot.getValue().toString());
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


        options_container= (LinearLayout) findViewById(R.id.options_container);
        email = getIntent().getExtras().getString("email").substring(0,getIntent().getExtras().getString("email").indexOf('@'));


        Log.v("MYTAG", "trying to add layout in oncreate");
    }


    public void parseValues(String values){
        String[] traits = values.split(",");
        // need to figure out which has id
//        for(int i = 0; i< traits.length; i++){
//
//        }

        String id = traits[8].substring(traits[8].indexOf("=") + 1);
        if(id.contains("@")){
            id = id.substring(0,id.indexOf("@"));
        }


        String name = traits[5].substring(traits[5].indexOf("=") + 1);
        System.out.println(name);
        // still adding age
        // still need to add score
        // only list if they haven't liked
        options_container.addView(this.create_option(id,name, 22));
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
        view_profile.putExtra("email", email);
        startActivity(view_profile);
    }


    /**name: handle_like
     * action: adds a like to the database for id to id
     * */
    protected void handle_like(String id){
        //TODO: insert like into like table
        // add in like and update list of ids, you've liked
        final String to_add = id;
        reff = FirebaseDatabase.getInstance().getReference().child("Likes");
        reff.child(email).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    reff.child(email).setValue(to_add);
                }else{
                    String current_like_List = dataSnapshot.getValue().toString();
                    if(!current_like_List.contains(to_add)){
                        reff.child(email).setValue(current_like_List + " " + to_add);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reff.child(to_add).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    System.out.println("No match");
                }else{
                    String current_like_List = dataSnapshot.getValue().toString();
                    if(current_like_List.contains(email)){
                        Toast.makeText(getApplicationContext(),"New Match!",Toast.LENGTH_SHORT).show();
                        addMatch(email, to_add);
                        addMatch(to_add, email);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){
        Intent to_home=new Intent(Options.this, Home.class);
        to_home.putExtra("email", email);
        startActivity(to_home);

    }

    /**name: handle_home
     * action: takes to previous page
     * */
    protected void handle_back_btn(View v){
        Intent to_home=new Intent(Options.this, Home.class);
        to_home.putExtra("email", email);
        startActivity(to_home);
    }




    ///////////////////////////
    //////Helpers
    ///////////////////////////


    /**name: create_option
     * action: creates a profile option object
     * */

    protected ProfileOption create_option(String id, String name, int age){
        ProfileOption option= new ProfileOption(this);
        option.set_all(name, age);
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

    public void addMatch(String key,  String add_to_match_list){
        reff = FirebaseDatabase.getInstance().getReference().child("Matches");
        final String key_final = key;
        final String add_final = add_to_match_list;
        reff.child(key).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    reff.child(key_final).setValue(add_final);
                }else{
                    String current_match_List = dataSnapshot.getValue().toString();
                    if(!current_match_List.contains(add_final)){
                        reff.child(key_final).setValue(current_match_List + " " + add_final);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

