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

public class EditProfile extends AppCompatActivity {

    public Traits currentProfile;
    DatabaseReference reff;
    String signInId;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // figure out how to get actual sign in id
//        signInId = getIntent().getExtras().getString("email");
//        System.out.println("Sign In ID: " + signInId);
//        currentProfile = new Traits(signInId);

        //set bio_val edittext to allow for done button
        EditText bio_val= (EditText) findViewById(R.id.bio_val);
        bio_val.setLines(4);
        bio_val.setHorizontallyScrolling(false);
        bio_val.setImeActionLabel(getString(R.string.bio_done), 0);

    }

    protected void handle_save(View v) {
          currentProfile.setId(signInId);
          TextView nameText = (TextView)findViewById(R.id.profile_name);
          currentProfile.setName(nameText.getText().toString());
          TextView bioText = (TextView)findViewById(R.id.bio_val);
          currentProfile.setBio(bioText.getText().toString());
          currentProfile.setStar_sign(getSpinnerText(R.id.ss_val));
          currentProfile.setMb_type(getSpinnerText(R.id.mb_val));
          currentProfile.setPet(getSpinnerText(R.id.pet_val));
          currentProfile.setSmoking(getSpinnerText(R.id.smoke_val));
          currentProfile.setDrinking(getSpinnerText(R.id.drink_val));
          currentProfile.setPolitics(getSpinnerText(R.id.politics_val));
          key = signInId.substring(0,signInId.indexOf('@'));


          reff = FirebaseDatabase.getInstance().getReference().child("Profile");
          reff.orderByChild("id").equalTo(signInId).addValueEventListener(new ValueEventListener() {

              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    updateUserProfile(reff.child(key), currentProfile.getBio(), currentProfile.getDrinking(),currentProfile.getFarmer(),currentProfile.getId(), currentProfile.getMb_type(),currentProfile.getName(), currentProfile.getNight_in(),currentProfile.getPet(), currentProfile.getPolitics(),currentProfile.getSmoking(),currentProfile.getStar_sign() );

//                  if(dataSnapshot.exists()){
//                      System.out.println("no new user needed, need to update existing prfile");
//                      updateUserProfile(reff.child(key), currentProfile.getBio(), currentProfile.getDrinking(),currentProfile.getFarmer(),currentProfile.getId(), currentProfile.getMb_type(),currentProfile.getName(), currentProfile.getNight_in(),currentProfile.getPet(), currentProfile.getPolitics(),currentProfile.getSmoking(),currentProfile.getStar_sign() );
//                  }else{
//                      System.out.println("new user created");
//                      reff.child(key).setValue(currentProfile);
//                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });
//          Query queryForUser = reff.orderByChild("id").equalTo(signInId);
//          if(queryForUser == null) {
//              System.out.println("new user created");
//              reff.push().setValue(currentProfile);
//          }else {
//              System.out.println("no new user created");
//          }
          startActivity(new Intent(EditProfile.this, Profile.class));
    }


    /*Name: populate
     * action:
     *  queries database for all profile information for user
     *  sets the values of each trait spinner to those values, or "none" if not completed yet
     *
     *
     **/

    protected void populate(){


    }

    public String getSpinnerText(int id){
        Spinner mySpinner = (Spinner) findViewById(id);
        String text = mySpinner.getSelectedItem().toString();
        return text;
    }

    public void updateUserProfile(DatabaseReference db, String bio, String drinking, String farmer, String id, String mb_tpe, String name, String night_in, String pet, String politics, String smoking, String star_sign){
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
    }


    ////////////////////////////
    //Navigation button handlers
    ////////////////////////////

    /**name: handle_back_btn
     * action: takes to previous page
     * */
    protected void handle_back_btn(View v){
        startActivity(new Intent(EditProfile.this, Profile.class));
    }

    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){
        startActivity(new Intent(EditProfile.this, Home.class));
    }
}