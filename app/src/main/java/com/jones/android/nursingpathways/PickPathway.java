package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PickPathway extends AppCompatActivity {

    //This needs to be put into a data contract!!!!
    //TODO: PUT THIS INTO A DATA CONTRACT
    final private int ALLIED_PATHWAY = 100;

    //These variables allow the activity to be oriented correctly.
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private LinearLayout linearLayout;

    //This string variable will load in the possible pathway paths.
    private String[] pathwayPossibilities;
    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_pathway);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //setting up context and using it to get the pathway possibilities array.
        context = getApplicationContext();
        pathwayPossibilities = context.getResources().getStringArray(R.array.PathwayCategory);

        //This shared preferences allows us to record the user choices. THIS shared preferences variable will be
        //for the courses that are done.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //The editor so we can save those preferences.
        editor =sharedPreferences.edit();

        //Initializing the layout parameters
        linearLayout = (LinearLayout) findViewById(R.id.linLay_PickP_Buttons);
        params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_app_button_width), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,25,0,0);

        //This COMpAct code creates a button for each pathway possibility and then sets up the button.
        for(int i=0; i<pathwayPossibilities.length;i++){
            Button bttn = new Button(context);
            bttn.setLayoutParams(params);
            setUpButtons(bttn,pathwayPossibilities[i], i);
            //This adds each new button to the layout.
            linearLayout.addView(bttn);

        }
    }

    private void setUpButtons(Button bttn, final String category, final int position){
        //Sets the button color and the button text
        bttn.setText(category);
        if (category.equals(pathwayPossibilities[0])){
            bttn.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        } else if (category.equals(pathwayPossibilities[1])) {
            bttn.setBackground(getResources().getDrawable(R.drawable.bttn_blue));
        }else if (category.equals(pathwayPossibilities[2])) {
            bttn.setBackground(getResources().getDrawable(R.drawable.bttn_red));
        }else if (category.equals(pathwayPossibilities[3])) {
            bttn.setBackground(getResources().getDrawable(R.drawable.bttn_purple));
        }else if (category.equals(pathwayPossibilities[4])) {
            bttn.setBackground(getResources().getDrawable(R.drawable.bttn_yellow));
        } else{
            bttn.setBackground(getResources().getDrawable(R.drawable.bttn_blue));}

        //Formats the button text
        bttn.setTextColor(getResources().getColor(R.color.pathBlack));
        bttn.setTextSize(15);

        //This onclickclistener is important as it resets the screen and starts up the selection of the
        //Concentration choice.
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAndAskForWhichPath(category, position);
            }
        });

    }

    private void setUpPathwayButton(Button bttn, String category){
        //This function sets up the colors of the buttons
        bttn.setText(category);
        bttn.setLayoutParams(params);
        bttn.setBackground(getResources().getDrawable(R.drawable.bttn_green));
        bttn.setTextColor(getResources().getColor(R.color.pathBlack));
        bttn.setTextSize(15);
        //TODO: Implement this statement so that all the possible pathways are represented and put
        //TODO: into play.
        if (category.equals("PATHWAYS")){
            //Implement
        }

        //The important part of this function,  it sets the pathway choice.  It needs to be expnded
        //TODO: EXPAND THIS SO THAT IT ACCOMMODATES all pathways.
        bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("PathwayChoice", ALLIED_PATHWAY);
                editor.commit();
                finishWithResult();
            }
        });
    }

    //This is the function that allows the user to select with program they are taking in each path.
    private void resetAndAskForWhichPath(String categoryChosen,int position){
        //THis is important as it prompts the user to now select which of the programs they are on.
        getSupportActionBar().setTitle("Select Your Program");

        //We need to remove all of the old buttons.
        linearLayout.removeAllViews();

        //TODO: IMPLEMENT THIS SO THAT ALL THE PROGRAM THEY ARE TO TAKE.
        //This switch statement allows the program to select all of the programs that are in Pathway
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

        //This sets up the buttons that allow the user to select which program in their pathway to use.
        for(int i=0; i<pathwaySelected.length; i++){
            Button bttn = new Button(context);
            setUpPathwayButton(bttn,pathwaySelected[i]);
            linearLayout.addView(bttn);
        }
    }

    //This tells the MainActivity that they haven't taken courses.
    private void finishWithResultNo()
    {
        Bundle conData = new Bundle();
        conData.putString("result_FirstOpenScreen", "No");
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    //This tells the MainActivity that they have taken courses.  The main activity will launch setup when receiving these values.
    private void finishWithResult()
    {
        Bundle conData = new Bundle();
        conData.putString("result_FirstOpenScreen", "Yes");
        Intent intent = new Intent();
        intent.putExtras(conData);
        setResult(RESULT_OK, intent);
        finish();
    }
}
