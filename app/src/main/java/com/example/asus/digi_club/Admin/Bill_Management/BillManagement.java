package com.example.asus.digi_club.Admin.Bill_Management;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.asus.digi_club.Admin.Food_Managemnet.Food;
import com.example.asus.digi_club.Constraints;
import com.example.asus.digi_club.MemberInfo;
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

public class BillManagement extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    Map<Integer,Integer> selectedItem=new HashMap<>();
    Button delete;
    boolean itemdelete=false;
    ArrayList<Food> memberInfos;
    final int[] range={0,1,2,3,4,5,6,7,8,9,10};
    Button confirmbill;
    SharedPrefManager sharedPrefManager;
    User user;
    String user_id,admin_id;
    double totalBill=0.0;
    String description="";
    int key,quantity2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_management);
        recyclerView=findViewById(R.id.recycle);
        user_id=getIntent().getStringExtra("id");
        sharedPrefManager= SharedPrefManager.getInstance(getApplicationContext());
        user=sharedPrefManager.getUser();
        admin_id=user.getId();

        confirmbill=findViewById(R.id.confirmbill);
        progressDialog=new ProgressDialog(BillManagement.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();

    }


    public void getAllMemberData(){


        progressDialog.show();
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(
                Request.Method.POST,
                Constraints.GETFOODITEMS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        memberInfos=new ArrayList<>();
                        for(int i=0;i<response.length();i++){

                            JSONObject student = null;
                            try {


                                student = response.getJSONObject(i);
                                String foodId = student.getString("id");
                                String foodName = student.getString("name");
                                String price = student.getString("price");
                                String quantity= student.getString("quantity");
                                String picturePath=student.getString("imagepath");
                                memberInfos.add(new Food(foodId,foodName,price,quantity,picturePath));

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



    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<Food> memberInfos;
        public RecycleAdapter(ArrayList<Food> memberInfos){
            this.memberInfos=memberInfos;
        }
        public  class ViewAdapter extends RecyclerView.ViewHolder{

            View mView;
            TextView price,name,available;
            CircleImageView profile_image;
            CheckBox checkBox;
            Spinner quantity2;
            String[] str={"0","1","2","3","4","5","6","7","8","9","10"};
            public ViewAdapter(View itemView) {
                super(itemView);
                mView=itemView;
                checkBox=mView.findViewById(R.id.selector);
                quantity2=mView.findViewById(R.id.quantity);
                available=mView.findViewById(R.id.avail);
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


            Food memberInfo=memberInfos.get(position);
            if(memberInfo.getPicturePath().equals("pizza")) holder.profile_image.setImageResource(R.drawable.pizza);
            else if(memberInfo.getPicturePath().equals("burger")) holder.profile_image.setImageResource(R.drawable.burger);
            else if(memberInfo.getPicturePath().equals("clabber")) holder.profile_image.setImageResource(R.drawable.clabber);
            else if(memberInfo.getPicturePath().equals("burger")) holder.profile_image.setImageResource(R.drawable.burger);
            else if(memberInfo.getPicturePath().equals("egg")) holder.profile_image.setImageResource(R.drawable.egg);
            else if(memberInfo.getPicturePath().equals("juice")) holder.profile_image.setImageResource(R.drawable.juice);
            holder.price.setText(memberInfo.price+"/=");
            holder.name.setText(memberInfo.foodName);
            holder.available.setText("Available Item: "+memberInfo.quatiy);



            holder.quantity2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(selectedItem.get(position)!=null){

                        if(availablity(position,range[holder.quantity2.getSelectedItemPosition()])){
                            int quantity=range[holder.quantity2.getSelectedItemPosition()];
                            selectedItem.put(position,quantity);
                            confirmbill.setVisibility(View.VISIBLE);
                        }
                        else{
                            selectedItem.remove(position);
                            confirmbill.setVisibility(View.INVISIBLE);
                            AlertDialog.Builder builder=new AlertDialog.Builder(BillManagement.this);
                            builder.setTitle("Sorry...");
                            builder.setMessage("Insufficient Item.Please Select Quantity "+(Integer.parseInt(memberInfos.get(position).quatiy)));
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }


                    }
                    else{


                        if(availablity(position,range[holder.quantity2.getSelectedItemPosition()])&&range[holder.quantity2.getSelectedItemPosition()]>0){
                            key=position;
                            quantity2=range[holder.quantity2.getSelectedItemPosition()];
                            confirmbill.setVisibility(View.VISIBLE);
                        }

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

                        if(key==position){
                            selectedItem.put(position,quantity2);
                        }
                        else {
                            selectedItem.put(position,0);
                            Toast.makeText(getApplicationContext(),"Please select quantity",Toast.LENGTH_LONG).show();
                        }

                        confirmbill.setVisibility(View.VISIBLE);

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

    public boolean availablity(int position,int selectedQuantity){


        if((Integer.parseInt(memberInfos.get(position).quatiy)-selectedQuantity>=0)) return true;
        else return false;
    }

    public void generateBill(View view)  {

        int quantity=10;
        String itemName=null;
        if(selectedItem!=null&&selectedItem.size()>0){

            StringBuilder s=new StringBuilder();
            s.append("Food   quantity     Price\n\n");
            for(int key:selectedItem.keySet()){
                if(selectedItem.get(key)<=0){
                    quantity=selectedItem.get(key);
                    itemName=memberInfos.get(key).foodName;
                    break;
                }
                totalBill+=(selectedItem.get(key)*Double.parseDouble(memberInfos.get(key).price));
                double everyItemBill=(selectedItem.get(key)*Double.parseDouble(memberInfos.get(key).price));
                s.append(memberInfos.get(key).foodName+"      "+selectedItem.get(key)+"        "+everyItemBill+"\n\n");
                description+=memberInfos.get(key).foodId+"-"+memberInfos.get(key).foodName+"-"+memberInfos.get(key).price+"-"+selectedItem.get(key)+",";

            }

            String billpaper="Total Bill: "+totalBill+"/="+"\n\n"+s.toString();
            if(quantity<=0){

                AlertDialog.Builder builder=new AlertDialog.Builder(BillManagement.this);
                builder.setMessage("Please Select "+itemName +"'s Quantity");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            else{
                confirmbill.setVisibility(View.INVISIBLE);
                selectedItem.remove(selectedItem);
                showLog(billpaper);
            }
        }
        else {

            AlertDialog.Builder builder=new AlertDialog.Builder(BillManagement.this);
            builder.setMessage("Please Select Item");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            builder.show();
        }


    }

    private void showLog(final String billpaper) {

        AlertDialog.Builder builder=new AlertDialog.Builder(BillManagement.this);
        builder.setTitle("Your Bill");
        builder.setMessage(billpaper);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                addBill();
                sendMail(billpaper);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();

    }

    public void addBill(){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.Transaction,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        if (response.contains("success")) {

                            Toast.makeText(getApplicationContext(),"Bill Added Successfully",Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(getApplicationContext(),"Fail to add Bill",Toast.LENGTH_LONG).show();

                        }
                        getAllMemberData();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Fail to add Bill",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type","add");
                params.put("user_id", user_id);
                params.put("admin_id",admin_id);
                params.put("total_bill",totalBill+"");
                params.put("description",description);
                return params;
            }

        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void sendMail(String billpaper)  {


        SendMail sm = new SendMail(this,"bsse0923@iit.du.ac.bd","Your Bill", billpaper);
        sm.execute();
       /* Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bsse0923@iit.du.ac.bd"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Your Bill");
        i.putExtra(Intent.EXTRA_TEXT   , billpaper);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BillManagement.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }*/

       /* BackgroundMail bm = new BackgroundMail(getApplicationContext());
        bm.setGmailUserName("monir230chowdhury@gmail.com");
        bm.setGmailPassword("00000923");
        bm.setMailTo("bsse0923@iit.du.ac.bd");
        bm.setFormSubject("Your Bill");
        bm.setFormBody(billpaper);
        bm.send();*/




    }


}
