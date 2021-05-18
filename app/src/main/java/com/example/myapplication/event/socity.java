package com.example.myapplication.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

public class socity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socity);
    }
    public void trinity(View view) {
        Intent intent = new Intent(this, trinity.class);
        startActivity(intent);
    }

    public void flutter(View view) {
        Intent intent = new Intent(this, flutter.class);
        startActivity(intent);
    }

    public void codethon(View view) {
        Intent intent = new Intent(this, codethon.class);
        startActivity(intent);
    }
}