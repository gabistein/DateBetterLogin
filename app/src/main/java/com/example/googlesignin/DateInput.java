package com.example.googlesignin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class DateInput extends AppCompatActivity {
    protected EditText time_text, date_text, map_text;
    protected Button time_btn, date_btn, map_btn;
    String email,from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_input);

        email=getIntent().getStringExtra("email");
        from=getIntent().getStringExtra("from");

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
        map_text= (EditText)findViewById(R.id.text_set_time);
        map_btn= (Button) findViewById(R.id.btn_set_address);



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

                        time_text.setText(hourOfDay + ":" + minute+format);
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
        //TODO: save whatever to the db, put extras into db

        //TODO: depending on from go to mathes or dates

        startActivity(intent);
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


}
