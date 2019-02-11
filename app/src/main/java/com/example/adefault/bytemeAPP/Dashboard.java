package com.example.adefault.bytemeAPP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    ImageButton scan, settings, list, pest, profile, about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        refIDs();
        scan.setOnClickListener(ButtonClick);
        settings.setOnClickListener(ButtonClick);
        list.setOnClickListener(ButtonClick);
        pest.setOnClickListener(ButtonClick);
        profile.setOnClickListener(ButtonClick);
        about.setOnClickListener(ButtonClick);
    }

    private OnClickListener ButtonClick = view -> {
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

            default:
                CharSequence text = "This function is not yet available.\nPlease stay tuned for our next update.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(view.getContext(), text, duration);
                toast.show();
                break;
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
