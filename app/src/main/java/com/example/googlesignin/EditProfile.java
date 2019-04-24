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
          // Set id for db
          if(signInId.contains("@")){
              currentProfile.setId(signInId.substring(0,signInId.indexOf("@")));
          }else{
              currentProfile.setId(signInId);
          }

          // issse with on save not grabbing value for name, age, and bio -- some sort of autocomplete?

          // Set name
          String currentName;
          TextView nameText = (TextView)findViewById(R.id.profile_name);
          if(nameText.getText().toString().equals("")){
              EditText hintText = (EditText)findViewById(R.id.profile_name);
              currentName = hintText.getHint().toString();
          }else{
              currentName = nameText.getText().toString();
          }
          // System.out.println("nameText is : "  + nameText.getText().toString());
          currentProfile.setName(currentName);

          // Set age
          String currentAge;
          TextView ageText = (TextView) findViewById(R.id.birthday);
          if(ageText.getText().toString().equals("")){
              EditText hintText = (EditText) findViewById(R.id.birthday);
              currentAge = hintText.getHint().toString();
          } else{
              currentAge = ageText.getText().toString();
          }
          currentProfile.setAge(currentAge);

          // Set bio
          String currentBio;
          TextView bioText = (TextView)findViewById(R.id.bio_val);
         if(bioText.getText().toString().equals("")){
             EditText hintText = (EditText) findViewById(R.id.bio_val);
             currentBio = hintText.getHint().toString();
         }else{
             currentBio = bioText.getText().toString();
         }
          currentProfile.setBio(currentBio);

          // Set identifies as
          currentProfile.setGender(getSpinnerText(R.id.g_val));

          // Set interested in
          currentProfile.setPreference(getSpinnerText(R.id.o_val));

          // Set star sign
          currentProfile.setStar_sign(getSpinnerText(R.id.ss_val));

          // Set MB
          currentProfile.setMb_type(getSpinnerText(R.id.mb_val));

          // Set pet
          currentProfile.setPet(getSpinnerText(R.id.pet_val));

          //Set drinking
          currentProfile.setDrinking(getSpinnerText(R.id.drink_val));

          //Set smoking
          currentProfile.setSmoking(getSpinnerText(R.id.smoke_val));

          //Set politics
          currentProfile.setPolitics(getSpinnerText(R.id.politics_val));

          //Set earth
          currentProfile.setEarth_flat(getSpinnerText(R.id.earth_val));

          // Set farmer
          currentProfile.setFarmer(getSpinnerText(R.id.farm_val));

          // Set night in/out
          currentProfile.setNight_in(getSpinnerText(R.id.night_val));
          if(signInId.contains("@")){
              key = signInId.substring(0,signInId.indexOf("@"));
          }else{
              key = signInId;
          }

          // System.out.println("KEY: " + key);

          reff = FirebaseDatabase.getInstance().getReference().child("Profile");
          reff.orderByChild("id").equalTo(signInId).addListenerForSingleValueEvent(new ValueEventListener() {

              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    updateUserProfile(reff.child(key),currentProfile.getGender(), currentProfile.getPreference(), currentProfile.getAge(), currentProfile.getBio(), currentProfile.getDrinking(),currentProfile.getFarmer(),currentProfile.getId(), currentProfile.getMb_type(),currentProfile.getName(), currentProfile.getNight_in(),currentProfile.getPet(), currentProfile.getPolitics(),currentProfile.getSmoking(),currentProfile.getStar_sign(), currentProfile.getEarth_flat() );
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
        if(email.contains("@")){

            email = email.substring(0,email.indexOf("@"));

        }
        reff.child(email).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    /**new key for values
                    * age:0
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
                    String values = dataSnapshot.getValue().toString();
                    //this step removes the surrounding brackets
                    values=values.substring(1, values.length()-1);

                    value_array=string_to_sort(values);

                    //Set age: 0
                    setHint(0,R.id.birthday);

                    // Set Bio: 1
                    setHint(1, R.id.bio_val);

                    //set drinking: 2
                    setSpinner(2, R.array.drink_options, R.id.drink_val);

                    // set earth: 3
                    setSpinner(3, R.array.earth_options, R.id.earth_val);

                    //setfamer: 4
                    setSpinner(4, R.array.farm_options, R.id.farm_val);

                    // set gender/identify as: 5
                    setSpinner(5, R.array.identify_as, R.id.g_val);

                    //set interested in: 7
                    // set orientation
                    setSpinner(7, R.array.interested_in, R.id.o_val);

                    // set mb: 8
                    setSpinner(8, R.array.mb_options, R.id.mb_val);

                    // Set name: 9
                    setHint(9, R.id.profile_name);


                    //set night in or out: 10
                    setSpinner(10, R.array.night_options, R.id.night_val);


                    // Set pet: 11
                    setSpinner(11, R.array.pet_options, R.id.pet_val);

                    // set politicis: 12
                    setSpinner(12, R.array.politics_options, R.id.politics_val);

                    // set smoking: 13
                    setSpinner(13, R.array.smoke_options, R.id.smoke_val);

                    // set starsign: 14
                    setSpinner(14, R.array.star_options, R.id.ss_val);


                }catch(Exception e){
                    System.out.println("hit a null in edit");
                }
//



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


    public String getSpinnerText(int id){
        Spinner mySpinner = (Spinner) findViewById(id);
        String text = mySpinner.getSelectedItem().toString();
        return text;
    }

    public void updateUserProfile(DatabaseReference db, String gender, String orientation, String age, String bio, String drinking, String farmer, String id, String mb_tpe, String name, String night_in, String pet, String politics, String smoking, String star_sign, String earth){
        db.child("id").setValue(id); //id
        db.child("name").setValue(name); // name
        db.child("age").setValue(age); // age
        db.child("bio").setValue(bio); // bio
        db.child("gender").setValue(gender); // identifies as
        db.child("interested_in").setValue(orientation); // interested in
        db.child("star_sign").setValue(star_sign); // star sign
        db.child("mb_type").setValue(mb_tpe); // meyers briggs
        db.child("pet").setValue(pet); // pet
        db.child("drinking").setValue(drinking); // drinking
        db.child("smoking").setValue(smoking); // smoking
        db.child("politics").setValue(politics); // politics
        db.child("earth_is").setValue(earth); // earth
        db.child("farmer").setValue(farmer); // farmer
        db.child("night_in").setValue(night_in); // night in/out





    }

    public void setHint(int i, int text_view_id){
        String name = value_array[i].substring(value_array[i].indexOf("=") + 1);
        System.out.println("Set Hint: " + name);
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


    ////////////////////////////
    //Navigation button handlers
    ////////////////////////////

    /**name: handle_back_btn
     * action: takes to previous page
     * */
    protected void handle_back_btn(View v){
        Intent to_profile= new Intent(EditProfile.this, Profile.class);
        to_profile.putExtra("email", signInId);
        startActivity(to_profile);
    }

    /**name: handle_home
     * action: takes to home page
     * */
    protected void handle_home(View v){
        Intent to_home=new Intent(EditProfile.this, Home.class);
        to_home.putExtra("email", signInId);
        startActivity(to_home);
    }
}