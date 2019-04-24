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

import java.util.Arrays;

public class ViewMatch extends AppCompatActivity {

    String other_id;

    DatabaseReference reff;
    String values;
    String[] value_array;
    String email;
    String came_from;
    String date_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);
        email=getIntent().getStringExtra("email");
        System.out.println("in match" + email);
        //testing if info is being passed
        other_id= getIntent().getStringExtra("other_id");
        email = getIntent().getStringExtra("email");
        came_from=getIntent().getStringExtra("came_from");
        date_id=getIntent().getStringExtra("date_id");
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

            System.out.println("came_from is view_date");
            Intent back = new Intent(ViewMatch.this, ViewDate.class);
            back.putExtra("email", email);
            back.putExtra("date_id", date_id);
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

                    //this step removes the surrounding brackets
                    values=values.substring(1, values.length()-1);

                    value_array = string_to_sort(values);

                    // Set name: 9
                    setValue(9, R.id.profile_name);
                    String name = value_array[9].substring(value_array[9].indexOf("=") + 1);

                    //Set age: 0
                    String age = value_array[0].substring(value_array[0].indexOf("=")+1);
                    TextView nameText = (TextView) findViewById(R.id.profile_name);
                    nameText.setText(name + ", " + age);


                    // Set Bio
                    setValue(1, R.id.bio_val);

                    // set drinking
                    setValue(2, R.id.drink_val);

                    //set earth
                    setValue(3,R.id.earth_val);

                    //set farmer
                    setValue(4, R.id.farmer_val);

                    // Set Identifies as
                    setValue(5, R.id.g_val);

                    // Set interested in
                    setValue(7, R.id.o_val);

                    // Set MB
                    setValue(8, R.id.mb_val);

                    // set night in/out
                    setValue(10, R.id.night_val);

                    // Set pet
                    setValue(11, R.id.pet_val);

                    // set politics
                    setValue(12, R.id.politics_val);

                    //set smoking
                    setValue(13, R.id.smoke_val);
                    // Set star sign
                    setValue(14, R.id.ss_val);

                }catch(Exception e){
                    System.out.println("hit a null");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

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



    public void setValue(int i, int text_view_id){
        String name = value_array[i].substring(value_array[i].indexOf("=") + 1);
        TextView nameText = (TextView) findViewById(text_view_id);
        nameText.setText(name);

    }

}