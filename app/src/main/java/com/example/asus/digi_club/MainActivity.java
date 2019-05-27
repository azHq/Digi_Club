package com.example.asus.digi_club;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.digi_club.Admin.Home;

public class MainActivity extends AppCompatActivity {

    SharedPrefManager sharedPrefManager;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                sharedPrefManager=SharedPrefManager.getInstance(getApplicationContext());
                user=sharedPrefManager.getUser();

                if(user.getId()!=null) {

                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                }
                else{

                    startActivity(new Intent(MainActivity.this,Navdrawer.class));
                    finish();
                }

            }
        },1000);
    }
}
