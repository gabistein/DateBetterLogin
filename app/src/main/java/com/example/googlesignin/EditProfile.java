package com.example.googlesignin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Arrays;

public class EditProfile extends AppCompatActivity {

    public Traits currentProfile;
    DatabaseReference reff;
    String signInId;
    String key;
    String[] value_array;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // figure out how to get actual sign in id
        signInId = getIntent().getExtras().getString("email");
        System.out.println("Sign In ID: " + signInId);
        currentProfile = new Traits(signInId);
        email=  signInId;
        System.out.println("email:" + email);
        //set bio_val edittext to allow for done button
        EditText bio_val= (EditText) findViewById(R.id.bio_val);
        bio_val.setLines(4);
        bio_val.setHorizontallyScrolling(false);
        bio_val.setImeActionLabel(getString(R.string.bio_done), 0);
        populate();


    }

    protected void handle_save(View v) {
          currentProfile.setId(signInId);
          TextView nameText = (TextView)findViewById(R.id.profile_name);
          System.out.println("nameText is : "  + nameText.getText().toString());
          currentProfile.setName(nameText.getText().toString());
          TextView bioText = (TextView)findViewById(R.id.bio_val);
          currentProfile.setBio(bioText.getText().toString());
          TextView ageText = (TextView)findViewById(R.id.birthday);
//           currentProfile.setAge(Integer.parseInt(ageText.toString()));
          currentProfile.setStar_sign(getSpinnerText(R.id.ss_val));
          currentProfile.setMb_type(getSpinnerText(R.id.mb_val));
          currentProfile.setPet(getSpinnerText(R.id.pet_val));
          currentProfile.setSmoking(getSpinnerText(R.id.smoke_val));
          currentProfile.setDrinking(getSpinnerText(R.id.drink_val));
          currentProfile.setPolitics(getSpinnerText(R.id.politics_val));
          key = signInId;


          reff = FirebaseDatabase.getInstance().getReference().child("Profile");
          reff.orderByChild("id").equalTo(signInId).addListenerForSingleValueEvent(new ValueEventListener() {

              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    updateUserProfile(reff.child(key),0, currentProfile.getBio(), currentProfile.getDrinking(),currentProfile.getFarmer(),currentProfile.getId(), currentProfile.getMb_type(),currentProfile.getName(), currentProfile.getNight_in(),currentProfile.getPet(), currentProfile.getPolitics(),currentProfile.getSmoking(),currentProfile.getStar_sign() );
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });

          Intent to_edit = new Intent(EditProfile.this, Profile.class);
          to_edit.putExtra("email", signInId);
          startActivity(to_edit);
    }


    /*Name: populate
     * action:
     *  queries database for all profile information for user
     *  sets the values of each trait spinner to those values, or "none" if not completed yet
     *
     *
     **/

    protected void populate(){
        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        reff.child(email).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String values = dataSnapshot.getValue().toString();
                System.out.println(values);
                value_array = values.split(",");

                // Set name
                setHint(5,R.id.profile_name);


                // Set Bio
                setHint(6,R.id.bio_val);

                // how to set spinner value
                setSpinner(1,R.array.star_options,R.id.ss_val);

                // set mb
                setSpinner(3, R.array.mb_options, R.id.mb_val);


                //set drinking
                setSpinner(2, R.array.drink_options, R.id.drink_val);

//                // set smoking
               setSpinner(4,R.array.smoke_options,R.id.smoke_val);
//
//                // set politicis
               setSpinner(0,R.array.politics_options,R.id.politics_val);
//
////                // Set pet

                String[] pet_options = getResources().getStringArray(R.array.pet_options);
                Spinner spinner = (Spinner) findViewById(R.id.pet_val);
                spinner.setSelection(findIndex(pet_options,value_array[11].substring((value_array[11]).indexOf("=") +1, (value_array[11]).indexOf(("}")))));

//



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public String getSpinnerText(int id){
        Spinner mySpinner = (Spinner) findViewById(id);
        String text = mySpinner.getSelectedItem().toString();
        return text;
    }

    public void updateUserProfile(DatabaseReference db, int age, String bio, String drinking, String farmer, String id, String mb_tpe, String name, String night_in, String pet, String politics, String smoking, String star_sign){
        db.child("bio").setValue(bio);
        db.child("drinking").setValue(drinking);
        db.child("farmer").setValue(farmer);
        db.child("id").setValue(id);
        db.child("mb_type").setValue(mb_tpe);
        db.child("name").setValue(name);
        db.child("night_in").setValue(night_in);
        db.child("pet").setValue(pet);
        db.child("politics").setValue(politics);
        db.child("smoking").setValue(smoking);
        db.child("star_sign").setValue(star_sign);
        db.child("age").setValue(age);
    }

    public void setHint(int i, int text_view_id){
        String name = value_array[i].substring(value_array[i].indexOf("=") + 1);
        EditText nameText = (EditText) findViewById(text_view_id);
        nameText.setHint(name);

    }

    public void setSpinner(int i, int options_id, int spinner_id){
        String[] options = getResources().getStringArray(options_id);
        Spinner spinner = (Spinner) findViewById(spinner_id);
        spinner.setSelection(findIndex(options,value_array[i].substring(value_array[i].indexOf("=") + 1)));
    }
    // Linear-search function to find the index of an element
    public static int findIndex(String arr[], String t)
    {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i].equals(t)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
}