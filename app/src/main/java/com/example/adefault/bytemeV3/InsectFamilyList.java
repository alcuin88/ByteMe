package com.example.adefault.bytemeV3;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.example.adefault.bytemeV3.Adapters.BugsListAdapter;
import com.example.adefault.bytemeV3.databaseObjects.BugsListResponseV1;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InsectFamilyList extends AppCompatActivity {

    private static final String TAG = "InsectFamilyList";
    private RecyclerView mBugs;
    private List<BugsListResponseV1> list;
    private ProgressBar progressBar;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refIDs();
        init();
        get();
        search.addTextChangedListener(watchEditText);
    }

    private void init(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bugs_List_V1");
    }

    public void refIDs(){
        setContentView(R.layout.activity_insect_family_list);
        mBugs = findViewById(R.id.bugs_list);
        progressBar = findViewById(R.id.progress_bar);
        search = findViewById(R.id.search_text);
        search.setVisibility(View.GONE);
    }

    public void get(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    BugsListResponseV1 value = dataSnapshot1.getValue(BugsListResponseV1.class);
                    BugsListResponseV1 response = new BugsListResponseV1();

                    response.setKey(dataSnapshot1.getKey());
                    response.setBugName(value.getBugName());
                    response.setBugImage(value.getBugImage());
                    response.setDescription(value.getDescription());
                    response.setSigns(value.getSigns());
                    response.setSymptoms(value.getSymptoms());
//                    response.setTreatment(GeoFenceResponse.getTreatment());
//                    response.setGetRid(GeoFenceResponse.getGetRid());

                    list.add(response);
                }
                progressBar.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                Display(list);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read GeoFenceResponse
                Log.w(TAG, "Failed to read GeoFenceResponse.", error.toException());
            }
        });
    }

    public void Display(List<BugsListResponseV1> myList){
        BugsListAdapter adapter = new BugsListAdapter(myList, this);
        RecyclerView.LayoutManager recyce = new GridLayoutManager(this,2);
        mBugs.setLayoutManager(recyce);
        mBugs.setAdapter(adapter);
    }

    public TextWatcher watchEditText = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            List<BugsListResponseV1> newList = null;
            newList  =
                    Lists.newArrayList(Collections2.filter(list,
                            new ListFilter(s.toString().toUpperCase())));
            Display(newList);
        }
    };

    public final class ListFilter implements Predicate<BugsListResponseV1>{
        private final Pattern pattern;

        public ListFilter(final String regex){
            pattern = Pattern.compile(regex);
        }

        @Override
        public boolean apply(final BugsListResponseV1 input)
        {
            return pattern.matcher(input.getBugName().toUpperCase()).find();
        }
    }
}
