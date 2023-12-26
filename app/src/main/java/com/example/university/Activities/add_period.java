package com.example.university.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class add_period extends AppCompatActivity {

    TextView From,To;
    EditText ID;
    int chour,cmins;
    int chourTo,cminsTo;

    String selected_from="";
    String selected_To="";
    boolean check=false;

    int hourfrom,minfrom;
    Button Add,get,Update;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_period);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));
        From =findViewById(R.id.From);
        To =findViewById(R.id.To);
        ID =findViewById(R.id.ID);
        Add =findViewById(R.id.Add);
        get =findViewById(R.id.get);
        Update =findViewById(R.id.Update);


        From.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                chour=calendar.get(Calendar.HOUR_OF_DAY);
                cmins=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog =  new TimePickerDialog(add_period.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        From.setText(hourOfDay+":"+minute+":00");
                        selected_from=hourOfDay+":"+minute+":00";
                        hourfrom=hourOfDay;
                        minfrom=minute;
                    }
                },chour,cmins,false);
                timePickerDialog.show();
            }
        });

        To.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                chourTo=calendar.get(Calendar.HOUR_OF_DAY);
                cminsTo=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog =  new TimePickerDialog(add_period.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay<hourfrom||(hourfrom==hourOfDay&&(minute-minfrom)<=30))
                        {
                            DesignerToast.Warning(getApplicationContext(), "The period must be correct and more than half an hour", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }else
                        {
                            selected_To=hourOfDay+":"+minute+":00";
                            To.setText(hourOfDay+":"+minute+":00");
                            check=true;
                        }
                    }
                },chourTo,cminsTo,false);
                timePickerDialog.show();
            }
        });

        progressDialog = new ProgressDialog(add_period.this);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                    if (!selected_from.isEmpty()&&!selected_To.isEmpty()) {
                        progressDialog.show();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_Add_Period, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("message").equals("Period added successfully")) {
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
                                stringMap.put("From_T", selected_from);
                                stringMap.put("To_T", selected_To);
                                return stringMap;
                            }
                        };
                        RequestHandler.getInstance(add_period.this).addToRequestQueue(stringRequest);
                    }else
                    {
                        DesignerToast.Info(getApplicationContext(), "Enter information of Period First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
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
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.URL_Get_Period, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                if(!obj.getBoolean("error")){
                                    String From_T = obj.optString("From_T");
                                    String To_T = obj.optString("To_T");
                                    From.setText(From_T+"");
                                    selected_from=From_T;
                                    selected_To=To_T;
                                    To.setText(To_T);
                                }else{
                                    From.setText("");
                                    To.setText("");
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
                            From.setText("");
                            To.setText("");
                            DesignerToast.Error(getApplicationContext(), error.getMessage(), Gravity.BOTTOM, Toast.LENGTH_SHORT);
                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> stringMap = new HashMap<>();
                            stringMap.put("Period_id", id+"");
                            return stringMap;
                        }
                    };
                    RequestHandler.getInstance(add_period.this).addToRequestQueue(stringRequest2);
                }else
                {
                    DesignerToast.Info(getApplicationContext(), "Enter information of Period First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(ID.getText().toString());
                if(!ID.getText().toString().isEmpty()) {
                    if (!selected_from.isEmpty() && !selected_To.isEmpty()) {
                        progressDialog.show();
                        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.URL_Update_Period, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("message").equals("Period Updated successfully")) {
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
                                stringMap.put("Period_id", id + "");
                                stringMap.put("From_T", selected_from + "");
                                stringMap.put("To_T", selected_To);
                                return stringMap;
                            }
                        };
                        RequestHandler.getInstance(add_period.this).addToRequestQueue(stringRequest3);
                    } else {
                        DesignerToast.Info(getApplicationContext(), "Enter infromation of period First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                    }
                }else {
                    DesignerToast.Info(getApplicationContext(), "Enter infromation of period First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }

            }
        });

    }
}