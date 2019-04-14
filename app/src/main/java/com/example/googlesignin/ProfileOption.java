package com.example.googlesignin;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileOption extends LinearLayout {
    public ProfileOption(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.profileoption, this, true);

        TextView t= new TextView(context);
        t.setText("TEST");
        this.addView(t);

    }
}
