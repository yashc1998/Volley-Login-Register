package com.example.yashc.volleyloginandregister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yashc on 29-12-2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private final String URL = "https://projectsandroid.000webhostapp.com/reg&login/regsiter.php";

    TextInputEditText registerUsername, registerPassword, registerConfirmPassword, registerEmail, phoneNo;
    Button Register;

    private static final String KEY_USER = "username";
    private static final String KEY_PASS = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";


    String username;
    String password, confirmPassword;
    String email ;
    String phoneno;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);
        registerConfirmPassword = findViewById(R.id.register_confirm_password);
        registerEmail = findViewById(R.id.register_email);
        phoneNo = findViewById(R.id.register_phone);

        Register = findViewById(R.id.register);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(validUser(username, password, confirmPassword, email, phoneno))
                    registerUser();
            }
        });
    }

    private void registerUser() {


        username = registerUsername.getText().toString();
        password = registerPassword.getText().toString();
        confirmPassword = registerConfirmPassword.getText().toString();
        email = registerEmail.getText().toString();
        phoneno = phoneNo.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put(KEY_USER, username);
                params.put(KEY_PASS, password);
                params.put(KEY_EMAIL, email);
                params.put(KEY_PHONE, phoneno);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

    private boolean validUser(String username, String password, String confirmPassword, String email, String phoneno){

        if(username.equals("")){

            registerUsername.setError("Field Empty");
            return false;
        }
        else if (password.equals("")){

            registerPassword.setError("Field Empty");
            return false;
        }
        else if(password != confirmPassword){

            registerPassword.setError("");
            registerConfirmPassword.setError("Password did not mathched");
            return false;
        }
        else if(email.equals("")){

            registerEmail.setError("Field Empty");
            return false;
        }
        else if(!email.contains("@")){

            registerEmail.setError("Enter valid email");
            return false;
        }
        else if (phoneno.equals("")){

            phoneNo.setError("Emplty Field");
            return false;
        }

        return true;
    }
}
