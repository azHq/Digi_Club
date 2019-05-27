package com.example.asus.digi_club;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TypeSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_selector);
    }

    public void Sign_In_As_Admin(View view){

        Intent tnt=new Intent(getApplicationContext(),SignIn.class);
        tnt.putExtra("table_name","Admin_table");
        startActivity(tnt);

    }
    public void Sign_In_As_User(View view){

        Intent tnt=new Intent(getApplicationContext(),SignIn.class);
        tnt.putExtra("table_name","memberinfo");
        startActivity(tnt);
    }
}
