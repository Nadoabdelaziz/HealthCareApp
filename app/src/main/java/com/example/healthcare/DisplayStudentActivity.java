package com.example.healthcare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import com.bumptech.glide.load.Options;
import com.example.healthcare.models.Code128;

import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.healthcare.models.Code128;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import de.hdodenhof.circleimageview.CircleImageView;
public class DisplayStudentActivity extends AppCompatActivity {

        public int REQUEST_CALL = 1;
        RelativeLayout frameLayout;
        ImageView imageView,backbtn;
        Space space;
        TextView label1,label2,label3;
        Button health;
        CircleImageView circleImageView;
        TextView fullName, speciality,SchoolName,Gender,BloodT,DateC,Nation,PhNo,Diseases,labelHealth;
        String name,id,school,gender,blood,datee,nation,phno,diseases,prec;
        ProgressBar progressBar;

        int pageHeight = 1120;
        int pagewidth = 792;
        private static final int PERMISSION_REQUEST_CODE = 200;
        Button savepdf,sharepdf;
        // creating a bitmap variable
        // for storing our images
        Bitmap bmp, scaledbmp;

    LinearLayout D1,D2,D3,D4;
    ImageView I1,I2,I3,I4;

    TextView d1,d2,d3,d4;
    TextView PR1,PR2,PR3,PR4;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_student);

            savepdf = findViewById(R.id.savepdf);
            sharepdf = findViewById(R.id.sharepdf);

        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.appmainscreen);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 180, 180, false);

        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        savepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to
                // generate our PDF file.
                generatePDF();
            }
        });




        labelHealth = findViewById(R.id.labelHealth);


            D1 = (LinearLayout) findViewById(R.id.disease1layout);
            D2 = (LinearLayout) findViewById(R.id.disease2layout);
            D3 = (LinearLayout) findViewById(R.id.disease3layout);
            D4 = (LinearLayout) findViewById(R.id.disease4layout);


            d1 = findViewById(R.id.disease1);
            d2 = findViewById(R.id.disease2);
            d3 = findViewById(R.id.disease3);
            d4 = findViewById(R.id.disease4);

            PR1 = findViewById(R.id.prc1);
            PR2 = findViewById(R.id.prc2);
            PR3 = findViewById(R.id.prc3);
            PR4 = findViewById(R.id.prc4);

            I1 = findViewById(R.id.imgphoto1);
            I2 = findViewById(R.id.imgphoto2);
            I3 = findViewById(R.id.imgphoto3);
            I4 = findViewById(R.id.imgphoto4);


            space = findViewById(R.id.spaceisa);

            ImageView ivBarcodenew = (ImageView)findViewById(R.id.code128_barcode);


            fullName = findViewById(R.id.fullName);
            speciality = findViewById(R.id.ID);

            SchoolName = findViewById(R.id.Scname);
            Gender = findViewById(R.id.Gendern);
            BloodT = findViewById(R.id.BloodN);
            DateC = findViewById(R.id.DateN);
            Nation = findViewById(R.id.NatN);
            PhNo = findViewById(R.id.PhoneN);

            label1 = findViewById(R.id.genderlabel);
            label2 = findViewById(R.id.bloodlabel);
            label3 = findViewById(R.id.nationlabel);

            //Diseases = findViewById(R.id.disease);

            health = (Button)findViewById(R.id.health);

            health.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DisplayStudentActivity.this,HealthActivity.class);
                    startActivity(intent);
                }
            });

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
//            frameLayout.setBackground(dr);


            Intent intent = getIntent();

            name = intent.getStringExtra("fullName");
            id = intent.getStringExtra("nickname");
            school = intent.getStringExtra("schoolname");
            gender = intent.getStringExtra("gender");
            blood = intent.getStringExtra("bloodtype");
            nation = intent.getStringExtra("nation");
            phno = intent.getStringExtra("phoneNumber");

            diseases = intent.getStringExtra("diseases");

            prec = intent.getStringExtra("prec");


            if(!gender.equals("tech") && !blood.equals("tech") && !nation.equals("tech") && !diseases.equals("tech") && !prec.equals("tech")){
            Gender.setText(gender);
            BloodT.setText(blood);
            Nation.setText(nation);

                int count=0;
                for( int i= 0; i < diseases.length(); i++)
                {
                    if(diseases.charAt(i) == '-')
                        count++;
                }

                String[] arrOfStr = diseases.split("-", count+1);
                String[] arrPrec = prec.split("-",count+1);

                if(arrOfStr.length==1){
                    D1.setVisibility(View.VISIBLE);
                    d1.setVisibility(View.VISIBLE);
                    d1.setText(arrOfStr[0]);
                    PR1.setText(arrPrec[0]);
                    switch (arrOfStr[0]){
                        case "امراض القلب" :
                            I1.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I1.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I1.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I1.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I1.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I1.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I1.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I1.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I1.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I1.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I1.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I1.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }
                }
                else if(arrOfStr.length==2) {
                    D1.setVisibility(View.VISIBLE);
                    d1.setVisibility(View.VISIBLE);
                    d1.setText(arrOfStr[0]);

                    PR1.setText(arrPrec[0]);
                    PR2.setText(arrPrec[1]);


                    D2.setVisibility(View.VISIBLE);
                    d2.setVisibility(View.VISIBLE);
                    d2.setText(arrOfStr[1]);

                    switch (arrOfStr[0]){
                        case "امراض القلب" :
                            I1.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I1.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I1.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I1.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I1.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I1.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I1.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I1.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I1.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I1.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I1.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I1.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }


                    switch (arrOfStr[1]){
                        case "امراض القلب" :
                            I2.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I2.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I2.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I2.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I2.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I2.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I2.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I2.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I2.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I2.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I2.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I2.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }

                }
                else  if(arrOfStr.length==3) {
                    D1.setVisibility(View.VISIBLE);
                    d1.setVisibility(View.VISIBLE);
                    d1.setText(arrOfStr[0]);

                    D2.setVisibility(View.VISIBLE);
                    d2.setVisibility(View.VISIBLE);
                    d2.setText(arrOfStr[1]);

                    D3.setVisibility(View.VISIBLE);
                    d3.setVisibility(View.VISIBLE);
                    d3.setText(arrOfStr[2]);


                    PR1.setText(arrPrec[0]);
                    PR2.setText(arrPrec[1]);
                    PR3.setText(arrPrec[2]);


                    switch (arrOfStr[0]){
                        case "امراض القلب" :
                            I1.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I1.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I1.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I1.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I1.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I1.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I1.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I1.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I1.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I1.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I1.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I1.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }


                    switch (arrOfStr[1]){
                        case "امراض القلب" :
                            I2.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I2.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I2.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I2.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I2.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I2.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I2.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I2.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I2.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I2.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I2.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I2.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }

                    switch (arrOfStr[2]){
                        case "امراض القلب" :
                            I3.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I3.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I3.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I3.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I3.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I3.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I3.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I3.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I3.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I3.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I3.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I3.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }

                }
                else if(arrOfStr.length==4){
                    D1.setVisibility(View.VISIBLE);
                    d1.setVisibility(View.VISIBLE);
                    d1.setText(arrOfStr[0]);

                    D2.setVisibility(View.VISIBLE);
                    d2.setVisibility(View.VISIBLE);
                    d2.setText(arrOfStr[1]);

                    D3.setVisibility(View.VISIBLE);
                    d3.setVisibility(View.VISIBLE);
                    d3.setText(arrOfStr[2]);

                    D4.setVisibility(View.VISIBLE);
                    d1.setVisibility(View.VISIBLE);
                    d4.setText(arrOfStr[3]);

                    PR1.setText(arrPrec[0]);
                    PR2.setText(arrPrec[1]);
                    PR3.setText(arrPrec[2]);
                    PR4.setText(arrPrec[3]);


                    switch (arrOfStr[0]){
                        case "امراض القلب" :
                            I1.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I1.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I1.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I1.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I1.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I1.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I1.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I1.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I1.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I1.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I1.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I1.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }


                    switch (arrOfStr[1]){
                        case "امراض القلب" :
                            I2.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I2.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I2.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I2.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I2.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I2.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I2.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I2.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I2.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I2.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I2.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I2.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }

                    switch (arrOfStr[2]){
                        case "امراض القلب" :
                            I3.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I3.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I3.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I3.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I3.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I3.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I3.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I3.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات وإعاقات" :
                            I3.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I3.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I3.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I3.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }

                    switch (arrOfStr[3]){
                        case "امراض القلب" :
                            I4.setImageResource(R.drawable.heart_disease);
                            break;
                        case "أمراض الكلى" :
                            I4.setImageResource(R.drawable.kidney_disease);
                            break;
                        case "أمراض عصبية" :
                            I4.setImageResource(R.drawable.nerve_disease);
                            break;
                        case "مشاكل سمعية" :
                            I4.setImageResource(R.drawable.ear_disease);
                            break;
                        case "مشاكل نفسية" :
                            I4.setImageResource(R.drawable.phyco_disease);
                            break;
                        case "ربو" :
                            I4.setImageResource(R.drawable.rio);
                            break;
                        case "األنيميا المنجلية" :
                            I4.setImageResource(R.drawable.anemia_disease);
                            break;
                        case "فقر الدما لثالسيميا" :
                            I4.setImageResource(R.drawable.blood_disease);
                            break;
                        case "إصابات والإعاقات" :
                            I4.setImageResource(R.drawable.body_disease);
                            break;
                        case "النظارات أو العدسات" :
                            I4.setImageResource(R.drawable.eyes_disease);
                            break;
                        case "السكري" :
                            I4.setImageResource(R.drawable.sugar_disease);
                            break;
                        case "حساسية الطعام" :
                            I4.setImageResource(R.drawable.food_disease);
                            break;
                        default:
                            break;



                    }

                }
        }
        else{
            Gender.setVisibility(View.GONE);
            BloodT.setVisibility(View.GONE);
            Nation.setVisibility(View.GONE);

            labelHealth.setVisibility(View.GONE);

            label3.setVisibility(View.GONE);
            label2.setVisibility(View.GONE);
            label1.setVisibility(View.GONE);

            ivBarcodenew.setVisibility(View.GONE);

            space.setVisibility(View.VISIBLE);

        }



                //Log.d("TAGCount", "onCreate: "+arrOfStr.length);


