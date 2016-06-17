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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

//This class is designed to implement the Delay registration requirement.  It will present the user with a prompt
//Asking how long do they want to delay.  Upon selection, the class sets the timer and redirects the user to the pathwaydisplay.
public class DelayRegistration extends AppCompatActivity {

    //These are for the alarm.
    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;

    //These variables allow access to the buttons
    private Button btn_15Min;
    private Button btn_2Hours;
    private Button btn_tomorrow;
    private Button btn_never;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_registration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //These integers hold the address of the notifications that could have popped up.  I'm not sure why we want
        //to remove them.
        //TODO: DO WE ACTUALLY NEED TO DISMISS THE NOTIFICATIONS?
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

        //Parameters for the layout.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_long), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,50,0,0);

        //This allows access to the buttons
        btn_15Min = (Button) findViewById(R.id.btn_15Min);
        btn_2Hours = (Button) findViewById(R.id.btn_2Hours);
        btn_tomorrow = (Button) findViewById(R.id.btn_tomorrow);

        //This sets the style of teh button
        btn_15Min.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_15Min.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_15Min.setTextSize(16);
        btn_15Min.setLayoutParams(params);

        //This sets the style of teh button
        btn_2Hours.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_2Hours.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_2Hours.setTextSize(16);
        btn_2Hours.setLayoutParams(params);

        //This sets the style of teh button
        btn_tomorrow.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_tomorrow.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_tomorrow.setTextSize(16);
        btn_tomorrow.setLayoutParams(params);

        //This sets the timer for each timer button.
        setRegistrationReminderOnClickListener(btn_15Min, 15);
        setRegistrationReminderOnClickListener(btn_2Hours, 120);
        setRegistrationReminderOnClickListener(btn_tomorrow, 1440);

        //This sets up the never button with the right style
        btn_never = (Button) findViewById(R.id.btn_never);
        btn_never.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_never.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_never.setTextSize(16);
        btn_never.setLayoutParams(params);
        //This directs the user to the 'need help' page.
        btn_never.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DelayRegistration.this, RegistrationDenied.class);
                startActivity(intent);
            }
        });

    }

    //This function allows code reuse for setting the button timers.
    //It creates an onclicklistener that sets the alarm and directs the user to the pathway.
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
                Toast.makeText(getApplicationContext(), "You will be reminded in " + minutes +" minutes.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(DelayRegistration.this, PathWayDisplay.class));
            }
        });
    }
}
