package com.example.asus.digi_club;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.digi_club.Admin.Home;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    EditText username,email,password;
    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    String table_name;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        table_name=getIntent().getStringExtra("table_name");

        progressDialog=new ProgressDialog(SignIn.this);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
    }

    public void Login(View v){

        progressDialog.setTitle("Sign In");
        progressDialog.setMessage("Please wait,Your are signing....");
        progressDialog.show();
        userRegister();
    }

    public void userRegister(){

        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.SIGNINURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();




                        if (response.contains("success")) {

                            String[] str=response.toString().split("-");
                            Toast.makeText(getApplicationContext(),"Sign Up Successfully", Toast.LENGTH_SHORT).show();

                            User user = new User(str[1],str[2],null,Email,Password);
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                            finish();
                            if(str[2].contains("user")){
                                startActivity(new Intent(getApplicationContext(), Navdrawer.class));
                            }
                            else if(str[2].contains("super")){

                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }
                            else if(str[2].contains("sub_admin")){

                                startActivity(new Intent(getApplicationContext(), com.example.asus.digi_club.Admin.Sub_Admin.Home.class));
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                       // Toast.makeText(getApplicationContext(),"Login Fail", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("table_name", table_name);
                params.put("email", Email);
                params.put("password", Password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
