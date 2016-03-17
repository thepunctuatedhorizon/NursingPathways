package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UpdateClassesInProgress extends AppCompatActivity {
    List<CheckBox> checkBoxesDone;
    boolean [] coursesDone;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_classes_inprogress);

        final String[] courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        final Context context = getApplicationContext();
        final SharedPreferences sharedPrefDone = getSharedPreferences("courses", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPrefDone.edit();
        final SharedPreferences sharedPrefInProgress = getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editorIP = sharedPrefInProgress.edit();
        checkBoxesDone = new ArrayList<CheckBox>();
        coursesDone = new boolean[courseLabels.length];


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.relativeLayoutUpdate);

        for (int i = 0; i < courseLabels.length; i++) {
            if (sharedPrefDone.getBoolean(courseLabels[i],false)) {
                //Ignore it?
            }
            if(sharedPrefInProgress.getBoolean(courseLabels[i],false)){
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(courseLabels[i]);
                checkBox.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                checkBox.setLayoutParams(params);
                linearLayout.addView(checkBox);
                checkBoxesDone.add(checkBox);
            }


        }
        if(checkBoxesDone.size()==0){
            //Really Dumb way of handling this.
            TextView textView = (TextView) findViewById(R.id.pleaseUpdate);
            textView.setText("It Seems No Courses were in progress.  That's ok! Just hit save.");
        }
        LinearLayout buttonContainer = new LinearLayout(context);
        buttonContainer.setGravity(Gravity.CENTER_HORIZONTAL);

        Button saveButton = new Button(context);
        Button resetButton = new Button(context);

        saveButton.setText("Save");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Save Actions
                int counter = 0;
                for (int i = 0; i < courseLabels.length; i++) {
                    if(checkBoxesDone.size()>0 && checkBoxesDone.size()>counter  && checkBoxesDone.get(counter).getText()==courseLabels[i]) {
                        CheckBox box = checkBoxesDone.get(counter);
                        counter++;
                        if (box.isChecked()) {
                            coursesDone[i] = true;
                            editor.putBoolean(courseLabels[i], true);
                            editor.commit();
                            editorIP.putBoolean(courseLabels[i], false);
                            editorIP.commit();
                        }
                    }
                }
                startActivity(new Intent(getApplicationContext(), PathWayDisplay.class));
            }
        });

        resetButton.setText("Reset");
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
}
