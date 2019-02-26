package com.example.adefault.bytemeV3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.automl.v1beta1.AnnotationPayload;
import com.google.cloud.automl.v1beta1.ExamplePayload;
import com.google.cloud.automl.v1beta1.Image;
import com.google.cloud.automl.v1beta1.ModelName;
import com.google.cloud.automl.v1beta1.PredictResponse;
import com.google.cloud.automl.v1beta1.PredictionServiceClient;
import com.google.cloud.automl.v1beta1.PredictionServiceSettings;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PredictionProcess {
    private static String displayResult = "";
    private GoogleCredentials credentials;
    private String modelID;
    private String projectID;
    private InsectNode head;

    public void main(Context context, byte[][] imageBytes, GoogleCredentials credentials, String modelID, String projectID){
        this.credentials = credentials;
        this.modelID = modelID;
        this.projectID = projectID;
        new LongOperation(context).execute(imageBytes);

    }

    public static String GetResult(){
        return displayResult;
    }

    class LongOperation extends AsyncTask<byte[][], String, String> {
        private Context context;
        private ProgressDialog progressDialog;
        private PredictionServiceClient predictionClient;

        LongOperation(Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected String doInBackground(byte[][]... bytes) {
            String result;
            float confidence;

            try {
                PredictionServiceSettings.Builder settingsBuilder = PredictionServiceSettings.newBuilder();
                PredictionServiceSettings predictionSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
                predictionClient = PredictionServiceClient.create(predictionSettings);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < 3; i++){
                ModelName name = ModelName.of(projectID, "us-central1", modelID);
                ByteString content = ByteString.copyFrom(bytes[0][i]);
                Image image = Image.newBuilder().setImageBytes(content).build();
                ExamplePayload examplePayload = ExamplePayload.newBuilder().setImage(image).build();

                Map<String, String> params = new HashMap<>();
                params.put("score_threshold", "0.70");
                PredictResponse response = predictionClient.predict(name, examplePayload, params);


                for (AnnotationPayload annotationPayload : response.getPayloadList()) {
                    result =  annotationPayload.getDisplayName();
                    confidence =  annotationPayload.getClassification().getScore();
                    PushResult(result, confidence);
                }
            }
            return null;
        }

        void PushResult(String res, float conf){
            boolean flag = false;
            InsectNode temp = CreateNode(res, conf);
            if(head == null)
                head = temp;
            else
            {
                for(InsectNode temp1 = head; temp1 != null; temp1 = temp1.GetNexLink()){
                    if (temp1.GetInsectName().equalsIgnoreCase(res)){
                        temp1.SetConfidence(temp1.GetConfidence() + conf);
                        temp1.SetCounter(temp1.GetCount() + 1);
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    temp.SetNexLink(head);
                    head = temp;
                }
            }
        }

        InsectNode CreateNode(String res, float conf){
            InsectNode temp = new InsectNode();
            temp.SetInsectName(res);
            temp.SetConfidence(conf);
            temp.SetCounter(1);
            return temp;
        }

        void getResult(){
            displayResult = "";
            int divider = 0;
            float confidence = 0;
            String insectName = "";
            InsectNode temp;
            for(temp = head; temp != null; temp = temp.GetNexLink())
                if(temp.GetCount() > divider)
                    divider = temp.GetCount();

            for(temp = head; temp != null; temp = temp.GetNexLink())
                temp.SetConfidence(temp.GetConfidence()/divider);

            for(temp = head; temp != null; temp = temp.GetNexLink()){
                if(confidence == 0 || temp.GetConfidence() > confidence){
                    confidence = temp.GetConfidence();
                    insectName = temp.GetInsectName();
                }
            }

            displayResult = insectName;
            if(confidence == 0 && insectName.equalsIgnoreCase(""))
                displayResult = "No Results Found.";
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
        }

        @Override
        protected void onPostExecute(String s){
            progressDialog.dismiss();
            getResult();
            head = null;
            Intent scanIntent = new Intent(context, InsectScan.class);
            context.startActivity(scanIntent);
        }
    }

}
