package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UpdateClasses extends AppCompatActivity {

    List<CheckBox> checkBoxesDone;
    List<CheckBox> checkBoxesInProgress;
    List<CourseClass> courses;
    boolean [] coursesDone;
    boolean [] coursesInProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_classes2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final String[] courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        final Context context = getApplicationContext();
        checkBoxesDone = new ArrayList<CheckBox>();
        coursesDone = new boolean[courseLabels.length];
        coursesInProgress = new boolean[courseLabels.length];

        CourseClassLoader loader = new CourseClassLoader(context);
        courses = loader.loadClassObjects();

        SharedPreferences sharedPrefDone = getSharedPreferences("courses", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPrefDone.edit();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.relativeLayoutUpdateReal);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_checkbox_width), LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < courseLabels.length; i++) {
            if(!courses.get(courseLabels.length-1 -i).getPreReqs().equals("PERMISSION")) {

                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(courseLabels[i]);
                checkBox.setTextColor(Color.BLACK);
                checkBox.setPadding(5,5,5,5);
                //checkBox.setGravity(Gravity.CENTER_HORIZONTAL);
                //checkBox.setHeight(100);


                //checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                if(sharedPrefDone.getBoolean(courseLabels[i],true)){
                    checkBox.setChecked(true);
                }
                checkBox.setLayoutParams(params);
                linearLayout.addView(checkBox);
                checkBoxesDone.add(checkBox);
            } else {
                CheckBox checkBox = new CheckBox(context);
                checkBoxesDone.add(checkBox);
            }

        }
        LinearLayout buttonContainer = new LinearLayout(context);
        buttonContainer.setGravity(Gravity.CENTER_HORIZONTAL);

        Button saveButton = new Button(context);
        Button resetButton = new Button(context);

        saveButton.setText(R.string.save);
        saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        saveButton.setTextColor(getResources().getColor(R.color.pathBlack));
        saveButton.setTextSize(15);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save Actions



                int counter = 0;
                for (int i = 0; i < courseLabels.length; i++) {
                    if (checkBoxesDone.size()>0&& checkBoxesDone.size()>counter) {
                        CheckBox box = checkBoxesDone.get(counter);
                        counter++;

                        if(box.isChecked() ) {
                            coursesDone[i] = true;
                            editor.putBoolean(courseLabels[i], true);
                            editor.commit();
                        } else {
                            coursesDone[i] = false;
                            editor.putBoolean(courseLabels[i], false);
                            editor.commit();
                        }
                    }
                }
                editor.putInt("arraySize", courseLabels.length);
                editor.commit();
                Intent intent = getIntent();
                //if the intent has a key then it is that we only want to edit the courses taken not the courses inprogress.
                if (intent.getStringExtra("KEY").equals("Yes"))
                {
                    startActivity(new Intent(context,PathWayDisplay.class));
                    finish();
                } else {
                    resetAndAskForInProgress();
                }
            }
        });

        resetButton.setText(R.string.reset);
        resetButton.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        resetButton.setTextColor(getResources().getColor(R.color.pathBlack));
        resetButton.setTextSize(15);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RESET ACTIONS
                for (CheckBox box : checkBoxesDone) {
                    box.setChecked(false);
                }
            }
        });

        buttonContainer.addView(saveButton);
        buttonContainer.addView(resetButton);
        linearLayout.addView(buttonContainer);


    }

    private void resetAndAskForInProgress()
    {
        setContentView(R.layout.activity_update_classes2);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.relativeLayoutUpdateReal);
        //linearLayout.removeAllViews();

        SharedPreferences sharedPrefIP = getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPrefIP.edit();


        TextView textView = (TextView) findViewById(R.id.pleaseUpdateReal);
        textView.setText(R.string.checkInProgress);

        final String[] courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        final Context context = getApplicationContext();
        checkBoxesInProgress= new ArrayList<CheckBox>();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_checkbox_width), LinearLayout.LayoutParams.WRAP_CONTENT);


        for (int i = 0; i < courseLabels.length; i++) {
            boolean check = courses.get(courseLabels.length-1 -i).getPreReqs().equals("PERMISSION");
            if(!coursesDone[i]&&!check) {
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(courseLabels[i]);
                checkBox.setTextColor(Color.BLACK);
                checkBox.setLayoutParams(params);
                if(sharedPrefIP.getBoolean(courseLabels[i],true)){
                    checkBox.setChecked(true);
                }
                linearLayout.addView(checkBox);
                checkBoxesInProgress.add(checkBox);
            }
            if (check){
                coursesDone[i] = true;
            }
        }
        LinearLayout buttonContainer = new LinearLayout(context);
        buttonContainer.setGravity(Gravity.CENTER_HORIZONTAL);

        Button saveButton = new Button(context);
        Button resetButton = new Button(context);

        saveButton.setText(R.string.save);
        saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        saveButton.setTextColor(getResources().getColor(R.color.pathBlack));
        saveButton.setTextSize(15);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save Actions
                SharedPreferences sharedPrefInProgress = getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorIP = sharedPrefInProgress.edit();

                int counter = 0;
                for (int i = 0; i < courseLabels.length; i++) {
                    if(coursesDone[i]) {
                        //What do we care?
                        editorIP.putBoolean(courseLabels[i], false);
                        coursesInProgress[i] = false;
                        editorIP.commit();
                    } else {
                        CheckBox box = checkBoxesInProgress.get(counter);
                        counter++;
                        if (box.isChecked()) {
                            editorIP.putBoolean(courseLabels[i], true);
                            coursesInProgress[i] = true;
                            editorIP.commit();
                        } else {
                            editorIP.putBoolean(courseLabels[i], false);
                            coursesInProgress[i] = false;
                            editorIP.commit();
                        }
                    }
                }
                editorIP.putInt("arraySize", courseLabels.length);
                editorIP.commit();

                startActivity(new Intent(context,PathWayDisplay.class));
            }
        });

        resetButton.setText(R.string.reset);
        resetButton.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        resetButton.setTextColor(getResources().getColor(R.color.pathBlack));
        resetButton.setTextSize(15);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RESET ACTIONS
                for (CheckBox box : checkBoxesInProgress) {
                    box.setChecked(false);
                }
            }
        });

        buttonContainer.addView(saveButton);
        buttonContainer.addView(resetButton);
        linearLayout.addView(buttonContainer);


    }
}
