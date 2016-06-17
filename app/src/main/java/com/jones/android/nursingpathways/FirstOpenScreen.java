package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

public class FirstOpenScreen extends AppCompatActivity {

    //This is shown to the students when they first install.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_open_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Setting up the yes button
        final Button yesButton = (Button) findViewById(R.id.yesButton);
        yesButton.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        yesButton.setTextColor(getResources().getColor(R.color.pathBlack));
        yesButton.setTextSize(15);

        //Setting up the no button
        final Button noButton = (Button) findViewById(R.id.noButton);
        noButton.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        noButton.setTextColor(getResources().getColor(R.color.pathBlack));
        noButton.setTextSize(15);


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finishWithResult(); }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishWithResultNo();
            }

        });
    }

    //This tells the MainActivity that they haven't taken courses.
    private void finishWithResultNo()
    {

        Intent intent = new Intent(getApplicationContext(), PathWayDisplay.class);
        startActivity(intent);
        finish();
    }

    //This tells the MainActivity that they have taken courses.  The main activity will launch setup when receiving these values.
    private void finishWithResult()
    {
        Intent intent = new Intent(getApplicationContext(), SetUp.class);
        startActivity(intent);
        finish();
    }
}
