package com.jones.android.nursingpathways;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class DelayRegistration extends AppCompatActivity {
    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;
    private Button btn_15Min;
    private Button btn_2Hours;
    private Button btn_tomorrow;
    private Button btn_never;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int mNotificationId = 071;
        int m2NotificationId = 061;
        int m3NotificationId = 051;
        int mNotification4 = 001;
        int mNotification5 = 002;

        //This is the string for the notification, as can be clearly seen.
        String ns = Context.NOTIFICATION_SERVICE;
        //This notification manager is to get the notification service.
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        //These three calls cancel the notifications that can be active
        nMgr.cancel(mNotificationId);
        nMgr.cancel(m2NotificationId);
        nMgr.cancel(m3NotificationId);
        nMgr.cancel(mNotification4);
        nMgr.cancel(mNotification5);


        btn_15Min = (Button) findViewById(R.id.btn_15Min);
        btn_2Hours = (Button) findViewById(R.id.btn_2Hours);
        btn_tomorrow = (Button) findViewById(R.id.btn_tomorrow);


        setRegistrationReminderOnClickListener(btn_15Min, 15);
        setRegistrationReminderOnClickListener(btn_2Hours, 120);
        setRegistrationReminderOnClickListener(btn_tomorrow, 1440);

        btn_never = (Button) findViewById(R.id.btn_never);
        btn_never.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DelayRegistration.this, RegistrationDenied.class);
                startActivity(intent);
            }
        });

    }

    public void setRegistrationReminderOnClickListener(Button btn, final int minutes){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set Alarm
                int alarmId = 013423;
                long ms = minutes * 60 * (1000) + Calendar.getInstance().getTimeInMillis();
                alarmIntent = new Intent(getApplicationContext(), RegistrationAlarmReceiver.class );
                pendingIntent = PendingIntent.getBroadcast( getApplicationContext(), alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (alarmManager == null) {
                    alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent);
                Toast.makeText(getApplicationContext(), "You will be remined in " + minutes +" minutes.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DelayRegistration.this, PathWayDisplay.class));
            }
        });
    }
}
