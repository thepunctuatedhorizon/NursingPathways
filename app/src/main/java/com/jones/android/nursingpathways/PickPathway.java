package com.jones.android.nursingpathways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PickPathway extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_pathway);
        Button bttn = (Button) findViewById(R.id.alliedHeathPathway);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickPathway.this, SetUp.class);
                startActivity(intent);
            }
        });

        //TODO: SET UP GLOBAL Pathway Variable System
        //This system will point to the correct XML document with all the required strings
    }
}
