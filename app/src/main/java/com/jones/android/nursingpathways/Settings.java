package com.jones.android.nursingpathways;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button button4 = (Button) findViewById(R.id.button4);
        Button reviewReg = (Button) findViewById(R.id.registrationJUMP);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_long), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,50,0,0);



        button4.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        button4.setTextColor(getResources().getColor(R.color.pathBlack));
        button4.setTextSize(16);
        button4.setLayoutParams(params);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), UpdateClasses.class);
                intent.putExtra("KEY","No");
                startActivity(intent);
            }
        });

        reviewReg.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        reviewReg.setTextColor(getResources().getColor(R.color.pathBlack));
        reviewReg.setTextSize(16);
        reviewReg.setLayoutParams(params);
        reviewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), RegisterForClasses.class);
                startActivity(intent);
            }
        });
    }

}
