package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth auth;
     EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.etEmailAddress);
        password = findViewById(R.id.etPassword);
    }
    public void onStart() {

        super.onStart();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, start.class));
            finish();
        }
    }
     public void login(View view) {
        String lemail = email.getText().toString();
      final  String lpass = password.getText().toString();

         if (TextUtils.isEmpty(lemail)) {
             Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
             return;
         }
         if (TextUtils.isEmpty(lpass)) {
             Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
             return;
         }

         auth.signInWithEmailAndPassword(lemail, lpass)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Authentication failed" + task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(Login.this, "Login Succesfull", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, start.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                );
     }

    public void onSignUp(View view) {
        startActivity(new Intent(this, Signup.class));
        finish();
    }
}