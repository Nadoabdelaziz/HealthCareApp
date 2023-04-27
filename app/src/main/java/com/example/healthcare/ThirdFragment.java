package com.example.healthcare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.models.Student;
import com.example.healthcare.models.Teacher;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ThirdFragment extends Fragment {

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    View mview;
    final int RequestCameraPermissionID = 1001;

    Student SearchedStd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        this.mview =view;



        cameraView = (SurfaceView) mview.findViewById(R.id.surface_view);
        textView = (TextView) mview.findViewById(R.id.text_view);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            Log.d("TAGS", "onRequestPermissionsResult: here 2");
                            return;
                        }
                        Log.d("TAGS", "surfaceCreated: Should Work");
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size()!=0){
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                final StringBuilder stringBuilder=new StringBuilder();
                                for(int i=0;i<items.size();i++){
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");


                                }
                                String sb = new String("UTR Egyptian College");
                                String sb2 = new String("EXPIRY DATE");
                                String IDSearch = new String("ID:220300");




                                if(stringBuilder.toString().contains(sb) && stringBuilder.toString().contains(sb2)  && stringBuilder.toString().contains(IDSearch)){
                                    cameraSource.stop();
                                    Log.d("TAG", "run: found");

                                    String CropID = stringBuilder.toString().split("Postgraduate FAM")[0];
                                    final String newCropID = CropID.split("ID:")[1];

                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Congratulations")
                                            .setContentText("Your Comment is Created successfully")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
                                                    reference.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot data : dataSnapshot.getChildren())
                                                            {
                                                                Student student = data.getValue(Student.class);
                                                                if(newCropID.contains(student.getNickname())){
                                                                    Log.d("TAGSS", "onDataChange: found");
                                                                    SearchedStd = new Student(student.getName(),student.getNickname(),student.getPhoneNumber(),student.getSchoolname(),student.getGender(),student.getNationality(),student.getBloodtype(),student.getDisease(),student.getPrecuations(),student.getKnown_as());
                                                                    Log.d("TAGSS", "onClick: "+SearchedStd.getName());

                                                                            Intent intent = new Intent(getContext(), DisplayStudentActivity.class);
                                                                            intent.putExtra("fullName",SearchedStd.getName());
                                                                            intent.putExtra("nickname",SearchedStd.getNickname());
                                                                            intent.putExtra("schoolname",SearchedStd.getSchoolname());
                                                                            intent.putExtra("gender",SearchedStd.getGender());
                                                                            intent.putExtra("bloodtype", SearchedStd.getBloodtype());
                                                                            intent.putExtra("nation",SearchedStd.getNationality());
                                                                            intent.putExtra("phoneNumber",SearchedStd.getPhoneNumber());

                                                                    startActivity(intent);

                                                                }
                                                                {
                                                                    Log.d("TAGSS", "Not exist " +student.getNickname());
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            System.out.println("The read failed: " + databaseError.getCode());
                                                        }
                                                    });

//                                                    Intent intent = new Intent(The.this, TheFragmnetsActivity.class);
//                                                    startActivity(intent);
                                                }
                                            })
                                            .show();
                                    textView.setText(newCropID);



                                }
                                else {
                                    Log.d("TAG", "run: not equal " + sb + sb2 + " but camera "+stringBuilder.toString());
                                }
//                                Log.d("TAG", "run: "+stringBuilder.toString());
//                                cameraSource.stop();

//                                Intent intent = new Intent(getActivity(),SplashActivity.class);
//                                startActivity(intent);
                                //textView.setText(stringBuilder.toString());

                            }
                        });
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case RequestCameraPermissionID:
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // Log.d("TAGS", "onRequestPermissionsResult: here 2");
                        return;
                    }
                    try {
                        //Log.d("TAGS", "onRequestPermissionsResult: here");
                        cameraSource.start(cameraView.getHolder());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}