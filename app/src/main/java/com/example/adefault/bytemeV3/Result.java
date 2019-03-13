package com.example.adefault.bytemeV3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.*;

public class Result extends AppCompatActivity {

    private static final String TAG = "Result";

    private TextView result, bugDescription;
    private CircleImageView bugImage;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    String scanResult, key;
    private Button maps, getRid, treatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = getIntent().getExtras();
        scanResult = bundle.getString("result");

        refIDs();
        if(!scanResult.equalsIgnoreCase("No Results Found.")){
            getImage();
            treatment.setOnClickListener(clickListener);
            maps.setOnClickListener(clickListener);
            getRid.setOnClickListener(clickListener);
        }else{
            result.setText(scanResult);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private OnClickListener clickListener = v -> {
        if(v.getId() == R.id.first_aid){
            Intent treatmentIntent = new Intent(v.getContext(), Treatment.class);
            treatmentIntent.putExtra("bugKey", key);
            treatmentIntent.putExtra("type", "Treatment");
            startActivity(treatmentIntent);
        }else if(v.getId() == R.id.map_button){
            Intent mapIntent = new Intent(v.getContext(), MapsActivity.class);
            startActivity(mapIntent);
        }else if(v.getId() == R.id.get_rid){
            Intent treatmentIntent = new Intent(v.getContext(), Treatment.class);
            treatmentIntent.putExtra("bugKey", key);
            treatmentIntent.putExtra("type", "GetRid");
            startActivity(treatmentIntent);
        }
    };

    private void refIDs(){
        bugImage = findViewById(R.id.bug_image);
        result = findViewById(R.id.result);
        bugDescription = findViewById(R.id.bug_description);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bugs_List_V1");
        treatment = findViewById(R.id.first_aid);
        maps = findViewById(R.id.map_button);
        getRid = findViewById(R.id.get_rid);
    }

    private void getImage(){
        Log.d(TAG, "GET IMAGE");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    String bugName = dataSnapshot1.child("BugName").getValue().toString().replace(" ", "");
                    String scanRes = scanResult.replace(" ", "");
                    Log.d(TAG, "COMPARE= " + bugName + " == " + scanResult);
                    if(bugName.equalsIgnoreCase(scanRes)){
                        key = dataSnapshot1.getKey();
                        result.setText(dataSnapshot1.child("BugName").getValue().toString());
                        Glide.with(Result.this)
                                .load(dataSnapshot1.child("BugImage").getValue().toString())
                                .into(bugImage);
                        bugDescription.setText(dataSnapshot1.child("Description").getValue().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveResult(){

    }
}
