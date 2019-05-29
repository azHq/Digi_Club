package com.example.asus.digi_club;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.digi_club.Admin.Home;
import com.example.asus.digi_club.UserPanel.Navdrawer;

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

                if(user!=null&&user.getType()!=null) {

                    if(user.getType()!=null&&user.getType().contains("user")){

                        startActivity(new Intent(getApplicationContext(),Navdrawer.class));
                        finish();
                    }
                    else if(user.getType()!=null&&user.getType().contains("super")){

                        startActivity(new Intent(getApplicationContext(), Home.class));
                        finish();
                    }
                    else if(user.getType()!=null&&user.getType().contains("sub_admin")){

                        startActivity(new Intent(getApplicationContext(), com.example.asus.digi_club.Admin.Sub_Admin.Home.class));
                        finish();
                    }


                }
                else{

                    startActivity(new Intent(MainActivity.this,TypeSelector.class));
                    finish();
                }

            }
        },1000);
    }
}
