package com.example.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.DoctorUI.DoctorMenuActivity;
import com.example.healthcare.models.HealthPrompt;
import com.example.healthcare.models.Student;
import com.example.healthcare.models.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText login;
    private EditText password;
    private TextView errorMessage;
    private TextView AutherrorMessage;

    CheckBox rememberMe;
    SharedPreferences sp;
    DatabaseReference ref;

    DatabaseReference ref2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        errorMessage = findViewById(R.id.errorMessage);
        AutherrorMessage = findViewById(R.id.UnAuthMsg);

        rememberMe = findViewById(R.id.rememberMe);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        //sp.edit().putBoolean("loggedHealth", false).apply();

//        sp.edit().putBoolean("loggedDoctor",false).apply();
//        sp.edit().putBoolean("loggedPatient",false).apply();

        if (sp.getBoolean("loggedPatient", false)) {
            Log.d("TAGG", "onCreate: logged in 12");
            Intent intent = new Intent(MainActivity.this, TheFragmnetsActivity.class);
            startActivity(intent);
        }
        if(sp.getBoolean("loggedDoctor", false))
        {
            Log.d("TAGG", "onCreate: logged in 13");
            Intent intent = new Intent(MainActivity.this, TheFragmnetsActivity.class);
            startActivity(intent);
        }
        if(sp.getBoolean("loggedHealth", false))
        {
            Log.d("TAGG", "onCreate: logged in 1");
            Intent intent = new Intent(MainActivity.this, HealthFragmentsActivity.class);
            startActivity(intent);
        }

    }

    public void createAccount(View view) {
        Intent createAccountIntent = new Intent(this, CreateAccount.class);
        startActivity(createAccountIntent);
    }


    public void login(View view) {
        errorMessage.setVisibility(View.GONE);
        AutherrorMessage.setVisibility(View.GONE);

        String tempLogin = login.getText().toString().trim();
        String tempPassword = password.getText().toString().trim();
        if (
                TextUtils.isEmpty(tempLogin)
                        || TextUtils.isEmpty(tempPassword)
        ) {
            Toast.makeText(MainActivity.this, "Login or Password are empty", Toast.LENGTH_SHORT).show();
        } else {
            final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#33aeb6"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
            mAuth.signInWithEmailAndPassword(tempLogin, tempPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        ref2 = FirebaseDatabase.getInstance().getReference("HealthPrompts");
//                        ref = FirebaseDatabase.getInstance().getReference("Doctors");
                        ref2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                String email = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]","")).child("email").getValue(String.class);
                                // Log.d("TAG", "onComplete: "+FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]",""));

                                if (email != null) {
                                    for(DataSnapshot data : dataSnapshot.getChildren())
                                    {
                                        HealthPrompt healthPrompt = data.getValue(HealthPrompt.class);
                                        Log.d("TAG", "onDataChange1: "+healthPrompt.getEmail());
                                        Log.d("TAG", "onDataChange2: "+login.getText().toString());
                                        //Log.d("TAG", teacher.getFirstName());
                                    }
                                    pDialog.hide();
                                    if (rememberMe.isChecked()) {
                                        Log.d("TAGG", "onCreate: logged in 2");
                                        sp.edit().putBoolean("loggedHealth", true).apply();
                                    } else {
                                        Log.d("TAGG", "onCreate: logged in 3");
                                        sp.edit().putBoolean("loggedHealth", false).apply();
                                    }
                                    Intent intent = new Intent(MainActivity.this, HealthFragmentsActivity.class);
                                    MainActivity.this.startActivity(intent);
                                }
                                else {
                                    ref = FirebaseDatabase.getInstance().getReference("Teachers");
//                        ref = FirebaseDatabase.getInstance().getReference("Doctors");
                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            String email = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]","")).child("email").getValue(String.class);
                                            // Log.d("TAG", "onComplete: "+FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]",""));

                                            // admin logged in
                                            if (email == null) {
                                                Log.d("TAG", "here 1");
                                                pDialog.hide();
                                                if (rememberMe.isChecked()) {
                                                    sp.edit().putBoolean("loggedPatient", true).apply();
                                                } else
                                                    sp.edit().putBoolean("loggedPatient", false).apply();

                                                //admin user
                                                Log.d("TAGG", "onCreate: logged in MAIN");
                                                Intent intent = new Intent(MainActivity.this, TheFragmnetsActivity.class);
                                                MainActivity.this.startActivity(intent);
                                            }
//                                else if(){
//
//                                }

                                            // Teacher Logged in
                                            else {
                                                Log.d("TAGG", "onCreate: logged in MAIN 2");
                                                for(DataSnapshot data : dataSnapshot.getChildren())
                                                {
                                                    Teacher teacher = data.getValue(Teacher.class);
                                                    Log.d("TAG", "onDataChange1: "+teacher.getEmail());
                                                    Log.d("TAG", "onDataChange2: "+login.getText().toString());
                                                    if(teacher.getEmail().equals(login.getText().toString()) && teacher.getMaritalStatus().equals("0")){
                                                        Log.d("TAG", "onDataChange: "+teacher.getEmail());
                                                        AutherrorMessage.setVisibility(View.VISIBLE);
                                                        pDialog.hide();
                                                        return;
                                                    }
                                                    //Log.d("TAG", teacher.getFirstName());

                                                }

                                                Log.d("TAG", "here 2");
                                                pDialog.hide();
                                                if (rememberMe.isChecked()) {
                                                    sp.edit().putBoolean("loggedDoctor", true).apply();
                                                } else
                                                    sp.edit().putBoolean("loggedDoctor", false).apply();
                                                Log.d("TAGG", "onCreate: logged in 11");
                                                Intent intent = new Intent(MainActivity.this, TheFragmnetsActivity.class);
                                                MainActivity.this.startActivity(intent);
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                                //Log.d("TAGG", "onCreate: logged in 44");
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });






                    } else {
                        errorMessage.setVisibility(View.VISIBLE);
                        pDialog.hide();
//
                    }
                }
            });
        }
    }

    public void passwordForgotten(View view) {
        ResetPasswordDialog resetPasswordDialog = new ResetPasswordDialog();
        resetPasswordDialog.show(getSupportFragmentManager(), "reset password dialog");

    }
}
