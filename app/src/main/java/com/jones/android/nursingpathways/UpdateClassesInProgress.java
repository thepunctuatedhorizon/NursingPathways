package com.jones.android.nursingpathways;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        int mNotificationId = 071;
        int mNotificationSecondId = 061;
        int mNotification3 = 051;
        int mNotification4 = 001;
        int mNotification5 = 002;
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(mNotificationId);
        nMgr.cancel(mNotificationSecondId);
        nMgr.cancel(mNotification3);
        nMgr.cancel(mNotification4);
        nMgr.cancel(mNotification5);


        final String[] courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        final Context context = getApplicationContext();
        final SharedPreferences sharedPrefDone = getSharedPreferences("courses", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPrefDone.edit();
        final SharedPreferences sharedPrefInProgress = getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editorIP = sharedPrefInProgress.edit();
        checkBoxesDone = new ArrayList<CheckBox>();
        coursesDone = new boolean[courseLabels.length];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_checkbox_width), LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.relativeLayoutUpdate);

        for (int i = 0; i < courseLabels.length; i++) {
            //TODO: Revise or remove?
            if (sharedPrefDone.getBoolean(courseLabels[i],false)) {
                //Ignore it?
            }
            if(sharedPrefInProgress.getBoolean(courseLabels[i],false)){
                CheckBox checkBox = new CheckBox(context);
                checkBox.setText(courseLabels[i]);
                checkBox.setTextColor(Color.BLACK);
                checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                checkBox.setLayoutParams(params);
                linearLayout.addView(checkBox);
                checkBoxesDone.add(checkBox);
            }


        }
        if(checkBoxesDone.size()==0){
            //Really Dumb way of handling this.
            TextView textView = (TextView) findViewById(R.id.pleaseUpdate);
            textView.setText("It seems no courses were in progress.  That's OK! Just hit save.");
        }
        LinearLayout buttonContainer = new LinearLayout(context);
        buttonContainer.setGravity(Gravity.CENTER_HORIZONTAL);

        Button saveButton = new Button(context);
        Button resetButton = new Button(context);

        saveButton.setText("Save");
        saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        saveButton.setTextColor(getResources().getColor(R.color.pathBlack));
        saveButton.setTextSize(15);
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
                Intent intent = getIntent();
                if (intent.hasExtra("PreRecBlank")){
                    startActivity(new Intent(getApplicationContext(), RegisterForClasses.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), PathWayDisplay.class));
                }
            }
        });

        resetButton.setText("Reset");
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
}
