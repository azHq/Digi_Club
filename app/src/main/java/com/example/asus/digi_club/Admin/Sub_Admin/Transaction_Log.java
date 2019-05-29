package com.example.asus.digi_club.Admin.Sub_Admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.asus.digi_club.Constraints;
import com.example.asus.digi_club.DatabaseConnector;
import com.example.asus.digi_club.R;
import com.example.asus.digi_club.SendMail;
import com.example.asus.digi_club.SharedPrefManager;
import com.example.asus.digi_club.User;
import com.example.asus.digi_club.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Transaction_Log extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    User user;
    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> deleteItem=new ArrayList<>();
    int branch_id=-1;
    Button delete;
    boolean itemdelete=false;
    ArrayList<Transaction_Log_Info> memberInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction__log);
        recyclerView=findViewById(R.id.recycle);
        progressDialog=new ProgressDialog(Transaction_Log.this);
        progressDialog.setMessage("Please wait...");
        sharedPrefManager= SharedPrefManager.getInstance(getApplicationContext());
        user=sharedPrefManager.getUser();
        branch_id=Integer.parseInt(user.getId());

        getAllMemberData();
    }

    public void initRecycle(){

        RecycleAdapter recycleAdapter=new RecycleAdapter(memberInfos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recycleAdapter);
        progressDialog.dismiss();
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<Transaction_Log_Info> memberInfos;
        public RecycleAdapter(ArrayList<Transaction_Log_Info> memberInfos){
            this.memberInfos=memberInfos;
        }
        public  class ViewAdapter extends RecyclerView.ViewHolder{

           TextView id,user_id,admin_id,date,total_bill,des;
           View mView;
            public ViewAdapter(View itemView) {
                super(itemView);
                mView=itemView;

                id=mView.findViewById(R.id.t_id);
                user_id=mView.findViewById(R.id.user_id);
                admin_id=mView.findViewById(R.id.admin_id);
                date=mView.findViewById(R.id.date);
                total_bill=mView.findViewById(R.id.bill);
                des=mView.findViewById(R.id.des);
            }


        }
        @NonNull
        @Override
        public RecycleAdapter.ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_log_item,parent,false);
            return new RecycleAdapter.ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleAdapter.ViewAdapter holder, final int position) {


            Transaction_Log_Info memberInfo=memberInfos.get(position);

            holder.id.setText("Transaction Id: "+memberInfo.id);
            holder.user_id.setText("user_Id: "+memberInfo.user_id);
            holder.admin_id.setText("Admin_Id: "+memberInfo.admin_id);
            holder.date.setText("Date: "+memberInfo.date);
            holder.total_bill.setText("Total_Bill: "+memberInfo.total_bill+"/=");
            holder.des.setText("Description: "+memberInfo.des);


        }

        @Override
        public int getItemCount() {
            return memberInfos.size();
        }



    }




    public void showMessage(String title){

        android.app.AlertDialog.Builder builder=new android.app.AlertDialog.Builder(Transaction_Log.this);
        builder.setMessage(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }


    private void sendMail(String password) {


        SendMail sm = new SendMail(this, "bsse0923@iit.du.ac.bd", "Your Password",password);
        sm.execute();
    }

    public void getAllMemberData()  {


        progressDialog.show();
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(branch_id);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.POST,
                Constraints.GET_ALL_TRANSACTION_DATA,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        memberInfos=new ArrayList<>();
                        for(int i=0;i<response.length();i++){

                            JSONObject student = null;
                            try {
                                student = response.getJSONObject(i);
                                String id = student.getString("id");
                                String user_id = student.getString("user_id");
                                String admin_id = student.getString("admin_id");
                                String date = student.getString("date");
                                String total_bill=student.getString("total_bill");
                                String description=student.getString("description");
                                memberInfos.add(new Transaction_Log_Info(id,user_id,admin_id,date,total_bill,description));

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
