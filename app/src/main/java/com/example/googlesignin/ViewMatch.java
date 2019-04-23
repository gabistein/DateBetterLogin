package com.example.googlesignin;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class ViewMatch extends AppCompatActivity {

    String other_id;

    DatabaseReference reff;
    String values;
    String[] value_array;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);
        email=getIntent().getStringExtra("email");

        //testing if info is being passed
        other_id= getIntent().getStringExtra("other_id");
        email = getIntent().getStringExtra("email");
        populate_profile();
        Log.v("MYTAG", other_id);
    }



    protected void handle_back_btn(View v){
        if(getIntent().getStringExtra("came_from").equals("options")){
            System.out.println("Current id going back from viewing options" + email);
            Intent back = new Intent(ViewMatch.this, Options.class);
            back.putExtra("email", email);
            startActivity(back);
        }else if(getIntent().getStringExtra("came_from").equals("matches")) {
            Intent back = new Intent(ViewMatch.this, Matches.class);
            back.putExtra("email", email);
            startActivity(back);

        }else if(getIntent().getStringExtra("came_from").equals("view_date")){

            Intent back = new Intent(ViewMatch.this, ViewDate.class);
            back.putExtra("email", email);
            startActivity(back);
        }

    }

    protected void handle_home(View v){
        Intent to_home=new Intent(ViewMatch.this, Home.class);
        to_home.putExtra("email",email);
        startActivity(to_home);
    }


    protected void populate_profile(){
        System.out.println("called");
        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        reff.child(other_id).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    values = dataSnapshot.getValue().toString();
                    System.out.println(values);
                    value_array = values.split(",");

                    // Set name
                    setValue(10, R.id.profile_name);
                    String name = value_array[10].substring(value_array[10].indexOf("=") + 1);
                    String age = value_array[13].substring(value_array[13].indexOf("=")+1);
                    TextView nameText = (TextView) findViewById(R.id.profile_name);
                    nameText.setText(name + ", " + age);

                    // Set Bio
                    setValue(4, R.id.bio_val);

                    // Set Identifies as
                    setValue(3, R.id.g_val);

                    // Set interested in
                    setValue(0, R.id.o_val);

                    // Set star sign
                    setValue(7, R.id.ss_val);


                    // Set MB
                    setValue(8, R.id.mb_val);


                    // Set pet
                    String pet = value_array[value_array.length-1].substring((value_array[value_array.length-1]).indexOf("=") + 1, (value_array[value_array.length-1]).indexOf(("}")));
                    TextView pet_text = (TextView) findViewById(R.id.pet_val);
                    pet_text.setText(pet);

                    // set drinking
                    setValue(2, R.id.drink_val);

                    //set smoking
                    setValue(9, R.id.smoke_val);

                    // set politics
                    setValue(1, R.id.politics_val);
                    //set earth
                    setValue(6,R.id.earth_val);

                    //set farmer
                    setValue(11, R.id.farmer_val);
                    // set night in/out
                    setValue(5, R.id.night_val);

                }catch(Exception e){
                    System.out.println("hit a null");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void setValue(int i, int text_view_id){
        String name = value_array[i].substring(value_array[i].indexOf("=") + 1);
        TextView nameText = (TextView) findViewById(text_view_id);
        nameText.setText(name);

    }

}