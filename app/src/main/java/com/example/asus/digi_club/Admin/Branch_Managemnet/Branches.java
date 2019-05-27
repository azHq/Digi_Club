package com.example.asus.digi_club.Admin.Branch_Managemnet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.asus.digi_club.R;

public class Branches extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Branches");
    }
}
