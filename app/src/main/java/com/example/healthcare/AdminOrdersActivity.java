package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthcare.models.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminOrdersActivity extends AppCompatActivity {

    TextView neword,doneord;
    int count1,count2 = 0;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);


        back= (ImageView) findViewById(R.id.backbtn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        neword = (TextView) findViewById(R.id.neworders);
        doneord = (TextView) findViewById(R.id.doneorders);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Teachers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Teacher teacher = data.getValue(Teacher.class);
                    if(teacher.getMaritalStatus().equals("0")){
                        count1++;
                    }
                    else
                    {
                        count2++;
                    }
                }

                neword.setText(count1+" \n\n طلبات جديدة");
                doneord.setText(count2 +" \n\n موافقة");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        neword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminOrdersActivity.this, RegisterOrdersActivity.class);
                startActivity(intent);
            }
        });

    }
}