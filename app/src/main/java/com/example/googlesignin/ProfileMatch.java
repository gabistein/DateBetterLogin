package com.example.googlesignin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileMatch  extends LinearLayout{

    protected TextView profile_name;
    protected TextView profile_age;

    protected Button date_button;

    public ProfileMatch(Context context, AttributeSet attrs) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        //inflate layout with xml from profilematch.xml and make the root be this linear layout
        LayoutInflater.from(context).inflate(R.layout.profilematch, this, true);
        //initialize instance variables
        this.init();


    }

    public ProfileMatch(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        //inflate layout with xml from profilematch.xml and make the root be this linear layout
        LayoutInflater.from(context).inflate(R.layout.profilematch, this, true);
        this.init();


    }

    public void init(){
        profile_age=findViewById(R.id.pm_age);
        profile_name=findViewById(R.id.pm_name);
//        profile_score=findViewById(R.id.pm_match);
        date_button=findViewById(R.id.pm_date);
    }

    public void set_age(int age){
        profile_age.setText("Age: "+age);
    }
    public void set_name(String name){
        profile_name.setText(name);
    }

    public Button get_date_button(){
        return date_button;
    }

    public void set_all( String name,int age, double score){
        this.set_name(name);
        this.set_age(age);
    }
}
