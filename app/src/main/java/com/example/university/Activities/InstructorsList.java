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
import com.example.university.oop.AdapterCourseList;
import com.example.university.oop.AdapterInstructorList;
import com.example.university.oop.CourseData;
import com.example.university.oop.InstructorData;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstructorsList extends AppCompatActivity {

    ListView list;
    ArrayList<InstructorData> Instructors= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors_list);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));

        list = findViewById(R.id.list);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.populate_Instructors, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("instructor");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.optInt("Instructor_id");
                            int Salary = jsonObject.optInt("Salary");
                            String name = jsonObject.optString("Full_Name");
                            String Job_Tittle = jsonObject.optString("Job_Tittle");

                            InstructorData instructorData = new InstructorData(Job_Tittle,name,id,Salary);
                            if (instructorData != null)
                            {
                                Instructors.add(instructorData);
                            }

                        } catch (JSONException e) {
                            DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }


                    AdapterInstructorList adapterInstructorList = new AdapterInstructorList(InstructorsList.this, Instructors);

                    list.setAdapter(adapterInstructorList);
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

        RequestHandler.getInstance(InstructorsList.this).addToRequestQueue(stringRequest);

    }
}