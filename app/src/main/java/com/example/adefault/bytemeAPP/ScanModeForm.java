package com.example.adefault.bytemeAPP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;

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
}
