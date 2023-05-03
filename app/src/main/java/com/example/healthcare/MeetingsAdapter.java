package com.example.healthcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare.models.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeetingsAdapter extends RecyclerView.Adapter {

    List<Event> eventslist;
    Data data;
    Activity activity;


    MeetingsAdapter(List<Event> eventslist, Activity activity) {
        this.eventslist = eventslist;
        this.activity = activity;
    }

    MeetingsAdapter(Data data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_list_item, parent, false);
        return new MeetingHolder(v, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MeetingHolder mHolder = (MeetingHolder) holder;

        //final String currEventTitle = eventslist.get(position).getTitle();
        final Event currEvent = eventslist.get(position);

        //final Meeting currMeeting = data.getMeetingList().get(position);

        Date today = Data.justGetDate(new Date());

        //Log.e("disable", today + " " + currMeeting.getStart());




        mHolder.updateView(currEvent);

        mHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventslist.size();
    }


    class MeetingHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView startTextView;
        TextView endTextView;
        Activity activity;
        String meetingId;
        View parent;

        public MeetingHolder(@NonNull View itemView, Activity activity) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.meeting_title);
            startTextView = itemView.findViewById(R.id.meeting_start);
            endTextView = itemView.findViewById(R.id.meeting_end);
            this.activity = activity;
            this.parent = itemView;
        }

        public void updateView(Event event) {
//            this.meetingId = meetingId;
//            if(meetingId != null) {
//                Meeting meeting = data.getMeetingById(meetingId);
                titleTextView.setText(event.getTitle());
                startTextView.setText(event.getFrom());
                endTextView.setText(event.getTo());
            }
        }


        @Override
        public String toString() {
            return super.toString();
        }
    }

