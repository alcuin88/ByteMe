package com.example.adefault.bytemeAPP;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InsectFamilyList extends AppCompatActivity {

    private FirebaseApp firebaseApp;
    private RecyclerView mBugs;
    private Firestore db;
    private BugsListAdapter adapter;
    private List<BugsListResponse> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_family_list);
        mBugs = findViewById(R.id.bugs_list);
        init();
        try {
            getData();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        boolean isInit = false;
        try{
            List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
            for(FirebaseApp app : firebaseApps){
                if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                    isInit = true;
                    firebaseApp = app;
                }
            }
            if(!isInit){
                InputStream serviceAccount  = getResources().openRawResource(R.raw.credentials);
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .setProjectId("bytemev1")
                        .build();

                firebaseApp = FirebaseApp.initializeApp(options);
            }
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getData() throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> query = db.collection("Bugs").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        int count = 0;
        for (QueryDocumentSnapshot document : documents) {
            String insectName = document.getString("InsectName");
            String insectImage = document.getString("InsectImage");
            if(count == 0) {
                list = BugsListResponse.createBugsList(insectName, insectImage);
                adapter = new BugsListAdapter(list);
            }
            else {
                list.addAll(BugsListResponse.createBugsList(insectName, insectImage));
            }
            count++;
        }
        mBugs.setAdapter(adapter);

        mBugs.setLayoutManager(new LinearLayoutManager(this));
    }
}
