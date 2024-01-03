package com.example.university.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.university.R;
import com.example.university.oop.CourseData;
import com.example.university.oop.InstructorData;

public class InstuctorDetails extends AppCompatActivity {

    TextView Name,Job_Tittle,salary,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instuctor_details);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));

        Name=findViewById(R.id.Name);
        Job_Tittle=findViewById(R.id.Job_Tittle);
        salary=findViewById(R.id.Salary);
        id=findViewById(R.id.id);

        InstructorData myObject = (InstructorData) getIntent().getSerializableExtra("key");

        Name.setText("Full Name: "+myObject.getFull_Name()+"");
        Job_Tittle.setText("Job Tittle: "+myObject.getJob_Tittle()+"");
        salary.setText("Salary: "+myObject.getSalary()+"");
        id.setText("Instructor ID: "+myObject.getInstructor_id()+"");

    }
}