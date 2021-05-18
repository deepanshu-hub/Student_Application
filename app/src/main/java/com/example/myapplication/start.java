package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.event.socity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class start extends AppCompatActivity {

    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        firestore = FirebaseFirestore.getInstance();

    }
    public void onClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void ipecLive(View view) {
        goToUrl ();
    }
    private void goToUrl() {
        Uri uriUrl = Uri.parse("http://ipeclive.ipec.org.in/");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,Login.class));
        finish();
    }

    public void quiz(View view) {
        loadData();
    }
    private void loadData() {
        catList.clear();

        firestore.collection("QUIZ").document("Catogries")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();

                    if(doc.exists()) {
                        long count = (long)doc.get("COUNT");

                        for(int i=1; i<= count; i++) {
                            String catName = doc.getString("CAT" + String.valueOf(i));
                            catList.add(catName);
                        }
                        Intent intent = new Intent( start.this, category.class);
                       startActivity(intent);

                    }
                    else {
                        Toast.makeText(start.this, "No Category document exists", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    Toast.makeText(start.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void result(View view) {
        goToResult ();
    }
    private void goToResult() {
        Uri uriUrl = Uri.parse("https://erp.aktu.ac.in/webpages/oneview/oneview.aspx");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void events(View view) {
        Intent intent = new Intent(this, socity.class);
        startActivity(intent);
    }


}