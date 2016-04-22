package com.jones.android.nursingpathways;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_long), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,50,0,0);

        Button purpleBttn = (Button) findViewById(R.id.purple);
        Button blueBttn = (Button) findViewById(R.id.blue);
        Button greenBttn = (Button) findViewById(R.id.green);
        Button redBttn = (Button) findViewById(R.id.red);

        purpleBttn.setBackground(getResources().getDrawable(R.drawable.bttn_purple));
        purpleBttn.setTextColor(getResources().getColor(R.color.pathBlack));
        purpleBttn.setTextSize(18);
        purpleBttn.setLayoutParams(params);

        greenBttn.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        greenBttn.setTextColor(getResources().getColor(R.color.pathBlack));
        greenBttn.setTextSize(18);
        greenBttn.setLayoutParams(params);

        blueBttn.setBackground(getResources().getDrawable(R.drawable.bttn_blue));
        blueBttn.setTextColor(getResources().getColor(R.color.pathBlack));
        blueBttn.setTextSize(18);
        blueBttn.setLayoutParams(params);

        redBttn.setBackground(getResources().getDrawable(R.drawable.bttn_red));
        redBttn.setTextColor(getResources().getColor(R.color.pathBlack));
        redBttn.setTextSize(18);
        redBttn.setLayoutParams(params);
    }
}
