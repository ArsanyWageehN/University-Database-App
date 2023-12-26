package com.example.university.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.university.R;
import com.example.university.SQL.Constants;
import com.example.university.SQL.RequestHandler;
import com.example.university.oop.FacultyData;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addClass extends AppCompatActivity {

    Spinner spinner_faculty;
    ArrayList<FacultyData> faculties= new ArrayList<>();
    ArrayAdapter<FacultyData> adapter_faculty;

    FacultyData selectedFaculty;

    Button add;
    ProgressDialog progressDialog;

    EditText Class_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));



        spinner_faculty = findViewById(R.id.spinner_faculty);
        add = findViewById(R.id.add);
        Class_text = findViewById(R.id.Class_text);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_getFaculties, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("faculty");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.optInt("faculty_id");
                            String name = jsonObject.optString("faculty_name");

                            FacultyData facultyData = new FacultyData(id,name);
                            if(facultyData!=null)
                                faculties.add(facultyData);
                            adapter_faculty = new ArrayAdapter<>(addClass.this,
                                    android.R.layout.simple_spinner_item,faculties);
                            adapter_faculty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_faculty.setAdapter(adapter_faculty);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                } catch (JSONException e) {

                    DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestHandler.getInstance(addClass.this).addToRequestQueue(stringRequest);

        spinner_faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFaculty = (FacultyData) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        progressDialog = new ProgressDialog(addClass.this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedFaculty.getName().isEmpty()&&!Class_text.getText().toString().isEmpty()) {
                    progressDialog.show();
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_add_ClassRoom, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                if (jsonObject.getString("message").equals("ClassRoom added successfully")) {
                                    DesignerToast.Success(getApplicationContext(), jsonObject.getString("message"), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                } else {
                                    DesignerToast.Warning(getApplicationContext(), jsonObject.getString("message"), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                }
                            } catch (JSONException e) {
                                DesignerToast.Error(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            DesignerToast.Error(getApplicationContext(), error.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> stringMap = new HashMap<>();
                            stringMap.put("class_type", Class_text.getText().toString());
                            stringMap.put("faculty_id", selectedFaculty.getId()+"");
                            return stringMap;
                        }
                    };
                    RequestHandler.getInstance(addClass.this).addToRequestQueue(stringRequest2);
                }else
                {
                    DesignerToast.Info(getApplicationContext(), "Enter name of ClassRoom First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }
            }
        });

    }
}