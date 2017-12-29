package com.example.yashc.volleyloginandregister;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private final String URL = "https://projectsandroid.000webhostapp.com/reg&login/login.php";

    TextInputEditText loginName, loginPassword;
    Button login, loginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginName = findViewById(R.id.edittext_username);
        loginPassword = findViewById(R.id.edittext_password);

        login = findViewById(R.id.login_button);
        loginRegister = findViewById(R.id.register_button);


        loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {

        final String nameOrEmail = loginName.getText().toString();
        final String password = loginPassword.getText().toString();

        if(TextUtils.isEmpty(nameOrEmail)){
            loginName.setError("Empty Field");
            loginName.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){

            loginPassword.setError("Empty Field");
            loginPassword.requestFocus();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String error = jsonObject.getString("error");

                    if(error.equals("null")){
                        String username = jsonObject.getString("Username");
                        String email = jsonObject.getString("Email");
                        String phone = jsonObject.getString("Phone No");
                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    }else{

                        Toast.makeText(LoginActivity.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("username", nameOrEmail);
                map.put("password", password);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
