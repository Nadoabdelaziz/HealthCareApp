package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.healthcare.DoctorUI.DisplayPatientInfo;
import com.example.healthcare.DoctorUI.SearchPatientAdapter;
import com.example.healthcare.models.Patient;
import com.example.healthcare.models.Student;
import com.example.healthcare.models.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FirstFragment extends Fragment {
    protected View mView;
    Context context;
//
//    ListView listView;
    SearchPatientAdapter adapter;
    List<Student> myStudents;
    List<Teacher> myTeachers;

    //    SearchView searchView;

    // Recycler View object
    RecyclerView recyclerView;
    RecyclerView SecrecyclerView;

    // Array list for recycler view data source
    ArrayList<String> source;

    // Layout Manager
    // RecyclerView.LayoutManager RecyclerViewLayoutManager;

    // adapter class object
    StudentsAdapter studentsAdapter;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;
    LinearLayoutManager SecHorizontalLayout;

    View ChildView;
    int RecyclerViewItemPosition;
    private FirebaseAuth fbAuth;

    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        this.mView = view;

        final TextView txt = (TextView) mView.findViewById(R.id.user_name);

        final TextView USRtype = (TextView) mView.findViewById(R.id.user_name2);

        final TextView lefttxt = (TextView) mView.findViewById(R.id.watchallforadmin);
        final TextView righttxt = (TextView) mView.findViewById(R.id.trendingforadmin);

        final TextView showall = (TextView) mView.findViewById(R.id.showall);

        fbAuth = FirebaseAuth.getInstance();
        if(fbAuth.getCurrentUser() != null){
            final String uid = fbAuth.getCurrentUser().getUid().toString();

            recyclerView = (RecyclerView)mView.findViewById(R.id.recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            SecrecyclerView = (RecyclerView)mView.findViewById(R.id.secondrecyclerviewforadmin);
            SecrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            if(!fbAuth.getCurrentUser().getEmail().equals("admin@gmail.com")) {


                if (fbAuth.getCurrentUser().getEmail().equals("health@live.com")) {
                    USRtype.setText("أمين");
                    showall.setText("مشاهدة الكل");

                    databaseReference = FirebaseDatabase.getInstance().getReference("HealthPrompts");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            txt.setText(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]", "")).child("firstName").getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    myStudents = new ArrayList<>();
                    //AddItemsToRecyclerViewArrayList();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Student student = data.getValue(Student.class);
                                myStudents.add(student);

                            }
                            studentsAdapter = new StudentsAdapter(myStudents);
                            HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
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

                else {
                    USRtype.setText("مدرس");
                    showall.setText("مشاهدة الكل");
                    // hi text
                    databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            txt.setText(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]", "")).child("firstName").getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    myStudents = new ArrayList<>();
                    //AddItemsToRecyclerViewArrayList();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Student student = data.getValue(Student.class);
                                myStudents.add(student);

                            }
                            studentsAdapter = new StudentsAdapter(myStudents);
                            HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
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
            else{

                USRtype.setText("مشرف");

                lefttxt.setVisibility(View.VISIBLE);
                righttxt.setVisibility(View.VISIBLE);
                SecrecyclerView.setVisibility(View.VISIBLE);

                showall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(),CreateStudentActivity.class);
                        startActivity(intent);
                    }
                });


                // hi text
                databaseReference = FirebaseDatabase.getInstance().getReference("Admins");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        txt.setText(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]","")).child("firstName").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                myTeachers = new ArrayList<>();
                myStudents = new ArrayList<>();
                //AddItemsToRecyclerViewArrayList();


                //for students
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Students");
                reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren())
                        {
                            Student student = data.getValue(Student.class);
                            myStudents.add(student);

                        }
                        studentsAdapter = new StudentsAdapter(myStudents);
                        SecHorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(SecHorizontalLayout);
                        recyclerView.setAdapter(studentsAdapter);
                        recyclerView.setHasFixedSize(true);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });



                //for teachers
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren())
                        {
                            Teacher teacher = data.getValue(Teacher.class);
                            myTeachers.add(teacher);

                        }
                        studentsAdapter = new StudentsAdapter(myTeachers,true);
                        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        SecrecyclerView.setLayoutManager(HorizontalLayout);
                        SecrecyclerView.setAdapter(studentsAdapter);
                        SecrecyclerView.setHasFixedSize(true);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });





            }
        }
        else{
            Log.d("TAG", "not logged ");
            txt.setText("hello");
        }



//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), DisplayPatientInfo.class);
//                intent.putExtra("fullName",myPatients.get(position).getFullName());
//                intent.putExtra("email",myPatients.get(position).getEmail());
//                intent.putExtra("birthDate",myPatients.get(position).getBirthDate());
//                intent.putExtra("phoneNumber",myPatients.get(position).getPhoneNumber());
//                intent.putExtra("cin", myPatients.get(position).getCin());
//                intent.putExtra("maritalStatus", myPatients.get(position).getMaritalStatus());
//                startActivity(intent);
//            }
//        });


//        source.add("محمد");
//        source.add("علي");
//        source.add("حسن");
//        source.add("سعيد");
//        source.add("ياسر");

//
//
//        recyclerView = (RecyclerView)mView.findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//
//        myPatients = new ArrayList<>();
//        //AddItemsToRecyclerViewArrayList();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patients");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot data : dataSnapshot.getChildren())
//                {
//                    Patient patient = data.getValue(Patient.class);
//                    myPatients.add(patient);
//
//                }
//                studentsAdapter = new StudentsAdapter(myPatients);
//                HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//                recyclerView.setLayoutManager(HorizontalLayout);
//                recyclerView.setAdapter(studentsAdapter);
//                recyclerView.setHasFixedSize(true);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });






        //Log.d("TAG", String.valueOf(myPatients.isEmpty()));


        //Log.d("TAG", String.valueOf(myPatients.isEmpty()));
        //Log.d("TAG", String.valueOf(studentsAdapter.getItemCount()));




        return view;
    }

    // Function to add items in RecyclerView.
    public void AddItemsToRecyclerViewArrayList() {
        // Adding items to ArrayList

        source = new ArrayList<>();


//        recyclerView.setAdapter(new StudentsAdapter(source));
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patients");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot data : dataSnapshot.getChildren())
//                {
//                    Patient patient = data.getValue(Patient.class);
//                    myPatients.add(patient);
//                    Log.d("TAG", patient.getFirstName());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });



        source.add("محمد");
        source.add("علي");
        source.add("حسن");
        source.add("سعيد");
        source.add("ياسر");
    }



}