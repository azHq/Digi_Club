package com.example.asus.digi_club.UserPanel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.example.asus.digi_club.Admin.Branch_Managemnet.Branch_item_info;
import com.example.asus.digi_club.Constraints;
import com.example.asus.digi_club.DatabaseConnector;
import com.example.asus.digi_club.R;
import com.example.asus.digi_club.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Branches extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> deleteItem=new ArrayList<>();
    Button delete;
    boolean itemdelete=false;
    boolean update=false;
    ArrayList<Branch_item_info[]> foodItemInfo=new  ArrayList<Branch_item_info[]>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches2);

        recyclerView=findViewById(R.id.recycle);
        progressDialog=new ProgressDialog(Branches.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();
    }

    public void initRecycle(){

        RecycleAdapter recycleAdapter=new RecycleAdapter(foodItemInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recycleAdapter);
        progressDialog.dismiss();
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<Branch_item_info[]> foodItemInfo;
        public RecycleAdapter(ArrayList<Branch_item_info[]> foodItemInfo){
            this.foodItemInfo=foodItemInfo;
        }
        public  class ViewAdapter extends RecyclerView.ViewHolder{

            View mView;
            CardView cardView;
            TextView name,price,name2,price2;
            CircleImageView profile_image,profile_image2;
            CheckBox checkBox1,checkBox2;
            public ViewAdapter(View itemView) {
                super(itemView);
                mView=itemView;
                checkBox1=mView.findViewById(R.id.seletor1);
                checkBox2=mView.findViewById(R.id.seletor2);
                name=mView.findViewById(R.id.name);
                price=mView.findViewById(R.id.status);

                name2=mView.findViewById(R.id.name2);
                price2=mView.findViewById(R.id.status2);
                profile_image=mView.findViewById(R.id.profile_image);
                profile_image2=mView.findViewById(R.id.profile_image2);
                cardView=mView.findViewById(R.id.card2);

            }


        }
        @NonNull
        @Override
        public RecycleAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_item,parent,false);
            return new RecycleAdapter.ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleAdapter.ViewAdapter holder, final int position) {


            final Branch_item_info[] memberInfo=foodItemInfo.get(position);

            if(itemdelete||update){
                holder.checkBox1.setVisibility(View.VISIBLE);

            }
            if(memberInfo[0].getImage_path()==null) holder.profile_image.setImageResource(R.drawable.azaz12);
            else{
                holder.profile_image.setImageResource(R.drawable.egg);
            }
            holder.price.setText("Status:"+memberInfo[0].status);
            holder.name.setText(memberInfo[0].name);


            holder.checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(itemdelete){

                        deleteItem.add((memberInfo[0].id)+"");
                        delete.setVisibility(View.VISIBLE);
                    }
                    else{


                    }

                }
            });


            if(memberInfo[1]!=null){
                if(itemdelete||update){

                    holder.checkBox2.setVisibility(View.VISIBLE);
                }
                holder.cardView.setVisibility(View.VISIBLE);
                if(memberInfo[1].getImage_path()==null) holder.profile_image2.setImageResource(R.drawable.azaz12);
                else{
                    holder.profile_image2.setImageResource(R.drawable.burger);
                }
                holder.price2.setText("Status:"+memberInfo[1].status);
                holder.name2.setText(memberInfo[1].name);
                holder.checkBox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(itemdelete){

                            deleteItem.add((memberInfo[1].id)+"");
                            delete.setVisibility(View.VISIBLE);
                        }
                        else{

                        }
                    }
                });
            }




        }

        @Override
        public int getItemCount() {

            return foodItemInfo.size();
        }



    }



    public void getAllMemberData(){


        progressDialog.show();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.POST,
                Constraints.Branch_Info,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        foodItemInfo=new ArrayList<>();
                        for(int i=0;i<response.length();i++){
                            Branch_item_info[] foodItem=new Branch_item_info[2];
                            JSONObject student = null;
                            try {
                                student = response.getJSONObject(i);
                                int id = student.getInt("id");
                                String name = student.getString("name");
                                String manager_name=student.getString("manager_name");
                                int manager_id=student.getInt("manager_id");
                                String location=student.getString("location");
                                String contact=student.getString("contact");
                                String path = student.getString("image_path");
                                String status=student.getString("status");

                                foodItem[0]=new Branch_item_info( id,name,manager_name,manager_id,status,location,contact,path);

                                i++;
                                if(i<response.length()){
                                    student = response.getJSONObject(i);
                                    id = student.getInt("id");
                                    name = student.getString("name");
                                    manager_name=student.getString("manager_name");
                                    manager_id=student.getInt("manager_id");
                                    location=student.getString("location");
                                    contact=student.getString("contact");
                                    path = student.getString("image_path");
                                    status=student.getString("status");
                                    foodItem[1]=new Branch_item_info( id,name,manager_name,manager_id,status,location,contact,path);


                                }

                                foodItemInfo.add(foodItem);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        RecycleAdapter recycleAdapter=new RecycleAdapter(foodItemInfo);
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
