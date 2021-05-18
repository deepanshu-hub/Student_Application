package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Signup extends AppCompatActivity {

       FirebaseAuth firebaseAuth;
      EditText email, password, confirmPassword, year, branch;
   // ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
      firebaseAuth = FirebaseAuth.getInstance();
      email = findViewById(R.id.etEmailAddress);
      password = findViewById(R.id.etPassword);
      confirmPassword = findViewById(R.id.etConfirmPassword);
      year = findViewById(R.id.year);
      branch = findViewById(R.id.branch);

    }
//    protected void onResume() {
//        super.onResume();
//        progressBar.setVisibility(View.GONE);
//    }
    public void onRegisterClicked(View view) {

        //Fetching data
        String emailInput = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cnpass = confirmPassword.getText().toString().trim();
        String yr = year.getText().toString().trim();
        String br = branch.getText().toString().trim();

        if (TextUtils.isEmpty(emailInput)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(yr)) {
            Toast.makeText(getApplicationContext(), "Enter Year!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(br)) {
            Toast.makeText(getApplicationContext(), "Enter Branch!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!cnpass.equals(pass)) {
            Toast.makeText(getApplicationContext(), "password and confirm password not matched", Toast.LENGTH_SHORT).show();
            return;
        }
    //    progressBar.setVisibility(View.VISIBLE);
        //create user with email/password by adding complete listener
        firebaseAuth.createUserWithEmailAndPassword(emailInput, pass)
                .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(Signup.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    //   progressBar.setVisibility(View.GONE);

                        // If sign-in fails, display a message to the user. If sign-in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Signup.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                            Log.e("MyTag", task.getException().toString());
                        } else {
                            Toast.makeText(Signup.this, "Sign up successfull", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Signup.this, start.class);
                            startActivity(intent);
                            finish();
                            // startActivity(new Intent(MainActivity.this, Sign_out.class));
                        }
                    }
                });
    }

    public void onLogin(View view) {
        startActivity(new Intent(this, Login.class));
        finish();
    }

}
