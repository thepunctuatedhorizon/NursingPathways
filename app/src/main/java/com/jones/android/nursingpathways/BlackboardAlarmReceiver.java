package com.jones.android.nursingpathways;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by jones on 3/19/2016.
 */

public class BlackboardAlarmReceiver extends BroadcastReceiver {

    //This Broadcast Receiver builds a notification when the alarm triggers it.
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            //Commented out what IDE says are unused variables. -DD
           // Bundle bundle = intent.getExtras();
           // String message = bundle.getString("alarm_message");
            Toast.makeText(context, "We are showing the toast.", Toast.LENGTH_SHORT).show();
            Notification.Builder notificationBuilder = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.pathway_icon)
                    .setContentTitle("Blackboard")
                    .setContentText("Have you checked Blackboard Recently");

            Intent resultIntent = new Intent(context, CheckBlackBoardFromNotification.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    resultIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT );
            notificationBuilder.setContentIntent(resultPendingIntent);

            // Sets an ID for the notification
            int mNotificationId = 061;
// Gets an instance of the NotificationManager service
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// Builds the notification and issues it.
            mNotifyMgr.notify(mNotificationId, notificationBuilder.build());
        }
        catch (Exception e)
        {
            Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
