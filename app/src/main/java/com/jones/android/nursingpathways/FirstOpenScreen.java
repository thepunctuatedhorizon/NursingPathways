package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
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
    //TODO: MAKE THE INTERFACE ACCEPTABLE!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_open_screen);



        final Button yesButton = (Button) findViewById(R.id.yesButton);
        final Button noButton = (Button) findViewById(R.id.noButton);


        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishWithResult();
            }
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
        Bundle conData = new Bundle();
        conData.putString("result_FirstOpenScreen", "No");
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    //This tells the MainActivity that they have taken courses.  The main activity will launch setup when receiving these values.
    private void finishWithResult()
    {
        Bundle conData = new Bundle();
        conData.putString("result_FirstOpenScreen", "Yes");
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }
}