//            Diseases.setText(arrOfStr[0]);

//            for (String a : arrOfStr)
//                System.out.println(a);

            drawBarcode(id);




            fullName.setText(name);
            //speciality.setText("ID: "+id+" : رقم تعريفي ");
            speciality.setText(id);
            SchoolName.setText(school);


            DateC.setText("غير متاح");
            PhNo.setText(phno);
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
    private void generatePDF() {
        // creating an object variable
        // for our PDF document.

        Intent intent = getIntent();

        name = intent.getStringExtra("fullName");
        id = intent.getStringExtra("nickname");
        school = intent.getStringExtra("schoolname");
        gender = intent.getStringExtra("gender");
        blood = intent.getStringExtra("bloodtype");
        nation = intent.getStringExtra("nation");
        phno = intent.getStringExtra("phoneNumber");

        diseases = intent.getStringExtra("diseases");

        prec = intent.getStringExtra("prec");


        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();
        Paint title2 = new Paint();
        Paint text = new Paint();


        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 580, 0, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(55);
        title.setColor(ContextCompat.getColor(this, R.color.red_btn_bg_color));

        canvas.drawText("لسلامتك", 239, 120, title);


        title2.setTextSize(45);
        title2.setColor(ContextCompat.getColor(this, R.color.red_btn_bg_color));

        text.setTextSize(29);
        text.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        text.setColor(ContextCompat.getColor(this, R.color.main_blue_color));

        canvas.drawText("\n  معلومات شخصية",409,250,title2);

        canvas.drawText("الاسم :  "+name+"\n", 409, 350, text);
        canvas.drawText("الرقم تعريفي :  "+id+"\n", 409, 400, text);
        canvas.drawText("اسم المدرسة :  "+school+"\n", 409, 450, text);
        canvas.drawText("الجنس :  "+gender+"\n", 409, 500, text);
        canvas.drawText(" فصيلة الدم :  "+blood+"\n", 409, 550, text);
        canvas.drawText("الجنسية :  "+nation+"\n", 409, 600, text);
        canvas.drawText("رقم الهاتف :  "+phno+"\n \n \n", 409, 650, text);

        canvas.drawText("\n  معلومات صحية ",409,850,title2);
        canvas.drawText("يعاني : "+diseases+"\n", 409, 950, text);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        File file = new File(Environment.getExternalStorageDirectory(), "Student"+id+".pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(DisplayStudentActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    //Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
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
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail().equals("health@live.com")){
            Intent intent = new Intent(DisplayStudentActivity.this,HealthFragmentsActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(DisplayStudentActivity.this, TheFragmnetsActivity.class);
            startActivity(intent);
        }
    }
}
