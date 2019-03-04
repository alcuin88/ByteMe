package com.example.adefault.bytemeV3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.bytemeV3.databaseObjects.TreatmentResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.ViewPager.*;

public class Treatment extends AppCompatActivity {

    private static final String TAG = "Treatment";

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private String bugType;
    private String Type;
    private FirebaseDatabase database;
    private DatabaseReference myRef1, myRef2;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;
    private List<TreatmentResponse> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        Bundle bundle = getIntent().getExtras();

        bugType = bundle.getString("bugKey");
        Type = bundle.getString("type");

        init();
        getValues();
        refIDs();

    }

    public void refIDs(){
        mSlideViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);
    }

    public void addDotsIndicator(int position){

        mDots = new TextView[list.size()];
        mDotLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
            mDots[position].setTextSize(40);
            mDots[position].setText(Html.fromHtml("&#8226;"));
        }
    }

    OnPageChangeListener viewListener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public void init(){
        database = FirebaseDatabase.getInstance();
        myRef1 = database.getReference("Bugs_List_V1").child(bugType).child(Type);
        myRef2 = database.getReference("Bugs_List_V1").child(bugType).child(Type).child("Steps");
    }

    public void getValues(){
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    TreatmentResponse value = dataSnapshot1.getValue(TreatmentResponse.class);
                    TreatmentResponse response = new TreatmentResponse();
                    response.setDescription(value.getDescription());
                    response.setImage(value.getImage());
                    response.setSteps(value.getSteps());

                    list.add(response);
                }
                sliderAdapter = new SliderAdapter(Treatment.this, list);

                mSlideViewPager.setAdapter(sliderAdapter);

                addDotsIndicator(0);
                mSlideViewPager.addOnPageChangeListener(viewListener);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}