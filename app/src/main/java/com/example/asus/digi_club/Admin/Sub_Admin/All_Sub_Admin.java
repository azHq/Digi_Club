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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.digi_club.Admin.Member_Management.AllMembers;
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

public class All_Sub_Admin extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> deleteItem=new ArrayList<>();
    Button delete;
    boolean itemdelete=false;
    ArrayList<Sub_Admin_Info> memberInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__sub__admin);
        delete=findViewById(R.id.delete);
        recyclerView=findViewById(R.id.recycle);
        progressDialog=new ProgressDialog(All_Sub_Admin.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();
    }

    public void initRecycle(){

        RecycleAdapter recycleAdapter=new RecycleAdapter(memberInfos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recycleAdapter);
        progressDialog.dismiss();
    }


    public void delete(View view){


        for(String i:deleteItem){

            DatabaseConnector databaseConnector=new DatabaseConnector(getApplicationContext());
            databaseConnector.deleteMember(i, Constraints.Add_Delete_Admin,"delete");
        }
        delete.setVisibility(View.INVISIBLE);
        itemdelete=false;
        deleteItem=new ArrayList<>();
        getAllMemberData();
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<Sub_Admin_Info> memberInfos;
        public RecycleAdapter(ArrayList<Sub_Admin_Info> memberInfos){
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
                checkBox=mView.findViewById(R.id.seletor);

                name=mView.findViewById(R.id.name);
                dptName=mView.findViewById(R.id.dept);
                profile_image=mView.findViewById(R.id.profile_image);

            }


        }
        @NonNull
        @Override
        public RecycleAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.allmemberitem,parent,false);
            return new RecycleAdapter.ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleAdapter.ViewAdapter holder, final int position) {


            Sub_Admin_Info memberInfo=memberInfos.get(position);

            if(itemdelete) holder.checkBox.setVisibility(View.VISIBLE);
            if(memberInfo.image_path==null) holder.profile_image.setImageResource(R.drawable.profile2);
            else{
                holder.profile_image.setImageResource(R.drawable.saeed_sir);
            }
            holder.dptName.setText(memberInfo.email);
            holder.name.setText(memberInfo.name);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteItem.add(memberInfos.get(position).id+"");
                    delete.setVisibility(View.VISIBLE);
                }
            });


        }

        @Override
        public int getItemCount() {
            return memberInfos.size();
        }



    }



    public void setData() throws JSONException {

       /* for(int i=0;i<10;i++){

            memberInfos.add(new MemberInfo("123","Md.Saeed Siddik","Md.SaeedSiddik@gmail.com","iit",null));
        }*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.allmenbersmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.addmember) {

            addMember();
            return true;
        }
        if (id == R.id.terminate) {

            if(itemdelete) itemdelete=false;
            else itemdelete=true;
            initRecycle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMember(){

        AlertDialog.Builder builder=new AlertDialog.Builder(All_Sub_Admin.this);
        View view=getLayoutInflater().inflate(R.layout.add_sub_admin_view,null);
        final EditText name=view.findViewById(R.id.memberName);
        final EditText dept=view.findViewById(R.id.deptName);
        Button addmember=view.findViewById(R.id.addmember);
        builder.setView(view);
        final Dialog dialog=builder.show();
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(name.getText().toString().length()>2&&dept.getText().toString().length()>2){
                    addMember(name.getText().toString(),dept.getText().toString(),"sub_admin");

                }
                else{

                    Toast.makeText(getApplicationContext(),"Please Enater Name and Email",Toast.LENGTH_LONG).show();
                    addMember();
                }

                dialog.dismiss();

            }
        });

    }

    public void addMember(final String memberName,final String email,final String type){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.Add_Delete_Admin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        if (response.contains("success")) {


                            String password=response.split("-")[1].replace("\"}","");
                            Toast.makeText(getApplicationContext(),"Member added successfully",Toast.LENGTH_LONG).show();
                            sendMail(password);


                        } else {

                            Toast.makeText(getApplicationContext(),"Fail to add member",Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();
                        getAllMemberData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                       // Toast.makeText(getApplicationContext(),"Fail to add member",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type","add");
                params.put("name", memberName);
                params.put("email",email);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }

    private void sendMail(String password) {


        SendMail sm = new SendMail(this, "bsse0923@iit.du.ac.bd", "Your Password",password);
        sm.execute();
    }

    public void getAllMemberData()  {


        progressDialog.show();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.POST,
                Constraints.GET_ALL_DATA,
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
                                String name = student.getString("user_name");
                                String email = student.getString("email");
                                String type = student.getString("type");
                                String path=student.getString("image_path");
                                memberInfos.add(new Sub_Admin_Info(id,email,type,name,null));

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
