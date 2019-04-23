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
    String from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);

        //testing if info is being passed
        other_id= getIntent().getStringExtra("other_id");
        from=getIntent().getStringExtra("from");
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
        intent.putExtra("from", "ViewMatch");
        intent.putExtra("other_id", other_id );
        startActivity(intent);

    }


    protected void handle_back_btn(View v){
        if(from.equals("Matches")){
            startActivity(new Intent(ViewMatch.this, Matches.class));
        }else if(from.equals("ViewDate")){
            //TODO: pass in View Date ID
            int date_id=getIntent().getIntExtra("date_id", 0);
            Intent viewdate=new Intent(ViewMatch.this,ViewDate.class);
            viewdate.putExtra("date_id", date_id);
            startActivity(viewdate);
        }

    }

    protected void handle_home(View v){
        startActivity(new Intent(ViewMatch.this, Home.class));
    }

}
