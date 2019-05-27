package com.example.asus.digi_club.Admin.Bill_Management;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.digi_club.Admin.Food_Managemnet.MenuManagement;
import com.example.asus.digi_club.Admin.Member_Management.MemberInfo;
import com.example.asus.digi_club.Constraints;
import com.example.asus.digi_club.DatabaseConnector;
import com.example.asus.digi_club.R;
import com.example.asus.digi_club.SendMail;
import com.example.asus.digi_club.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Profiles extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> deleteItem=new ArrayList<>();
    Button delete;
    boolean itemdelete=false;
    ArrayList<MemberInfo> memberInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profiles);
        recyclerView=findViewById(R.id.recycle);
        progressDialog=new ProgressDialog(User_Profiles.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();

    }
    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<MemberInfo> memberInfos;
        public RecycleAdapter(ArrayList<MemberInfo> memberInfos){
            this.memberInfos=memberInfos;
        }
        public  class ViewAdapter extends RecyclerView.ViewHolder{

            View mView;
            Button viewProfile,sendMessage;
            TextView name,dptName;
            CircleImageView profile_image;
            CheckBox checkBox;
            public ViewAdapter(View itemView) {
                super(itemView);
                mView=itemView;
                viewProfile=mView.findViewById(R.id.view_profile);
                sendMessage=mView.findViewById(R.id.sendMessage);

                name=mView.findViewById(R.id.name);
                dptName=mView.findViewById(R.id.dept);
                profile_image=mView.findViewById(R.id.profile_image);

            }


        }
        @NonNull
        @Override
        public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item,parent,false);
            return new ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewAdapter holder, final int position) {


            MemberInfo memberInfo=memberInfos.get(position);

            if(memberInfo.getImagePath()==null) holder.profile_image.setImageResource(R.drawable.profile2);
            else{
                holder.profile_image.setImageResource(R.drawable.saeed_sir);
            }
            holder.dptName.setText(memberInfo.department);
            holder.name.setText(memberInfo.name);

            holder.viewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent tnt=new Intent(getApplicationContext(),BillManagement.class);
                    tnt.putExtra("id",memberInfos.get(position).id);
                    startActivity(tnt);
                }
            });

            holder.sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



        }

        @Override
        public int getItemCount() {
            return memberInfos.size();
        }



    }


    public void getAllMemberData(){


        progressDialog.show();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.POST,
                Constraints.MEMBERINFOURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        memberInfos=new ArrayList<>();
                        memberInfos.add(new MemberInfo("234","Md.Saeed Siddik",null,"IIT","image"));
                        memberInfos.add(new MemberInfo("234","Nadia Nahar",null,"IIT",null));
                        for(int i=0;i<response.length();i++){

                            JSONObject student = null;
                            try {
                                student = response.getJSONObject(i);
                                String id = student.getString("id");
                                String name = student.getString("name");
                                String dept = student.getString("department");
                                memberInfos.add(new MemberInfo(id,name,null,dept,null));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        RecycleAdapter recycleAdapter=new RecycleAdapter(memberInfos);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(recycleAdapter);
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


        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);


    }
}
