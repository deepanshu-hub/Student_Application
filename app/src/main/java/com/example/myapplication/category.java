package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.start.catList;

public class category extends AppCompatActivity {

    private GridView catGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        catGrid = findViewById(R.id.catGridview);

        CatGridAdapter  adapter = new CatGridAdapter(catList);
        catGrid.setAdapter(adapter);
    }
}