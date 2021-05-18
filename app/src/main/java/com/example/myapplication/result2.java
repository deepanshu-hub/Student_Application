package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import static android.graphics.Color.GREEN;

public class result2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        Intent intent1 = getIntent();
        String s1 = intent1.getStringExtra(MainActivity.cal);
        String s2 = intent1.getStringExtra(MainActivity.skClass);
        String s3 = intent1.getStringExtra(MainActivity.l);
        String s4 = intent1.getStringExtra(MainActivity.fpercent);

        TextView textView = findViewById(R.id.textView14);
        textView.setText(s1);
        textView.setTextColor(Color.RED);

        TextView textView1 = findViewById(R.id.textView16);
        textView1.setText(s2);

        TextView textView2 = findViewById(R.id.textView18);
        textView2.setText(s3);

        TextView textView3 = findViewById(R.id.textView20);
        textView3.setText(s4 + "%");
        textView3.setTextColor(GREEN);

        TextView textView4 = findViewById(R.id.textView21);
        textView4.setText("assuming there would be " +  s2 +" more class(es).");
    }
}