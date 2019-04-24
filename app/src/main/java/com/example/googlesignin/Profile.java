package com.example.googlesignin;
import java.util.Arrays;
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
    //email_id for purposes persisting state in other classes
    String email_id;
    DatabaseReference reff;
    String values;
    String[] value_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = getIntent().getExtras().getString("email");
        email_id= getIntent().getExtras().getString("email");
        if(email.contains("@")){
            email = email.substring(0,email.indexOf('@'));
        }

        populate_profile();

//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                populate_profile();
//            }
//        }, 2000);

    }

    protected void populate_profile(){
        System.out.println("called");
        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        reff.child(email).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               try {
                   /**new key for values
                    *age:0
                    * bio:1
                    * drinking:2
                    * earth_is:3
                    * farmer:4
                    * gender:5
                    * id:6
                    * interested_in:7
                    * mb_type:8
                    * name:9
                    * night_in:10
                    * pet:11
                    * politics:12
                    * smoking:13
                    * star_sign:14
                    * */
                   values = dataSnapshot.getValue().toString();
                   //this step removes the surrounding brackets
                   values=values.substring(1, values.length()-1);

                   value_array=string_to_sort(values);

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
        //query based on user

        //get and set image

        //get all properties,
        //loop and create text views for each
        //add all to about view

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

    protected void handle_edit_profile(View v){
        Intent to_edit = new Intent(Profile.this, EditProfile.class);
        to_edit.putExtra("email", email_id);
        startActivity(to_edit);

    }

    protected void handle_home(View v){
        Intent to_home=new Intent(Profile.this, Home.class);
        to_home.putExtra("email", email_id);
        startActivity(to_home);
    }

    protected void handle_back_btn(View v){
        Intent to_home= new Intent(Profile.this, Home.class);
        to_home.putExtra("email", email_id);
        startActivity(to_home);
    }


    public void setValue(int i, int text_view_id){
        String name = value_array[i].substring(value_array[i].indexOf("=") + 1);
        TextView nameText = (TextView) findViewById(text_view_id);
        nameText.setText(name);

    }
}


