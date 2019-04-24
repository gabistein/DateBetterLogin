package com.example.googlesignin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class DateInput extends AppCompatActivity {

    protected EditText time_text, date_text, map_text, message_text;
    protected Button time_btn, date_btn;

    String email,from;
    public DateTraits dateDetails;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_input);

        // INTENT EXTRAS AFTER MATCHING

        email=getIntent().getStringExtra("email");
        if(email.contains("@")){
            email = email.substring(0, email.indexOf("@"));
        }
        from=getIntent().getStringExtra("other_id");
        if(from.contains("@")){
            from = from.substring(0, from.indexOf("@"));
        }


        //Time
        time_text= (EditText)findViewById(R.id.text_set_time);
        time_btn= (Button) findViewById(R.id.btn_set_time);
        time_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_time();
            }
        });


        //Date
        date_text= (EditText)findViewById(R.id.text_set_date);
        date_btn= (Button) findViewById(R.id.btn_set_date);
        date_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_date();
            }
        });




        //Map
        map_text= (EditText)findViewById(R.id.text_set_address);
        message_text = (EditText) findViewById(R.id.bio_val);


    }


    /**name: handle_date
     * action: when date_btn is pressed, dialog box pops up, date selection sets text box text
     * */
    protected void handle_date(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date_text.setText((monthOfYear + 1)+"/"+ dayOfMonth + "/" + year);

                    }
                }, year, month, day);
        datePickerDialog.show();

    }

    /**name: handle_date
     * action: when date_btn is pressed, dialog box pops up, date selection sets text box text
     * */
    protected void handle_time(){

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {



                        String format= hourOfDay>12? "pm" : "am";
                        hourOfDay= hourOfDay>12? hourOfDay-12: hourOfDay;
                        String s_hourOfDay=""+hourOfDay;
//                        if(hourOfDay<10){
//                            s_hourOfDay="0"+hourOfDay;
//                        }
                        String s_min= ""+minute;
                        if (minute<10){
                            s_min="0"+s_min;
                        }

                        time_text.setText(s_hourOfDay + ":" + s_min+format);
                    }
                }, hour, minute, false);

        timePickerDialog.show();



    }


    ////NAVIGAITON

    /**name: handle_invite
     * action: when date_btn is pressed, dialog box pops up, date selection sets text box text
     * */

    protected void handle_invite(View v){
        Intent intent = new Intent(DateInput.this, Matches.class);
        String date = date_text.getText().toString();
        String time = time_text.getText().toString();
        String message = message_text.getText().toString();
        String map = map_text.getText().toString();
        getProfileName(from, email);
        //TODO: save whatever to the db, put extras into db
        dateDetails = new DateTraits(email+from, email, "from_name", from, "email_name", date, time, map, message, "pending" );
        //TODO: depending on from go to mathes or dates

        reff = FirebaseDatabase.getInstance().getReference().child("Dates");
        reff.orderByChild("date_id").equalTo(email+from).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDateProfile(reff.child(email+from), dateDetails.getInviter_id(), dateDetails.getInviter_name(), dateDetails.getInvitee_id(), dateDetails.getInvitee_name(), dateDetails.getDate(), dateDetails.getTime(), dateDetails.getLocation(), dateDetails.getMessage(), dateDetails.getStatus() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // startActivity(intent);

    }

    protected void handle_back_btn(View v){
        if(from==null){
            //TODO: go HOME
        }else if(from.equals("Matches")){
            Intent to_matches=new Intent(DateInput.this, Matches.class);
            to_matches.putExtra("email", email);
            startActivity(to_matches);
        }else if(from.equals("ViewMatch")){
            //TODO: pass in View Date ID
            int date_id=getIntent().getIntExtra("date_id", 0);
            Intent viewdate=new Intent(DateInput.this,ViewMatch.class);
            viewdate.putExtra("date_id", date_id);
            viewdate.putExtra("email", email);
            startActivity(viewdate);
        }

    }

    protected void handle_home(View v){
        Intent to_home= new Intent(DateInput.this, Home.class);
        to_home.putExtra("email", email);
        startActivity(to_home);
    }
    public void updateDateProfile(DatabaseReference db, String inviter_id, String inviter_name, String invitee_id, String invitee_name, String date, String time, String location, String message, String status ){
        db.child("inviter_id").setValue(inviter_id); // identifies as
        db.child("inviter_name").setValue(dateDetails.getInviter_name()); // interested in
        db.child("invitee_id").setValue(invitee_id); // star sign
        db.child("invitee_name").setValue(dateDetails.getInvitee_name()); // meyers briggs
        db.child("date").setValue(date); // pet
        db.child("time").setValue(time); // drinking
        db.child("location").setValue(location); // smoking
        db.child("message").setValue(message); // politics
        db.child("status").setValue(status); // earth


    }

    public void getProfileName(String invitee_id, String inviter_id){
        final String final_invitee_id = invitee_id;
        final String final_inviter_id = inviter_id;

        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        reff.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // System.out.println(dataSnapshot.getValue().toString());
                String all_vals = dataSnapshot.getValue().toString();
                String[] all_vals_array = all_vals.split(",");
                String name = all_vals_array[10].substring(all_vals_array[10].indexOf("=") + 1);
                if(final_invitee_id.contains(dataSnapshot.getKey())){
                    dateDetails.setInvitee_name(name);
                }else if(final_inviter_id.contains(dataSnapshot.getKey())){

                    dateDetails.setInviter_name(name);
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
