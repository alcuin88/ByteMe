package com.example.adefault.byteme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private Spinner popupSpinner;
    private static final String[] Fonts = {"Arial", "Times New Roman", "Calibri", "Tahoma", "Georgia", "Comic Sans MS", "Aubrey", "Bodoni MT",
    "Britanic Bold", "Californian FB", "Calisto MT", "Cambria", "Century", "Caviar Dreams", "Courgette", "Eras Light ITC", "Geosanslight",
    "Gill Sans MT", "Goudy Old Style", "High Tower Text", "Imprint MT Shadow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        popupSpinner = (Spinner) findViewById(R.id.popupSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Fonts);
        popupSpinner.setAdapter(adapter);

        popupSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupSpinner.getSelectedItem().toString().trim().equals("Arial")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Times New Roman")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Calibri")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Tahoma")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Georgia")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Comic Sans MS")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Aubrey")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Bodoni MT")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Britanic Bold")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Californian FB")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Calisto MT")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Cambria")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Century")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Caviar Dreams")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Courgette")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Eras Light ITC")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Geosanslight")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Gill Sans MT")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Goudy Old Style")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("High Tower Text")) {

                } else if (popupSpinner.getSelectedItem().toString().trim().equals("Imprint MT Shadow")) {

                }
            }
        });
    }
}

