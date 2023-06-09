package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthcare.models.Comment;
import com.example.healthcare.models.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MeetingActivity extends AppCompatActivity {

    Event ev1;
    Meeting meeting;
    TextView startDate;
    TextView endDate,errorshow;
    EditText titleEditText,desc;
    Button editMeetingBtn;
    TextView contactName;
    TextView contactPhone;
    Button doneBtn;
    Data data;

    DatabaseReference databaseReference;
    FirebaseUser user;

    ImageView back;

    public static final int EDIT_MEETING = 235;

    static final int SET_START = 1;
    static final int SET_END = 2;
    static final int PICK_CONTACT_REQUEST = 3;

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        meeting = (Meeting) savedInstanceState.getSerializable("meeting");
        super.onRestoreInstanceState(savedInstanceState);
        updateMeetingViews();
        updateContactViews();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("meeting", meeting);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        data = new Data(this);

        errorshow = (TextView) findViewById(R.id.errordata);
        back = (ImageView) findViewById(R.id.backbtn);
        desc = (EditText) findViewById(R.id.desc);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String meetingId = null;
        Intent intent = getIntent();
        Date meetingStartDate = new Date();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                meetingId = extras.getString("meeting_id");

                if (extras.containsKey("date")) {
                    // Add Meeting intent
                    setTitle("Add Meeting");
                    meetingStartDate = new Date(extras.getLong("date"));
                    Log.e("START", meetingStartDate + "");
                    meeting = new Meeting(meetingStartDate);
                    findViewById(R.id.delete_meeting_btn).setVisibility(View.INVISIBLE);
                } else {
                    // Edit meeting intent
                    setTitle("Edit Meeting");
                    if (meetingId != null) {
                        Meeting tempMeeting = data.getMeetingById(meetingId);
                        if (tempMeeting != null) {
                            meeting = tempMeeting;
                        }
                    }
                }
            }
        }


        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        titleEditText = findViewById(R.id.meeting_title);
        //editMeetingBtn = findViewById(R.id.edit_meeting_title_btn);
//        contactName = findViewById(R.id.meeting_contact_name);
//        contactPhone = findViewById(R.id.meeting_contact_number);
        doneBtn = (Button) findViewById(R.id.done_btn);


//        titleEditText.edit
//        meeting.setTitle(titleEditText.getText().toString());
//        titleEditText.requestFocus();
//        titleEditText.setFocusableInTouchMode(true);
        //editMeetingBtn.setText("احفظ");
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(titleEditText, InputMethodManager.SHOW_FORCED);

//        editMeetingBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    Log.d("TAG", "onCreate: pre addition : "+titleEditText.getText().toString());
//                    meeting.setTitle(titleEditText.getText().toString());
//                    Log.d("TAG", "onCreate: "+titleEditText.getText().toString());
//                    titleEditText.requestFocus();
//                    titleEditText.setFocusableInTouchMode(true);
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(titleEditText, InputMethodManager.SHOW_FORCED);
//            }
//        });


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onCreate: "+titleEditText.getText().toString());
                String myString = null;
                if(!desc.getText().toString().isEmpty() && !startDate.getText().toString().equals("من") && !endDate.getText().toString().equals("الى") && !titleEditText.getText().toString().isEmpty()) {
                    ev1 = new Event(titleEditText.getText().toString() , startDate.getText().toString(),endDate.getText().toString(),desc.getText().toString());
                    addnewMeeting(ev1);
                    //data.addOrUpdateMeeting(meeting);
//                    Log.e("MEETINGS", "Add meeting: " + titleEditText.getText().toString() + " - " +startDate.getText().toString() + " - "+endDate.getText().toString()+" - " +desc.getText().toString());
//                    Log.e("MEETINGS", data.getMeetingList().toString());
//                    finishActivity(EDIT_MEETING);
//                    finish();
                }
                else{
                    errorshow.setVisibility(View.VISIBLE);
                }
            }
        });

        updateMeetingViews();
        updateContactViews();
    }

    public void setEndDate(View v) {
        promptDate(SET_END);
    }

    public void setStartDate(View v) {
        promptDate(SET_START);
    }


    public void pickContactIntent(View v) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    public void openContact(View v) {
        // Open contact using the default contacts viewer
        if (meeting.getContactId() != null) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(meeting.getContactId()));
                intent.setData(uri);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong with opening the contact.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No contact associated with event!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Displays dialogs to select date and time, meeting is updated with the selected date/time
     *
     * @param type SET_START - Set meeting start time
     *             SET_END - Set meeting end time
     */
    void promptDate(final int type) {

        Log.d("TAG", "onCreate: pre addition : "+titleEditText.getText().toString());
        meeting.setTitle(titleEditText.getText().toString());
        Log.d("TAG", "onCreate: "+titleEditText.getText().toString());

        //Log.d("TAG", "promptDate: "+titleEditText.getText().toString());

        DatePickerDialog datePickerDialog = new DatePickerDialog(this) {
            int yearOfCentury = 0;
            int monthOfDay = 0;
            int dayOfMonth = 0;

            @Override
            public void onDateChanged(@NonNull DatePicker view, int yearOfCentury, int monthOfDay, int dayOfMonth) {
                this.yearOfCentury = yearOfCentury;
                this.monthOfDay = monthOfDay;
                this.dayOfMonth = dayOfMonth;
            }

            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                Log.d("TAG", "promptDate2 : "+titleEditText.getText().toString());

                if (which == DialogInterface.BUTTON_POSITIVE) {
                    super.onClick(dialog, which);
                    final int year = yearOfCentury;
                    final int month = monthOfDay;
                    final int day = dayOfMonth;
                    Log.d("TAG", "onClick: "+day+month+year);
//                    new TimePickerDialog(MeetingActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteOfHOur) {
//                            Calendar cal = new GregorianCalendar(year, month, day, hourOfDay, minuteOfHOur);
//
//                            Date date = new Date(cal.getTimeInMillis());
//                            if (type == SET_START)
//                                meeting.setStart(date);
//                            if (type == SET_END)
//                                meeting.setEnd(date);
//                            updateMeetingViews();
//                        }
//                    }, 0, 0, true).show();

                    if (startDate.getText().equals("من")){
                        startDate.setText(day + "/"+month+"/"+year);
                    }
                    else {
                        endDate.setText(day + "/"+month+"/"+year);
                    }
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                }
                updateMeetingViews();
            }
        };
        Calendar cal = Calendar.getInstance();

