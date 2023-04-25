package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.healthcare.DoctorUI.DisplayPatientInfo;
import com.example.healthcare.DoctorUI.SearchPatientAdapter;
import com.example.healthcare.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstFragment extends Fragment {
    protected View mView;
    Context context;

    ListView listView;
    SearchPatientAdapter adapter;
    List<Patient> myPatients;
    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);
        this.mView = view;

        listView = mView.findViewById(R.id.student_list);
        searchView = mView.findViewById(R.id.mySearchViewS);
        myPatients = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Patients");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myPatients.clear();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Patient patient = data.getValue(Patient.class);
                    myPatients.add(patient);
                    Collections.sort(myPatients);
                    adapter = new SearchPatientAdapter(getContext(), myPatients);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    adapter.filter("");
                    listView.clearTextFilter();
                } else {
                    adapter.filter(s);
                }
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DisplayPatientInfo.class);
                intent.putExtra("fullName",myPatients.get(position).getFullName());
                intent.putExtra("email",myPatients.get(position).getEmail());
                intent.putExtra("birthDate",myPatients.get(position).getBirthDate());
                intent.putExtra("phoneNumber",myPatients.get(position).getPhoneNumber());
                intent.putExtra("cin", myPatients.get(position).getCin());
                intent.putExtra("maritalStatus", myPatients.get(position).getMaritalStatus());
                startActivity(intent);
            }
        });

        return view;
    }

}