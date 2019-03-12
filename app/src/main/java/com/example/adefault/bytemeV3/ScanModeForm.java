package com.example.adefault.bytemeV3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ScanModeForm extends AppCompatActivity {

    Button byBite, byInsect, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_mode_form);

        refIDs();
        byBite.setOnClickListener(ClickListener);
        byInsect.setOnClickListener(ClickListener);
        back.setOnClickListener(ClickListener);
    }

    private OnClickListener ClickListener = view -> {
        switch(view.getId()){
            case R.id.byBiteButton:
                Intent biteScanIntent = new Intent(view.getContext(), BiteScanInput.class);
                startActivity(biteScanIntent);
                break;

            case R.id.byInsectButton:
                Intent insectScanIntent = new Intent(view.getContext(), InsectScanInput.class);
                startActivity(insectScanIntent);
                break;

            case R.id.backButton:
                Intent backIntent = new Intent(view.getContext(), NavDrawer.class);
                backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backIntent);
                finish();
                break;
        }
    };

    private void refIDs(){
        byBite = findViewById(R.id.byBiteButton);
        byInsect = findViewById(R.id.byInsectButton);
        back = findViewById(R.id.backButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConstraintLayout background = findViewById(R.id.rootLayout);
        SharedPreferences settings = getSharedPreferences("Background", Context.MODE_PRIVATE);
        if (settings.getInt("background", getResources().getColor(R.color.light)) == getResources().getColor(R.color.vampire)) {
            background.setBackgroundColor(getResources().getColor(R.color.vampire));
            back.setTextColor(getResources().getColor(R.color.light));
        }
        else {
            background.setBackgroundColor(getResources().getColor(R.color.light));
            back.setTextColor(getResources().getColor(R.color.vampire));
        }
    }
}
