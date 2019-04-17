package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ViewMatch extends AppCompatActivity {

    String other_id;
    String cur_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_option);

        //testing if info is being passed
        other_id= getIntent().getStringExtra("other_id");
        Log.v("MYTAG", other_id);
    }

    protected void handle_unmatch(View v){
        //TODO: deletes match or deletes likes
        Log.v("btn","unmatch");

        //takes back to matches page
        startActivity(new Intent(ViewMatch.this, Matches.class));

    }

    protected void handle_invite(View v){
        Log.v("btn", "send date invite");

        //create new intent for date and set extras to reflect current user and date invitee
        Intent intent= new Intent(ViewMatch.this, DateInput.class);
        intent.putExtra("other_id", other_id );

    }


    protected void handle_back_btn(View v){
        startActivity(new Intent(ViewMatch.this, Matches.class));
    }

    protected void handle_home(View v){
        startActivity(new Intent(ViewMatch.this, Home.class));
    }

}
