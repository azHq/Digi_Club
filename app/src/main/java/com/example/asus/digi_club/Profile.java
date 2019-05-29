package com.example.asus.digi_club;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.asus.digi_club.Admin.Member_Management.AllMembers;
import com.example.asus.digi_club.Admin.Member_Management.MemberInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    User user;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    TextView user_name,email,bill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager= SharedPrefManager.getInstance(getApplicationContext());
        user=sharedPrefManager.getUser();
        user_name=findViewById(R.id.user_name);
        email=findViewById(R.id.email);
        bill=findViewById(R.id.bill);
        progressDialog=new ProgressDialog(Profile.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();

    }

    public void getAllMemberData(){


        progressDialog.show();
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(user.getId());
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.POST,
                Constraints.Get_Single_Member,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        for(int i=0;i<response.length();i++){

                            JSONObject student = null;
                            try {
                                student = response.getJSONObject(i);
                                String id = student.getString("id");
                                String name = student.getString("name");
                                String dept = student.getString("email");
                                String total_bill = student.getString("total_bill");

                                user_name.setText("Name: "+name);
                                email.setText("Email: "+user.getEmail());
                                bill.setText("Total Bill: "+total_bill);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();


                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){

                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }


        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }


}
