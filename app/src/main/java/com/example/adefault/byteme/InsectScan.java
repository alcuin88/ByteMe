package com.example.adefault.byteme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InsectScan extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_scan);
        refIDs();
        result.setText(PredictionProcess.GetResult());
    }

    private void refIDs(){
        result = findViewById(R.id.result);
    }
}
