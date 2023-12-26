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
import com.example.university.SQL.Constants;
import com.example.university.R;
import com.example.university.SQL.RequestHandler;
import com.vdx.designertoast.DesignerToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_Lecturer extends AppCompatActivity {

    EditText Name,salary,ID;

    Spinner spinner_Job;
    String selected_Job;
    Button Add,get,Update;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));

        spinner_Job =findViewById(R.id.spinner_Job);
        Name =findViewById(R.id.Name);
        salary =findViewById(R.id.salary);
        Add =findViewById(R.id.Add);
        ID =findViewById(R.id.ID);
        get =findViewById(R.id.get);
        Update =findViewById(R.id.Update);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Professor");
        arrayList.add("Assistant");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Add_Lecturer.this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Job.setAdapter(arrayAdapter);
        spinner_Job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_Job = parent.getItemAtPosition(position).toString();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        progressDialog = new ProgressDialog(Add_Lecturer.this);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name = Name.getText().toString();
                if(!salary.getText().toString().isEmpty()) {
                    int salary_int = Integer.parseInt(salary.getText().toString());
                    if (!name.isEmpty()) {
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Lecturer, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("message").equals("Lecturer added successfully")) {
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
                                stringMap.put("Full_Name", name);
                                stringMap.put("Salary", salary_int + "");
                                stringMap.put("Job_Tittle", selected_Job);
                                return stringMap;
                            }
                        };
                        RequestHandler.getInstance(Add_Lecturer.this).addToRequestQueue(stringRequest);
                    }else
                    {
                        DesignerToast.Info(getApplicationContext(), "Enter information of Lecturer First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                    progressDialog.dismiss();
                    }
                }else
                {
                    DesignerToast.Info(getApplicationContext(), "Enter information of Lecturer First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                    progressDialog.dismiss();
                }
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                int id = Integer.parseInt(ID.getText().toString());
                if (!ID.getText().toString().isEmpty()) {
                    progressDialog.show();
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_Get_Lecturer, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(!obj.getBoolean("error")){
                                    String Job_Tittle = obj.optString("Job_Tittle");
                                    String Full_Name = obj.optString("Full_Name");
                                    int Salary = obj.optInt("Salary");
                                    salary.setText(Salary+"");
                                    Name.setText(Full_Name);
                                    if(Job_Tittle.equals("Professor"))
                                    {
                                        spinner_Job.setSelection(0);
                                    }else
                                    {
                                        spinner_Job.setSelection(1);
                                    }
                                }else{
                                    salary.setText("");
                                    Name.setText("");
                                    DesignerToast.Warning(getApplicationContext(), obj.getString("message"), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            salary.setText("");
                            Name.setText("");
                            DesignerToast.Error(getApplicationContext(), error.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> stringMap = new HashMap<>();
                            stringMap.put("Instructor_id", id+"");
                            return stringMap;
                        }
                    };
                    RequestHandler.getInstance(Add_Lecturer.this).addToRequestQueue(stringRequest2);
                }else
                {
                    DesignerToast.Info(getApplicationContext(), "Enter information of Lecturer First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                int salary_int = Integer.parseInt(salary.getText().toString());
                int id_int = Integer.parseInt(ID.getText().toString());
                if (!name.isEmpty()) {
                    progressDialog.show();
                    StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.URL_Update_Lecturer, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                if (jsonObject.getString("message").equals("Lecturer Updated successfully")) {
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
                            stringMap.put("Full_Name", name);
                            stringMap.put("Salary", salary_int+"");
                            stringMap.put("Job_Tittle", selected_Job);
                            stringMap.put("Instructor_id", id_int+"");
                            return stringMap;
                        }
                    };
                    RequestHandler.getInstance(Add_Lecturer.this).addToRequestQueue(stringRequest3);
                }else
                {
                    DesignerToast.Info(getApplicationContext(), "Enter name of Faculty First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }

            }
        });

    }
}