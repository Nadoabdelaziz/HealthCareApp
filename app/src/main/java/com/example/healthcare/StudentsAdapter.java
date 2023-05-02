package com.example.healthcare;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.models.Admin;
import com.example.healthcare.models.Patient;
import com.example.healthcare.models.Student;
import com.example.healthcare.models.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyView>  {

    // List with String type
    List<Student> list;
    List<Teacher> teacherslist;
    boolean teacherslist_for_admin;


    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;
        TextView id;
        View view;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            this.view=view;
            // initialise TextView with id
            textView = (TextView)view
                    .findViewById(R.id.textview);
            id = (TextView) view.findViewById(R.id.textview100);
        }


    }


    // Constructor for adapter class
    // which takes a list of String type
    public StudentsAdapter(List<Student> horizontalList)
    {
        this.list = horizontalList;
        Log.d("UTAG", String.valueOf(horizontalList.isEmpty()));
    }


    public StudentsAdapter(List<Teacher> horizontalList, boolean admintrue)
    {
        this.teacherslist_for_admin=admintrue;
        this.teacherslist = horizontalList;
        Log.d("UTAG", String.valueOf(horizontalList.isEmpty()));
    }


    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.student_item,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }


    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(MyView holder,
                                 final int position) {

        if (!teacherslist_for_admin){
            holder.view.setOnClickListener(new View.OnClickListener() {  // <--- here
                @Override
                public void onClick(View v) {
                    final View newV = v;

                    Log.i("W4K", "Click-" + position);
                    final List<Student> Stds = new ArrayList<>();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Stds.clear();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Student student = data.getValue(Student.class);
                                Stds.add(student);
                            }
                            Student std = (Student) Stds.get(position);
                            //        Log.d("TAG", "DisplayStdInfo: Student Clicked");

                            Intent intent = new Intent(newV.getContext(), DisplayStudentActivity.class);
                            intent.putExtra("fullName", std.getName());
                            intent.putExtra("nickname", std.getNickname());
                            intent.putExtra("schoolname", std.getSchoolname());
                            intent.putExtra("gender", std.getGender());
                            intent.putExtra("bloodtype", std.getBloodtype());
                            intent.putExtra("nation", std.getNationality());
                            intent.putExtra("phoneNumber", std.getPhoneNumber());
                            intent.putExtra("diseases", std.getDisease());
                            intent.putExtra("prec", std.getPrecuations());

                            newV.getContext().startActivity(intent);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });

                    Log.d("TAG", "onClick: " + Stds.isEmpty());
                    Log.d("TAG", "onClick: " + position);
                    //Log.d("TAG", "onClick: "+Stds.get(position));
//                Student std = (Student) Stds.get(position);
//                Log.d("TAG", "onClick: "+std.getName());

                    //v.getContext().startActivity(new Intent(v.getContext(),MainActivity.class));  // <--- here
                }
            });

        }
        else{
            holder.view.setOnClickListener(new View.OnClickListener() {  // <--- here
                @Override
                public void onClick(View v) {
                    final View newV = v;

                    Log.i("W4K", "Click-" + position);
                    final List<Teacher> TeacherZ = new ArrayList<>();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            TeacherZ.clear();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Teacher teacher = data.getValue(Teacher.class);
                                TeacherZ.add(teacher);
                            }
                            Teacher std = (Teacher) TeacherZ.get(position);
                            //        Log.d("TAG", "DisplayStdInfo: Student Clicked");

                            Intent intent = new Intent(newV.getContext(), DisplayStudentActivity.class);
                            intent.putExtra("fullName", std.getFirstName());
                            intent.putExtra("nickname", std.getCin());
                            intent.putExtra("schoolname", std.getLastName());
                            intent.putExtra("gender", "tech");
                            intent.putExtra("bloodtype", "tech");
                            intent.putExtra("nation", "tech");
                            intent.putExtra("phoneNumber", std.getPhoneNumber());
                            intent.putExtra("diseases", "tech");
                            intent.putExtra("prec", "tech");
//                            intent.putExtra("diseases", "NO");
                            newV.getContext().startActivity(intent);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            System.out.println("The read failed: " + databaseError.getCode());
                        }
                    });

                    Log.d("TAG", "onClick: " + TeacherZ.isEmpty());
                    Log.d("TAG", "onClick: " + position);
                    //Log.d("TAG", "onClick: "+Stds.get(position));
//                Student std = (Student) Stds.get(position);
//                Log.d("TAG", "onClick: "+std.getName());

                    //v.getContext().startActivity(new Intent(v.getContext(),MainActivity.class));  // <--- here
                }
            });
        }
        // Set the text of each item of
        // Recycler view with the list items
        if(!teacherslist_for_admin) {
            holder.textView.setText(list.get(position).getName());
            holder.id.setText("ID: " + list.get(position).getNickname());
        }
        else{
            holder.textView.setText(teacherslist.get(position).getFirstName());
            holder.id.setText("ID: "+ teacherslist.get(position).getCin());
        }
        //Log.d("UTAG", list.get(position).getPhoneNumber().toString());
        //holder.id.setText(list.get(position).getPhoneNumber().toString());
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        if(!teacherslist_for_admin){
            return list.size();
        }
        return teacherslist.size();
    }
}

