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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_black_board_from_notification);
        int mNotificationId = 071;
        int mNotificationSecondId = 061;
        int mNotification3 = 051;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(mNotificationId);
        nMgr.cancel(mNotificationSecondId);
        nMgr.cancel(mNotification3);
        Button checkBB = (Button) findViewById(R.id.bttnCheckBlackBoard);
        checkBB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ccbcmd-bb.blackboard.com/webapps/login/"));
                startActivity(intent);
            }
        });

        int blackboardShow =  2 * 7 * 24 * 60 * 60;
        setTimeToShowBlackboardPrompt(blackboardShow);
    }

    private void setTimeToShowBlackboardPrompt(int seconds){
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
