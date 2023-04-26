package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.healthcare.DoctorUI.SearchPatientAdapter;
import com.example.healthcare.models.Patient;
import com.example.healthcare.models.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {


    SearchPatientAdapter adapter;
    List<Teacher> myTeachers;
    RecyclerView recyclerView;
    ArrayList<String> source;
    StudentsAdapter studentsAdapter;
    LinearLayoutManager HorizontalLayout;

    View ChildView;
    int RecyclerViewItemPosition;
    private FirebaseAuth fbAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        final TextView txt = (TextView) findViewById(R.id.admin_user_name);
        fbAuth = FirebaseAuth.getInstance();
        if(fbAuth.getCurrentUser() != null){
            final String uid = fbAuth.getCurrentUser().getUid().toString();
            databaseReference = FirebaseDatabase.getInstance().getReference("Admins");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    txt.setText(dataSnapshot.child(uid).child("firstName").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else{
            Log.d("TAG", "not logged ");
            txt.setText("hello");
        }


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


//        source.add("محمد");
//        source.add("علي");
//        source.add("حسن");
//        source.add("سعيد");
//        source.add("ياسر");



        recyclerView = (RecyclerView)findViewById(R.id.recyclerviewTeachers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myTeachers = new ArrayList<>();
        //AddItemsToRecyclerViewArrayList();

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
                HorizontalLayout = new LinearLayoutManager(AdminActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(HorizontalLayout);
                recyclerView.setAdapter(studentsAdapter);
                recyclerView.setHasFixedSize(true);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        //Log.d("TAG", String.valueOf(myPatients.isEmpty()));


        //Log.d("TAG", String.valueOf(myPatients.isEmpty()));
        //Log.d("TAG", String.valueOf(studentsAdapter.getItemCount()));
    }

}