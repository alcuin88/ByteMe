package com.example.adefault.bytemeV3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.auth.oauth2.GoogleCredentials;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InsectScanInput extends AppCompatActivity {

    private String modelID = "";
    private String projectID;
    private static Bitmap[] result = new Bitmap[5];
    private ImageView selectedImage;
    private Button scanInsect, uploadImage, process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_scan_input);

        refIDs();

        scanInsect.setOnClickListener(ClickListener);
        uploadImage.setOnClickListener(ClickListener);
        process.setOnClickListener(ClickListener);
    }

    private OnClickListener ClickListener = view -> {
        switch(view.getId()){
            case R.id.scanBiteButton:
                StartCamera();
                break;

            case R.id.uploadImage:
                OpenGallery();
                break;

            case R.id.process:
                byte[][] byteArray = new byte[3][];
                for(int i = 0; i < 3; i++){
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    result[i].compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byteArray[i] = byteArrayOutputStream.toByteArray();
                }
                PredictionProcess process = new PredictionProcess();
                process.main(view.getContext(), byteArray, getCredentials(), modelID, projectID, null, null, null);
                break;
        }
    };

    public GoogleCredentials getCredentials(){
        try{
           InputStream stream = getResources().openRawResource(R.raw.credentials);
            return GoogleCredentials.fromStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void OpenGallery(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoPickerIntent, 1);
    }

    public void StartCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmapInsect;
        int maxSize = 100;
        if(requestCode == 0&& resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            assert extras != null;
            bitmapInsect = (Bitmap) extras.get("data");
            selectedImage.setImageBitmap(bitmapInsect);
            bitmapInsect = getResizedBitmap(bitmapInsect, maxSize);
            for(int i = 0; i < 3; i++)
                result[i] = bitmapInsect;
        }
        else if (requestCode == 1&& resultCode == RESULT_OK){
            try {
                Uri imageUri = data.getData();
                selectedImage.setImageURI(imageUri);
                bitmapInsect = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                bitmapInsect = getResizedBitmap(bitmapInsect, maxSize);
                for(int i = 0; i < 3; i++)
                    result[i] = bitmapInsect;
            } catch (IOException e) {
                Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void refIDs(){
        scanInsect = findViewById(R.id.scanBiteButton);
        selectedImage = findViewById(R.id.biteImage);
        uploadImage = findViewById(R.id.uploadImage);
        process = findViewById(R.id.process);
        modelID = getResources().getString(R.string.bug_model);
        projectID = getResources().getString(R.string.proj_id);
    }
}
