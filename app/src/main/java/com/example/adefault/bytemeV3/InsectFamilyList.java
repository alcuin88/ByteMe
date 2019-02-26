package com.example.adefault.bytemeV3;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;


import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class InsectFamilyList extends AppCompatActivity {

    private static final String TAG = "InsectFamilyList";
    private RecyclerView mBugs;
    private List<BugsListResponse> list;
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

    public void refIDs(){
        setContentView(R.layout.activity_insect_family_list);
        mBugs = findViewById(R.id.bugs_list);
        progressBar = findViewById(R.id.progress_bar);
        search = findViewById(R.id.search_text);
        search.setVisibility(View.GONE);
    }

    public void init(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Bugs_List");
    }

    public void get(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    BugsListResponse value = dataSnapshot1.getValue(BugsListResponse.class);
                    BugsListResponse response = new BugsListResponse();
                    String bugName = value.getBugName();
                    String bugImage = value.getBugImage();
                    response.setBugName(bugName);
                    response.setBugImage(bugImage);
                    list.add(response);
                }
                Display(list);
                progressBar.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void Display(List<BugsListResponse> myList){
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
            List<BugsListResponse> newList = null;
            newList  =
                    Lists.newArrayList(Collections2.filter(list,
                            new ListFilter(s.toString().toUpperCase())));
            Display(newList);
        }
    };

    public final class ListFilter implements Predicate<BugsListResponse>{
        private final Pattern pattern;

        public ListFilter(final String regex){
            pattern = Pattern.compile(regex);
        }

        @Override
        public boolean apply(final BugsListResponse input)
        {
            return pattern.matcher(input.getBugName().toUpperCase()).find();
        }
    }
}
