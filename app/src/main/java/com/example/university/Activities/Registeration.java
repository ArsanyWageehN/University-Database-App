package com.example.university.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.vdx.designertoast.DesignerToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registeration extends AppCompatActivity {

    EditText ID_S,ID_C;
    Button Register;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setStatusBarColor(Color.parseColor("#2396F1"));
        ID_S = findViewById(R.id.ID_S);
        ID_C = findViewById(R.id.ID_C);
        Register = findViewById(R.id.Register);
        progressDialog = new ProgressDialog(Registeration.this);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_S = ID_S.getText().toString();
                String id_C = ID_C.getText().toString();

                if (!id_C.isEmpty() && !id_S.isEmpty()) {
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RegisterCourseFor_Student, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                if (jsonObject.getString("message").equals("Registration successfully")) {
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

                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> stringMap = new HashMap<>();
                            stringMap.put("crs_code", id_C);
                            stringMap.put("stu_id", id_S);
                            return stringMap;
                        }
                    };

                    RequestHandler.getInstance(Registeration.this).addToRequestQueue(stringRequest);
                }else{
                    DesignerToast.Info(getApplicationContext(), "Enter Information First", Gravity.BOTTOM, Toast.LENGTH_SHORT);
                }
            }
        });

    }
}