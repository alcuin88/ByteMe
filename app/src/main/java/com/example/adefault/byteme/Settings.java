package com.example.adefault.byteme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private Spinner popupSpinner, popupSpinner2;
    private static final String[] Fonts = {"Arial", "Times New Roman", "Calibri", "Tahoma", "Georgia", "Comic Sans MS", "Aubrey", "Bodoni MT",
    "Britanic Bold", "Californian FB", "Calisto MT", "Cambria", "Century", "Caviar Dreams", "Courgette", "Eras Light ITC", "Geosanslight",
    "Gill Sans MT", "Goudy Old Style", "High Tower Text", "Imprint MT Shadow"};
    private static final String[] FontSize = {"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
    "24", "25", "26", "27", "28", "29", "30"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        popupSpinner = (Spinner)findViewById(R.id.popupSpinner);
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

//                popupSpinner2 = findViewById(R.id.spinner2);
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, FontSize);
//                popupSpinner2.setAdapter(adapter);

                popupSpinner2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (popupSpinner2.getSelectedItem().toString().trim().equals("8")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("9")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("10")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("11")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("12")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("13")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("14")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("15")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("16")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("17")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("18")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("19")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("20")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("21")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("22")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("23")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("24")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("25")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("26")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("27")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("28")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("29")) {

                        } else if (popupSpinner2.getSelectedItem().toString().trim().equals("30")) {
                        }
                    }
                });
            }
        });
    }
}





