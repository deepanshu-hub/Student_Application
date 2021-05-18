package com.example.myapplication.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

public class flutter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter);
    }

    public void register(View view) {
        Uri uriUrl = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSfDEoBypJ4kkwDcqn8LetIzR9fQbcBpBr3JiWWCKlXkWFyjXw/viewform");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}