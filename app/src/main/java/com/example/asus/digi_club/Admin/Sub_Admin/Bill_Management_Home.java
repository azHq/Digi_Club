package com.example.asus.digi_club.Admin.Sub_Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.asus.digi_club.Admin.Bill_Management.User_Profiles;
import com.example.asus.digi_club.Admin.Branch_Managemnet.Branches;
import com.example.asus.digi_club.Admin.Food_Managemnet.MenuManagement;
import com.example.asus.digi_club.Admin.Member_Management.MemberType_Selector;
import com.example.asus.digi_club.R;

public class Bill_Management_Home extends AppCompatActivity {

    LinearLayout add_new_bill,all_user_bill,transaction_log,monthly_bill_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill__management__home);

        add_new_bill=findViewById(R.id.food);
        all_user_bill=findViewById(R.id.member);
        transaction_log=findViewById(R.id.bill);
        monthly_bill_update=findViewById(R.id.Branches);

        add_new_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), User_Profiles.class));
            }
        });
        all_user_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), All_Users_Bill_Info.class));
            }
        });
        transaction_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Transaction_Log.class));
            }
        });
        monthly_bill_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Montly_Update_Bill.class));
            }
        });

    }
}
