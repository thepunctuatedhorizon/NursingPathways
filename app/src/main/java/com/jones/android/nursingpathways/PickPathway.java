package com.jones.android.nursingpathways;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PickPathway extends AppCompatActivity {

    final private int ALLIED_PATHWAY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_pathway);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final SharedPreferences.Editor editor =sharedPreferences.edit();

        Button bttn = (Button) findViewById(R.id.alliedHeathPathway);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("PathwayChoice", ALLIED_PATHWAY);
                editor.commit();
                Intent intent = new Intent(PickPathway.this, SetUp.class);
                startActivity(intent);
            }
        });

        //TODO: SET UP GLOBAL Pathway Variable System
        //This system will point to the correct XML document with all the required strings
    }
}
