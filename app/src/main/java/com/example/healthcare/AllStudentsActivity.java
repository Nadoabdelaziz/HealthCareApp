package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.healthcare.models.Student;
import com.example.healthcare.models.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllStudentsActivity extends AppCompatActivity {

    List<Student> myStudents,studentsSearch;

    List<Teacher> myteachers,teachersearch;

    //    SearchView searchView;

    ImageView back;
    Button addstd;
    // Recycler View object
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    StudentsAdapter studentsAdapter,adapterSearch;
    LinearLayoutManager HorizontalLayout;
    SearchView searchView;
    TextView titleType;
    TextView titleType2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_students);

        titleType = (TextView) findViewById(R.id.txtt2);
        titleType2 = (TextView) findViewById(R.id.txtt);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myStudents = new ArrayList<>();
        studentsSearch = new ArrayList<>();

        myteachers = new ArrayList<>();
        teachersearch = new ArrayList<>();

        back = (ImageView) findViewById(R.id.backbtn);
        addstd = (Button) findViewById(R.id.addnewstd);


        addstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllStudentsActivity.this, CreateStudentActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchView = findViewById(R.id.mySearchView);


        boolean teacherspage = getIntent().getBooleanExtra("teachers_for_admin", false);

        if (teacherspage) {

            titleType.setText("المعلمون");
            titleType2.setText("الملفات الشخصية للمعلمين");
            addstd.setVisibility(View.GONE);


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String query) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Teacher teacher = data.getValue(Teacher.class);
//                                Log.d("TAG", "Change++++: "+student.getName());
                                if(teacher.getFirstName().equals(query)){
//                                    Log.d("TAG", "onDataChange: added here 1");
                                    teachersearch.add(teacher);
                                }

                            }
                            adapterSearch = new StudentsAdapter(teachersearch,true);
                            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(HorizontalLayout);
                            recyclerView.setAdapter(adapterSearch);
                            recyclerView.setHasFixedSize(true);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                    searchView.clearFocus();
                    teachersearch.clear();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return true;
                }
            });



            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                        Log.d("TAG", "onDataChange: added here 2");
                        Teacher teacher = data.getValue(Teacher.class);
                        myteachers.add(teacher);

                    }
                    studentsAdapter = new StudentsAdapter(myteachers,true);
                    HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(HorizontalLayout);
                    recyclerView.setAdapter(studentsAdapter);
                    recyclerView.setHasFixedSize(true);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });

        } else {

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String query) {
                    Log.d("TAG", "dkhlt ");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Student student = data.getValue(Student.class);
                                Log.d("TAG", "Change++++: " + student.getName());
                                if (student.getName().equals(query)) {
                                    Log.d("TAG", "onDataChange: added here 1");
                                    studentsSearch.add(student);
                                }

                            }
                            adapterSearch = new StudentsAdapter(studentsSearch);
                            HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(HorizontalLayout);
                            recyclerView.setAdapter(adapterSearch);
                            recyclerView.setHasFixedSize(true);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });
                    searchView.clearFocus();
                    studentsSearch.clear();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return true;
                }
            });


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Log.d("TAG", "onDataChange: added here 2");
                        Student student = data.getValue(Student.class);
                        myStudents.add(student);

                    }
                    studentsAdapter = new StudentsAdapter(myStudents);
                    HorizontalLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(HorizontalLayout);
                    recyclerView.setAdapter(studentsAdapter);
                    recyclerView.setHasFixedSize(true);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }
    }
}