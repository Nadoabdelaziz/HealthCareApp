package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthcare.models.Doctor;
import com.example.healthcare.models.Patient;
import com.example.healthcare.models.Relationship;
import com.example.healthcare.models.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterOrdersActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseUser user;
    ListView myTeacherListview;
    List<Teacher> Teachers;
    AuthOrdersAdapter adapter;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_orders);
        myTeacherListview = findViewById(R.id.myOrders);
        Teachers = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teachers.clear();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Teacher teacher = data.getValue(Teacher.class);
                    if(teacher.getMaritalStatus().equals("0")){
                        Teachers.add(teacher);
                    }
                }
                adapter = new AuthOrdersAdapter(RegisterOrdersActivity.this, Teachers);
                myTeacherListview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

//        myTeacherListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LayoutInflater inflater = getLayoutInflater();
//                View row = inflater.inflate(R.layout.authorder_row, parent, false);
//                Button accept = (Button) row.findViewById(R.id.acceptButton);
//                deleteImageView.setOnClickListener(new OnClickListener() {
//                    public void onClick(View v) {
//                        //...
//                    }
//                });
//                Log.d("TAG", "onItemClick: ");
//            }
//        });

    }

    public void acceptR(View v){
        final int position = myTeacherListview.getPositionForView((View) v.getParent());

        database = FirebaseDatabase.getInstance();
  //      user = FirebaseAuth.getInstance().getCurrentUser();
        //final String userUid = user.getUid();
//        Toast.makeText(this, adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();

        Teacher teacher = (Teacher) adapter.getItem(position);


        DatabaseReference dbRef = database.getReference("Teachers");
        final Teacher teacher1 = new Teacher(teacher.getFirstName(),teacher.getLastName(),teacher.getBirthDate(),teacher.getPhoneNumber(),teacher.getEmail(),teacher.getCin(),"1");
        dbRef.child(teacher1.getEmail().replaceAll("[-+.@:.]","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().setValue(teacher1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        database = FirebaseDatabase.getInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        final String userUid = user.getUid();

//        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String data = dataSnapshot.child(userUid).child("firstName").getValue(String.class);
////                Log.d("TAG", data);
////                fullName.setText(fullNameRetrieved);
////                specialityRetrieved = dataSnapshot.child(uid).child("lastName").getValue(String.class);
////                speciality.setText(specialityRetrieved);
////                emailRetrieved = dataSnapshot.child(uid).child("email").getValue(String.class);
////                email.setText(emailRetrieved);
////                phoneNumberRetrieved = dataSnapshot.child(uid).child("phoneNumber").getValue(String.class);
////                phoneNumber.setText(phoneNumberRetrieved);
//
////                addressRetrieved = dataSnapshot.child(uid).child("address").getValue(String.class);
////                cityRetrieved = dataSnapshot.child(uid).child("city").getValue(String.class);
////                address.setText(addressRetrieved + ", " + cityRetrieved);
//
////                codeRetrieved = dataSnapshot.child(uid).child("code").getValue(String.class);
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        database = FirebaseDatabase.getInstance();
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        final String userUid = user.getUid();
//        DatabaseReference dbRef = database.getReference("Teachers");
//        //String[] fn = fullName.getText().toString().split(" ");
//        final Teacher teacher = new Teacher();
//        dbRef.child(userUid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                dataSnapshot.getRef().setValue(patient);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        //Log.d("TAG", "acceptR: "+myTeacherListview.getItemAtPosition(position));
    }
}