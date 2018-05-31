package com.example.kayarkar.sita;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
        	/* Get Messages */
            Object[] sms = (Object[]) intentExtras.get("pdus");

            for (int i = 0; i < sms.length; ++i) {
            	/* Parse Each Message */
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String phone = smsMessage.getOriginatingAddress();
                String message = smsMessage.getMessageBody().toString();
                if(message.equals("w")){
                    Toast.makeText(context, "Baby Urinate",
                            Toast.LENGTH_SHORT).show();
                    Log.d("ME", "Notification started");

                    String strtitle = "MCBC";
                    // Open NotificationView Class on Notification Click
                    intent = new Intent(context, outActivity.class);
                    // Send data to NotificationView Class
                    intent.putExtra("MCBC", strtitle);
                    intent.putExtra("Baby Urinate", message);
                    // Open NotificationView.java Activity
                    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    // Create Notification using NotificationCompat.Builder
                    android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            context)
                            // Set Icon
                            .setSmallIcon(R.drawable.launching)
                            // Set Ticker Message
                            .setTicker(message)
                            // Set Title
                            .setContentTitle(context.getString(R.string.cancel))
                            // Set Text
                            .setContentText("Baby Urinate")
                            // Add an Action Button below Notification
                            .addAction(R.drawable.ic_launcher_background, "Action Button", pIntent)
                            // Set PendingIntent into Notification
                            .setContentIntent(pIntent)
                            // Dismiss Notification
                            .setAutoCancel(true);

                    // Create Notification Manager
                    NotificationManager notificationmanager = (NotificationManager) context
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    // Build Notification with Notification Manager
                    notificationmanager.notify(0, builder.build());


                }
                else  if(message.equals("u")){
                    Toast.makeText(context, "baby is out of cradle",
                            Toast.LENGTH_SHORT).show();
                    Log.d("ME", "Notification started");

                    String strtitle = "MCBC";
                    // Open NotificationView Class on Notification Click
                    intent = new Intent(context, outActivity.class);
                    // Send data to NotificationView Class
                    intent.putExtra("MCBC", strtitle);
                    intent.putExtra("baby is out of cradle", message);
                    // Open NotificationView.java Activity
                    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    // Create Notification using NotificationCompat.Builder
                    android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(
                            context)
                            // Set Icon
                            .setSmallIcon(R.drawable.launching)
                            // Set Ticker Message
                            .setTicker(message)
                            // Set Title
                            .setContentTitle(context.getString(R.string.cancel))
                            // Set Text
                            .setContentText("baby is out of cradle")
                            // Add an Action Button below Notification
                            .addAction(R.drawable.ic_launcher_background, "Action Button", pIntent)
                            // Set PendingIntent into Notification
                            .setContentIntent(pIntent)
                            // Dismiss Notification
                            .setAutoCancel(true);

                    // Create Notification Manager
                    NotificationManager notificationmanager = (NotificationManager) context
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    // Build Notification with Notification Manager
                    notificationmanager.notify(0, builder.build());


                }
                else  if(message.equals("m")){
                    PackageManager pm = context.getPackageManager();
                    Intent launchIntent = pm.getLaunchIntentForPackage("com.google.android.apps.messaging");
                    launchIntent.putExtra("some_data", "value");
                    context.startActivity(launchIntent);


                }
                Toast.makeText(context, phone + ": " + message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}