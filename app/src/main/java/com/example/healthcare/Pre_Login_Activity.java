package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class Pre_Login_Activity extends AppCompatActivity {

    ViewPager mViewPager;


    // images array
    int[] images = {R.drawable.loginkids, R.drawable.logindoc,R.drawable.loginfam};

    // Creating Object of ViewPagerAdapter
    SliderPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);


        Button btnLog = (Button) findViewById(R.id.loginnew);
        Button btnReg = (Button) findViewById(R.id.registernew);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Pre_Login_Activity.this, LoginOptionsActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(Pre_Login_Activity.this, CreateAccount.class);
                startActivity(createAccountIntent);
            }
        });


        mViewPager = (ViewPager)findViewById(R.id.viewmainpager);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new SliderPagerAdapter(Pre_Login_Activity.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.dots);
        tabLayout.setupWithViewPager(mViewPager, true);
    }


    }