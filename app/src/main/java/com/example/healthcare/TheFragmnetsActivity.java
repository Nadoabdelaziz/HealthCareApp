package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
    FirstFragment ThirdFr;
    FirstFragment FourFr;
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
                                    viewPager.setCurrentItem(1);
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
            ThirdFr=new FirstFragment();
            FourFr = new FirstFragment();
            FifthFr = new FifthFragment();
            adapter.addFragment(firstFr);
            adapter.addFragment(SecFr);
            adapter.addFragment(ThirdFr);
            adapter.addFragment(FourFr);
            adapter.addFragment(FifthFr);
            viewPager.setAdapter(adapter);
        }


    }
