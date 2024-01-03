package com.example.university.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.university.R;
import com.example.university.SQL.Constants;
import com.example.university.SQL.RequestHandler;
import com.example.university.oop.AdapterInstructorList;
import com.example.university.oop.AdapterStudentsList;
import com.example.university.oop.InstructorData;
import com.example.university.oop.StudentData;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudentsList extends AppCompatActivity {

    ListView list;
    ArrayList<StudentData> Students = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));


        list = findViewById(R.id.list);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.populate_Students, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("student");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            int stu_id = jsonObject.optInt("stu_id");
                            int dep_id = jsonObject.optInt("dep_id");
                            int group_id = jsonObject.optInt("group_id");
                            int stu_gpa = jsonObject.optInt("stu_gpa");
                            String gender = jsonObject.optString("gender");
                            String std_name = jsonObject.optString("std_name");
                            String Nationality = jsonObject.optString("Nationality");
                            String Religion = jsonObject.optString("Religion");
                            String city = jsonObject.optString("city");
                            String country = jsonObject.optString("country");


                            StudentData studentData = new StudentData(std_name,gender,Nationality,city,country,Religion,stu_id,dep_id,group_id,stu_gpa);
                            if (studentData != null)
                            {
                                Students.add(studentData);
                            }

                        } catch (JSONException e) {
                            DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }


                    AdapterStudentsList adapterStudentsList = new AdapterStudentsList(StudentsList.this, Students);

                    list.setAdapter(adapterStudentsList);
                } catch (JSONException e) {
                    DesignerToast.Info(getApplicationContext(), "Nothing here", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                    onBackPressed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestHandler.getInstance(StudentsList.this).addToRequestQueue(stringRequest);

    }
}