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
import com.jones.android.nursingpathways.CourseClass;

public class PathWayDisplay extends AppCompatActivity {

    String[] courseLabels;
    String[] coursePrereqs;
    List<Button> buttonOnPathway;
    List<Boolean> theClassListDone;
    List<Boolean> theClassListInProgress;
    List<CourseClass> theCourseObjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_way_display);
        theClassListDone = new ArrayList<Boolean>();
        theClassListInProgress = new ArrayList<Boolean>();
        theCourseObjects = new ArrayList<CourseClass>();
        courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        coursePrereqs = getResources().getStringArray(R.array.Prereqs);
        Intent intent = getIntent();

        if(intent!=null)
        {

        }

        SharedPreferences sharedPrefDone = getSharedPreferences("courses", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefInProgress = getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);


        buttonOnPathway = new ArrayList<Button>();
        LinearLayout layout = (LinearLayout) findViewById(R.id.content_path_way_display_linearLayout);
        Context context = getApplicationContext();

        for (int i = 0; i< courseLabels.length; i++)
        {
            boolean preReq = false;
            if (!coursePrereqs[i].equals("NONE")){
                preReq = true;
            }
            CourseClass course = new CourseClass(courseLabels[i],
                    "",
                    sharedPrefDone.getBoolean(courseLabels[i], false),
                    sharedPrefInProgress.getBoolean(courseLabels[i], false),
                    preReq,
                    coursePrereqs[i]);
            theCourseObjects.add(course);
        }


        for (int i = 0; i < courseLabels.length; i++) {

            boolean buttonAdded = false;
            CourseClass course = theCourseObjects.get(i);
            if (course.getDone()) {
                theClassListDone.add(true);
                theClassListInProgress.add(false);

                Button button = new Button(context);
                button.setText(course.getTitle());
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
            if(course.getInProgress() && !buttonAdded){
                theClassListDone.add(false);
                theClassListInProgress.add(true);

                Button button = new Button(context);
                button.setText(course.getTitle());
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
                button.setText(course.getTitle());
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
