package com.example.university.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.university.oop.DepartmentData;
import com.example.university.oop.FacultyData;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class addGroup extends AppCompatActivity {

    Spinner spinner_faculty,spinner_Department;
    ArrayList<FacultyData> faculties= new ArrayList<>();
    ArrayList<DepartmentData> Departments= new ArrayList<>();
    ArrayAdapter<FacultyData> adapter_faculty;
    ArrayAdapter<DepartmentData> adapter_Dep;

    FacultyData selectedFaculty;
    DepartmentData selectedDep;

    Button add;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));
        spinner_faculty = findViewById(R.id.spinner_faculty);
        spinner_Department = findViewById(R.id.spinner_Department);
        add = findViewById(R.id.add);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_getFaculties, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("faculty");
                    FacultyData zero = new FacultyData(0,"Choose");
                    if(zero!=null)
                        faculties.add(zero);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.optInt("faculty_id");
                            String name = jsonObject.optString("faculty_name");

                            FacultyData facultyData = new FacultyData(id,name);
                            if(facultyData!=null)
                                faculties.add(facultyData);
                            adapter_faculty = new ArrayAdapter<>(addGroup.this,
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

        RequestHandler.getInstance(addGroup.this).addToRequestQueue(stringRequest);


        spinner_faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Departments.clear();
                selectedFaculty = new FacultyData(0,"");
                selectedDep = new DepartmentData(0,0,"");
                selectedFaculty = (FacultyData) parent.getSelectedItem();
                Departments.clear();
                adapter_Dep = new ArrayAdapter<>(addGroup.this,
                        android.R.layout.simple_spinner_item, Departments);
                adapter_Dep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Department.setAdapter(adapter_Dep);
                if (position != 0) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_getDepartments, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Departments");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.optInt("dep_id");
                                    int id_faculty = jsonObject.optInt("faculty_id");
                                    String name = jsonObject.optString("Dep_name");

                                    DepartmentData departmentData = new DepartmentData(id, id_faculty, name);
                                    if (departmentData != null)
                                        Departments.add(departmentData);
                                    adapter_Dep = new ArrayAdapter<>(addGroup.this,
                                            android.R.layout.simple_spinner_item, Departments);
                                    adapter_Dep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_Department.setAdapter(adapter_Dep);
                                } catch (JSONException e) {
                                    DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                }
                            }
                        } catch (JSONException e) {
                            Departments.clear();
                            DesignerToast.Warning(getApplicationContext(), "Nothing here", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put("faculty_id", selectedFaculty.getId() + "");
                        return stringMap;
                    }
                };

                RequestHandler.getInstance(addGroup.this).addToRequestQueue(stringRequest);

                spinner_Department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedDep = (DepartmentData) parent.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        progressDialog = new ProgressDialog(addGroup.this);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedFaculty.getName().isEmpty()&&!selectedDep.getName().isEmpty()) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:


                                    progressDialog.show();
                                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_AddGroup, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            progressDialog.dismiss();
                                            JSONObject jsonObject = null;
                                            try {
                                                jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("message").equals("Group added successfully")) {
                                                    DesignerToast.Success(getApplicationContext(), jsonObject.getString("message"), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                                } else {
                                                    DesignerToast.Warning(getApplicationContext(), jsonObject.getString("message"), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                                }
                                            } catch (JSONException e) {
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
                                            stringMap.put("dep_id", selectedDep.getId() + "");
                                            return stringMap;
                                        }
                                    };
                                    RequestHandler.getInstance(addGroup.this).addToRequestQueue(stringRequest2);


                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(addGroup.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }else
                {
                    DesignerToast.Info(getApplicationContext(), "choose Department First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }

            }
        });


    }
}