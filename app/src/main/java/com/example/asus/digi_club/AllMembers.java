package com.example.asus.digi_club;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllMembers extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> deleteItem=new ArrayList<>();
    Button delete;
    boolean itemdelete=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_members);
        delete=findViewById(R.id.delete);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Members");
        progressDialog=new ProgressDialog(AllMembers.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();

    }

    public void delete(View view){

       
        for(String i:deleteItem){

            DatabaseConnector databaseConnector=new DatabaseConnector(getApplicationContext());
            databaseConnector.deleteMember(i,Constraints.MEMBERURL);
        }
        delete.setVisibility(View.INVISIBLE);
        itemdelete=false;
        deleteItem=new ArrayList<>();
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
                checkBox=mView.findViewById(R.id.seletor);

                name=mView.findViewById(R.id.name);
                dptName=mView.findViewById(R.id.dept);
                profile_image=mView.findViewById(R.id.profile_image);

            }


        }
        @NonNull
        @Override
        public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.allmemberitem,parent,false);
            return new ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewAdapter holder, final int position) {


            MemberInfo memberInfo=memberInfos.get(position);

            if(itemdelete) holder.checkBox.setVisibility(View.VISIBLE);
            if(memberInfo.getImagePath()==null) holder.profile_image.setImageResource(R.drawable.azaz12);
            else{
                holder.profile_image.setImageResource(R.drawable.saeed_sir);
            }
            holder.dptName.setText(memberInfo.department);
            holder.name.setText(memberInfo.name);

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

            itemdelete=true;
            getAllMemberData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMember(){

        AlertDialog.Builder builder=new AlertDialog.Builder(AllMembers.this);
        View view=getLayoutInflater().inflate(R.layout.addmemberview,null);
        final EditText name=view.findViewById(R.id.memberName);
        final EditText dept=view.findViewById(R.id.deptName);
        Button addmember=view.findViewById(R.id.addmember);
        builder.setView(view);
        final Dialog dialog=builder.show();
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addMember(name.getText().toString(),dept.getText().toString());
                dialog.dismiss();

            }
        });

    }

    public  void addMember(final String memberName,final String dept){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.MEMBERURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        if (response.contains("success")) {

                            Toast.makeText(getApplicationContext(),"Member added successfully",Toast.LENGTH_LONG).show();


                        } else {

                            Toast.makeText(getApplicationContext(),"Fail to add member",Toast.LENGTH_LONG).show();

                        }
                        getAllMemberData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Fail to add member",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type","add");
                params.put("name", memberName);
                params.put("dept",dept);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



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

                        ArrayList<MemberInfo> memberInfos=new ArrayList<>();
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
                        recyclerView=findViewById(R.id.recycle);
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
