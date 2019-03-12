package com.example.adefault.bytemeV3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adefault.bytemeV3.databaseObjects.UserObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "SignUp";
    private Button signIn, signUp;
    private EditText email, password, confrimPassword;
    private TextView error_message;
    private String[] e_message;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        refIDs();
        signIn.setOnClickListener(ClickListener);
        signUp.setOnClickListener(ClickListener);
        error_message.setVisibility(View.GONE);
    }

    private OnClickListener ClickListener = view -> {
        switch(view.getId()){
            case R.id.logIn:
                Intent signInIntent = new Intent(view.getContext(), Login.class);
                signInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signInIntent);
                finish();
                break;

            case R.id.signUp:
                signUp();
                break;
        }
    };

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm() || !checkPassword()) {
            return;
        }

        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        onAuthSuccess(task.getResult().getUser());
                    } else {
                        Toast.makeText(SignUp.this, "Sign Up Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Required");
            result = false;
        } else {
            password.setError(null);
        }

        if (TextUtils.isEmpty(confrimPassword.getText().toString())) {
            confrimPassword.setError("Required");
            result = false;
        } else {
            confrimPassword.setError(null);
        }

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Required");
            result = false;
        } else {
            email.setError(null);
        }

        return result;
    }

    public boolean checkPassword(){
        String pass1, pass2;
        pass1 = password.getText().toString().trim();
        pass2 = confrimPassword.getText().toString().trim();
        if(pass1.equalsIgnoreCase(pass2)) {
            if(pass1.length() >= 8){
                error_message.setVisibility(View.GONE);
                return true;
            }
            else{
                error_message.setText(e_message[2]);
                error_message.setVisibility(View.VISIBLE);
                return false;
            }
        }
        else{
            error_message.setText(e_message[1]);
            error_message.setVisibility(View.VISIBLE);
            return false;
        }
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        Intent signInIntent = new Intent(SignUp.this, Login.class);
        signInIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(signInIntent);
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        UserObject user = new UserObject();
        user.setUserName(name);
        user.setEmailAddress(email);
        user.setPassword(password.getText().toString());

        mDatabase.child("users").child(userId).setValue(user);
    }

    public void refIDs(){
        signIn = findViewById(R.id.logIn);
        signUp = findViewById(R.id.signUp);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        confrimPassword = findViewById(R.id.confirmPassword);
        error_message = findViewById(R.id.error_message);
        e_message = new String[]{
                "Username not available.",
                "Password doesn't match.",
                "Password must be atleast 8 characters."
        };
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

    }
}
