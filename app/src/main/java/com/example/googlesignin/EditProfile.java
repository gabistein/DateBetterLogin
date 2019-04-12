package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class EditProfile extends AppCompatActivity {

    public Traits currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // figure out how to get actual sign in id
        String signInId = "";

        Traits currentProfile = new Traits(signInId);

        //set bio_val edittext to allow for done button
        EditText bio_val= (EditText) findViewById(R.id.bio_val);
        bio_val.setLines(4);
        bio_val.setHorizontallyScrolling(false);
        bio_val.setImeActionLabel(getString(R.string.bio_done), 0);

    }

    protected void handle_save(View v) {
        // assuming new profile
//        this.id = id;
//        this.name = "";
//        this.bio = "";
//        this.star_sign = "";
//        this.mb_type = "";
//        this.pet = "";
//        this.drinking = false;
//        this.smoking = false;
//        this.politics = "";
//        this.farmer = false;
//        this. night_in = false;
          TextView nameText = (TextView)findViewById(R.id.profile_name);
          TextView bioText = (TextView)findViewById(R.id.bio_val);
          currentProfile.setName(nameText.getText().toString());
          currentProfile.setBio(bioText.getText().toString());
          currentProfile.setDrinking(getSpinnerText(R.id.drink_val));
          currentProfile.setStar_sign(getSpinnerText(R.id.ss_val));
         //  currentProfile.setEarth_flat(getSpinnerText());
//        prof.addProfile("Dwight", "I like beets", "ENTJ", "Virgo", true, false);
//        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
//        reff.push().setValue(prof);
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
}