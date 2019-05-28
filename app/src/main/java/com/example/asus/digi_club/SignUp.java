package com.example.asus.digi_club;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class SignUp extends AppCompatActivity {

    EditText username,email,password;
    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPrefManager=SharedPrefManager.getInstance(getApplicationContext());
        user=sharedPrefManager.getUser();

        if(user.getId()!=null){

            startActivity(new Intent(getApplicationContext(), Navdrawer.class));

        }else{

            progressDialog=new ProgressDialog(SignUp.this);

            username=findViewById(R.id.username);
            email=findViewById(R.id.email);
            password=findViewById(R.id.password);

        }






    }

    public void signUp(View view){

        progressDialog.setTitle("Sign Up");
        progressDialog.setMessage("Please wait,Your signing up....");
        progressDialog.show();
        userRegister();
    }

    public void userRegister(){


        final String userName = username.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();


        if (TextUtils.isEmpty(userName)) {
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Email)) {
            email.setError("Please enter your email");
            email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.SIGNUPURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();



                        if (response.contains("success")) {

                            String[] str=response.toString().split(",");
                            Toast.makeText(getApplicationContext(),"Sign Up Successfully", Toast.LENGTH_SHORT).show();

                            User user = new User(str[1],"type",userName,Email,Password);
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            startActivity(new Intent(getApplicationContext(), Navdrawer.class));

                        } else {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Error server", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", userName);
                params.put("email", Email);
                params.put("password", Password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
