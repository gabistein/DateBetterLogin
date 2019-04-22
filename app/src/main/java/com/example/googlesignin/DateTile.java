package com.example.googlesignin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DateTile extends LinearLayout {

    protected TextView dt_invitee_name;
    protected TextView dt_inviter_name;
    protected TextView dt_status;


    public DateTile(Context context, AttributeSet attrs) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        //inflate layout with xml from profilematch.xml and make the root be this linear layout
        LayoutInflater.from(context).inflate(R.layout.datetile, this, true);
        //initialize instance variables
        this.init();


    }

    public DateTile(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        //inflate layout with xml from profilematch.xml and make the root be this linear layout
        LayoutInflater.from(context).inflate(R.layout.datetile, this, true);
        this.init();


    }

    public void init(){
        dt_inviter_name=findViewById(R.id.dt_inviter_name);
        dt_invitee_name=findViewById(R.id.dt_invitee_name);
        dt_status=findViewById(R.id.dt_status);

    }

    public void set_invitee_name(String name){
        dt_invitee_name.setText(name);
    }
    public void set_inviter_name(String name){
        dt_inviter_name.setText(name);
    }

    public void set_status(String status){
        dt_status.setText(status);
    }

    public void set_all( String invitee_name, String inviter_name, String status){
        this.set_invitee_name(invitee_name);
        this.set_inviter_name(inviter_name);
        this.set_status(status);
    }
}
