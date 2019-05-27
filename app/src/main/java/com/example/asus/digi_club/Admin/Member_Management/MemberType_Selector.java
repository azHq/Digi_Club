package com.example.asus.digi_club.Admin.Member_Management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.asus.digi_club.Admin.Sub_Admin.All_Sub_Admin;
import com.example.asus.digi_club.Admin.Sub_Admin.Home;
import com.example.asus.digi_club.R;

public class MemberType_Selector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_type__selector);
    }

    public void Sub_Admin(View view){

        Intent tnt=new Intent(getApplicationContext(),All_Sub_Admin.class);
        tnt.putExtra("table_name","Admin_table");
        startActivity(tnt);

    }
    public void User(View view){

        Intent tnt=new Intent(getApplicationContext(),AllMembers.class);
        tnt.putExtra("table_name","memberinfo");
        startActivity(tnt);
    }
}
