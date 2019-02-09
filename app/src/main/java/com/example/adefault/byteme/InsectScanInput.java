package com.example.adefault.byteme;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InsectScanInput extends AppCompatActivity {

    private InsectNode head;

    private PredictionServiceClient predictionClient;
    private static final int CAMERA_IMAGE_REQUEST = 1;
    private static Bitmap[] result = new Bitmap[5];
    private ImageView selectedImage;
    private Button scanInsect, uploadImage, process;
    private static String displayResult = "";
    private boolean Camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_scan_input);

        refIDs();

        scanInsect.setOnClickListener(ClickListener);
        uploadImage.setOnClickListener(ClickListener);
        process.setOnClickListener(ClickListener);
    }

    private OnClickListener ClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.scanInsectButton:
                    StartCamera();
                    break;

                case R.id.uploadImage:
                    OpenGallery();
                    break;

                case R.id.process:
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[][] byteArray = new byte[3][];
                    for(int i = 0; i < 3; i++){
                        result[i].compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byteArray[i] = byteArrayOutputStream.toByteArray();
                    }
                    Prediction(byteArray);
                    break;
            }
        }
    };

    public static String GetResult(){
        return displayResult;
    }

    public void OpenGallery(){
        Camera = false;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    public void StartCamera() {
        Camera=true;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmapInsect;
        if(Camera){
            if(requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                assert extras != null;
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                for(int i = 0; i < 3; i++)
                    result[i] = imageBitmap;
                selectedImage.setImageBitmap(imageBitmap);
            }
        }
        else{
            if(resultCode == RESULT_OK){
                Uri photoUri = data.getData();
                if (photoUri != null) {
                    try {
                        bitmapInsect = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                        for(int i = 0; i < 3; i++)
                            result[i] = bitmapInsect;
                        selectedImage.setImageBitmap(bitmapInsect);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class LongOperation extends AsyncTask<byte[][], String, String> {
        private Context context;
        private ProgressDialog progressDialog;

        LongOperation(Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected String doInBackground(byte[][]... bytes) {
            String result;
            float confidence;

            try {
                InputStream stream = getResources().openRawResource(R.raw.credentials);
                GoogleCredentials credentials = GoogleCredentials.fromStream(stream);
                PredictionServiceSettings.Builder settingsBuilder = PredictionServiceSettings.newBuilder();
                PredictionServiceSettings predictionSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
                predictionClient = PredictionServiceClient.create(predictionSettings);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i = 0; i < 3; i++){
                ModelName name = ModelName.of("bytemev1", "us-central1", "ICN6559731632964049689");
                ByteString content = ByteString.copyFrom(bytes[0][i]);
                Image image = Image.newBuilder().setImageBytes(content).build();
                ExamplePayload examplePayload = ExamplePayload.newBuilder().setImage(image).build();

                Map<String, String> params = new HashMap<>();
                params.put("score_threshold", "0.80");

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

        void ConcatResult(){
            displayResult = "";
            InsectNode temp;
            for(temp = head; temp != null; temp = temp.GetNexLink()){
                displayResult += "\n\nInsect Name: " + temp.GetInsectName()
                        + "\nConfidence: " + temp.GetConfidence();
            }
            if(displayResult.equalsIgnoreCase(""))
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
            ConcatResult();
            head = null;
            Intent scanIntent = new Intent(context, InsectScan.class);
            startActivity(scanIntent);
        }
    }

    public void Prediction(byte[][] imageBytes){
        new LongOperation(this).execute(imageBytes);
    }

    private void refIDs(){
        scanInsect = findViewById(R.id.scanInsectButton);
        selectedImage = findViewById(R.id.insectImage);
        uploadImage = findViewById(R.id.uploadImage);
        process = findViewById(R.id.process);
    }
}
