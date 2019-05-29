package com.example.asus.digi_club.Admin.Sub_Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.asus.digi_club.Admin.Member_Management.AllMembers;
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

public class All_Users_Bill_Info extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> deleteItem=new ArrayList<>();
    Button delete;
    boolean itemdelete=false;
    ArrayList<Member_info2> memberInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__users__bill__info);
        recyclerView=findViewById(R.id.recycle);
        progressDialog=new ProgressDialog(All_Users_Bill_Info.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();

    }

    public void initRecycle(){

        RecycleAdapter recycleAdapter=new RecycleAdapter(memberInfos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recycleAdapter);
        progressDialog.dismiss();
    }


    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<Member_info2> memberInfos;
        public RecycleAdapter(ArrayList<Member_info2> memberInfos){
            this.memberInfos=memberInfos;
        }
        public  class ViewAdapter extends RecyclerView.ViewHolder{

            View mView;
            Button viewProfile,sendMessage;
            TextView name,dptName,bill;
            CircleImageView profile_image;
            CheckBox checkBox;
            public ViewAdapter(View itemView) {
                super(itemView);
                mView=itemView;
                viewProfile=mView.findViewById(R.id.view_profile);
                sendMessage=mView.findViewById(R.id.sendMessage);
                checkBox=mView.findViewById(R.id.seletor);

                name=mView.findViewById(R.id.name);
                bill=mView.findViewById(R.id.bill);
                dptName=mView.findViewById(R.id.dept);
                profile_image=mView.findViewById(R.id.profile_image);

            }


        }
        @NonNull
        @Override
        public RecycleAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_bill_info_item,parent,false);
            return new RecycleAdapter.ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleAdapter.ViewAdapter holder, final int position) {


            Member_info2 memberInfo=memberInfos.get(position);

            if(itemdelete) holder.checkBox.setVisibility(View.VISIBLE);
            if(memberInfo.getImagePath()==null) holder.profile_image.setImageResource(R.drawable.profile2);
            else{
                holder.profile_image.setImageResource(R.drawable.profile2);
            }
            holder.dptName.setText(memberInfo.department);
            holder.name.setText(memberInfo.name);
            holder.bill.setText(memberInfo.total_bill+"/=");

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteItem.add(memberInfos.get(position).id);
                    delete.setVisibility(View.VISIBLE);
                }
            });


        }

        @Override
        public int getItemCount() {
            return memberInfos.size();
        }



    }





    private void sendMail(String password) {


        SendMail sm = new SendMail(this, "bsse0923@iit.du.ac.bd", "Your Password",password);
        sm.execute();
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
                        for(int i=0;i<response.length();i++){

                            JSONObject student = null;
                            try {
                                student = response.getJSONObject(i);
                                String id = student.getString("id");
                                String name = student.getString("name");
                                String dept = student.getString("department");
                                String total_bill=student.getString("total_bill");
                                memberInfos.add(new Member_info2(id,name,null,dept,null,total_bill));

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
