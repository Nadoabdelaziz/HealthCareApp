package com.example.healthcare;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.DoctorUI.DisplayDoctorProfileInfo;
import com.example.healthcare.DoctorUI.DoctorEditProfileInfo;
import com.example.healthcare.DoctorUI.DoctorMenuActivity;
import com.google.android.gms.tasks.OnSuccessListener;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class FifthFragment extends Fragment {

    View mview;


    TextView fullName, speciality, email, phoneNumber, address;
    CircleImageView circleImageView;
    FirebaseUser user;
    String uid;
    String Uri;
    DatabaseReference databaseReference;
    String fullNameRetrieved, specialityRetrieved, emailRetrieved, phoneNumberRetrieved, addressRetrieved, cityRetrieved, codeRetrieved;
    SharedPreferences sp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fifth, container, false);
        this.mview = view;

        sp = getActivity().getSharedPreferences("login",getActivity().MODE_PRIVATE);

        fullName = mview.findViewById(R.id.fullName);
        speciality = mview.findViewById(R.id.speciality);
        email = mview.findViewById(R.id.email);
        phoneNumber = mview.findViewById(R.id.phoneNumber);
//        address = mview.findViewById(R.id.fullAddress);
        circleImageView = mview.findViewById(R.id.profile_image);

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        Button logout = (Button) mview.findViewById(R.id.logoutbtn);


        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fullNameRetrieved = dataSnapshot.child(uid).child("firstName").getValue(String.class);
                fullName.setText(fullNameRetrieved);
                specialityRetrieved = dataSnapshot.child(uid).child("lastName").getValue(String.class);
                speciality.setText(specialityRetrieved);
                emailRetrieved = dataSnapshot.child(uid).child("email").getValue(String.class);
                email.setText(emailRetrieved);
                phoneNumberRetrieved = dataSnapshot.child(uid).child("phoneNumber").getValue(String.class);
                phoneNumber.setText(phoneNumberRetrieved);

//                addressRetrieved = dataSnapshot.child(uid).child("address").getValue(String.class);
//                cityRetrieved = dataSnapshot.child(uid).child("city").getValue(String.class);
//                address.setText(addressRetrieved + ", " + cityRetrieved);

//                codeRetrieved = dataSnapshot.child(uid).child("code").getValue(String.class);

                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference profileRef = storageReference.child("Profile pictures").child(emailRetrieved + ".jpg");
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<android.net.Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri = uri.toString();
                        Picasso.get().load(uri).into(circleImageView);
                    }
                });



            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putBoolean("loggedDoctor",false).apply();
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                startActivity(new Intent(getContext(), Pre_Login_Activity.class));

            }
        });
        return view;

//    private void startRevealActivity (View v){
//        //calculates the center of the View v you are passing
//        int revealX = (int) (v.getX() + v.getWidth() / 2);
//        int revealY = (int) (v.getY() + v.getHeight() / 2);
//
//        //create an intent, that launches the second activity and pass the x and y coordinates
//        Intent intent = new Intent(getContext(), DoctorEditProfileInfo.class);
//        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_X, revealX);
//        intent.putExtra(RevealAnimation.EXTRA_CIRCULAR_REVEAL_Y, revealY);
//        intent.putExtra("fullName", fullNameRetrieved);
//        intent.putExtra("speciality", specialityRetrieved);
//        intent.putExtra("email", emailRetrieved);
//        intent.putExtra("phoneNumber", phoneNumberRetrieved);
//        intent.putExtra("address", addressRetrieved);
//        intent.putExtra("city", cityRetrieved);
//        intent.putExtra("code", codeRetrieved);
//        intent.putExtra("imageUri", Uri);
//
//        //just start the activity as an shared transition, but set the options bundle to null
//        ActivityCompat.startActivity(this, intent, null);
//
//        //to prevent strange behaviours override the pending transitions
//        getActivity().overridePendingTransition(0, 0);
//    }

//    public void editProfile (View view){
//        startRevealActivity(view);
//    }
//        public void onBackPressed() {
//            Intent intent = new Intent(getContext(), DoctorMenuActivity.class);
//            startActivity(intent);
//        }
    }
}