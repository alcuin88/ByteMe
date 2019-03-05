package com.example.adefault.bytemeV3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.adefault.bytemeV3.Nodes.SignsAndSymptomsNode;
import com.example.adefault.bytemeV3.databaseObjects.BugsListResponseV1;
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
    private List<InsectNode> head;
    private List<Integer> signs;
    private List<Integer> symptoms;
    private List<InsectNode> resultList;
    private List<BugsListResponseV1> list;

    public void main(Context context, byte[][] imageBytes, GoogleCredentials credentials,
                     String modelID, String projectID, List<Integer> signs, List<Integer> symptoms,
                     List<BugsListResponseV1> list){
        this.credentials = credentials;
        this.modelID = modelID;
        this.projectID = projectID;
        this.signs = signs;
        this.symptoms = symptoms;
        this.list = list;
        resultList = new ArrayList<>();
        head = new ArrayList<>();
        new LongOperation(context).execute(imageBytes);
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
            double confidence;

            try {
                PredictionServiceSettings.Builder settingsBuilder = PredictionServiceSettings.newBuilder();
                PredictionServiceSettings predictionSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
                predictionClient = PredictionServiceClient.create(predictionSettings);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < bytes.length; i++){
                ModelName name = ModelName.of(projectID, "us-central1", modelID);
                ByteString content = ByteString.copyFrom(bytes[0][i]);
                Image image = Image.newBuilder().setImageBytes(content).build();
                ExamplePayload examplePayload = ExamplePayload.newBuilder().setImage(image).build();

                Map<String, String> params = new HashMap<>();
                params.put("score_threshold", "0.50");
                PredictResponse response = predictionClient.predict(name, examplePayload, params);


                for (AnnotationPayload annotationPayload : response.getPayloadList()) {
                    result =  annotationPayload.getDisplayName();
                    confidence =  annotationPayload.getClassification().getScore();
                    PushResult(result, confidence);
                }
            }
            return null;
        }

        void PushResult(String res, double conf){
            boolean flag = false;
            InsectNode temp = CreateNode(res, conf);
            if(head.size() == 0)
                head.add(temp);
            else
            {
                for (InsectNode temp1: head) {
                    if (temp1.GetInsectName().equalsIgnoreCase(res)){
                        temp1.SetConfidence(temp1.GetConfidence() + conf);
                        temp1.SetCounter(temp1.GetCount() + 1);
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    head.add(temp);
                }
            }
        }

        InsectNode CreateNode(String res, double conf){
            InsectNode temp = new InsectNode();
            temp.SetInsectName(res);
            temp.SetConfidence(conf);
            temp.SetCounter(1);
            return temp;
        }

        void getResult(){
            displayResult = "";
            int divider = 0;
            double confidence = 0;
            String insectName = "";
            List<SignsAndSymptomsNode> signsScore;
            List<SignsAndSymptomsNode> symptomsScore;
            List<SignsAndSymptomsNode> signsAndSymptomsFinalList = new ArrayList<>();

            for(InsectNode temp: head)    //Get divider
                if(temp.GetCount() > divider)
                    divider = temp.GetCount();

            for(InsectNode temp: head)
                temp.SetConfidence(temp.GetConfidence()/divider);       //Get percentage of confidence

            if(signs != null || symptoms != null){
                if(signs != null){
                    signsScore = getScores(1);                                  //Get signs score
                    signsAndSymptomsFinalList = combineSignsAndSymptoms(signsAndSymptomsFinalList, signsScore);         //add signs to signsAndSymptomsFinalList
                }
                if(symptoms != null){
                    symptomsScore = getScores(2);                               //Get symptoms score
                    signsAndSymptomsFinalList = combineSignsAndSymptoms(signsAndSymptomsFinalList, symptomsScore);      //add symptoms to signsAndSymptomsFinalList
                }
                signsAndSymptomsFinalList = getAverage(signsAndSymptomsFinalList);                                  //Get avergage of all scores
                getAverageFromAPIandAlgo(signsAndSymptomsFinalList);
            }
            for(InsectNode temp: head){
                if(confidence == 0 || temp.GetConfidence() > confidence){
                    confidence = temp.GetConfidence();
                    insectName = temp.GetInsectName();
                }
            }

            displayResult = insectName;
            if(confidence < 0.70)
                displayResult = "No Results Found.";
            if(confidence == 0 && insectName.equalsIgnoreCase(""))
                displayResult = "No Results Found.";
        }

        private void getAverageFromAPIandAlgo(List<SignsAndSymptomsNode> signsAndSymptomsFinalList){
            boolean found = false;
            for(SignsAndSymptomsNode list: signsAndSymptomsFinalList){
                for(InsectNode temp: head){
                    if(temp.GetInsectName().equalsIgnoreCase(list.getKey())){
                        double apiConf = temp.GetConfidence() * 0.60;
                        double signsAndSymptoms = list.getPoints() * 0.40;
                        temp.SetConfidence(apiConf + signsAndSymptoms);
                        found = true;
                        break;
                    }
                }
                if(found == false){
                    InsectNode node = new InsectNode();
                    double signsAndSymptoms = list.getPoints() * 0.40;
                    node.SetConfidence(signsAndSymptoms + 0);
                    node.SetInsectName(list.getBugName());
                    head.add(node);
                }
                found = false;
            }
        }

        private List<SignsAndSymptomsNode> getAverage(List<SignsAndSymptomsNode> finalList){
            int high = 0;
            for(int i = 0; i < finalList.size(); i++){
                if(finalList.get(i).getOccr() > high)
                    high = finalList.get(i).getOccr();
            }

            for(int i = 0; i < finalList.size(); i++)
                finalList.get(i).setPoints(finalList.get(i).getPoints()/(double)high);

            return finalList;
        }

        private List<SignsAndSymptomsNode> combineSignsAndSymptoms(List<SignsAndSymptomsNode> finalList, List<SignsAndSymptomsNode> list){
            boolean found = false;
            for(int i = 0; i < list.size(); i++)
                if(finalList.size() == 0)
                    finalList.add(list.get(i));
                else{
                    for(int x = 0; x < finalList.size(); x++){
                        if(finalList.get(x).getBugName().equalsIgnoreCase(list.get(i).getBugName())){
                            finalList.get(x).setOccr(finalList.get(x).getOccr() + list.get(i).getOccr());
                            finalList.get(x).setPoints(finalList.get(x).getPoints() + list.get(i).getPoints());
                            found = true;
                            break;
                        }
                    }
                    if(found == false)
                        finalList.add(list.get(i));
                    found = false;
                }
            return finalList;
        }

        private List<SignsAndSymptomsNode> getScores(int type){
            List<SignsAndSymptomsNode> score = new ArrayList<>();
            int counter = 0;
            Log.d("Prediction:", "Type: " + type);
            if(type == 1){
                for(int a = 0; a < list.size(); a++){
                    if(list.get(a).getSigns().size() >= signs.size()){
                        for(int b = 0; b < signs.size(); b++){
                            for(int c = 0; c < list.get(a).getSigns().size(); c++){
                                if(signs.get(b) == list.get(a).getSigns().get(c).getSignsID())
                                    counter++;
                            }
                        }
                        SignsAndSymptomsNode node = new SignsAndSymptomsNode();
                        node.setKey(list.get(a).getKey());
                        node.setPoints(((double)counter)/(list.get(a).getSigns().size()));
                        node.setBugName(list.get(a).getBugName());
                        node.setOccr(1);
                        score.add(node);
                    }
                    counter = 0;
                }
            }else{
                for(int a = 0; a < list.size(); a++){
                    if(list.get(a).getSymptoms().size() >= symptoms.size()){
                        for(int b = 0; b < symptoms.size(); b++){
                            for(int c = 0; c < list.get(a).getSymptoms().size(); c++){
                                if(symptoms.get(b) == list.get(a).getSymptoms().get(c).getSymptomsID())
                                    counter++;
                            }
                        }
                        SignsAndSymptomsNode node = new SignsAndSymptomsNode();
                        node.setKey(list.get(a).getKey());
                        node.setPoints(((double)counter)/(list.get(a).getSymptoms().size()));
                        node.setBugName(list.get(a).getBugName());
                        node.setOccr(1);
                        score.add(node);
                    }
                    counter = 0;
                }
            }

            return score;
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
            Intent scanIntent = new Intent(context, Result.class);
            scanIntent.putExtra("result", displayResult);
            context.startActivity(scanIntent);
        }
    }
}
