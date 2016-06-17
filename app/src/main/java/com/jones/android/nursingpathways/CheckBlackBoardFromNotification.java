package com.jones.android.nursingpathways;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Random;

public class CheckBlackBoardFromNotification extends AppCompatActivity {
    private static AlarmManager alarmManager = null;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //This Class is triggered when a person taps the notification.  It has a button that
        // directly links blackboard.
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_check_black_board_from_notification);

        context = getApplicationContext();

        //The five types of notification codes
        int mNotificationId = 071;
        int mNotificationSecondId = 061;
        int mNotification3 = 051;
        int mNotification4 = 001;
        int mNotification5 = 002;

        //The string needed for Notification Manater
        String ns = Context.NOTIFICATION_SERVICE;

        //The notification manager
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);

        //Canceling notifications when the use clicks on a notification.
        nMgr.cancel(mNotificationId);
        nMgr.cancel(mNotificationSecondId);
        nMgr.cancel(mNotification3);
        nMgr.cancel(mNotification4);
        nMgr.cancel(mNotification5);

        //This sets up the button that sends the user to Blackboard.
        Button checkBB = (Button) findViewById(R.id.bttnCheckBlackBoard);
        checkBB.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        checkBB.setTextColor(getResources().getColor(R.color.pathBlack));
        checkBB.setTextSize(15);
        checkBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Sets up the button to send user to blackboard
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ccbcmd-bb.blackboard.com/webapps/login/"));
                startActivity(intent);
            }
        });
        // THis sets up the blackboard prompt in up three weeks from now.
        Random random = new Random();
        int rand = random.nextInt(14);
        int blackboardShow = (7+rand)* 24 * 60 * 60;
        setTimeToShowBlackboardPrompt(blackboardShow);
    }

    private void setTimeToShowBlackboardPrompt(int seconds){
        //This function sets an alarm for showing the blackboard prompt.
        int alarmId = 013424;
        long ms = seconds * (1000) + Calendar.getInstance().getTimeInMillis();
        Intent alarmIntent = new Intent(this, BlackboardAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent);

    }
}
