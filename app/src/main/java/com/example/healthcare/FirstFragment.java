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
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.healthcare.DoctorUI.DisplayPatientInfo;
import com.example.healthcare.DoctorUI.SearchPatientAdapter;
import com.example.healthcare.models.Patient;
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
    List<Patient> myPatients;
//    SearchView searchView;

    // Recycler View object
    RecyclerView recyclerView;

    // Array list for recycler view data source
    ArrayList<String> source;

    // Layout Manager
    // RecyclerView.LayoutManager RecyclerViewLayoutManager;

    // adapter class object
    StudentsAdapter studentsAdapter;

    // Linear Layout Manager
    LinearLayoutManager HorizontalLayout;

    View ChildView;
    int RecyclerViewItemPosition;
    private FirebaseAuth fbAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        this.mView = view;

//        listView = mView.findViewById(R.id.student_list);
//        searchView = mView.findViewById(R.id.mySearchViewS);

        TextView txt = (TextView) mView.findViewById(R.id.user_name);

        fbAuth = FirebaseAuth.getInstance();

//        Log.d("TAG", fbAuth.getCurrentUser().getEmail().toString());
        if(fbAuth.getCurrentUser() != null){
            txt.setText(fbAuth.getCurrentUser().getEmail().toString());
        }
        else{
            Log.d("TAG", "not logged ");
            txt.setText("hello");
        }
        myPatients = new ArrayList<>();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                if (TextUtils.isEmpty(s)) {
//                    adapter.filter("");
//                    listView.clearTextFilter();
//                } else {
//                    adapter.filter(s);
//                }
//                return true;
//            }
//        });
//
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


        recyclerView
                = (RecyclerView)mView.findViewById(
                R.id.recyclerview);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext());


        // Set LayoutManager on Recycler View
//        recyclerView.setLayoutManager(
//                RecyclerViewLayoutManager);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Adding items to RecyclerView.

        AddItemsToRecyclerViewArrayList();

        // calling constructor of adapter
        // with source list as a parameter
        studentsAdapter = new StudentsAdapter(source);

        // Set Horizontal Layout Manager
        // for Recycler view
        HorizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);

        // Set adapter on recycler view
        recyclerView.setAdapter(studentsAdapter);




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



        source.add("mohamed");
        source.add("ali");
        source.add("hassan");
        source.add("said");
        source.add("yasser");
    }
}