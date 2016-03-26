package com.jones.android.nursingpathways;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class CheckBlackBoardFromNotification extends AppCompatActivity {
    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //This Class is triggered when a person taps the notification.  It has a button that
        // directly links blakcboard.
        // TODO: update this class so that the user doesn't barf from the inglorious interface.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_black_board_from_notification);
        context = getApplicationContext();
        int mNotificationId = 071;
        int mNotificationSecondId = 061;
        int mNotification3 = 051;
        int mNotification4 = 001;
        int mNotification5 = 002;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(mNotificationId);
        nMgr.cancel(mNotificationSecondId);
        nMgr.cancel(mNotification3);
        nMgr.cancel(mNotification4);
        nMgr.cancel(mNotification5);
        Button checkBB = (Button) findViewById(R.id.bttnCheckBlackBoard);
        checkBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ccbcmd-bb.blackboard.com/webapps/login/"));
                startActivity(intent);
            }
        });
        // THis sets up the blackboard propmt to two weeks.
        //TODO: Do we need it to be two weeks? Or a random number of weeks?
        int blackboardShow =  2 * 7 * 24 * 60 * 60;
        setTimeToShowBlackboardPrompt(blackboardShow);
    }

    private void setTimeToShowBlackboardPrompt(int seconds){
        //This function sets an alarm for showing the blackboard prompt.
        int alarmId = 013424;
        long ms = seconds * (1000) + Calendar.getInstance().getTimeInMillis();
        alarmIntent = new Intent(this, BlackboardAlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent);

    }
}
