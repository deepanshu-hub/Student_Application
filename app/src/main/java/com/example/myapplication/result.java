package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String s1 = intent.getStringExtra(MainActivity.cal);
        String s2 = intent.getStringExtra(MainActivity.skClass);
        String s3 = intent.getStringExtra(MainActivity.l);
        String s4 = intent.getStringExtra(MainActivity.fpercent);

        TextView textView = findViewById(R.id.textView6);
        textView.setText(s1);
        textView.setTextColor(Color.GREEN);

        TextView textView1 = findViewById(R.id.textView8);
        textView1.setText(s2);

        TextView textView2 = findViewById(R.id.textView10);
        textView2.setText(s3);

        TextView textView3 = findViewById(R.id.textView12);
        textView3.setText("75%");
        textView3.setTextColor(Color.GREEN);
    }
}
