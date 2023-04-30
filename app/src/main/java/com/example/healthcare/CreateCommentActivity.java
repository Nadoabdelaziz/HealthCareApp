package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.healthcare.models.Appointment;
import com.example.healthcare.models.Comment;
import com.example.healthcare.models.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateCommentActivity extends AppCompatActivity {

    loadingDialog ld;
    //private FirebaseAuth fbAuth;
    List<Comment> comments;
    DatabaseReference databaseReference;
    FirebaseUser user;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        Button btn = (Button) findViewById(R.id.sendComment);

        back = (ImageView) findViewById(R.id.backbtn);
        //fbAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateComment();
            }
        });
        comments = new ArrayList<>();


        final EditText sickname = (EditText)findViewById(R.id.editTextsickness);
        final EditText stdname = (EditText)findViewById(R.id.editTextStdName);
        final EditText comment = (EditText)findViewById(R.id.editTextcomment);

        if(
                TextUtils.isEmpty(sickname.toString())
                        || TextUtils.isEmpty(stdname.toString())
                        || TextUtils.isEmpty(comment.toString()))
        {
            Toast.makeText(CreateCommentActivity.this, "All fields are required !", Toast.LENGTH_LONG).show();
        }
        else
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    comments.clear();
                    for(DataSnapshot data : dataSnapshot.getChildren())
                    {
                        Comment comment = data.getValue(Comment.class);
                        if(comment.getSickness().equals(sickname.toString()) && comment.getComment().equals(comment) && comment.getStdName().equals(stdname))
                        {
                            new SweetAlertDialog(CreateCommentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("You Already have similar Comment")
                                    .show();
                            return;
                        }
                        comments.add(comment);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });




        }



    }
    public void CreateComment() {
        final EditText sickname = (EditText)findViewById(R.id.editTextsickness);
        final EditText stdname = (EditText)findViewById(R.id.editTextStdName);
        final EditText comment = (EditText)findViewById(R.id.editTextcomment);

         if(TextUtils.isEmpty(sickname.getText()) && TextUtils.isEmpty(stdname.getText()) && TextUtils.isEmpty(comment.getText()))
            {
                new SweetAlertDialog(CreateCommentActivity.this)
                        .setTitleText("Creating Comment")
                        .setContentText("Please enter all data")
                        .show();
                return;
            }
            user = FirebaseAuth.getInstance().getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference("Patients");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Comment commentRecord = new Comment(sickname.getText().toString(),stdname.getText().toString(), comment.getText().toString());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments");
                    reference.push().setValue(commentRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            new SweetAlertDialog(CreateCommentActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Congratulations")
                                    .setContentText("Your Comment is Created successfully")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            Intent intent = new Intent(CreateCommentActivity.this, TheFragmnetsActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .show();

                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CreateCommentActivity.this); // getActivity() for Fragment
                            prefs.edit().putBoolean("has_new_comment", true).commit();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            new SweetAlertDialog(CreateCommentActivity.this, SweetAlertDialog.ERROR_TYPE)
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