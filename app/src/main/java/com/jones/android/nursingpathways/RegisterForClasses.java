package com.jones.android.nursingpathways;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;

public class RegisterForClasses extends AppCompatActivity {

    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_classes);
        int mNotificationId = 071;
        int mNotificationSecondId = 061;
        int mNotification3 = 051;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(mNotificationId);
        nMgr.cancel(mNotificationSecondId);
        nMgr.cancel(mNotification3);
        context = getApplicationContext();

        setUpAlarms();


    }


    private  void setUpAlarms(){
        Calendar calendar = Calendar.getInstance();

        int year       = calendar.get(Calendar.YEAR);
        int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);
        Log.e("The", month + "");
        int blackboardShow = 2 * 7 * 24 * 60 * 60;
        setTimeToShowBlackboardPrompt(blackboardShow);

        int dayConversion = 24 * 60 * 60;
        int registerShow;

        switch (month) {
            case 0:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 5 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 4 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 4 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 4 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 4 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 5 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 1:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 4 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 3 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 3 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 3 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 3 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 3 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 2:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 3 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 2 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 2 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 2 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 3 * 30 * dayConversion;
                        break;

                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 3:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 1 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 1 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 1 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 4:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 5:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 1 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 1 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 1 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 6:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 0 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 7:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 3 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 2 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 2 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 2 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 3 * 30 * dayConversion;
                        break;

                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 8:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 1 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 1 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 1 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 9:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 10:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 1 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 1 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 1 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 2 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;
            case 11:
                switch (weekOfMonth){
                    case 0:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 * 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 * 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 * 30 * dayConversion + 7 * 1 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0 * 30 * dayConversion;
                        break;
                    default:
                        registerShow = 1 * 30 * dayConversion;
                        break;
                }
                setTimeToShowRegistrationPrompt(registerShow);
                break;

        }
    }
    private void setTimeToShowRegistrationPrompt(int seconds){
        int alarmId = 013423;
        long ms = seconds * (1000) + Calendar.getInstance().getTimeInMillis();
        alarmIntent = new Intent(this, RegistrationAlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent );

    }

    private void setTimeToShowBlackboardPrompt(int seconds){
        int alarmId = 013424;
        long ms = seconds * (1000) + Calendar.getInstance().getTimeInMillis();
        alarmIntent = new Intent(this, BlackboardAlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast( context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent);

    }
}
