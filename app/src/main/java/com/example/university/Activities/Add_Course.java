package com.example.university.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class Add_Course extends AppCompatActivity {

    EditText Name,Credits,ID,desc;
    Button Add,get,Update;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));


        Name =findViewById(R.id.Name);
        Add =findViewById(R.id.Add);
        ID =findViewById(R.id.ID);
        Credits =findViewById(R.id.Credits);
        desc =findViewById(R.id.desc);
        get =findViewById(R.id.get);
        Update =findViewById(R.id.Update);


        progressDialog = new ProgressDialog(Add_Course.this);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String name = Name.getText().toString();
                String Desc = desc.getText().toString();
                if(!Credits.getText().toString().isEmpty()) {
                    int Credits_int = Integer.parseInt(Credits.getText().toString());
                    if (!name.isEmpty()&&!Desc.isEmpty()) {
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Add_Course, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("message").equals("Course added successfully")) {
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
                                stringMap.put("crs_name", name);
                                stringMap.put("credits", Credits_int + "");
                                stringMap.put("crs_desc", Desc + "");
                                return stringMap;
                            }
                        };
                        RequestHandler.getInstance(Add_Course.this).addToRequestQueue(stringRequest);
                    }else
                    {
                        DesignerToast.Info(getApplicationContext(), "Enter information of Course First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        progressDialog.dismiss();
                    }
                }else
                {
                    DesignerToast.Info(getApplicationContext(), "Enter information of Course First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
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
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_Get_Course, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(!obj.getBoolean("error")){
                                    String crs_name = obj.optString("crs_name");
                                    String crs_desc = obj.optString("crs_desc");
                                    int credits = obj.optInt("credits");
                                    Name.setText(crs_name);
                                    Credits.setText(credits+"");
                                    desc.setText(crs_desc);
                                }else{
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
                            Name.setText("");
                            DesignerToast.Error(getApplicationContext(), error.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> stringMap = new HashMap<>();
                            stringMap.put("crs_code", id+"");
                            return stringMap;
                        }
                    };
                    RequestHandler.getInstance(Add_Course.this).addToRequestQueue(stringRequest2);
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
                String Desc = desc.getText().toString();
                if(!Credits.getText().toString().isEmpty()) {
                    int Credits_int = Integer.parseInt(Credits.getText().toString());
                    int id_int = Integer.parseInt(ID.getText().toString());
                    if (!name.isEmpty()) {
                        progressDialog.show();
                        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.URL_Update_Course, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("message").equals("Course Updated successfully")) {
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
                                stringMap.put("crs_name", name);
                                stringMap.put("credits", Credits_int + "");
                                stringMap.put("crs_code", id_int + "");
                                stringMap.put("crs_desc", Desc);
                                return stringMap;
                            }
                        };
                        RequestHandler.getInstance(Add_Course.this).addToRequestQueue(stringRequest3);
                    } else {
                        DesignerToast.Info(getApplicationContext(), "Enter name of Course First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                    }
                }else {
                    DesignerToast.Info(getApplicationContext(), "Enter credits of Course First", Gravity.BOTTOM, Toast.LENGTH_SHORT);

                }

            }
        });
    }
}