package com.jones.android.nursingpathways;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //This activity should only be run once as it is designed to set up and send to other activities.

    //The boolean firstTimeOpeningApp indicates that this is the first install.
    //If the app is first installed, there will be no shared preferences and thus the
    //if statements will not trigger.  Thus this flag will not be changed from true
    //and this will trigger the application to set up.
    private boolean firstTimeOpeningApp = true;

    //This boolean is designed to ensure that the MainActivity doesn't get run again.
    private boolean paused = false;

    //This boolean is designed to send the application to the UpdateClasses Activity
    private boolean timeToUpdateClasses = true;

    //This boolean will be set by a function to make sure that the registration prompt is
    //sent at the correct time.
    private boolean registrationSoon = false;


    //This is unnecessary,
    private ImageView image3;

    //This is the shared preferences private repository.
    private SharedPreferences sharedPreferences;
    //This is the shared preferences editor.  This will allow the saving of the preferences.
    private SharedPreferences.Editor editor;

    //This stack of intents allow the setting of the blackboard prompt
    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;

    //This variable allows the application to use the right context.
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This will stop the application from rerunning the main application once everything has
        // been set up.  This is important so that the main application is not on the backstack.
        if(paused){finish();}

        setContentView(R.layout.activity_main);

        //This is unnecessary.
        image3 = (ImageView) findViewById(R.id.imageView) ;

        //This is getting the shared preferences for this application.  It doesn't matter if it is
        // the first time opening. This call doesn't return a null object.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //This is getting an editor class so we can edit the preferences.
        editor =sharedPreferences.edit();


        //This integer is designed to hold the number of times until displaying the Blackboard notification
        int numberOfTimesUntilDisplay;
        //This integer will be used to hold how many times the application has been loaded.
        int numberOfOpenings;


        //This variable is designed to keep the application context the same throughout the Main Activity
        context = getApplicationContext();

        //These three integers refer to the kinds of notifications that could be active when the application
        //starts. There may be up to five
        //TODO: Make sure that all of the notifications are represented here.
        int mNotificationId = 071;
        int m2NotificationId = 061;
        int m3NotificationId = 051;
        int mNotification4 = 001;
        int mNotification5 = 002;

        //This is the string for the notification, as can be clearly seen.
        String ns = Context.NOTIFICATION_SERVICE;
        //This notification manager is to get the notification service.
        NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
        //These three calls cancel the notifications that can be active
        nMgr.cancel(mNotificationId);
        nMgr.cancel(m2NotificationId);
        nMgr.cancel(m3NotificationId);
        nMgr.cancel(mNotification4);
        nMgr.cancel(mNotification5);




        //Remove when finished RegisterForClasses

        startActivity(new Intent(this, RegistrationDenied.class));
        finish();


        //This function is designed to check if the registration is within two weeks,  if it is
        //close, the flag registrationSoon is set to true.
        checkIfRegistrationIsClose();

        // This checks to see if the application has the number of times until display preference set
        //and loads the said preference. This is for the Blackboard log in prompt launched base upon
        // how many loads of the application has happened.
        if (sharedPreferences.contains("NumberOfTimesUntilDisplay")){
            numberOfTimesUntilDisplay = sharedPreferences.getInt("NumberOfTimesUntilDisplay",6);
        } else {
            //if there is no number stored, we just arbitrarily store 6 times until the randomization feature kicks in.
            numberOfTimesUntilDisplay = 6;
        }


        // This checks to see if the application has the number of times the app has been opened.
        // It also loads the said preference into memory. That is, if the number of openings exists, then the number of openings
        // needs to be incremented and saved. Otherwise, it just needs to be set to zero and the preference written to memory.
        if (sharedPreferences.contains("NumberOfOpenings")){
            numberOfOpenings = sharedPreferences.getInt("NumberOfOpenings", 0);
            int num = numberOfOpenings+1;
            editor.putInt("NumberOfOpenings", num);
            editor.commit();
        } else {
            numberOfOpenings =0;
            editor.putInt("NumberOfOpenings", 0);
            editor.commit();
        }


        //This section of code asks if the Time To Update Courses preference exists.  If it doesn't exist,
        //this section of code sets the flag to false and writes it in the memory. If it does exist, it sets the
        //flag to true and then writes the time to update courses preference to false. As is needed to reset the feature.
        if (sharedPreferences.contains("TimeToUpdateCourses")) {
            timeToUpdateClasses = sharedPreferences.getBoolean("TimeToUpdateCourses", true);

            editor.putBoolean("TimeToUpdateCourses", false);
            editor.commit();
        } else {
            timeToUpdateClasses = true;
            editor.putBoolean("TimeToUpdateCourses",false);
            editor.commit();
        }


        //This section of code sees if this is the first install of the application.
        //If it is the first time the application will not have this preference and will execute the
        //else statement.  The else statement contains the code to set up alarms, randomize the blackboard
        //prompt, and writing the preferences to the memory.
        //Otherwise it sets the flag first time opening the app to false.
        if (sharedPreferences.contains("FirstTimeOpening")) {
            firstTimeOpeningApp = sharedPreferences.getBoolean("FirstTimeOpening", true);

            editor.putBoolean("FirstTimeOpening", false);
            editor.commit();
        } else {

            editor.putInt("NumberOfTimesUntilDisplay",0);
            editor.commit();
            setUpAlarmsFirstInstall();
            randomizeNextBlackboardPrompt();
            firstTimeOpeningApp = true;
            editor.putBoolean("FirstTimeOpening", false);
            editor.commit();
        }


        //This checks to see if it is time to set and display the "Have you logged into Blackboard Recently?
        if (numberOfOpenings>=numberOfTimesUntilDisplay){
            setAndDisplayHaveYouLoggedInToBlackboard("Have you logged into Blackboard Recently?", 0);
            randomizeNextBlackboardPrompt();
        }


        //This if statement is where things start to get fun.
        //This if statement checks to see if it is time to update classes.  It also checks to see if
        //the first time opening the app is false (it is true when first installs so this has to be checked for.
        //If the flag timeToUpdateClasses
        if(timeToUpdateClasses&&!firstTimeOpeningApp){
            Intent intent = new Intent(context, UpdateClassesInProgress.class);
            timeToUpdateClasses = false;
            editor.putBoolean("timeToUpdateCourses", false);
            // schedule when to update
            startActivity(intent);
            return;
        }

        //This is where most of the application runs will end up.
        if (firstTimeOpeningApp&&timeToUpdateClasses) {
            //If it is the first time opening, both flags will be true and thus we launch first open screen.
            Intent intent = new Intent(context,FirstOpenScreen.class);
            firstTimeOpeningApp = false;
            editor.putBoolean("FirstTimeOpening",false);
            startActivityForResult(intent, 1);
        } else if (firstTimeOpeningApp && !timeToUpdateClasses) {
            //A case that shouldn't happen, but has to be protected against
            Intent intent = new Intent(context,FirstOpenScreen.class);
            firstTimeOpeningApp = false;
            editor.putBoolean("FirstTimeOpening",false);
            startActivityForResult(intent, 1);
        } else {
            //The normal case.  If registration is soon then we launch RegisterForClasses.
            if (registrationSoon) {
                startActivity(new Intent(context, RegisterForClasses.class));
                setTimeToUpdateClasses(14);
            } else {
                //Otherwise, it is time to show the pathway.
                startActivity(new Intent(context, PathWayDisplay.class));
            }
        }
    }

    @Override
    public  void onStart()
    {
        super.onStart();
    }

    @Override
     public void onResume() {
        super.onResume();
        //This causes the application to quit when the back button is pressed in the other activities.
        //Otherwise, there would be no way to exit the app.
        if (paused)
        {
            finish();
        }

    }

    @Override
    public void onPause() {
        //When the onPause method happens, it means that the MainActivity has been passed into the background
        //Thus, it is necessary to set the paused flag true.  See onResume for mor info.
        super.onPause();
        paused = true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //This function catches what happened to the FirstOpenScreen screen.
        //If the user selected no, the result code will be RESULT_CANCELED and the pathway needs to be
        // set to no progress and then displayed.
        if (requestCode== 1){
            if (resultCode==RESULT_CANCELED){
                setAllPathwayVariablesToScratch();
                Intent intent = new Intent(getApplicationContext(),PathWayDisplay.class);
                startActivity(intent);
            } else if (resultCode == RESULT_OK){
                //If the result code is OK then we need to send the user to the setUp class
                Intent intent = new Intent(getApplicationContext(),SetUp.class);
                startActivity(intent);
            }
        }
    }


    private void setAllPathwayVariablesToScratch() {
        //This method does exactly what it is named to do.
        SharedPreferences sharedPrefDone = getSharedPreferences("courses", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefDone.edit();
        SharedPreferences sharedPrefIP = getSharedPreferences("courses", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorIP = sharedPrefDone.edit();
        final String[] courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        for (int i=0; i<courseLabels.length;i++){
            editor.putBoolean(courseLabels[i], false);
            editorIP.putBoolean(courseLabels[i],false);

            editor.apply();
            editorIP.apply();
        }
    }


    private void setTimeToUpdateClasses(int days){
        //This function will set the UpdateClasses timer
        int alarmId = 013523;
        long ms = Calendar.getInstance().getTimeInMillis() + days * 24 * 60 * 60 * (1000) ;
        alarmIntent = new Intent(this, UpdateClassesInProgressAlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast( context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent);
    }


    private  void setAndDisplayHaveYouLoggedInToBlackboard(String text, int days) {
        //The annoying but useful have you logged into Blackboard prompt builder function.
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.pathway_icon)
                .setContentTitle("Blackboard")
                .setContentText(text);

        Intent resultIntent = new Intent(this, CheckBlackBoardFromNotification.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT );
        notificationBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 071;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, notificationBuilder.build());
    }


    private void randomizeNextBlackboardPrompt() {

        //This function randomizes the number of times the application must be opened before the
        //Have you checked blackboard prompt is shown.
        Random randomGenerator = new Random();
        int random = randomGenerator.nextInt(5);
        if (random<2){ random = 2; }
        editor.putInt("NumberOfTimesUntilDisplay", random);
        editor.putInt("NumberOfOpenings", 0);
        editor.commit();
    }


    private void setTimeToShowBlackboardPrompt(int seconds){

        //This does exacty what it says it does.
        int alarmId = 013424;
        long ms = seconds * (1000) + Calendar.getInstance().getTimeInMillis();
        alarmIntent = new Intent(this, BlackboardAlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast( context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent);

    }


    private void setTimeToShowRegistrationPrompt(int seconds){
        //This does exactly what it says it does.
        int alarmId = 013423;
        long ms = seconds * (1000) + Calendar.getInstance().getTimeInMillis();
        alarmIntent = new Intent(this, RegistrationAlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast( context, alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        }
        alarmManager.set(AlarmManager.RTC_WAKEUP, ms, pendingIntent );

    }


    private void sendToRegistrationFlag(int daysTil) {
        //This checks to see if registration is soon.  If it is it will set the registration flag
        //to true. This will cause the application to prompt the user to register.
        if(daysTil<15) {
            registrationSoon = true;
        }
    }


    private  void checkIfRegistrationIsClose(){
        //Very complicated function to see how close registration is.
        Calendar calendar = Calendar.getInstance();

        int year       = calendar.get(Calendar.YEAR);
        int month      = calendar.get(Calendar.MONTH); // Jan = 0, dec = 11
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekOfMonth= calendar.get(Calendar.WEEK_OF_MONTH);


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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
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
                sendToRegistrationFlag(registerShow);
                break;

        }
    }


    private  void setUpAlarmsFirstInstall(){
        //When the user first uses the application, the alarms must be set up
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


}
