package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.healthcare.models.Student;
import com.example.healthcare.models.Teacher;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TheFragmnetsActivity extends AppCompatActivity {


    //zizo
    BottomNavigationView bottomNavigationView;
    //This is our viewPager
    private ViewPager viewPager;

    FirstFragment firstFr;
//    TeacherActivity SecFr;
//    TeacherActivity ThirdFr;
//    TeacherActivity FourFr;
    SecondFragment SecFr;
    ThirdFragment ThirdFr;
    FourthFragment FourFr;
    FifthFragment FifthFr;


    MenuItem prevMenuItem;

    String mCurrentPhotoPath;
    private static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 3;
    static final int REQUEST_IMAGE_GALLERY = 4;

    Activity MyActivity = TheFragmnetsActivity.this;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_fragmnets);
            //zizo

            //Initializing viewPager
            viewPager = (ViewPager) findViewById(R.id.viewpager);

            //Initializing the bottomNavigationView
            bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavigationView);


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); // getActivity() for Fragment
            prefs.getBoolean("has_new_comment", false);
            if(prefs.getBoolean("has_new_comment", false)){
                Menu menu = bottomNavigationView.getMenu();
                MenuItem item = menu.findItem(R.id.home);
                item.setIcon(R.drawable.comment_notify);


                new CountDownTimer(5000, 50) {

                    @Override
                    public void onTick(long arg0) {
                        // TODO Auto-generated method stub

                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        item.setIcon(R.drawable.comment);
                    }
                }.start();
                prefs.edit().putBoolean("has_new_comment", false).commit();
            }


        bottomNavigationView.setOnNavigationItemSelectedListener(

                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.person:
                                    viewPager.setCurrentItem(0);
                                    Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
                                    break;

                                case R.id.home:
                                    viewPager.  setCurrentItem(1);
                                    Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
                                    break;

                                case R.id.settings:
                                    viewPager.setCurrentItem(2);
                                    Log.d("ViewPager", "onNavigationItemSelected: "+viewPager.getCurrentItem());
                                    break;

                                case R.id.settings2:
                                    viewPager.setCurrentItem(3);
                                    Log.d("Viewpager", "onNavigationItemSelected: "+ viewPager.getCurrentItem());
                                    break;

                                case R.id.settings3:
                                    viewPager.setCurrentItem(4);
                                    Log.d("Viewpager", "onNavigationItemSelected: "+ viewPager.getCurrentItem());
                                    break;
                            }
                            return false;
                        }
                    });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }


                @Override
                public void onPageSelected(int position) {
                    if (prevMenuItem != null) {

                        prevMenuItem.setChecked(false);
                    }
                    else
                    {
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
                    }
                    Log.d("page", "onPageSelected: "+position);
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    bottomNavigationView.getMenu().getItem(1).setTitleCondensed("true");
                    prevMenuItem = bottomNavigationView.getMenu().getItem(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            setupViewPager(viewPager);
        }

        @Override
        public void onBackPressed() {
            startActivity(getIntent());
        }




        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            firstFr=new FirstFragment();
            SecFr=new SecondFragment();
            ThirdFr=new ThirdFragment();
            FourFr = new FourthFragment();
            FifthFr = new FifthFragment();
            adapter.addFragment(firstFr);
            adapter.addFragment(SecFr);
            adapter.addFragment(ThirdFr);
            adapter.addFragment(FourFr);
            adapter.addFragment(FifthFr);
            viewPager.setAdapter(adapter);
        }

//    public void DisplayStdInfo(View v){
//
//        final List<Student> Stds = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Students");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Stds.clear();
//                for(DataSnapshot data : dataSnapshot.getChildren())
//                {
//                    Student student = data.getValue(Student.class);
//                    Stds.add(student);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
//
//        final int position = Stds.getPositionForView((View) v.getParent());
//
//
//        Log.d("TAG", "DisplayStdInfo: Student Clicked");
//
//        Intent intent = new Intent(TheFragmnetsActivity.this, DisplayStudentActivity.class);
//        intent.putExtra("fullName",SearchedStd.getName());
//        intent.putExtra("nickname",SearchedStd.getNickname());
//        intent.putExtra("schoolname",SearchedStd.getSchoolname());
//        intent.putExtra("gender",SearchedStd.getGender());
//        intent.putExtra("bloodtype", SearchedStd.getBloodtype());
//        intent.putExtra("nation",SearchedStd.getNationality());
//        intent.putExtra("phoneNumber",SearchedStd.getPhoneNumber());
//
//        intent.putExtra("diseases",SearchedStd.getDisease());
//
//
//        startActivity(intent);
//    }

    }
