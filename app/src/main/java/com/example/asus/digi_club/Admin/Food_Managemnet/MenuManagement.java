package com.example.asus.digi_club.Admin.Food_Managemnet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class MenuManagement extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> deleteItem=new ArrayList<>();
    Button delete;
    boolean itemdelete=false;
    boolean update=false;
    ArrayList<FoodItemInfo[]> foodItemInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_management);

        recyclerView=findViewById(R.id.recycle);
        delete=findViewById(R.id.delete);
        progressDialog=new ProgressDialog(MenuManagement.this);
        progressDialog.setMessage("Please wait...");
        getAllMemberData();
    }

    public void initRecycle(){

        RecycleAdapter recycleAdapter=new RecycleAdapter(foodItemInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(recycleAdapter);
        progressDialog.dismiss();
    }


    public void delete(View view){


        for(String i:deleteItem){

            DatabaseConnector databaseConnector=new DatabaseConnector(getApplicationContext());
            databaseConnector.deleteMember(i,Constraints.ADDFOODITEM,"delete");


        }
        delete.setVisibility(View.INVISIBLE);
        itemdelete=false;
        deleteItem=new ArrayList<>();
        getAllMemberData();
    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewAdapter>{

        ArrayList<FoodItemInfo[]> foodItemInfo;
        public RecycleAdapter(ArrayList<FoodItemInfo[]> foodItemInfo){
            this.foodItemInfo=foodItemInfo;
        }
        public  class ViewAdapter extends RecyclerView.ViewHolder{

            View mView;
            CardView cardView;
            TextView name,price,name2,price2,quantity1,quantity2;
            CircleImageView profile_image,profile_image2;
            CheckBox checkBox1,checkBox2;
            public ViewAdapter(View itemView) {
                super(itemView);
                mView=itemView;
                checkBox1=mView.findViewById(R.id.seletor1);
                checkBox2=mView.findViewById(R.id.seletor2);
                name=mView.findViewById(R.id.name);
                price=mView.findViewById(R.id.price);
                quantity1=mView.findViewById(R.id.quantity1);
                quantity2=mView.findViewById(R.id.quantity2);
                name2=mView.findViewById(R.id.name2);
                price2=mView.findViewById(R.id.price2);
                profile_image=mView.findViewById(R.id.profile_image);
                profile_image2=mView.findViewById(R.id.profile_image2);
                cardView=mView.findViewById(R.id.card2);

            }


        }
        @NonNull
        @Override
        public ViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menuitem,parent,false);
            return new ViewAdapter(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleAdapter.ViewAdapter holder, final int position) {


            final FoodItemInfo[] memberInfo=foodItemInfo.get(position);

            if(itemdelete||update){
                holder.checkBox1.setVisibility(View.VISIBLE);

            }
            if(memberInfo[0].getImagepath()==null) holder.profile_image.setImageResource(R.drawable.azaz12);
            else{
                holder.profile_image.setImageResource(R.drawable.egg);
            }
            holder.price.setText("Price: "+memberInfo[0].price+"TK");
            holder.name.setText(memberInfo[0].name);
            holder.quantity1.setText("Quantity: "+memberInfo[0].quantity);

            holder.checkBox1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(itemdelete){

                        deleteItem.add((memberInfo[0].id)+"");
                        delete.setVisibility(View.VISIBLE);
                    }
                    else{

                        updateItem(memberInfo[0].id,memberInfo[0].name,memberInfo[0].price+"",memberInfo[0].quantity,memberInfo[0].imagepath);
                    }

                }
            });


            if(memberInfo[1]!=null){
                if(itemdelete||update){

                    holder.checkBox2.setVisibility(View.VISIBLE);
                }
                holder.cardView.setVisibility(View.VISIBLE);
                if(memberInfo[1].getImagepath()==null) holder.profile_image2.setImageResource(R.drawable.azaz12);
                else{
                    holder.profile_image2.setImageResource(R.drawable.burger);
                }
                holder.price2.setText("Price: "+memberInfo[1].price+"TK");
                holder.name2.setText(memberInfo[1].name);
                holder.quantity2.setText("Quantity: "+memberInfo[1].quantity);
                holder.checkBox2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        if(itemdelete){

                            deleteItem.add((memberInfo[1].id)+"");
                            delete.setVisibility(View.VISIBLE);
                        }
                        else{

                            updateItem(memberInfo[1].id,memberInfo[1].name,memberInfo[1].price+"",memberInfo[1].quantity,memberInfo[1].imagepath);
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

    public void updateItem(final int id,final String item_name,final String price,final int quantity2,final String image_path){


        AlertDialog.Builder builder=new AlertDialog.Builder(MenuManagement.this);
        View view=getLayoutInflater().inflate(R.layout.addfooditem,null);
        final EditText name=view.findViewById(R.id.foodname);
        final EditText dept=view.findViewById(R.id.price);
        final EditText quantity=view.findViewById(R.id.quantity);
        Button addmember=view.findViewById(R.id.addmember);
        addmember.setText("Update Item");
        builder.setView(view);
        name.setText(item_name);
        dept.setText(price);
        quantity.setText(quantity2+"");
        final Dialog dialog=builder.show();
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(name.getText().length()>0&&dept.getText().length()>0){

                    update(id,name.getText().toString(),dept.getText().toString(),quantity.getText().toString(),"");
                    dialog.dismiss();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Enter Name And Price",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.foodmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_food) {
            addMember();
            return true;
        }
        if (id == R.id.update) {

            update=true;
            initRecycle();
            return true;
        }
        if (id == R.id.remove) {

            itemdelete=true;
            initRecycle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMember(){

        AlertDialog.Builder builder=new AlertDialog.Builder(MenuManagement.this);
        View view=getLayoutInflater().inflate(R.layout.addfooditem,null);
        final EditText name=view.findViewById(R.id.foodname);
        final EditText dept=view.findViewById(R.id.price);
        final EditText quantity=view.findViewById(R.id.quantity);
        Button addmember=view.findViewById(R.id.addmember);
        builder.setView(view);
        final Dialog dialog=builder.show();
        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addMember(name.getText().toString(),dept.getText().toString(),quantity.getText().toString(),"burger");
                dialog.dismiss();

            }
        });

    }

    public  void addMember(final String memberName,final String dept,final String quantity,final String image_path){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.ADDFOODITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        if (response.contains("success")) {

                            Toast.makeText(getApplicationContext(),"Member added successfully",Toast.LENGTH_LONG).show();

                            getAllMemberData();
                        } else {

                            Toast.makeText(getApplicationContext(),"Fail to add member",Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type","add");
                params.put("name",memberName);
                params.put("price",dept);
                params.put("quantity",quantity);
                params.put("image_path",image_path);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }

    public  void update(final int id ,final String name,final String price,final String quantity,final String image_path){

        update=false;
        progressDialog.setTitle("Updating...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constraints.UPDATE_FOOD_ITEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        if (response.contains("success")) {

                            Toast.makeText(getApplicationContext(),"Member updated successfully",Toast.LENGTH_LONG).show();


                        } else {

                            Toast.makeText(getApplicationContext(),"Fail to update item",Toast.LENGTH_LONG).show();

                        }

                        getAllMemberData();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id+"");
                params.put("name",name);
                params.put("quantity",quantity);
                params.put("image_path","egg");
                params.put("price",price);
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


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

                         foodItemInfo=new ArrayList<>();
                        for(int i=0;i<response.length();i++){
                            FoodItemInfo[] foodItem=new FoodItemInfo[2];
                            JSONObject student = null;
                            try {
                                student = response.getJSONObject(i);
                                int id = student.getInt("id");
                                String name = student.getString("name");
                                double price = student.getDouble("price");
                                String path = student.getString("imagepath");
                                int quantity=Integer.parseInt(student.getString("quantity"));
                                foodItem[0]=new FoodItemInfo(path,id,name,price,quantity);

                                i++;
                                if(i<response.length()){
                                    student = response.getJSONObject(i);
                                    id = student.getInt("id");
                                    name = student.getString("name");
                                    price = student.getDouble("price");
                                    path = student.getString("imagepath");
                                    quantity=Integer.parseInt(student.getString("quantity"));
                                    foodItem[1]=new FoodItemInfo(path,id,name,price,quantity);


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
