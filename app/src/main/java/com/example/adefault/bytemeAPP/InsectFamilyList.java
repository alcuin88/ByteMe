package com.example.adefault.bytemeAPP;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class InsectFamilyList extends AppCompatActivity {

    private static final String TAG = "InsectFamilyList";
    private RecyclerView mBugs;
    private List<BugsListResponse> list;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refIDs();
        init();
        get();
    }

    public void refIDs(){
        setContentView(R.layout.activity_insect_family_list);
        mBugs = findViewById(R.id.bugs_list);
        progressBar = findViewById(R.id.progress_bar);
    }

    public void init(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bugs_List");
    }

    public void get(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list = new ArrayList<BugsListResponse>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    BugsListResponse value = dataSnapshot1.getValue(BugsListResponse.class);
                    BugsListResponse response = new BugsListResponse();
                    String bugName = value.getBugName();
                    String bugImage = value.getBugImage();
                    response.setBugName(bugName);
                    response.setBugImage(bugImage);
                    list.add(response);
                }
                Display();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void Display(){
        progressBar.setVisibility(View.GONE);
        BugsListAdapter adapter = new BugsListAdapter(list, this);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(this,2);
        mBugs.setLayoutManager(recyce);
        mBugs.setAdapter(adapter);
    }
}
