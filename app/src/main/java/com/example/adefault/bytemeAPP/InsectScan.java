package com.example.adefault.bytemeAPP;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsectScan extends AppCompatActivity {

    private TextView result, bugDescription;
    private CircleImageView bugImage;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_scan);
        refIDs();
        scanResult = PredictionProcess.GetResult();
        if(!scanResult.equalsIgnoreCase("No Results Found.")){
            getImage();
        }else{
            result.setText(scanResult);
        }
    }

    private void refIDs(){
        bugImage = findViewById(R.id.bug_image);
        result = findViewById(R.id.result);
        bugDescription = findViewById(R.id.bug_description);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bugs_List");
    }

    private void getImage(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    BugsListResponse value = dataSnapshot1.getValue(BugsListResponse.class);
                    if(value.getBugName().trim().replaceAll("\\s+","").equalsIgnoreCase(scanResult.trim().replaceAll("\\s+",""))){
                        result.setText(value.getBugName());
                        Glide.with(InsectScan.this)
                                .load(value.getBugImage())
                                .into(bugImage);
                        bugDescription.setText(value.getHomeTreatment());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
