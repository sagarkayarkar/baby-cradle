package com.example.kayarkar.sita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class modeActivity extends AppCompatActivity {
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference=mfirebaseDatabase.getReference();
    private DatabaseReference mHeadingReference=mRootReference.child("motor");
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        mHeadingReference.setValue("0");
        Intent intent = new Intent(modeActivity.this, MyService2.class);
        startService(intent);
    }

    public void controlfromhome(View view) {
        Intent i = new Intent(this, DeviceListActivity.class);
        startActivity(i);
    }
    public void controloutfromhome(View view) {
        Intent i = new Intent(this, outActivity.class);
        startActivity(i);
    }


    //public void sms(View view) {
      //  SmsManager smsManager = SmsManager.getDefault();
        //smsManager.sendTextMessage("8421276033", null, "w", null, null); // msg.arg1 = bytes from connect thread


    //}



}