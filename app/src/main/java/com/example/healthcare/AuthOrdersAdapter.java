package com.example.healthcare;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.healthcare.models.Doctor;
import com.example.healthcare.models.Teacher;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthOrdersAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    List<Teacher> myTeacherList;
    ArrayList<Teacher> arrayList;


    public AuthOrdersAdapter(Context context, List<Teacher> myTeacherList) {
        mContext = context;
        this.myTeacherList = myTeacherList;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList();
        this.arrayList.addAll(myTeacherList);
    }

    @Override
    public int getCount() {
        return myTeacherList.size();
    }

    @Override
    public Object getItem(int position) {
        return myTeacherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.authorder_row, null);

        final CircleImageView doctorPicture = convertView.findViewById(R.id.profile_image);


        final TextView doctorFullName = convertView.findViewById(R.id.fullName);
        final TextView speciality = convertView.findViewById(R.id.speciality);
        final Teacher teacher = myTeacherList.get(position);
        final String emailDoctor = teacher.getEmail();


        doctorFullName.setText(teacher.getFirstName());
        speciality.setText(teacher.getEmail());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference profileRef = storageReference.child("Profile pictures").child(emailDoctor+".jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri).into(doctorPicture);

            }
        });
        return convertView;
    }
}