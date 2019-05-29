package com.example.asus.digi_club.Admin.Sub_Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.asus.digi_club.Admin.Bill_Management.User_Profiles;
import com.example.asus.digi_club.Admin.Food_Managemnet.MenuManagement;
import com.example.asus.digi_club.Admin.Member_Management.MemberType_Selector;
import com.example.asus.digi_club.Constraints;
import com.example.asus.digi_club.R;
import com.example.asus.digi_club.SharedPrefManager;
import com.example.asus.digi_club.UserPanel.AllMembers;
import com.example.asus.digi_club.UserPanel.Branches;
import com.example.asus.digi_club.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {


    LinearLayout food_management,bill_management,member_management,branch_management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        food_management=findViewById(R.id.food);
        bill_management=findViewById(R.id.bill);
        member_management=findViewById(R.id.member);
        branch_management=findViewById(R.id.Branches);

        food_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MenuManagement.class));
            }
        });
        bill_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Bill_Management_Home.class));
            }
        });
        member_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), AllMembers.class));
            }
        });
        branch_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Branches.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.branch_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.profile) {

            return true;
        }
        if (id == R.id.branch) {

            return true;
        }
        if (id == R.id.logout) {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void createRequset(){

        Map<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put("email", "user@gmail.com");
        jsonParams.put("username", "user");
        jsonParams.put("password", "pass");
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, Constraints.Bill_History,

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }*/
}
