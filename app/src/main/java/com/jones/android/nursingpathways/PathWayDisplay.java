package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PathWayDisplay extends AppCompatActivity {

    String[] courseLabels;
    List<Button> buttonOnPathway;
    List<Boolean> theClassListDone;
    List<Boolean> theClassListInProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_way_display);
        theClassListDone = new ArrayList<Boolean>();
        theClassListInProgress = new ArrayList<Boolean>();
        courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        Intent intent = getIntent();

        if(intent!=null)
        {

        }

        SharedPreferences sharedPrefDone = getSharedPreferences("courses", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefInProgress = getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);


        buttonOnPathway = new ArrayList<Button>();
        LinearLayout layout = (LinearLayout) findViewById(R.id.content_path_way_display_linearLayout);
        Context context = getApplicationContext();

        for (int i = 0; i < courseLabels.length; i++) {

            boolean buttonAdded = false;

            if (sharedPrefDone.getBoolean(courseLabels[i], false)) {
                theClassListDone.add(true);
                theClassListInProgress.add(false);

                Button button = new Button(context);
                button.setText(courseLabels[i]);
                button.setTextColor(Color.BLUE);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                button.setLayoutParams(params);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("Button", ((Button) view).getText().toString());
                    }
                });
                layout.addView(button);
                buttonOnPathway.add(button);

                buttonAdded = true;
            }
            if(sharedPrefInProgress.getBoolean(courseLabels[i],false) && !buttonAdded){
                theClassListDone.add(false);
                theClassListInProgress.add(true);

                Button button = new Button(context);
                button.setText(courseLabels[i]);
                button.setTextColor(Color.GREEN);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                button.setLayoutParams(params);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("ButtonIP", ((Button) view).getText().toString());
                    }
                });
                layout.addView(button);
                buttonOnPathway.add(button);
                buttonAdded = true;

            }

            if (!buttonAdded){
                theClassListInProgress.add(false);
                theClassListDone.add(false);
                Button button = new Button(context);
                button.setText(courseLabels[i]);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                button.setLayoutParams(params);
                button.setTextColor(Color.RED);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("Button Red", ((Button) view).getText().toString());
                    }
                });
                layout.addView(button);
                buttonOnPathway.add(button);

            }
        }



        TextView textView = (TextView) findViewById(R.id.textViewTemp);

        textView.setText("The Pathway");
        Button updatePathway = (Button) findViewById(R.id.editCourseList);
        updatePathway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpdateClasses.class));
            }
        });

    }






}
