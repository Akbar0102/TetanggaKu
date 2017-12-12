package com.example.android.tetanggaku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import adapter.SharedPrefManager;
import adapter.VolleySingleton;
import entity.URLs;
import entity.sharedWarga;

public class SignupActivity extends AppCompatActivity {
    EditText et_nama,et_email, et_password, et_nik, et_alamat, et_jenis_kelamin, et_jenis_perangkat, et_pekerjaan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MynavActivity.class));
        }

        TextView txtAlready = (TextView) findViewById(R.id.txt_already);
        txtAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = getIntent();
                setResult(RESULT_OK, intent2);
                finish();
            }
        });

        Button btn_signup = (Button) findViewById(R.id.btn_signup);
        et_nama = (EditText) findViewById(R.id.et_nama);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_nik = (EditText) findViewById(R.id.et_nik);
        et_alamat = (EditText) findViewById(R.id.et_alamat);
        et_jenis_kelamin = (EditText) findViewById(R.id.et_jenis_kelamin);
        et_jenis_perangkat = (EditText) findViewById(R.id.et_jenis_perangkat);
        et_pekerjaan = (EditText) findViewById(R.id.et_pekerjaan);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                /*db.open();
                db.insertWarga(et_nama.getText().toString(),et_email.getText().toString(),et_password.getText().toString(),et_nik.getText().toString(),et_alamat.getText().toString());
                db.close();
                Intent intent2 = getIntent();
                setResult(RESULT_OK, intent2);
                finish();*/
                /*if(et_email.getText().toString().trim().equals("") || et_password.getText().toString().trim().equals("")){
                    et_email.setError("Harap Isi Email!");
                    et_password.setError("Harap Isi Password!");
                }else{
                    Intent i = new Intent(MainActivity.this, MynavActivity.class);
                    startActivity(i);
                }*/
            }
        });
    }

    private void registerUser(){
        final String nama = et_nama.getText().toString();
        final String email = et_email.getText().toString();
        final String password = et_password.getText().toString();
        final String nik = et_nik.getText().toString();
        final String alamat = et_alamat.getText().toString();
        final String jenis_kelamin = et_jenis_kelamin.getText().toString();
        final String jenis_perangkat = et_jenis_perangkat.getText().toString();
        final String pekerjaan = et_pekerjaan.getText().toString();

        //TODO
        //validasi

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //konversi response ke jsonObject
                            JSONObject obj = new JSONObject(response);

                            //jika tanpa error di response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("mesage"), Toast.LENGTH_SHORT).show();

                                JSONObject userJson = obj.getJSONObject("user");

                                sharedWarga user = new sharedWarga(
                                        userJson.getString("nama"),
                                        userJson.getString("jenis"),
                                        userJson.getString("alamat"),
                                        userJson.getString("status")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                finish();
                                Intent intent2 = getIntent();
                                setResult(RESULT_OK, intent2);
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", nama);
                params.put("email", email);
                params.put("password", password);
                params.put("nik", nik);
                params.put("alamat", alamat);
                params.put("pekerjaan", pekerjaan);
                params.put("jenis_kelamin", jenis_kelamin);
                params.put("jenis_perangkat", jenis_perangkat);
                return params;
            }
        };

        VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);
    }
}

