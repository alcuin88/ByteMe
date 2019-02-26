package com.example.adefault.bytemeV3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adefault.bytemeV3.Adapters.SignsSpinnerAdapter;
import com.example.adefault.bytemeV3.databaseObjects.SignsResponse;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BiteScanInput extends AppCompatActivity {

    private String modelID = "";
    private String projectID;
    private static Bitmap[] result = new Bitmap[5];
    private ImageView selectedImage;
    private Button scanInsectBite, uploadImage, process;
    private Spinner spinner;
    private ArrayList<SignsResponse> signsResponses;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bite_scan_input);

        refIDs();

        scanInsectBite.setOnClickListener(ClickListener);
        uploadImage.setOnClickListener(ClickListener);
        process.setOnClickListener(ClickListener);
        populateSignsSpinner();
    }

    public void populateSignsSpinner(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Signs");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                signsResponses = new ArrayList<>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    SignsResponse value = dataSnapshot1.getValue(SignsResponse.class);
                    SignsResponse signsResponse = new SignsResponse();
                    signsResponse.setName(value.getName());
                    signsResponse.setSelected(false);
                    signsResponses.add(signsResponse);
                }
                SignsSpinnerAdapter myAdapter = new SignsSpinnerAdapter(BiteScanInput.this, 1,
                        signsResponses);
                spinner.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
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
                process.main(view.getContext(), byteArray, getCredentials(), modelID, projectID);
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
        int maxSize = 500;
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
        scanInsectBite = findViewById(R.id.scanBiteButton);
        selectedImage = findViewById(R.id.biteImage);
        uploadImage = findViewById(R.id.uploadImage);
        process = findViewById(R.id.process);
        modelID = getResources().getString(R.string.bite_model);
        projectID = getResources().getString(R.string.proj_id);
        spinner = findViewById(R.id.spinner);
        signsResponses = new ArrayList<>();
    }
}
