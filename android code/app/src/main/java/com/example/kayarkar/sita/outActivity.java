package com.example.kayarkar.sita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class outActivity extends AppCompatActivity {
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference=mfirebaseDatabase.getReference();
    private DatabaseReference mHeadingReference=mRootReference.child("motor");
    private TextView HeadingText;
    private EditText HeadingInput;
    int state=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        mHeadingReference.setValue("n");
        HeadingInput=(EditText)findViewById(R.id.headingInput);
    }
    public void start(View v) {
        mHeadingReference.setValue("1");
        state=1;
        //Toast.makeText(outofhome.this, "start :",
        //      Toast.LENGTH_SHORT).show();
        //mHeadingReference.setValue("p");
    }
    public void stop(View v) {
        state=0;
        mHeadingReference.setValue("0");
        //Toast.makeText(outofhome.this, "start :",
        //      Toast.LENGTH_SHORT).show();
        //mHeadingReference.setValue("p");
    }
    public void send(View v) {
        String heading =HeadingInput.getText().toString();
        mHeadingReference.setValue(heading);
        HeadingInput.setText("");
    }
    public void play(View v) {

        mHeadingReference.setValue("sing");

    }
    public void stopsong(View v) {

        mHeadingReference.setValue("stop");

    }
    public void on(View v) {


            mHeadingReference.setValue("fanon");
            Toast.makeText(getBaseContext(), "only fan", Toast.LENGTH_SHORT).show();



    }
    public void of(View v) {

            mHeadingReference.setValue("fanof");
            Toast.makeText(getBaseContext(), "fan of", Toast.LENGTH_SHORT).show();



    }
    public void view(View v) {

        mHeadingReference.setValue("video");
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
       // Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.ivuu");
      //  if (launchIntent != null) {
         //   startActivity(launchIntent);//null pointer check in case package name was not found

        }
    }

