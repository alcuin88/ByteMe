package com.example.adefault.bytemeV3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private TextView titleTextView, bodyTextView, body2TextView, title2TextView, title3TextView, body3TextView, body4TextView,
            body5TextView, title4TextView, body6TextView, body7TextView, body8TextView, body9TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        refIDs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScrollView background = findViewById(R.id.home_layout);
        SharedPreferences settings = getSharedPreferences("Background", Context.MODE_PRIVATE);
        if (settings.getInt("background", getResources().getColor(R.color.light)) == getResources().getColor(R.color.vampire)) {
            background.setBackgroundColor(getResources().getColor(R.color.vampire));
            titleTextView.setTextColor(getResources().getColor(R.color.light));
            bodyTextView.setTextColor(getResources().getColor(R.color.light));
            body2TextView.setTextColor(getResources().getColor(R.color.light));
            title2TextView.setTextColor(getResources().getColor(R.color.light));
            title3TextView.setTextColor(getResources().getColor(R.color.light));
            body3TextView.setTextColor(getResources().getColor(R.color.light));
            body4TextView.setTextColor(getResources().getColor(R.color.light));
            body5TextView.setTextColor(getResources().getColor(R.color.light));
            title4TextView.setTextColor(getResources().getColor(R.color.light));
            body6TextView.setTextColor(getResources().getColor(R.color.light));
            body7TextView.setTextColor(getResources().getColor(R.color.light));
            body8TextView.setTextColor(getResources().getColor(R.color.light));
            body9TextView.setTextColor(getResources().getColor(R.color.light));


        } else {
            background.setBackgroundColor(getResources().getColor(R.color.light));
            titleTextView.setTextColor(getResources().getColor(R.color.vampire));
            bodyTextView.setTextColor(getResources().getColor(R.color.vampire));
            body2TextView.setTextColor(getResources().getColor(R.color.vampire));
            title2TextView.setTextColor(getResources().getColor(R.color.vampire));
            title3TextView.setTextColor(getResources().getColor(R.color.vampire));
            body3TextView.setTextColor(getResources().getColor(R.color.vampire));
            body4TextView.setTextColor(getResources().getColor(R.color.vampire));
            body5TextView.setTextColor(getResources().getColor(R.color.vampire));
            title4TextView.setTextColor(getResources().getColor(R.color.vampire));
            body6TextView.setTextColor(getResources().getColor(R.color.vampire));
            body7TextView.setTextColor(getResources().getColor(R.color.vampire));
            body8TextView.setTextColor(getResources().getColor(R.color.vampire));
            body9TextView.setTextColor(getResources().getColor(R.color.vampire));
        }
    }

    private void refIDs() {
        titleTextView = findViewById(R.id.titleTextView);
        bodyTextView = findViewById(R.id.bodyTextView);
        body2TextView = findViewById(R.id.body2TextView);
        title2TextView = findViewById(R.id.title2TextView);
        title3TextView = findViewById(R.id.title3TextView);
        body3TextView = findViewById(R.id.body3TextView);
        body4TextView = findViewById(R.id.body4TextView);
        body5TextView = findViewById(R.id.body5TextView);
        title4TextView = findViewById(R.id.title4TextView);
        body6TextView = findViewById(R.id.body6TextView);
        body7TextView = findViewById(R.id.body7TextView);
        body8TextView = findViewById(R.id.body8TextView);
        body9TextView = findViewById(R.id.body9TextView);
    }
}
