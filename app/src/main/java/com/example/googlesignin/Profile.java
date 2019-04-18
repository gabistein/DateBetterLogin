package com.example.googlesignin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Profile extends AppCompatActivity {
    String email;
    DatabaseReference reff;
    String values;
    String[] value_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = getIntent().getExtras().getString("email");
        if(email.contains("@")){
            email = email.substring(0,email.indexOf('@'));
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                populate_profile();
            }
        }, 2000);

    }

    protected void populate_profile(){
        System.out.println("called");
        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        reff.child(email).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               try {
                   values = dataSnapshot.getValue().toString();
                   System.out.println(values);
                   value_array = values.split(",");

                   // Set name -- not updating after edit profile?
                   setValue(5, R.id.profile_name);


                   // Set Bio
                   setValue(6, R.id.bio_val);


                   // Set star sign
                   setValue(1, R.id.ss_val);


                   // Set MB
                   setValue(3, R.id.mb_val);


                   // Set pet
                   String pet = value_array[11].substring((value_array[11]).indexOf("=") + 1, (value_array[11]).indexOf(("}")));
                   TextView pet_text = (TextView) findViewById(R.id.pet_val);
                   pet_text.setText(pet);

                   // set drinking
                   setValue(2, R.id.drink_val);

                   //set smoking
                   setValue(4, R.id.smoke_val);

                   // set politics
                   setValue(0, R.id.politics_val);
               }catch(Exception e){
                   System.out.println("hit a null");
               }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //query based on user

        //get and set image

        //get all properties,
        //loop and create text views for each
        //add all to about view

    }

    protected void handle_edit_profile(View v){
        Intent to_edit = new Intent(Profile.this, EditProfile.class);
        to_edit.putExtra("email", email);
        startActivity(to_edit);

    }

    protected void handle_home(View v){
        startActivity(new Intent(Profile.this, Home.class));
    }

    protected void handle_back_btn(View v){
        startActivity(new Intent(Profile.this, Home.class));
    }


    public void setValue(int i, int text_view_id){
        String name = value_array[i].substring(value_array[i].indexOf("=") + 1);
        TextView nameText = (TextView) findViewById(text_view_id);
        nameText.setText(name);

    }
}


