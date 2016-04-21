package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PickPathway extends AppCompatActivity {

    final private int ALLIED_PATHWAY = 100;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LinearLayout linearLayout;
    private String[] pathwayPossibilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_pathway);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context = getApplicationContext();
        pathwayPossibilities = context.getResources().getStringArray(R.array.PathwayCategory);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        editor =sharedPreferences.edit();

        linearLayout = (LinearLayout) findViewById(R.id.linLay_PickP_Buttons);


        for(int i=0; i<pathwayPossibilities.length;i++){
            Button bttn = new Button(context);

            setUpButtons(bttn,pathwayPossibilities[i], i);
            linearLayout.addView(bttn);

        }
        //TODO: SET UP GLOBAL Pathway Variable System
        //This system will point to the correct XML document with all the required strings
    }

    private void setUpButtons(Button bttn, final String category, final int position){
        bttn.setText(category);
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAndAskForWhichPath(category, position);
            }
        });

    }

    private void setUpPathwayButton(Button bttn, String category){

        bttn.setText(category);
        if (category.equals("PATHWAYS")){
            //Implement
        }
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("PathwayChoice", ALLIED_PATHWAY);
                editor.commit();
                Intent intent = new Intent(PickPathway.this, SetUp.class);
                startActivity(intent);
            }
        });
    }

    private void resetAndAskForWhichPath(String categoryChosen,int position){
        linearLayout.removeAllViews();
        String[] pathwaySelected;
        switch(position){
            case 0:
                pathwaySelected = context.getResources().getStringArray(R.array.PathwayCategoryPRE);
                break;
            case 1:
                pathwaySelected = context.getResources().getStringArray(R.array.PathwayCategoryBLC);
                break;
            default:
                pathwaySelected = context.getResources().getStringArray(R.array.PathwayCategoryPRE);
                break;
        }

        for(int i=0; i<pathwaySelected.length; i++){
            Button bttn = new Button(context);

            setUpPathwayButton(bttn,pathwaySelected[i]);
            linearLayout.addView(bttn);
        }
    }
}
