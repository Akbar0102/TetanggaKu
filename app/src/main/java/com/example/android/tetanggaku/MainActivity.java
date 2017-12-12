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
import database.DbWarga;
import entity.URLs;
import entity.sharedWarga;

public class MainActivity extends AppCompatActivity {
    EditText et_username, et_password;
    boolean statlog;
    DbWarga db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MynavActivity.class));
            return;
        }

        TextView txtSignUp = (TextView) findViewById(R.id.txt_create);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        Button btn_login = (Button) findViewById(R.id.btn_login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();

            }
        });
    }

    private void userLogin(){
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();

        if(et_username.getText().toString().trim().equals("") || et_password.getText().toString().trim().equals("")){
            et_username.setError("Harap Isi Email!");
            et_password.setError("Harap Isi Password!");
        }else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                //konversi response ke jsonObject
                                JSONObject obj = new JSONObject(response);

                                //jika tanpa error di response
                                if (!obj.getBoolean("error")) {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    JSONObject userJson = obj.getJSONObject("user");

                                    sharedWarga user = new sharedWarga(
                                            userJson.getString("nama"),
                                            userJson.getString("jenis_perangkat"),
                                            userJson.getString("alamat"),
                                            userJson.getString("status")
                                    );

                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                    finish();
                                    et_username.setText("");
                                    et_password.setText("");
                                    Intent i = new Intent(MainActivity.this, MynavActivity.class);
                                    startActivity(i);
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
                    params.put("email", username);
                    params.put("password", password);
                    return params;
                }
            };

            VolleySingleton.getmInstance(this).addToRequestQueue(stringRequest);

        }
    }


}
