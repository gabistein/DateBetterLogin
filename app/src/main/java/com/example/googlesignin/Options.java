package com.example.googlesignin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        LinearLayout options_container= (LinearLayout) findViewById(R.id.options_container);

      //  options_container.addView(new ProfileOption(this));
        options_container.addView(this.create_option(1,"Sarah", 22, 100));
        options_container.addView(this.create_option(2,"Bob", 22, 100));
        options_container.addView(this.create_option(3,"Sue", 22, 100));



        Log.v("MYTAG", "trying to add layout in oncreate");
    }

    protected LinearLayout create_option_tile(int id, String name,int age, int match_score){

        //create new linear layout
        LinearLayout option= new LinearLayout(this);

        //set layout orientation to horizontal
        option.setOrientation(LinearLayout.HORIZONTAL);

        //set background
        option.setBackgroundResource(R.drawable.trait_background);

        //set layout parameters
        LinearLayout.LayoutParams layout_params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        option.setLayoutParams(layout_params);

        //set padding and margin
        option.setPadding(10,10,10,10);
        //TODO: set margins

        /////////////////////////
        //create a name text view
        /////////////////////////
        TextView name_text= new TextView(this);
        name_text.setText(name+", "+age);
        name_text.setTextAppearance(R.style.opt_name);
        ViewGroup.LayoutParams name_layout= name_text.getLayoutParams();

        option.addView(name_text);


        //////////////////////////
        //create and add a score text view
        //////////////////////////
        TextView score_text= new TextView(this);
        score_text.setText("score");
        score_text.setTextSize(18);
        score_text.setTextColor(getResources().getColor(R.color.gradient_dark_purple));
        this.set_layout_params(score_text);
        option.addView(score_text);


        ////////////////////////
        //create view button
        ///////////////////////
        Button view_profile_button= new Button(this);
        view_profile_button.setText("View Profile");
        view_profile_button.setTextSize(18);
        view_profile_button.setAllCaps(false);
        view_profile_button.setTextColor(getResources().getColor(R.color.white));
        view_profile_button.setBackground(getResources().getDrawable(R.drawable.btn_dark_gradient));
        //id needs to be set final for button
        final int btn_id=id;
        view_profile_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_view_profile(btn_id);
            }
        });
        this.set_layout_params(view_profile_button);
        option.addView(view_profile_button);



        /////////////////////////
        //create like button
        /////////////////////////
        Button like_button= new Button(this);
        like_button.setText("Like");
        like_button.setTextSize(20);
        like_button.setAllCaps(false);
        like_button.setTextColor(getResources().getColor(R.color.white));
        like_button.setBackground(getResources().getDrawable(R.drawable.btn_dark_gradient));
        like_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_like(btn_id);
            }
        });
        this.set_layout_params(like_button);
        option.addView(like_button);



        return option;

    }


    ///////////////////////////
    //////Button Handlers
    ///////////////////////////

    protected void handle_view_profile(int id){
        Intent view_profile= new Intent(Options.this, ViewOption.class );
        view_profile.putExtra("view_profile_id", ""+id);
        startActivity(view_profile);
    }


    protected void handle_like(int id){
        //TODO: insert like into like table
        Log.v("MYTAG", "liked id: "+id);
    }

    protected void handle_home(View v){
        startActivity(new Intent(Options.this, Home.class));
    }


    ///////////////////////////
    //////Helpers
    ///////////////////////////
    protected void clear_options(){
        LinearLayout options_container= (LinearLayout) findViewById(R.id.options_container);

    }

    protected void set_layout_params(View v){
        ViewGroup.MarginLayoutParams margins= new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        margins.setMargins(10,10,10,10);
        v.setLayoutParams(margins);
        v.setPadding(5,5,5,5);
    }

    protected ProfileOption create_option(int id, String name, int age, double score){
        ProfileOption option= new ProfileOption(this);
        option.set_all(name, age, score);
        final int btn_id=id;
        option.get_like_button().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_like(btn_id);
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //call check_and_update method to determine validity of move and update board
                handle_view_profile(btn_id);
            }
        });
        return option;
    }
}

