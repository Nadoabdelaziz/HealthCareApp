package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.healthcare.DoctorUI.DisplayPatientInfo;
import com.example.healthcare.DoctorUI.SearchPatientAdapter;
import com.example.healthcare.models.Comment;
import com.example.healthcare.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SecondFragment extends Fragment {
    ListView listView;
    SearchComentsAdapter adapter;
    List<Comment> myComments;
    //SearchView searchView;
    Context con;

    View mview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_second, container, false);
        this.mview=view;

        listView = mview.findViewById(R.id.Comment_list);
        //searchView = mview.findViewById(R.id.mySearchViewComment);
        myComments = new ArrayList<>();

        Button createComment = (Button) mview.findViewById(R.id.createcommmentbtn);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myComments.clear();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Comment comment = data.getValue(Comment.class);
                    myComments.add(comment);
                    Collections.sort(myComments);
                    Log.d("TAG", "onDataChange: "+con);
                    Log.d("TAG", "Context is ?? : "+con);
                    adapter = new SearchComentsAdapter(con, myComments);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

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

        createComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CreateCommentActivity.class);
                startActivity(intent);
            }
        });



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
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        con = context;
    }
}