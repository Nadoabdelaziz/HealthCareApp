package com.example.healthcare;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginOptionsActivity extends AppCompatActivity {

    Button tech,health,admin;
    ImageView backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        backbtn = (ImageView) findViewById(R.id.backbtn);

        tech = (Button) findViewById(R.id.buttonTech);
        health = (Button) findViewById(R.id.buttonHealth);
        admin = (Button) findViewById(R.id.buttonAdmin);


        tech.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                tech.setBackground(getDrawable(R.drawable.custombutton));
                tech.setTextColor(Color.WHITE);
                Drawable img = tech.getContext().getResources().getDrawable( R.drawable.tech_icon );
                img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                tech.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);

                new CountDownTimer(300, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        tech.setBackground(getDrawable(R.drawable.custombuttonunpressed));
                        tech.setTextColor(Color.parseColor("#7A7A7A"));
                        img.setColorFilter(Color.parseColor("#7A7A7A"), PorterDuff.Mode.SRC_ATOP);

                    }
                }.start();

                Intent intent = new Intent(LoginOptionsActivity.this,MainActivity.class);
                intent.putExtra("usertype","tech");
                startActivity(intent);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                health.setBackground(getDrawable(R.drawable.custombutton));
                health.setTextColor(Color.WHITE);
                Drawable img = health.getContext().getResources().getDrawable( R.drawable.health_icon );
                img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                health.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);

                new CountDownTimer(300, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        health.setBackground(getDrawable(R.drawable.custombuttonunpressed));
                        health.setTextColor(Color.parseColor("#7A7A7A"));
                        img.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
                    }
                }.start();


                Intent intent = new Intent(LoginOptionsActivity.this,MainActivity.class);
                intent.putExtra("usertype","health");
                startActivity(intent);

            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                admin.setBackground(getDrawable(R.drawable.custombutton));
                admin.setTextColor(Color.WHITE);
                Drawable img = admin.getContext().getResources().getDrawable( R.drawable.admin_icon );
                img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                admin.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);

                new CountDownTimer(300, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        admin.setBackground(getDrawable(R.drawable.custombuttonunpressed));
                        admin.setTextColor(Color.parseColor("#7A7A7A"));
                        img.setColorFilter(Color.parseColor("#7A7A7A"), PorterDuff.Mode.SRC_ATOP);
                    }
                }.start();


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