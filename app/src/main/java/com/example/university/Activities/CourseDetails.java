package com.example.university.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.university.R;
import com.example.university.oop.CourseData;

public class CourseDetails extends AppCompatActivity {

    TextView Name,desc,credits,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));

        Name=findViewById(R.id.Name);
        desc=findViewById(R.id.desc);
        credits=findViewById(R.id.credits);
        id=findViewById(R.id.id);

        CourseData myObject = (CourseData) getIntent().getSerializableExtra("key");

        Name.setText("Course Name: "+myObject.getCrs_name()+"");
        desc.setText("Course Description: "+myObject.getCrs_desc()+"");
        credits.setText("Course Credits: "+myObject.getCredits()+"");
        id.setText("Course ID: "+myObject.getCrs_code()+"");



    }
}