package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.healthcare.models.Admin;
import com.example.healthcare.models.Patient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAdminsActivity extends AppCompatActivity {

    private static final int Pick_Image_Request = 1;
    RevealAnimation mRevealAnimation;
    CircleImageView circleImageView;
    private Uri mImageUri;
    EditText fullName, cin, email, phoneNumber, birthDate, maritalStatus;
    String receivedFullName, receivedCin, receivedEmail, receivedPhoneNumber, receivedBirthDate, receivedMaritalStatus;
    String receivedImageUri;
    StorageReference mStorageReference;
    FirebaseDatabase database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admins);
        fullName = findViewById(R.id.fullName);
        cin = findViewById(R.id.cin);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);
        birthDate = findViewById(R.id.birthDate);
        maritalStatus = findViewById(R.id.maritalStatus);
        circleImageView = findViewById(R.id.profile_image);

        mStorageReference = FirebaseStorage.getInstance().getReference("Profile pictures");

        Intent intent = this.getIntent();   //get the intent to recieve the x and y coords, that you passed before

        receivedFullName = intent.getStringExtra("name");
        //receivedCin = intent.getStringExtra("cin");
        receivedEmail = intent.getStringExtra("email");
        receivedPhoneNumber = intent.getStringExtra("phoneNumber");
        //receivedBirthDate = intent.getStringExtra("birthDate");
        receivedMaritalStatus = intent.getStringExtra("school");
        //receivedImageUri = intent.getStringExtra("imageUri");
        //Uri uri = Uri.parse(receivedImageUri);

        fullName.setText(receivedFullName);
        //cin.setText(receivedCin);
        email.setText(receivedEmail);
        phoneNumber.setText(receivedPhoneNumber);
        //birthDate.setText(receivedBirthDate);
        maritalStatus.setText(receivedMaritalStatus);
        //Picasso.get().load(uri).into(circleImageView);

        FrameLayout rootLayout = findViewById(R.id.root); //there you have to get the root layout of your second activity
        mRevealAnimation = new RevealAnimation(rootLayout, intent, this);
    }

    public void onBackPressed() {
        mRevealAnimation.unRevealActivity();
    }

    public void editProfilePicture(View view) {
        openFileChooser();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Pick_Image_Request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Pick_Image_Request && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(circleImageView);
            uploadImage();
        }

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadImage() {
        StorageReference ref = mStorageReference.child(receivedEmail + "." + getExtension(mImageUri));
        ref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditAdminsActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void update(View view) {
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //final String userUid = user.getUid();
        DatabaseReference dbRef = database.getReference("Admins");
        //String[] fn = fullName.getText().toString().split(" ");
        final Admin admin = new Admin(fullName.getText().toString(),maritalStatus.getText().toString(),receivedBirthDate,phoneNumber.getText().toString(),receivedEmail
                ,"12345","2");
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("[-+.@:.]","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ERRORZ", "onDataChange: "+dataSnapshot.getRef());
                dataSnapshot.getRef().setValue(admin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERRORZ", "onCancelled: "+databaseError);
            }
        });
        Intent intent = new Intent(EditAdminsActivity.this, TheFragmnetsActivity.class);
        startActivity(intent);
    }
}
