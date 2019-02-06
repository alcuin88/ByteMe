package com.example.adefault.byteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {
    ImageButton scan, settings, list, pest, profile, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        refIDs();
        scan.setOnClickListener(ButtonClick);

    }

    private OnClickListener ButtonClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.scanButton:
                    Intent scanIntent = new Intent(view.getContext(), ScanModeForm.class);
                    startActivity(scanIntent);
                    break;

                case R.id.settingsButton:
                    Intent settingsIntent = new Intent(view.getContext(), Settings.class);
                    startActivity(settingsIntent);
                    break;

                case R.id.listButton:
                    Intent insectListIntent = new Intent(view.getContext(), InsectFamilyList.class);
                    startActivity(insectListIntent);
                    break;

                case R.id.profileButton:
                    Intent profileIntent = new Intent(view.getContext(), UserProfile.class);
                    startActivity(profileIntent);
                    break;

                case R.id.aboutButton:
                    Intent aboutIntent = new Intent(view.getContext(), About.class);
                    startActivity(aboutIntent);
                    break;
            }
        }
    };

    public void refIDs(){
        settings = findViewById(R.id.settingsButton);
        scan = findViewById(R.id.scanButton);
        list = findViewById(R.id.listButton);
        pest = findViewById(R.id.pestButton);
        profile = findViewById(R.id.profileButton);
        about = findViewById(R.id.aboutButton);
    }
}
