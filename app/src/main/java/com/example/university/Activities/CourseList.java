package com.example.university.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.university.R;
import com.example.university.SQL.Constants;
import com.example.university.SQL.RequestHandler;
import com.example.university.oop.AdapterCourseList;
import com.example.university.oop.CourseData;
import com.example.university.oop.PeriodData;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CourseList extends AppCompatActivity {

    ListView list;
    ArrayList<CourseData> Courses= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));

        list = findViewById(R.id.list);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_getCourses, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("course");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.optInt("crs_code");
                            int credits = jsonObject.optInt("credits");
                            String name = jsonObject.optString("crs_name");
                            String crs_desc = jsonObject.optString("crs_desc");

                            CourseData courseData = new CourseData(id,name,crs_desc,credits);
                            if (courseData != null)
                            {
                            Courses.add(courseData);
                            }

                        } catch (JSONException e) {
                            DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }
                    AdapterCourseList adapterCourseList = new AdapterCourseList(CourseList.this, Courses);

                    list.setAdapter(adapterCourseList);
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

        RequestHandler.getInstance(CourseList.this).addToRequestQueue(stringRequest);





    }
}