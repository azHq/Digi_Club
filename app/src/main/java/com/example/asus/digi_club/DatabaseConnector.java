package com.example.asus.digi_club;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnector {
    public  boolean status=false;
    public Context context;

    public DatabaseConnector(Context context){

        this.context=context;
    }


    public  void deleteMember(final String id,String URL){


        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("success")) {

                            Toast.makeText(context,"Member terminated successfully",Toast.LENGTH_LONG).show();


                        } else {

                            Toast.makeText(context,"Fail to terminated member",Toast.LENGTH_LONG).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context,"Fail to terminated member",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type","delete");
                params.put("id", id);
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);



    }


}
