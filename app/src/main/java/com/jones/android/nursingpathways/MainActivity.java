package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    boolean firstTimeOpeningApp = true;
    boolean paused = false;
    boolean timeToUpdateClasses = true;

    ImageView image3;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if(paused)
        {
            finish();
        }
        setContentView(R.layout.activity_main);
        image3 = (ImageView) findViewById(R.id.imageView) ;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor =sharedPreferences.edit();


        if (sharedPreferences.contains("TimeToUpdateCourses")) {
            timeToUpdateClasses = sharedPreferences.getBoolean("TimeToUpdateCourses", true);

            editor.putBoolean("TimeToUpdateCourses", false);
            editor.commit();
        } else {
            timeToUpdateClasses = true;
            editor.putBoolean("TimeToUpdateCourses",false);
            editor.commit();
        }

        if (sharedPreferences.contains("FirstTimeOpening")) {
            firstTimeOpeningApp = sharedPreferences.getBoolean("FirstTimeOpening", true);

            editor.putBoolean("FirstTimeOpening", false);
            editor.commit();
        } else {
            firstTimeOpeningApp = true;
            editor.putBoolean("FirstTimeOpening",false);
            editor.commit();
        }
        //MUST REMOVE IN PRODUCTION APPLICATION.
        //editor.putBoolean("FirstTimeOpening",true);
        //editor.commit();
        //firstTimeOpeningApp=true;
        timeToUpdateClasses = true;

        Context context = getApplicationContext();
        if(!timeToUpdateClasses)
        {
            //Check if there is a scheduled update within a day?
        }
        if(timeToUpdateClasses)
        {
            Intent intent = new Intent(context, UpdateClassesInProgress.class);
            timeToUpdateClasses = false;
            editor.putBoolean("timeToUpdateCourses", false);
            // schedule when to update
            startActivity(intent);
            return;
        }
        if (firstTimeOpeningApp && !timeToUpdateClasses)
        {
            Intent intent = new Intent(context,FirstOpenScreen.class);
            firstTimeOpeningApp = false;
            editor.putBoolean("FirstTimeOpening",false);
            startActivityForResult(intent, 1);
        } else {

            startActivity(new Intent(context,PathWayDisplay.class));
        }



    }
    @Override
    public  void onStart()
    {
        super.onStart();
    }

    @Override
     public void onResume()
    {
        super.onResume();

        if (paused)
        {
            finish();
        }

    }

    @Override
    public void onPause()
    {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode== 1){
            if (resultCode==RESULT_CANCELED){
                setAllPathwayVariablesToScratch();
                Intent intent = new Intent(getApplicationContext(),PathWayDisplay.class);
                startActivity(intent);
            } else if (resultCode == RESULT_OK){
                Intent intent = new Intent(getApplicationContext(),SetUp.class);
                startActivity(intent);
            }
        }
    }

    private void setAllPathwayVariablesToScratch()
    {

    }
}
