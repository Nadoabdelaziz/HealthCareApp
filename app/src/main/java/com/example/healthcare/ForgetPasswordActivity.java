package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button resetEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.email);
        resetEmailButton = findViewById(R.id.resetButton);
        resetEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText())) {
                    Toast.makeText(ForgetPasswordActivity.this, "Email field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//                                        SweetAlertDialog alertDialog = new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.SUCCESS_TYPE);
//                                        alertDialog.setTitleText("Email sent successfully !");
//                                        alertDialog.setContentText("Check your email box !");
//                                        alertDialog.show();
//                                        Button btn = alertDialog.findViewById(R.id.confirm_button);
//                                        btn.setBackgroundColor(Color.parseColor("#33AEB6"));
//                                        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                            @Override
//                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        new SweetAlertDialog(ForgetPasswordActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Done")
                                                .setContentText("Link is sent to your Email")
                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                        Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show();

//                                            }
//                                        });
//                                        alertDialog.show();
                                    }
                                }
                            });
                    }
                }
            });

            }
    }