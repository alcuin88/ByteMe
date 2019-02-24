package com.example.adefault.bytemeV3;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class Settings extends AppCompatActivity {

    private List<String> fontStyleSpinnerArray = new ArrayList<>();
    private List<Integer> fontSizeSpinnerArray = new ArrayList<>();
    private Spinner fontStyleSpinner, fontSizeSpinner;
    private TextView themeTextView;
    private String fontStyle;
    private int fontSize;
    private Button save, cancel;
    private int themeID;
    private ConstraintLayout settingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        refIDs();
        populateFontStyle();
        populateFontSize();
        fontStyleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fontStyle = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fontSizeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fontSize = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save.setOnClickListener(clickListener);
        cancel.setOnClickListener(clickListener);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.vampire_radioButton:
                if (checked){
                    themeID = R.id.vampire_radioButton;
                }
                    break;
            case R.id.light_radioButton:
                if (checked){
                    themeID = R.id.light_radioButton;
                }
                    break;
        }
    }

    public OnClickListener clickListener = v -> {
        Intent backIntent;
        switch (v.getId()){
            case R.id.save:
                toggleTheme();
                break;
            case R.id.cancel:
                backIntent = new Intent(v.getContext(), NavDrawer.class);
                backIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backIntent);
                finish();
                break;
        }
    };

    public void populateFontStyle(){
        fontStyleSpinnerArray.add("Arial");
        fontStyleSpinnerArray.add("Times New Roman");
        fontStyleSpinnerArray.add("Calibri");
        fontStyleSpinnerArray.add("Tahoma");
        fontStyleSpinnerArray.add("Georgia");
        fontStyleSpinnerArray.add("Comic Sans MS");
        fontStyleSpinnerArray.add("Aubrey");
        fontStyleSpinnerArray.add("Bodoni MT");
        fontStyleSpinnerArray.add("Britanic Bold");
        fontStyleSpinnerArray.add("Californian FB");
        fontStyleSpinnerArray.add("Calisto MT");
        fontStyleSpinnerArray.add("Cambria");
        fontStyleSpinnerArray.add("Century");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, fontStyleSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontStyleSpinner.setAdapter(adapter);
    }

    public void populateFontSize(){
        for(int i = 6; i <= 72; i++){
            fontSizeSpinnerArray.add(i);
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, fontSizeSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSizeSpinner.setAdapter(adapter);
    }

    private void refIDs(){
        fontStyleSpinner = findViewById(R.id.fontStyle_spinner);
        fontSizeSpinner = findViewById(R.id.fontSize_spinner);
        themeTextView = findViewById(R.id.themeTextView);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        settingsLayout = findViewById(R.id.settings_layout);
    }

    private void toggleTheme(){
        if(themeID == R.id.vampire_radioButton){
            themeTextView.setTextColor(getResources().getColor(R.color.light));
            settingsLayout.setBackgroundColor(getResources().getColor(R.color.vampire));
            fontStyleSpinner.setBackgroundColor(getResources().getColor(R.color.vampire));
            fontSizeSpinner.setBackgroundColor(getResources().getColor(R.color.vampire));
        }else{
            themeTextView.setTextColor(getResources().getColor(R.color.vampire));
            settingsLayout.setBackgroundColor(getResources().getColor(R.color.light));
            fontStyleSpinner.setBackgroundColor(getResources().getColor(R.color.light));
            fontSizeSpinner.setBackgroundColor(getResources().getColor(R.color.light));
        }
        themeTextView.setTextSize(fontSize);
        themeTextView.setFontFeatureSettings(fontStyle);
        Toast.makeText(this, "Settings saved.", Toast.LENGTH_SHORT).show();
    }
}





