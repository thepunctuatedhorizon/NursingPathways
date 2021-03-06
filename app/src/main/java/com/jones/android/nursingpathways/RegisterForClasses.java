package com.jones.android.nursingpathways;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.parallax.ScrollView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RegisterForClasses extends AppCompatActivity {

    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;
    private static Context context;


    List<Boolean> theClassesToRegister;
    List<Boolean> theClassListDone;
    List<Boolean> theClassListInProgress;
    List<CourseClass> theClassListObjects;

    private Button btn_register_complete;
    private Button btn_delay;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_for_classes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        context = getApplicationContext();
        CourseClassLoader courseClassLoader = new CourseClassLoader(context);
        theClassListObjects = courseClassLoader.loadClassObjects();

        final String LOGTAG = getClass().getSimpleName();


        int mNotificationId = 071;
        int mNotificationSecondId = 061;
        int mNotification3 = 051;
        int mNotification4 = 002;
        int mNotification5 = 001;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(mNotificationId);
        nMgr.cancel(mNotificationSecondId);
        nMgr.cancel(mNotification3);
        nMgr.cancel(mNotification4);
        nMgr.cancel(mNotification5);


        //btn_register_complete = (Button)findViewById(R.id.btnRegisterComplete);
        //btn_register_complete.setBackground(getResources().getDrawable(R.drawable.bttn_green));
       // btn_register_complete.setTextColor(getResources().getColor(R.color.pathBlack));
        //btn_register_complete.setTextSize(16);
        //btn_register_complete.setOnClickListener(new View.OnClickListener() {
          //   @Override
          //   public void onClick(View v) {

          //       Intent intent = new Intent(RegisterForClasses.this,PathWayDisplay.class);
           //      startActivity(intent);

            // }

           //  });

        btn_delay = (Button)findViewById(R.id.delay);
        btn_delay.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        btn_delay.setTextColor(getResources().getColor(R.color.pathBlack));
        btn_delay.setTextSize(16);
        btn_delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DelayRegistration.class);
                startActivity(intent);
            }

        });

        setUpAlarms();


        theClassesToRegister = new ArrayList<Boolean>();
        theClassListDone = new ArrayList<Boolean>();
        theClassListInProgress = new ArrayList<Boolean>();

        int numberAdded = 0;
        for (int i = 0; i < theClassListObjects.size(); i++) {
            boolean courseAdded = false;

            CourseClass course = theClassListObjects.get(i);

            if (course.getDone()) {
                theClassListDone.add(true);
                theClassListInProgress.add(false);
                theClassesToRegister.add(false);
                courseAdded = true;
            }
            if (course.getInProgress() && !courseAdded) {
                theClassListDone.add(false);
                theClassListInProgress.add(true);
                theClassesToRegister.add(false);
                courseAdded = true;
            }

            if (theClassListObjects.get(i).getPreReqs().equals("NONE") && !courseAdded)            {
                theClassListDone.add(false);
                theClassListInProgress.add(false);
                theClassesToRegister.add(true);
                numberAdded +=1;
                courseAdded = true;
            }



            String preReq = theClassListObjects.get(i).getPreReqs();
            int len = theClassListObjects.size()-1;
            for (int j=len-1; j>=0; j--)
            {

                String courseString = theClassListObjects.get(len-j).getTitle();
                if (courseString.equals(preReq)&&theClassListObjects.get(len-j).getDone()){

                    theClassesToRegister.add(true);
                    numberAdded +=1;
                    courseAdded = true;
                }
            }

            if (!theClassListObjects.get(i).getPreReqs().equals( "NONE") && !courseAdded)            {
                theClassListDone.add(false);
                theClassListInProgress.add(false);
                theClassesToRegister.add(false);
                courseAdded = true;
            }

            if (!courseAdded) {
                theClassListInProgress.add(false);
                theClassListDone.add(false);
                theClassesToRegister.add(true);
                numberAdded +=1;
                courseAdded = true;

                //WE HAVE A PROBLEM IF THIS IS EVER THE CASE.
                Log.e(LOGTAG, "PROBLEM!");
                //Log.e(LOGTAG, courseLabels[i]);
            }
        }
        final List<Boolean> recommendedCourse = new ArrayList<Boolean>();
        for (int i=0; i<theClassListObjects.size();i++){
            CourseClass course = theClassListObjects.get(i);
            // How do we determine if it is recommended?
            boolean recommended = true;

            if(recommended){
                recommendedCourse.add(true);
            }
        }


        LinearLayout layout = (LinearLayout) findViewById(R.id.register_for_classes_subLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_long), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,50,0,0);
        if ( numberAdded == 0){
            TextView txtV = new TextView(context);
            txtV.setText("It appears that you have taken all the classes you yourself can register for, or you are taking the prerequisites of the courses next in the sequence.  Please press below to indicate you have finished the prerequisite courses.  You may also click register for classes  You may also click finished if you are finished here. ");
            Button bttn  = new Button(context);
            bttn.setText("Update courses");
            bttn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateClassesInProgress.class);
                    intent.putExtra("PreRecBlank", true);
                    startActivity(intent);
                }
            });
            Button bttnF  = new Button(context);
            bttnF.setText("Finish");
            bttnF.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PathWayDisplay.class);
                    startActivity(intent);
                }
            });
            layout.addView(txtV);
            layout.addView(bttn);
            layout.addView(bttnF);

        }


        List<Button> courseButtons = new ArrayList<Button>();

        for (int i=0; i<theClassListObjects.size(); i++)        {
            if (theClassesToRegister.get(i)) {
               final CourseClass course = theClassListObjects.get(i);
                final String url = course.getUrl();
                Button button = new Button(context);
                button.setText(course.getTitle());
                button.setBackground(getResources().getDrawable(R.drawable.bttn_green));
                button.setTextColor(getResources().getColor(R.color.pathBlack));
                button.setTextSize(16);
                button.setLayoutParams(params);
                if(recommendedCourse.get(i)){
                    button.setTypeface(null, Typeface.BOLD_ITALIC);
                }


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("RegistrationButton", ((Button) view).getText().toString());
                        Uri uri = Uri.parse(url); //course.getUrl());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });
                layout.addView(button);
                courseButtons.add(button);
            }
        }



    }




    private  void setUpAlarms(){
        Calendar calendar = Calendar.getInstance();

        int year       = calendar.get(Calendar.YEAR);
        int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);

        Random random = new Random();
        int rand = random.nextInt(14);
        int blackboardShow = (7+rand)* 24 * 60 * 60;
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
                        registerShow = 4 * 30 * dayConversion + 7 * dayConversion;
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
                        registerShow = 3 * 30 * dayConversion + 7 * dayConversion;
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
                        registerShow = 2 * 30 * dayConversion + 7 * dayConversion;
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
                        registerShow = 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 30 * dayConversion + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 30 * dayConversion;
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
                        registerShow = 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0;
                        break;
                    default:
                        registerShow = 30 * dayConversion;
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
                        registerShow = 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 30 * dayConversion + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 30 * dayConversion;
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
                        registerShow = 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0;
                        break;
                    default:
                        registerShow = 0;
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
                        registerShow = 2 * 30 * dayConversion + 7 * dayConversion;
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
                        registerShow = 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 30 * dayConversion + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 30 * dayConversion;
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
                        registerShow = 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0;
                        break;
                    default:
                        registerShow = 30 * dayConversion;
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
                        registerShow = 30 * dayConversion + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 30 * dayConversion + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 30 * dayConversion + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 30 * dayConversion;
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
                        registerShow = 30 * dayConversion;
                        break;
                    case 1:
                        registerShow = 0 + 7*3* dayConversion;
                        break;
                    case 2:
                        registerShow = 0 + 7 * 2 * dayConversion;
                        break;
                    case 3:
                        registerShow = 0 + 7 * dayConversion;
                        break;
                    case 4:
                        registerShow = 0;
                        break;
                    default:
                        registerShow = 30 * dayConversion;
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
