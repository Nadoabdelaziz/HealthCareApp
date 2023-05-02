package com.example.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=4000;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences("login", MODE_PRIVATE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sp.getBoolean("loggedPatient", false)) {
                    Log.d("TAGG", "onCreate: logged in 12");
                    Intent intent = new Intent(SplashActivity.this, TheFragmnetsActivity.class);
                    startActivity(intent);
                }
                else if(sp.getBoolean("loggedDoctor", false))
                {
                    Log.d("TAGG", "onCreate: logged in 13");
                    Intent intent = new Intent(SplashActivity.this, TheFragmnetsActivity.class);
                    startActivity(intent);
                }
                else if(sp.getBoolean("loggedHealth", false))
                {
                    Log.d("TAGG", "onCreate: logged in 1");
                    Intent intent = new Intent(SplashActivity.this, HealthFragmentsActivity.class);
                    startActivity(intent);
                }
                else {

                    Intent homeIntent = new Intent(SplashActivity.this, Pre_Login_Activity.class);
                    startActivity(homeIntent);
                    finish();
                }
//                Intent homeIntent = new Intent(SplashActivity.this, Pre_Login_Activity.class);
//                startActivity(homeIntent);
//                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
