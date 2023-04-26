package com.example.healthcare;

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

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.MyView> {

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

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            // initialise TextView with id
            textView = (TextView)view
                    .findViewById(R.id.textview);
            //id = (TextView) view.findViewById(R.id.textView2);
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
    public void onBindViewHolder(final MyView holder,
                                 final int position)
    {

        // Set the text of each item of
        // Recycler view with the list items
        if(!teacherslist_for_admin) {
            holder.textView.setText(list.get(position).getName());
        }
        else{
            holder.textView.setText(teacherslist.get(position).getFirstName());
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

