package com.example.adefault.bytemeAPP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button signUp, signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        refIDs();
        signUp.setOnClickListener(SignUpButton);
        signIn.setOnClickListener(SignInButton);
    }

    private OnClickListener SignUpButton = view -> {
        if(view.getId() == R.id.signUpButton){
            Intent signUpIntent = new Intent(view.getContext(), SignUp.class);
            startActivity(signUpIntent);
        }
    };

    private OnClickListener SignInButton = view -> {
        if(view.getId() == R.id.signInButton){
            Intent signInIntent = new Intent(view.getContext(), Dashboard.class);
            startActivity(signInIntent);
        }
    };

    public void refIDs(){
        signUp = findViewById(R.id.signUpButton);
        signIn = findViewById(R.id.signInButton);
    }
}
