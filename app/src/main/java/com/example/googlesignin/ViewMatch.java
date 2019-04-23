package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ViewMatch extends AppCompatActivity {

    String other_id;
    String email;
    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);
        email=getIntent().getStringExtra("email");

        //testing if info is being passed
        other_id= getIntent().getStringExtra("other_id");
        from=getIntent().getStringExtra("from");
        Log.v("MYTAG", other_id);
    }

    protected void handle_unmatch(View v){
        //TODO: deletes match or deletes likes
        Log.v("btn","unmatch");

        //TODO: add pop up to confirm un match
        Intent to_matches= new Intent(ViewMatch.this, Matches.class);
        to_matches.putExtra("email",email);

        startActivity(to_matches);

    }

    protected void handle_invite(View v){
        Log.v("btn", "send date invite");

        //create new intent for date and set extras to reflect current user and date invitee
        Intent to_date_input= new Intent(ViewMatch.this, DateInput.class);
        to_date_input.putExtra("from", "ViewMatch");
        to_date_input.putExtra("other_id", other_id );
        to_date_input.putExtra("email", email);
        startActivity(to_date_input);

    }


    protected void handle_back_btn(View v){
        if(from.equals("Matches")){
            Intent to_matches=new Intent(ViewMatch.this, Matches.class);
            to_matches.putExtra("email", email);
            startActivity(new Intent(ViewMatch.this, Matches.class));
        }else if(from.equals("ViewDate")){
            //TODO: pass in View Date ID
            int date_id=getIntent().getIntExtra("date_id", 0);
            Intent to_view_date=new Intent(ViewMatch.this,ViewDate.class);
            to_view_date.putExtra("date_id", date_id);
            to_view_date.putExtra("email",email);
            startActivity(to_view_date);
        }

    }

    protected void handle_home(View v){
        Intent to_home=new Intent(ViewMatch.this, Home.class);
        to_home.putExtra("email",email);
        startActivity(to_home);
    }

}
