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
import com.example.university.oop.DepartmentData;
import com.example.university.oop.FacultyData;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class add_student extends AppCompatActivity {


    Spinner spinner_gender;
    String selected_gender;

    EditText Name,Nationality,Religion,country,city;

    Spinner spinner_faculty,spinner_Department,spinner_group;
    ArrayList<FacultyData> faculties= new ArrayList<>();
    ArrayList<DepartmentData> Departments= new ArrayList<>();
    ArrayList<Integer> groups= new ArrayList<>();
    ArrayAdapter<FacultyData> adapter_faculty;
    ArrayAdapter<DepartmentData> adapter_Dep;
    ArrayAdapter<Integer> adapter_group;

    FacultyData selectedFaculty;
    DepartmentData selectedDep;
    int selectedGroup=0;

    Button add;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));


        Name =findViewById(R.id.Name);
        Nationality =findViewById(R.id.Nationality);
        Religion =findViewById(R.id.Religion);
        country =findViewById(R.id.country);
        city =findViewById(R.id.city);
        spinner_group =findViewById(R.id.spinner_group);
        add =findViewById(R.id.Add);
        spinner_gender =findViewById(R.id.spinner_gender);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Male");
        arrayList.add("Female");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(add_student.this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(arrayAdapter);
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_gender = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        spinner_faculty = findViewById(R.id.spinner_faculty);
        spinner_Department = findViewById(R.id.spinner_dep);


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
                            adapter_faculty = new ArrayAdapter<>(add_student.this,
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

        RequestHandler.getInstance(add_student.this).addToRequestQueue(stringRequest);

        spinner_faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                groups.clear();
                adapter_group = new ArrayAdapter<>(add_student.this,
                        android.R.layout.simple_spinner_item, groups);
                adapter_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_group.setAdapter(adapter_group);
                Departments.clear();
                adapter_Dep = new ArrayAdapter<>(add_student.this,
                        android.R.layout.simple_spinner_item, Departments);
                adapter_Dep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Department.setAdapter(adapter_Dep);

                if (position == 0) {
                    selectedFaculty = new FacultyData(0,"");
                    selectedDep = new DepartmentData(0,0,"");
                    selectedGroup = 0;
                } else
                {
                    selectedFaculty = (FacultyData) parent.getSelectedItem();

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
                                        adapter_Dep = new ArrayAdapter<>(add_student.this,
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

                    RequestHandler.getInstance(add_student.this).addToRequestQueue(stringRequest);

                    spinner_Department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDep = (DepartmentData) parent.getSelectedItem();

                            groups.clear();
                            adapter_group = new ArrayAdapter<>(add_student.this,
                                    android.R.layout.simple_spinner_item, groups);
                            adapter_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_group.setAdapter(adapter_group);


                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_getGroups, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("group_std");

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            try {
                                                jsonObject = jsonArray.getJSONObject(i);
                                                int group_id = jsonObject.optInt("group_id");
                                                int dep_id = jsonObject.optInt("dep_id");

                                                groups.add(group_id);
                                                adapter_group = new ArrayAdapter<>(add_student.this,
                                                        android.R.layout.simple_spinner_item, groups);
                                                adapter_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinner_group.setAdapter(adapter_group);
                                            } catch (JSONException e) {
                                                DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        groups.clear();
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
                                    stringMap.put("dep_id", selectedDep.getId() + "");
                                    return stringMap;
                                }
                            };

                            RequestHandler.getInstance(add_student.this).addToRequestQueue(stringRequest2);

                            spinner_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedGroup = Integer.parseInt(parent.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });



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



        progressDialog = new ProgressDialog(add_student.this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name = Name.getText().toString();
                String nationality = Nationality.getText().toString();
                String religion = Religion.getText().toString();
                String Country = country.getText().toString();
                String City = city.getText().toString();
                if (!name.isEmpty()&&!nationality.isEmpty()
                        &&!religion.isEmpty()&&!Country.isEmpty()&&!City.isEmpty()
                &&!selected_gender.isEmpty() &&!selectedDep.getName().isEmpty() &&!selectedFaculty.getName().isEmpty()&&selectedGroup!=0) {
                        progressDialog.show();
                        StringRequest stringReques = new StringRequest(Request.Method.POST, Constants.URL_Add_Student, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("message").equals("Student added successfully")) {
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
                                stringMap.put("std_name", name);
                                stringMap.put("Nationality", nationality);
                                stringMap.put("Religion", religion);
                                stringMap.put("city", City);
                                stringMap.put("country", Country);
                                stringMap.put("group_id", selectedGroup+"");
                                stringMap.put("dep_id", selectedDep.getId()+"");
                                stringMap.put("gender", selected_gender+"");
                                return stringMap;
                            }
                        };
                        RequestHandler.getInstance(add_student.this).addToRequestQueue(stringReques);
                    }else
                    {
                        DesignerToast.Info(getApplicationContext(), "Enter information of Lecturer First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        progressDialog.dismiss();
                    }
            }
        });

    }
}