//        if (type == SET_START) {
//            cal.setTime(meeting.getStart());
//        } else if (type == SET_END) {
//            cal.setTime(meeting.getEnd());
//        }
        datePickerDialog.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    void updateMeetingViews() {
//        Log.d("TAG", "updateMeetingViews: final " +titleEditText.getText().toString() + "meeting " + meeting.getTitle());
//
//        startDate.setText(meeting.getStart().toString());
//        endDate.setText(meeting.getEnd().toString());
//        meeting.setTitle(titleEditText.getText().toString());
//        titleEditText.setText(meeting.getTitle());
    }

    void updateContactViews() {
//        if (this.meeting.getContactId() != null) {
//            Contact contact = Data.getContactById(MeetingActivity.this, this.meeting.getContactId());
//            if (contact != null) {
//                contactPhone.setText(contact.getPhone());
//                contactName.setText(contact.getName());
//            }
//        }

//        if (meeting.getContactId() == null) {
//            findViewById(R.id.add_contact).setVisibility(View.VISIBLE);
//            findViewById(R.id.contact_parent).setVisibility(View.INVISIBLE);
//        } else {
//            findViewById(R.id.add_contact).setVisibility(View.INVISIBLE);
//            findViewById(R.id.contact_parent).setVisibility(View.VISIBLE);
//
//        }
    }

//    public void deleteContact(View v) {
//        meeting.setContactId(null);
//        updateContactViews();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) { //User picked a contact; didn't cancel out
                // The Intent's data Uri identifies which contact was selected.

                String[] projection = {
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                };

                Cursor cursor = getContentResolver().query(data.getData(), projection,
                        null, null, null);

                try {
                    cursor.moveToFirst();
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    this.meeting.setContactId(id);
                } catch (NullPointerException e) {
                    Log.e("PICK_CONTACT_REQUEST", "Error with picking");
                }
            }
            updateContactViews();
        }
    }

    public void deleteMeeting(View v) {
        data.deleteMeeting(meeting);
        finishActivity(EDIT_MEETING);
//        finish();
        Intent intent = new Intent(MeetingActivity.this,TheFragmnetsActivity.class);
        startActivity(intent);
    }

    public void cancelEditMeeting(View v) {
        finishActivity(EDIT_MEETING);
        finish();
    }


    public void addnewMeeting(Event event){
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Teachers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
                reference.push().setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new SweetAlertDialog(MeetingActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Congratulations")
                                .setContentText("Your Event is Created successfully")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Intent intent = new Intent(MeetingActivity.this, TheFragmnetsActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();

//                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MeetingActivity.this); // getActivity() for Fragment
//                        prefs.edit().putBoolean("has_new_comment", true).commit();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        new SweetAlertDialog(MeetingActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong!")
                                .show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}