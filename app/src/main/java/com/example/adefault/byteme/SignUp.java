package com.example.adefault.byteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    Button signIn, signUp;
    EditText username, email, password;

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
    }
}
