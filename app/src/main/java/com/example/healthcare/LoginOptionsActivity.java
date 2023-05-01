package com.example.healthcare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginOptionsActivity extends AppCompatActivity {

    ImageView backbtn,tech,health,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        backbtn = (ImageView) findViewById(R.id.backbtn);

        tech = (ImageView) findViewById(R.id.buttonTech);
        health = (ImageView) findViewById(R.id.buttonHealth);
        admin = (ImageView) findViewById(R.id.buttonAdmin);


        tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                tech.setBackgroundColor(Color.parseColor("#0080FF"));
//                tech.setTextColor(Color.WHITE);
//
//                new CountDownTimer(300, 50) {
//
//                    @Override
//                    public void onTick(long arg0) {
//                        // TODO Auto-generated method stub
//
//                    }
//
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                    @Override
//                    public void onFinish() {
//                        tech.setBackground(getDrawable(R.drawable.custombuttonunpressed));
//                        tech.setTextColor(Color.BLACK);
//                    }
//                }.start();

                Intent intent = new Intent(LoginOptionsActivity.this,MainActivity.class);
                intent.putExtra("usertype","tech");
                startActivity(intent);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                health.setBackgroundColor(Color.parseColor("#0080FF"));
//                health.setTextColor(Color.WHITE);
//
//                new CountDownTimer(300, 50) {
//
//                    @Override
//                    public void onTick(long arg0) {
//                        // TODO Auto-generated method stub
//
//                    }
//
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                    @Override
//                    public void onFinish() {
//                        health.setBackground(getDrawable(R.drawable.custombuttonunpressed));
//                        health.setTextColor(Color.BLACK);
//                    }
//                }.start();


                Intent intent = new Intent(LoginOptionsActivity.this,MainActivity.class);
                intent.putExtra("usertype","health");
                startActivity(intent);

            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                admin.setBackgroundColor(Color.parseColor("#0080FF"));
//                admin.setTextColor(Color.WHITE);
//
//                new CountDownTimer(300, 50) {
//
//                    @Override
//                    public void onTick(long arg0) {
//                        // TODO Auto-generated method stub
//
//                    }
//
//                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                    @Override
//                    public void onFinish() {
//                        admin.setBackground(getDrawable(R.drawable.custombuttonunpressed));
//                        admin.setTextColor(Color.BLACK);
//                    }
//                }.start();


                Intent intent = new Intent(LoginOptionsActivity.this,MainActivity.class);
                intent.putExtra("usertype","admin");
                startActivity(intent);
            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginOptionsActivity.this,Pre_Login_Activity.class);
        startActivity(intent);
    }
}