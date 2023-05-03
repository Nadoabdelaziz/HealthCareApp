package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.healthcare.models.Comment;
import com.example.healthcare.models.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FourthFragment extends Fragment {

    View mview;

    List<Event> events;

    Event event;
    RecyclerView meetingsRecyclerList;
    Data data;
    CalendarView calender;
    TextView currDate;
    private Date currentDateOnCalender = new Date();
    MeetingsAdapter meetingsAdapter;
    DoubleLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        this.mview=view;





            data = new Data(getContext());
            event = new Event();

            meetingsRecyclerList = mview.findViewById(R.id.recycler);

            layoutManager = new DoubleLayoutManager(getContext());
            meetingsRecyclerList.setLayoutManager(layoutManager);
        layoutManager.setTargetStartPosition(data.getIndexForDate(currentDateOnCalender), 0);


            events = new ArrayList<>();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
            reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Event event = data.getValue(Event.class);
                    events.add(event);
                    Collections.sort(events);
                    meetingsAdapter = new MeetingsAdapter(events, getActivity());
                    meetingsRecyclerList.setAdapter(meetingsAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



//        meetingsAdapter = new MeetingsAdapter(data, getActivity());
//            meetingsRecyclerList.setAdapter(meetingsAdapter);

        ImageButton clicknewdate = mview.findViewById(R.id.button);
            mview.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addMeetingIntent = new Intent(getContext(), MeetingActivity.class);
                    Log.e("START", "Curr date on cal "+ currentDateOnCalender + "");
                    addMeetingIntent.putExtra("date", currentDateOnCalender.getTime());
                    startActivityForResult(addMeetingIntent, MeetingActivity.EDIT_MEETING);
                }
            });

//            meetingsRecyclerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                    super.onScrollStateChanged(recyclerView, newState);
//                }
//
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    int firstPos = layoutManager.findLastCompletelyVisibleItemPosition();
//                    if (firstPos >= 0) {
//                        Date startDate = data.getMeetingList().get(firstPos).getStart();
//                        calender.setDate(startDate.getTime());
//                        updateStoredDateOnCalender(startDate);
//                        setCalenderListTime(startDate);
//                    }
//
//                }
//            });

            calender = mview.findViewById(R.id.calendarView);
            //currDate = mview.findViewById(R.id.curr_date_display);

            //currDate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(new Date(calender.getDate())));

            calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, day, 0, 0);
                    Date date = cal.getTime();
                    int scrollToIndex = data.getIndexForDate(date);
                    layoutManager.scrollToPositionWithOffset(scrollToIndex, 0);
                    setCalenderListTime(date);
                    //currDate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(date));
                    updateStoredDateOnCalender(date);
                }
            });
        return view;
        }

        public void updateStoredDateOnCalender(Date date) {
            currentDateOnCalender = Data.justGetDate(date);
        }


        public void postponeSelectedDate() {
            Log.e("POSTPONE", data.getMeetingsOnDate(currentDateOnCalender) + "");
            for (Meeting meeting : data.getMeetingsOnDate(currentDateOnCalender)) {
                meeting.postponeMeeting();
                data.addOrUpdateMeeting(meeting);
            }
        }


        void setCalenderListTime(Date date) {
            //currDate.setText(DateFormat.getDateInstance(DateFormat.FULL).format(date));
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == MeetingActivity.EDIT_MEETING) {
                meetingsAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    }

