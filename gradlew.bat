package com.example.adefault.bytemeV3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.example.adefault.bytemeV3.Nodes.SignsAndSymptomsNode;
import com.example.adefault.bytemeV3.databaseObjects.BugsListResponseV1;
import com.example.adefault.bytemeV3.databaseObjects.BugsListResponse;
import com.example.adefault.bytemeV3.databaseObjects.SignsResponse;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.automl.v1beta1.AnnotationPayload;
import com.google.cloud.automl.v1beta1.ExamplePayload;
import com.google.cloud.automl.v1beta1.Image;
import com.google.cloud.automl.v1beta1.ModelName;
import com.google.cloud.automl.v1beta1.PredictResponse;
import com.google.cloud.automl.v1beta1.PredictionServiceClient;
import com.google.cloud.automl.v1beta1.PredictionServiceSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PredictionProcess {

    private static final String TAG = "PredictionProcess";
    private static String displayResult = "";
    private GoogleCredentials credentials;
    private String modelID;
    private String projectID;
    private InsectNode head;
    private List<Integer> signs;
    private List<Integer> symptoms;
    private List<InsectNode> resultList;
    private List<BugsListResponeV1> list;

    public void main(Context context, byte[][] imageBytes, GoogleCredentials credentials,
                     String modelID, String projectID, List<Integer> signs, List<Integer> symptoms){
        this.credentials = credentials;
        this.modelID = modelID;
        this.projectID = projectID;
        this.signs = signs;
        this.symptoms = sym