package com.example.adefault.bytemeAPP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class BiteMarkScan extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bite_mark_scan);
        refIDs();
        result.setText(PredictionProcess.GetResult());
    }

    private void refIDs(){
        result = findViewById(R.id.result);
    }


}
