package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    public static int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


    }

    public void phoneCallHelp(View view) {
        ImageView phoneCallImage = findViewById(R.id.phoneCallhelp);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        phoneCallImage.startAnimation(animation);
        makePhoneCall();
    }

    public void GmailHelp(View view) {
        ImageView gmailImage = findViewById(R.id.gmailhelp);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        animation.setInterpolator(interpolator);
        gmailImage.startAnimation(animation);
        sendMail();
    }

    private void sendMail() {
        String[] recipient = {"nada@email.com"};

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }

    private void makePhoneCall(){
        if(ContextCompat.checkSelfPermission(HelpActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(HelpActivity.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        }
        else
        {
            String dial = "tel:"+"123456789";
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                makePhoneCall();
            else
            {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}