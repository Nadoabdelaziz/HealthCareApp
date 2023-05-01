package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthcare.models.Comment;
import com.example.healthcare.models.Student;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateStudentActivity extends AppCompatActivity {

    public EditText name, nickname, PhoneNumber, schoolname, gender, nationality, bloodtype, disease, precuations, known_as;
    Button createButton;

    loadingDialog ld;
    //private FirebaseAuth fbAuth;
    List<Student> students;
    DatabaseReference databaseReference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);


        name = (EditText) findViewById(R.id.Stdname);
        //id
        nickname = (EditText) findViewById(R.id.Stdnickname);
        PhoneNumber = (EditText) findViewById(R.id.StdPhoneNumber);
        schoolname = (EditText) findViewById(R.id.StdSchoolname);
        gender = (EditText) findViewById(R.id.StdGender);
        nationality = (EditText) findViewById(R.id.StdNation);
        bloodtype = (EditText) findViewById(R.id.StdBldType);
        disease = (EditText) findViewById(R.id.Stddisease);
        precuations = (EditText) findViewById(R.id.StdPrctions);
        known_as = (EditText) findViewById(R.id.Stdknown);

        createButton = (Button) findViewById(R.id.createStdButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateStudent();
            }
        });
        students = new ArrayList<>();

        nickname.setVisibility(View.GONE);

        if (
                TextUtils.isEmpty(name.toString())
                        || TextUtils.isEmpty(PhoneNumber.toString())
                        || TextUtils.isEmpty(schoolname.toString())
                        || TextUtils.isEmpty(gender.toString())
                        || TextUtils.isEmpty(nationality.toString())
                        || TextUtils.isEmpty(bloodtype.toString())
                        || TextUtils.isEmpty(disease.toString())
                        || TextUtils.isEmpty(precuations.toString())
                        || TextUtils.isEmpty(known_as.toString())

        ) {
            Toast.makeText(CreateStudentActivity.this, "All fields are required !", Toast.LENGTH_LONG).show();
        } else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    students.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Student student= data.getValue(Student.class);
                        if (student.getName().equals(name.toString()) || student.getNickname().equals(nickname) || student.getPhoneNumber().equals(PhoneNumber) ) {
                            new SweetAlertDialog(CreateStudentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Student Already exist !")
                                    .show();
                            return;
                        }
                        students.add(student);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });


        }


    }

    public void CreateStudent() {
        if (       TextUtils.isEmpty(name.toString())
                || TextUtils.isEmpty(PhoneNumber.toString())
                || TextUtils.isEmpty(schoolname.toString())
                || TextUtils.isEmpty(gender.toString())
                || TextUtils.isEmpty(nationality.toString())
                || TextUtils.isEmpty(bloodtype.toString())
                || TextUtils.isEmpty(disease.toString())
                || TextUtils.isEmpty(precuations.toString())
                || TextUtils.isEmpty(known_as.toString())) {

            new SweetAlertDialog(CreateStudentActivity.this)
                    .setTitleText("Creating Student")
                    .setContentText("Please enter all data")
                    .show();
            return;
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admins");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Random r = new Random();
                int i1 = r.nextInt(100 - 20) + 20;
                String newID = "2203003"+String.valueOf(i1);
                Student studentRecord = new Student(name.getText().toString(),newID,PhoneNumber.getText().toString(),schoolname.getText().toString(),gender.getText().toString(),nationality.getText().toString(),bloodtype.getText().toString(),disease.getText().toString(),precuations.getText().toString(),known_as.getText().toString());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                reference.push().setValue(studentRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new SweetAlertDialog(CreateStudentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Congratulations")
                                .setContentText("Student is Created successfully")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(CreateStudentActivity.this, TheFragmnetsActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        new SweetAlertDialog(CreateStudentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong!")
                                .show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}