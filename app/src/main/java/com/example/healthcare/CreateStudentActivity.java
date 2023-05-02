package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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

public class CreateStudentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public EditText name, nickname, PhoneNumber, schoolname, gender, nationality, bloodtype;
    EditText precuations, known_as, precuations2, known_as2,precuations3, known_as3,precuations4, known_as4;
    Button createButton;
    ImageButton addhealthrecord;
    TextView btnprecuations,stdknown;

    LinearLayout L1,L2,L3,L4;

    loadingDialog ld;
    //private FirebaseAuth fbAuth;
    List<Student> students;
    DatabaseReference databaseReference;
    FirebaseUser user;

    String[] courses = {"حدد المرض","أمراض القلب", "أمراض الكلى",
            "أمراض عصبية", "مشاكل سمعية",
            "مشاكل نفسية", "ربو","الأنيميا المنجلية","فقر الدم(الثلاسيميا)",
            "الصرع","إصابات وإعاقات","السكري","حساسية الطعام","حساسية الأدوية","اخرى","لا يوجد" };
    Spinner spin,spin2,spin3,spin4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);



        L1 = (LinearLayout) findViewById(R.id.health1);
        L2 = (LinearLayout) findViewById(R.id.health2);
        L3 = (LinearLayout) findViewById(R.id.health3);
        L4 = (LinearLayout) findViewById(R.id.health4);

        btnprecuations = (TextView) findViewById(R.id.StdPrctions);
        stdknown = (TextView) findViewById(R.id.Stdknown);


        addhealthrecord = (ImageButton) findViewById(R.id.addhealthrecord);




        spin = findViewById(R.id.Stddisease);
        spin.setOnItemSelectedListener(this);

        spin2 = findViewById(R.id.Stddisease2);
        spin2.setOnItemSelectedListener(this);

        spin3 = findViewById(R.id.Stddisease3);
        spin3.setOnItemSelectedListener(this);

        spin4 = findViewById(R.id.Stddisease4);
        spin4.setOnItemSelectedListener(this);

        addhealthrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(L1.getVisibility() == View.GONE){
                    L1.setVisibility(View.VISIBLE);
                }
                else if(L1.getVisibility() == View.VISIBLE && L2.getVisibility() == View.GONE){
                    L2.setVisibility(View.VISIBLE);
                }
                else if(L1.getVisibility() == View.VISIBLE && L2.getVisibility() == View.VISIBLE && L3.getVisibility() == View.GONE){
                    L3.setVisibility(View.VISIBLE);
                }
                else if(L1.getVisibility() == View.VISIBLE && L2.getVisibility() == View.VISIBLE && L3.getVisibility() == View.VISIBLE && L4.getVisibility() == View.GONE ){
                    L4.setVisibility(View.VISIBLE);
                }
            }
        });

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                courses);

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);
        spin2.setAdapter(ad);
        spin3.setAdapter(ad);
        spin4.setAdapter(ad);



        name = (EditText) findViewById(R.id.Stdname);
        //id
        nickname = (EditText) findViewById(R.id.Stdnickname);
        PhoneNumber = (EditText) findViewById(R.id.StdPhoneNumber);
        schoolname = (EditText) findViewById(R.id.StdSchoolname);
        gender = (EditText) findViewById(R.id.StdGender);
        nationality = (EditText) findViewById(R.id.StdNation);
        bloodtype = (EditText) findViewById(R.id.StdBldType);
        //disease = (EditText) findViewById(R.id.Stddisease);

        precuations = (EditText) findViewById(R.id.StdPrctions);
        known_as = (EditText) findViewById(R.id.Stdknown);

        precuations2 = (EditText) findViewById(R.id.StdPrctions2);
        known_as2 = (EditText) findViewById(R.id.Stdknown2);

        precuations3 = (EditText) findViewById(R.id.StdPrctions3);
        known_as3 = (EditText) findViewById(R.id.Stdknown3);

        precuations4 = (EditText) findViewById(R.id.StdPrctions4);
        known_as4 = (EditText) findViewById(R.id.Stdknown4);


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
                || TextUtils.isEmpty(bloodtype.toString()))
        {

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

                String prec="";
                String dis ="";
                String knownas="";

                if(!spin.getSelectedItem().toString().equals("حدد المرض")){
                    dis =  spin.getSelectedItem().toString();
                }
                if(!spin2.getSelectedItem().toString().equals("حدد المرض")){
                    dis =dis + "-" +spin2.getSelectedItem().toString();
                }
                if(!spin3.getSelectedItem().toString().equals("حدد المرض")){
                    dis = dis + "-" + spin3.getSelectedItem().toString();
                }
                if(!spin4.getSelectedItem().toString().equals("حدد المرض")){
                    dis = dis + "-" + spin4.getSelectedItem().toString();
                }


                if(!precuations.getText().toString().equals("")){
                    prec = precuations.getText().toString();
                }
                if(!precuations2.getText().toString().equals("")){
                    prec = prec + "-" + precuations2.getText().toString();
                }
                if(!precuations3.getText().toString().equals("")){
                    prec = prec + "-" + precuations3.getText().toString();
                }
                if(!precuations4.getText().toString().equals("")){
                    prec = prec + "-" + precuations4.getText().toString();
                }


                if(!known_as.getText().toString().equals("")){
                    knownas = known_as.getText().toString();
                }
                if(!known_as2.getText().toString().equals("")){
                    knownas = knownas + "-" + known_as2.getText().toString();
                }
                if(!known_as3.getText().toString().equals("")){
                    knownas = knownas+ "-" + known_as3.getText().toString();
                }
                if(!known_as4.getText().toString().equals("")){
                    knownas = knownas + "-" + known_as4.getText().toString();
                }

                Student studentRecord = new Student(name.getText().toString(),newID,PhoneNumber.getText().toString(),schoolname.getText().toString(),gender.getText().toString(),nationality.getText().toString(),bloodtype.getText().toString(),dis,prec,knownas);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//        Toast.makeText(getApplicationContext(),
//                        courses[position],
//                        Toast.LENGTH_LONG)
//                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}