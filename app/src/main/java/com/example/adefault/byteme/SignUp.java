package com.example.adefault.byteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    Button signIn, signUp;
    EditText username, email, password;
    FirebaseDatabase database;
    DatabaseReference ref;
    Members members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        refIDs();
        signIn.setOnClickListener(ClickListener);
        signUp.setOnClickListener(ClickListener);
    }

    private OnClickListener ClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.signInButton:
                    Intent signInIntent = new Intent(view.getContext(), Login.class);
                    signInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signInIntent);
                    finish();
                    break;

                case R.id.signUpButton:
                    members.setUsername(username.getText().toString().trim());
                    members.setEmail(email.getText().toString().trim());
                    members.setPassword(password.getText().toString().trim());
                    ref.push().setValue(members);
                    Toast.makeText(SignUp.this, "Data inserted sucessfully", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    public void refIDs(){
        signIn = (Button)findViewById(R.id.signInButton);
        signUp = findViewById(R.id.signUpButton);
        username = findViewById(R.id.usernameTextBox);
        email = findViewById(R.id.emailTextbox);
        password = findViewById(R.id.passwordTextBox);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        members = new Members();
    }
}
