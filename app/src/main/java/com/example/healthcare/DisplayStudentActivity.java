package com.example.healthcare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.example.healthcare.models.Code128;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.healthcare.models.Code128;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
public class DisplayStudentActivity extends AppCompatActivity {

        public int REQUEST_CALL = 1;
        RelativeLayout frameLayout;
        ImageView imageView,backbtn;
        CircleImageView circleImageView;
        TextView fullName, speciality,SchoolName,Gender,BloodT,DateC,Nation,PhNo,Diseases;
        String name,id,school,gender,blood,datee,nation,phno,diseases;
        ProgressBar progressBar;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_student);


            fullName = findViewById(R.id.fullName);
            speciality = findViewById(R.id.ID);

            SchoolName = findViewById(R.id.Scname);
            Gender = findViewById(R.id.Gendern);
            BloodT = findViewById(R.id.BloodN);
            DateC = findViewById(R.id.DateN);
            Nation = findViewById(R.id.NatN);
            PhNo = findViewById(R.id.PhoneN);
            //Diseases = findViewById(R.id.diseases);

            backbtn = findViewById(R.id.backbtn);
            backbtn.bringToFront();

            frameLayout = findViewById(R.id.myFrameLayout);
//            imageView = findViewById(R.id.profile_image);
            circleImageView = findViewById(R.id.profile_image);
            circleImageView.bringToFront();
//            progressBar = findViewById(R.id.myProgressBar);
            circleImageView.setImageBitmap(ImageResizer.decodeSampledBitmapFromResource(getResources(), R.drawable.aliluchloutfi, 250, 500));
            Bitmap bMap = ImageResizer.decodeSampledBitmapFromResource(getResources(), R.drawable.background, 100, 100);
            BitmapDrawable dr = new BitmapDrawable(bMap);
            frameLayout.setBackground(dr);


            Intent intent = getIntent();

            name = intent.getStringExtra("fullName");
            id = intent.getStringExtra("nickname");
            school = intent.getStringExtra("schoolname");
            gender = intent.getStringExtra("gender");
            blood = intent.getStringExtra("bloodtype");
            nation = intent.getStringExtra("nation");
            phno = intent.getStringExtra("phoneNumber");

            diseases = intent.getStringExtra("diseases");


            drawBarcode(id);



            fullName.setText(" الأسم :  " + name);
            //speciality.setText("ID: "+id+" : رقم تعريفي ");
            speciality.setText("رقم تعريفي: "+id);
            SchoolName.setText("اسم المدرسة:  " + school);
            Gender.setText("الجنس :  "+gender);
            BloodT.setText(blood + "  : فصيلة الدم");
            Nation.setText("الجنسية :  "+nation);
            DateC.setText("تاريخ:   غير متاح");
            PhNo.setText("رقم الهاتف :  "+phno);
            //Diseases.setText(diseases);


            backbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAG", "onClick: pressed");
                    onBackPressed();
                }
            });

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference profileRef = storageReference.child("Profile pictures").child("" + ".jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(circleImageView);
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
        private void drawBarcode(String id) {
            String barcode = id;
            Code128 code = new Code128(DisplayStudentActivity.this);
            code.setData(barcode);
            Bitmap bitmap = code.getBitmap(680, 300);
            ImageView ivBarcode = (ImageView)findViewById(R.id.code128_barcode);
            ivBarcode.setImageBitmap(bitmap);
        }


//        public void phoneCall(View view) {
//            ImageView phoneCallImage = findViewById(R.id.phoneCall);
//            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
//            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//            animation.setInterpolator(interpolator);
//            phoneCallImage.startAnimation(animation);
//            makePhoneCall();
//        }
//
//        public void Gmail(View view) {
//            ImageView gmailImage = findViewById(R.id.gmail);
//            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
//            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//            animation.setInterpolator(interpolator);
//            gmailImage.startAnimation(animation);
//            sendMail();
//        }


//        public void Locate(View view) {
//            ImageView localisationImage = findViewById(R.id.localisation);
//            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
//            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
//            animation.setInterpolator(interpolator);
//            localisationImage.startAnimation(animation);
//            Intent intent = new Intent(com.example.healthcare.DisplayStudentActivity.this, DoctorLocalisation.class);
//            intent.putExtra("address", receivedAddress);
//            intent.putExtra("city", receivedCity);
//            startActivity(intent);
//        }
//
//        private void sendMail() {
//            String[] recipient = {receivedEmail};
//
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_EMAIL, recipient);
//            intent.setType("message/rfc822");
//            startActivity(Intent.createChooser(intent, "Choose an email client"));
//        }
//
//        private void makePhoneCall() {
//            if (ContextCompat.checkSelfPermission(com.example.healthcare.DisplayStudentActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(com.example.healthcare.DisplayStudentActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
//
//            } else {
//                String dial = "tel:" + receivedPhoneNumber;
//                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
//
//            }
//        }
//
//        @Override
//        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//            if (requestCode == REQUEST_CALL) {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//                    makePhoneCall();
//                else {
//                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DisplayStudentActivity.this,TheFragmnetsActivity.class);
        startActivity(intent);
    }
}
