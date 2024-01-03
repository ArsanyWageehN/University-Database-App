package com.example.university.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
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
import com.example.university.oop.ClassData;
import com.example.university.oop.CourseData;
import com.example.university.oop.DepartmentData;
import com.example.university.oop.FacultyData;
import com.example.university.oop.PeriodData;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registeration_lecturer extends AppCompatActivity {

    Spinner spinner_faculty,spinner_Department,spinner_Courses,spinner_group,spinner_classes,spinner_periods;
    ArrayList<FacultyData> faculties= new ArrayList<>();
    ArrayList<DepartmentData> Departments= new ArrayList<>();
    ArrayList<CourseData> Courses= new ArrayList<>();
    ArrayList<Integer> groups= new ArrayList<>();
    ArrayList<ClassData> classes= new ArrayList<>();
    ArrayList<PeriodData> periods= new ArrayList<>();
    ArrayAdapter<FacultyData> adapter_faculty;
    ArrayAdapter<DepartmentData> adapter_Dep;
    ArrayAdapter<CourseData> adapter_crs;
    ArrayAdapter<Integer> adapter_group;
    ArrayAdapter<ClassData> adapter_class;
    ArrayAdapter<PeriodData> adapter_Period;
    int selectedGroup=0;

    FacultyData selectedFaculty;
    DepartmentData selectedDep;
    CourseData selectedCRS;
    ClassData selectedClass;
    PeriodData selectedPeriod;

    Button Register;
    ProgressDialog progressDialog;

    EditText id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_lecturer);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));


        spinner_faculty = findViewById(R.id.spinner_faculty);
        spinner_Department = findViewById(R.id.spinner_Department);
        spinner_Courses = findViewById(R.id.spinner_Courses);
        spinner_group = findViewById(R.id.spinner_groups);
        spinner_classes = findViewById(R.id.spinner_classes);
        spinner_periods = findViewById(R.id.spinner_periods);
        id = findViewById(R.id.idd);
        Register = findViewById(R.id.Register);


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
                            adapter_faculty = new ArrayAdapter<>(Registeration_lecturer.this,
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

        RequestHandler.getInstance(Registeration_lecturer.this).addToRequestQueue(stringRequest);


        spinner_faculty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedDep = new DepartmentData(0,0,"");
                selectedFaculty = (FacultyData) parent.getSelectedItem();
                Departments.clear();
                adapter_Dep = new ArrayAdapter<>(Registeration_lecturer.this,
                        android.R.layout.simple_spinner_item, Departments);
                adapter_Dep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Department.setAdapter(adapter_Dep);

                selectedClass = new ClassData(0,0,"");
                classes.clear();
                adapter_class = new ArrayAdapter<>(Registeration_lecturer.this,
                        android.R.layout.simple_spinner_item, classes);
                adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_classes.setAdapter(adapter_class);

                selectedClass = new ClassData(0,0,"");
                classes.clear();
                adapter_class = new ArrayAdapter<>(Registeration_lecturer.this,
                        android.R.layout.simple_spinner_item, classes);
                adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_classes.setAdapter(adapter_class);

                selectedGroup = 0;
                groups.clear();
                adapter_group = new ArrayAdapter<>(Registeration_lecturer.this,
                        android.R.layout.simple_spinner_item, groups);
                adapter_group.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_group.setAdapter(adapter_group);

                selectedCRS = new CourseData(0,"","",0);
                Courses.clear();
                adapter_crs = new ArrayAdapter<>(Registeration_lecturer.this,
                        android.R.layout.simple_spinner_item, Courses);
                adapter_crs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Courses.setAdapter(adapter_crs);

                selectedPeriod = new PeriodData(0,"","");
                periods.clear();
                adapter_Period = new ArrayAdapter<>(Registeration_lecturer.this,
                        android.R.layout.simple_spinner_item, periods);
                adapter_Period.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_periods.setAdapter(adapter_Period);

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
                                        adapter_Dep = new ArrayAdapter<>(Registeration_lecturer.this,
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

                    RequestHandler.getInstance(Registeration_lecturer.this).addToRequestQueue(stringRequest);



                    spinner_Department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedDep = (DepartmentData) parent.getSelectedItem();

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
                                                    Courses.add(courseData);
                                                adapter_crs = new ArrayAdapter<>(Registeration_lecturer.this,
                                                        android.R.layout.simple_spinner_item, Courses);
                                                adapter_crs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinner_Courses.setAdapter(adapter_crs);

                                            } catch (JSONException e) {
                                                DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        Courses.clear();
                                        DesignerToast.Warning(getApplicationContext(), "Nothing here", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            RequestHandler.getInstance(Registeration_lecturer.this).addToRequestQueue(stringRequest);

                            spinner_Courses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectedCRS = (CourseData) parent.getSelectedItem();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


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
                                                adapter_group = new ArrayAdapter<>(Registeration_lecturer.this,
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

                            RequestHandler.getInstance(Registeration_lecturer.this).addToRequestQueue(stringRequest2);

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


                    StringRequest stringRequest11 = new StringRequest(Request.Method.POST, Constants.URL_getPeriods, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("table_period");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        jsonObject = jsonArray.getJSONObject(i);
                                        int id = jsonObject.optInt("Period_id");
                                        String from = jsonObject.optString("From_T");
                                        String to = jsonObject.optString("To_T");

                                        PeriodData periodData = new PeriodData(id, from, to);
                                        if (periodData != null)
                                            periods.add(periodData);
                                        adapter_Period = new ArrayAdapter<>(Registeration_lecturer.this,
                                                android.R.layout.simple_spinner_item, periods);
                                        adapter_Period.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinner_periods.setAdapter(adapter_Period);
                                    } catch (JSONException e) {
                                        DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                    }
                                }
                            } catch (JSONException e) {
                                periods.clear();
                                DesignerToast.Warning(getApplicationContext(), "Nothing here", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    RequestHandler.getInstance(Registeration_lecturer.this).addToRequestQueue(stringRequest11);

                    spinner_periods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedPeriod= (PeriodData) parent.getSelectedItem();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_getClasses, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("classroom");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                         jsonObject = jsonArray.getJSONObject(i);
                                        int id = jsonObject.optInt("class_number");
                                        int id_faculty = jsonObject.optInt("faculty_id");
                                        String name = jsonObject.optString("class_type");

                                        ClassData classData = new ClassData(id, id_faculty, name);
                                        if (classData != null)
                                            classes.add(classData);
                                        adapter_class = new ArrayAdapter<>(Registeration_lecturer.this,
                                                android.R.layout.simple_spinner_item, classes);
                                        adapter_class.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinner_classes.setAdapter(adapter_class);
                                    } catch (JSONException e) {
                                        DesignerToast.Warning(getApplicationContext(), e.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                    }
                                }
                            } catch (JSONException e) {
                                classes.clear();
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

                    RequestHandler.getInstance(Registeration_lecturer.this).addToRequestQueue(stringRequest2);

                    spinner_classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedClass= (ClassData) parent.getSelectedItem();
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


        progressDialog = new ProgressDialog(Registeration_lecturer.this);



        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedFaculty.getName().isEmpty()&&!selectedDep.getName().isEmpty()
                        &&!selectedCRS.getCrs_name().isEmpty()
                        &&!selectedClass.getType().isEmpty()
                        &&selectedGroup!=0
                        &&!selectedPeriod.getFrom().isEmpty()) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    progressDialog.show();
                                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_add_lecture, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            progressDialog.dismiss();
                                            JSONObject jsonObject = null;
                                            try {
                                                jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("message").equals("Lecture registered successfully")) {
                                                    DesignerToast.Success(getApplicationContext(), jsonObject.getString("message"), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                                } else {
                                                    DesignerToast.Warning(getApplicationContext(), jsonObject.getString("message"), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                                }
                                            } catch (JSONException e) {
                                                DesignerToast.Warning(getApplicationContext(), "maybe it added already or wrong information", Gravity.BOTTOM, Toast.LENGTH_SHORT);
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
                                            stringMap.put("faculty_id", selectedFaculty.getId() + "");
                                            stringMap.put("crs_code", selectedCRS.getCrs_code() + "");;
                                            stringMap.put("Period_id", selectedPeriod.getId() + "");
                                            stringMap.put("class_number", selectedClass.getId() + "");
                                            stringMap.put("group_id", selectedGroup + "");
                                            stringMap.put("Instructor_id", id.getText().toString());
                                            return stringMap;
                                        }
                                    };
                                    RequestHandler.getInstance(Registeration_lecturer.this).addToRequestQueue(stringRequest2);


                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(Registeration_lecturer.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }else
                {
                    DesignerToast.Info(getApplicationContext(), "choose Information First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }

            }
        });
    }
}