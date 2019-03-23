package com.example.asus.digi_club;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BillManagement extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Map<Integer,Integer> selectedItem=new HashMap<>();
    Button delete;
    boolean itemdelete=false;
    ArrayList<Food> foods=new ArrayList<>();
    Button confirmbill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_management);
        toolbar=findViewById(R.id.toolbar);
        confirmbill=findViewById(R.id.confirmbill);
        toolbar.setTitle("Generate Bill");
        progressDialog=new ProgressDialog(BillManagement.this);
        progressDialog.setMessage("Please wait...");
        setData();
        RecycleAdapter recycleAdapter=new RecycleAdapter(foods);
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recycleAdapter);

    }

    private void setData() {

        for(int i=0;i<5;i++){

            if(i==0){

                foods.add(new Food(i+"","pizza","250","3","pizza"));
            }
            else if(i==1){

                foods.add(new Food(i+"","burger","200","5","burger"));
            }
            else if(i==2){

                foods.add(new Food(i+"","clabber","100","5","clabber"));
            }
            else if(i==3){

                foods.add(new Food(i+"","egg","100","5","egg"));
            }
            else if(i==4){

                foods.add(new Food(i+"","juice","100","5","juice"));
            }
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
                        /*RecycleAdapter recycleAdapter=new RecycleAdapter(memberInfos);
                        recyclerView=findViewById(R.id.recycle);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(recycleAdapter);
                        progressDialog.dismiss();*/

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



    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<Food> memberInfos;
        public RecycleAdapter(ArrayList<Food> memberInfos){
            this.memberInfos=memberInfos;
        }
        public  class ViewAdapter extends RecyclerView.ViewHolder{

            View mView;
            TextView price,name;
            CircleImageView profile_image;
            CheckBox checkBox;
            Spinner quantity2;
            String[] str={"1","2","3","4","5","6","7","8","9","10"};
            public ViewAdapter(View itemView) {
                super(itemView);
                mView=itemView;
                checkBox=mView.findViewById(R.id.selector);
                quantity2=mView.findViewById(R.id.quantity);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (BillManagement.this,R.layout.spinnertext,str);
                adapter.setDropDownViewResource(R.layout.spinnertext);
                quantity2.setAdapter(adapter);
                name=mView.findViewById(R.id.name);
                price=mView.findViewById(R.id.price);
                profile_image=mView.findViewById(R.id.picture);

            }


        }
        @NonNull
        @Override
        public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.billmenuitem,parent,false);
            return new ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewAdapter holder, final int position) {

            final int[] range={1,2,3,4,5,6,7,8,9,10};
            Food memberInfo=memberInfos.get(position);
            if(memberInfo.getPicturePath().equals("pizza")) holder.profile_image.setImageResource(R.drawable.pizza);
            else if(memberInfo.getPicturePath().equals("burger")) holder.profile_image.setImageResource(R.drawable.burger);
            else if(memberInfo.getPicturePath().equals("clabber")) holder.profile_image.setImageResource(R.drawable.clabber);
            else if(memberInfo.getPicturePath().equals("burger")) holder.profile_image.setImageResource(R.drawable.burger);
            else if(memberInfo.getPicturePath().equals("egg")) holder.profile_image.setImageResource(R.drawable.egg);
            else if(memberInfo.getPicturePath().equals("juice")) holder.profile_image.setImageResource(R.drawable.juice);
            holder.price.setText(memberInfo.price+"/=");
            holder.name.setText(memberInfo.foodName);


            int quantity=range[holder.quantity2.getSelectedItemPosition()];
            holder.quantity2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(selectedItem.get(position)!=null){
                        int quantity=range[holder.quantity2.getSelectedItemPosition()];
                        selectedItem.put(position,quantity);

                    }

                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                    return;
                }

            });
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(holder.checkBox.isChecked()){

                        selectedItem.put(position,1);
                        confirmbill.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),"Please select quantity",Toast.LENGTH_LONG).show();
                    }
                    else{

                        selectedItem.remove(position);
                        if(selectedItem.size()>0)confirmbill.setVisibility(View.VISIBLE);
                        else confirmbill.setVisibility(View.INVISIBLE);

                    }


                }
            });


        }

        @Override
        public int getItemCount() {
            return memberInfos.size();
        }



    }

    public void generateBill(View view){

        double totalBill=0.0;
        StringBuilder s=new StringBuilder();
        s.append("Food        Price\n");
        for(int key:selectedItem.keySet()){

            totalBill+=(selectedItem.get(key)*Double.parseDouble(foods.get(key).price));
            double everyItemBill=(selectedItem.get(key)*Double.parseDouble(foods.get(key).price));
            s.append(foods.get(key).foodName+"       "+everyItemBill+"\n");
        }
        confirmbill.setVisibility(View.INVISIBLE);
        selectedItem.remove(selectedItem);

        String billpaper="Total Bill: "+totalBill+"/="+"\n\n"+s.toString();

        sendMail(billpaper);
        showLog(billpaper);

    }

    private void showLog(String billpaper) {

        AlertDialog.Builder builder=new AlertDialog.Builder(BillManagement.this);
        builder.setTitle("Your Bill");
        builder.setMessage(billpaper);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void sendMail(String billpaper) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bsse0923@iit.du.ac.bd"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Your Bill");
        i.putExtra(Intent.EXTRA_TEXT   , billpaper);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BillManagement.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(BillManagement.this, "Email have sent successfully.", Toast.LENGTH_SHORT).show();
    }
}
