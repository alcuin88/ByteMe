package com.example.adefault.byteme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import android.view.View;

public class InsectScanInput extends AppCompatActivity {

    private Map<String, String> params;
    private  ExamplePayload examplePayload;
    private  ModelName name;
    private PredictResponse response;
    private PredictionServiceClient predictionClient;
    private ByteArrayOutputStream byteArrayOutputStream;
    private static final int CAMERA_IMAGE_REQUEST = 1;
    private static String res = "";
    private Bitmap bitmapInsect;
    private ImageView selectedImage;
    private Button scanInsect, uploadImage, process;
    private TextView result;
    private byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_scan_input);

        refIDs();

        scanInsect.setOnClickListener(ClickListener);
        uploadImage.setOnClickListener(ClickListener);
        process.setOnClickListener(ClickListener);
    }

    public static String GetResult(){
        return res;
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
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmapInsect.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream .toByteArray();
                    Predict(byteArray);
                    break;
            }
        }
    };

    public void OpenGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    public void StartCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri photoUri = data.getData();
            if (photoUri != null) {
                try {
                    bitmapInsect = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                    selectedImage.setImageBitmap(bitmapInsect);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            bitmapInsect = imageBitmap;
//            selectedImage.setImageBitmap(imageBitmap);
//        }
//    }

    public void Predict(byte[] imageBytes){
        try {
            InputStream stream = getResources().openRawResource(R.raw.credentials);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream);

            PredictionServiceSettings.Builder settingsBuilder = PredictionServiceSettings.newBuilder();
            PredictionServiceSettings predictionSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
            predictionClient = PredictionServiceClient.create(predictionSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }

        name = ModelName.of("bytemev1", "us-central1", "ICN6559731632964049689");
        ByteString content = ByteString.copyFrom(imageBytes);
        Image image = Image.newBuilder().setImageBytes(content).build();
        examplePayload = ExamplePayload.newBuilder().setImage(image).build();

        params = new HashMap<>();
        params.put("score_threshold", "0.80");

        response = predictionClient.predict(name, examplePayload, params);
        res = "";
        for (AnnotationPayload annotationPayload : response.getPayloadList()) {
            res += "\nPredicted class name :" + annotationPayload.getDisplayName();
            res += "\nPredicted class name :" + annotationPayload.getClassification().getScore();
        }
        if(res.length() == 0)
            res = "No Results!";

        Intent scanIntent = new Intent(this, InsectScan.class);
        startActivity(scanIntent);
    }

    private void refIDs(){
        scanInsect = findViewById(R.id.scanInsectButton);
        selectedImage = findViewById(R.id.insectImage);
        uploadImage = findViewById(R.id.uploadImage);
        result = findViewById(R.id.result);
        process = findViewById(R.id.process);
    }
}
