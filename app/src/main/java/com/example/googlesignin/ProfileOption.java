package com.example.googlesignin;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileOption extends LinearLayout {

    protected TextView profile_name;
    protected TextView profile_age;
    protected TextView profile_score;
    protected Button like_button;

    public ProfileOption(Context context, AttributeSet attrs) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.profileoption, this, true);
        this.init();


    }

    public ProfileOption(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.profileoption, this, true);
        this.init();


    }

    public void init(){
        profile_age=findViewById(R.id.po_age);
        profile_name=findViewById(R.id.po_name);
        profile_score=findViewById(R.id.po_match);
        like_button=findViewById(R.id.po_like);
    }

    public void set_age(int age){
        profile_age.setText("Age: "+age);
    }
    public void set_name(String name){
        profile_name.setText(name);
    }
    public void set_score(double score){
        profile_score.setText(score+"% match");
    }

    public Button get_like_button(){
        return like_button;
    }

    public void set_all( String name,int age, double score, String like){
        this.set_name(name);
        this.set_age(age);
        this.set_score(score);
        this.set_like(like);
    }

    public void set_like(String like){
        this.like_button.setText(like);


    }


}
