package com.example.adefault.byteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {
    Button scan, list, pestControl, profile, about, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        refIDs();
        scan.setOnClickListener(ButtonClick);
        profile.setOnClickListener(ButtonClick);
    }

    private OnClickListener ButtonClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.scanButton:
                    Intent scanIntent = new Intent(view.getContext(), ScanModeForm.class);
                    startActivity(scanIntent);
                    break;

                case R.id.profileButton:
                    Intent profileIntent = new Intent(view.getContext(), UserProfile.class);
                    startActivity(profileIntent);
                    break;

                case R.id.listButton:
                    Intent listIntent = new Intent(view.getContext(), InsectFamilyList.class);
                    startActivity(listIntent);
                    break;
            }
        }
    };

    public void refIDs(){
        scan = findViewById(R.id.scanButton);
        list = findViewById(R.id.listButton);
        pestControl = findViewById(R.id.pestButton);
        profile = findViewById(R.id.profileButton);
        about = findViewById(R.id.aboutButton);
        settings = findViewById(R.id.settingsButton);
    }
}
