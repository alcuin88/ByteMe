package com.example.adefault.byteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InsectScanInput extends AppCompatActivity {

    Button scanInsect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_scan_input);

        refIDs();

        scanInsect.setOnClickListener(ClickListener);
    }

    private OnClickListener ClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.scanInsectButton:
                    Intent scanInsectIntent = new Intent(view.getContext(), InsectScan.class);
                    startActivity(scanInsectIntent);
                    break;
            }
        }
    };

    private void refIDs(){
        scanInsect = findViewById(R.id.scanInsectButton);
    }
}
