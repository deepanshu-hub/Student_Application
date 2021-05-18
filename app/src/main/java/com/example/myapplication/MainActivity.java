package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.String.*;

public class MainActivity extends AppCompatActivity {
    public static final  String cal = "calulation.main" ;
    public static final  String skClass = "skipClasses.main" ;
    public static final  String l = "Leaves.main" ;
    public static final  String fpercent = "FPercentage.main" ;


    EditText  ta, presents, days;
    String calculation, skipClasses, leaves, fPercent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView2 = findViewById(R.id.textView2);
        textView2.setTextColor(Color.BLACK);

        TextView textView3 = findViewById(R.id.textView3);
        textView3.setTextColor(Color.BLACK);

        TextView textView4 = findViewById(R.id.textView4);
        textView4.setTextColor(Color.BLACK);


        ta = findViewById(R.id.editTextTextPersonName2);
        presents = findViewById(R.id.editText);
        days = findViewById(R.id.editTextTextPersonName);
    }
    public void activity(View view ) {
       String s1 = ta.getText().toString();
       String s2 = presents.getText().toString();
       String s3 = days.getText().toString();
        Intent intent = new Intent(this, result.class);
        Intent intent1 = new Intent(this, result2.class);


        if(s1.equals("")) {
            ta.setError("Invalid Input");
            ta.requestFocus();
            return;
        }

       if (s2.equals("")) {
            presents.setError("Invalid Input");
            presents.requestFocus();
            return;
        }

        if (s3.equals("")) {
            days.setError("Invalid Input");
            days.requestFocus();
            return;
        }

        float taValue = Float.parseFloat(s1);
        float presentsValue = Float.parseFloat(s2);
        int d = Integer.parseInt(s3);
        double per = (presentsValue / taValue )*100;
        String percent = format("%.2f", per);
        float percentage = Float.parseFloat(percent);


        if(presentsValue > taValue ) {
            presents.setError("Invalid Input");
            presents.requestFocus();
            return;
        }
        calculation = percentage + "%";
        intent.putExtra(cal, calculation);
        intent1.putExtra(cal, calculation);
        if( percentage >= 75 && percentage <= 100) {
            int c=0;
            double p=75;
            while(percentage > 76) {
                taValue = taValue + 1;
                c++;
                percentage = (presentsValue / taValue) * 100;
                 p = percentage;
            }
            skipClasses =  c + "";
            leaves = (c/d )+ " days " + c%d + " lectures";
            fPercent = format("%.2f", p) + "";
            intent.putExtra(skClass, skipClasses);
            intent.putExtra(l,leaves );
            intent.putExtra(fpercent, fPercent);
            startActivity(intent);
        }

        else if(percentage < 75) {
            int c = 0;
            while (percentage < 75) {
                taValue = taValue + 1;
                presentsValue++;
                c++;
                percentage = (presentsValue / taValue) * 100;

            }
            skipClasses =  c + "";
            leaves = (c/d )+ " days " + c%d + " lectures";
            fPercent = percentage + "";
            intent1.putExtra(skClass, skipClasses);
            intent1.putExtra(l,leaves );
            intent1.putExtra(fpercent, fPercent);
            startActivity(intent1);
        }


    }

    public void reset(View view) {
        presents.setText("");
        ta.setText("");
        days.setText("");
    }
}
