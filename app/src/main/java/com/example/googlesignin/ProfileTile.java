package com.example.googlesignin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;

public class ProfileTile extends LinearLayout {

    //id is profiles id
    protected int id;
    protected String name ;
    protected int age;
    protected String star_sign;
    protected String mb_type;
    protected String pet;
    protected String drinking;
    protected String smoking;
    protected String politics;
    protected String farmer;
    protected String night_in;
    protected String earth;


    public ProfileTile(Context context) {
        super(context);
    }

    public ProfileTile(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileTile(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public ProfileTile(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }


    public ProfileTile(Context context,
                       int id,
                       String name,
                       int age,
                       String star_sign,
                       String mb_type,
                       String pet,
                       String drinking,
                       String smoking,
                       String politics,
                       String farmer,
                       String night_in,
                       String earth){
        super(context);
        this.id=id;
        this.name=name;
        this.age=age;
        this.star_sign=star_sign;
        this.mb_type=mb_type;
        this.pet=pet;
        this.drinking=drinking;
        this.smoking=smoking;
        this.politics= politics;
        this.farmer=farmer;
        this.night_in=night_in;
        this.earth=earth;
    }
}
