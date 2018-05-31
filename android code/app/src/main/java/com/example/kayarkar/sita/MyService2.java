package com.example.kayarkar.sita;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kayarkar on 20/12/2017.
 */

public class MyService2 extends Service {
    private FirebaseDatabase mfirebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference=mfirebaseDatabase.getReference();
    private DatabaseReference mReference=mRootReference.child("notify");
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
      mReference.setValue("e");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                if(dataSnapshot.getValue(String.class)!=null)
                {
                    String key = dataSnapshot.getKey();

                    if(key.equals("notify")){
                        String heading=dataSnapshot.getValue(String.class);
                        if(heading.equals("w")){
                                  Intent intent;
                            String strtitle = "MCBC";
                            // Open NotificationView Class on Notification Click
                            intent = new Intent(getBaseContext(), outActivity.class);
                            // Send data to NotificationView Class
                            intent.putExtra("MCBC", strtitle);

                            // Open NotificationView.java Activity
                            PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            // Create Notification using NotificationCompat.Builder
                            android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                    getBaseContext())
                                    // Set Icon
                                    .setSmallIcon(R.drawable.launching)
                                    // Set Ticker Message
                                    .setTicker("MCBC")
                                    // Set Title
                                    .setContentTitle(getBaseContext().getString(R.string.cancel))
                                    // Set Text
                                    .setContentText("Baby urinates")
                                    // Add an Action Button below Notification
                                    .addAction(R.drawable.ic_launcher_background, "Action Button", pIntent)
                                    // Set PendingIntent into Notification
                                    .setContentIntent(pIntent)
                                    // Dismiss Notification
                                    .setAutoCancel(true);

                            // Create Notification Manager
                            NotificationManager notificationmanager = (NotificationManager) getBaseContext()
                                    .getSystemService(Context.NOTIFICATION_SERVICE);
                            // Build Notification with Notification Manager
                            notificationmanager.notify(0, builder.build());

                            Toast.makeText(getBaseContext(), "your baby urinates", Toast.LENGTH_SHORT).show();
                        }
                        else   if(heading.equals("e")){
                            Toast.makeText(getBaseContext(), "cradle is empty", Toast.LENGTH_SHORT).show();
                        }
                     else    if(heading.equals("u")){

                            Intent intent;
                            String strtitle = "MCBC";
                            // Open NotificationView Class on Notification Click
                            intent = new Intent(getBaseContext(), outActivity.class);
                            // Send data to NotificationView Class

                            // Open NotificationView.java Activity
                            PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            // Create Notification using NotificationCompat.Builder
                            android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(
                                    getBaseContext())
                                    // Set Icon
                                    .setSmallIcon(R.drawable.launching)
                                    // Set Ticker Message
                                    .setTicker("MCBC")
                                    // Set Title
                                    .setContentTitle(getBaseContext().getString(R.string.cancel))
                                    // Set Text
                                    .setContentText("baby is in cradle")
                                    // Add an Action Button below Notification
                                    .addAction(R.drawable.ic_launcher_background, "Action Button", pIntent)
                                    // Set PendingIntent into Notification
                                    .setContentIntent(pIntent)
                                    // Dismiss Notification
                                    .setAutoCancel(true);

                            // Create Notification Manager
                            NotificationManager notificationmanager = (NotificationManager) getBaseContext()
                                    .getSystemService(Context.NOTIFICATION_SERVICE);
                            // Build Notification with Notification Manager
                            notificationmanager.notify(0, builder.build());
                            Toast.makeText(getBaseContext(), "baby is in cradle", Toast.LENGTH_SHORT).show();
                        }


                    }


                }

                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // [END_EXCLUDE]
            }
        };
        mReference.addValueEventListener(postListener);
       //setting loop play to true
        //this will make the ringtone continuously playing


        //staring the player


        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed

    }
}
