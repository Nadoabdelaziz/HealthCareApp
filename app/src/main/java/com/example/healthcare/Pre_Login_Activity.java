package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pre_Login_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);


        Button btnLog = (Button) findViewById(R.id.loginnew);
        Button btnReg = (Button) findViewById(R.id.registernew);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Pre_Login_Activity.this, MainActivity.class);
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
    }
}