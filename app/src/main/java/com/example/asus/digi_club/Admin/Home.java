package com.example.asus.digi_club.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.asus.digi_club.Admin.Bill_Management.BillManagement;
import com.example.asus.digi_club.Admin.Bill_Management.User_Profiles;
import com.example.asus.digi_club.Admin.Branch_Managemnet.Branches;
import com.example.asus.digi_club.Admin.Food_Managemnet.MenuManagement;
import com.example.asus.digi_club.Admin.Member_Management.AllMembers;
import com.example.asus.digi_club.Admin.Member_Management.MemberType_Selector;
import com.example.asus.digi_club.R;
public class Home extends AppCompatActivity {

    LinearLayout food_management,bill_management,member_management,branch_management;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        food_management=findViewById(R.id.food);
        bill_management=findViewById(R.id.bill);
        member_management=findViewById(R.id.member);
        branch_management=findViewById(R.id.Branches);

        food_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MenuManagement.class));
            }
        });
        bill_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), User_Profiles.class));
            }
        });
        member_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MemberType_Selector.class));
            }
        });
        branch_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Branches.class));
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.profile) {

            return true;
        }
        if (id == R.id.logout) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
