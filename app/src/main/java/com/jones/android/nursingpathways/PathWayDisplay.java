package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.jones.android.nursingpathways.CourseClass;

public class PathWayDisplay extends AppCompatActivity {

    private String[] courseLabels;
    private String[] coursePrereqs;
    private List<Button> buttonOnPathway;
    private List<Boolean> theClassListDone;
    private List<Boolean> theClassListInProgress;
    private List<CourseClass> theCourseObjects;



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



        buttonOnPathway = new ArrayList<Button>();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.content_path_way_display_linearLayout);
        Context context = getApplicationContext();

        CourseClassLoader courseClassLoader = new CourseClassLoader(context);
        theCourseObjects = courseClassLoader.loadClassObjects();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_app_button_width), LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < courseLabels.length; i++) {
            //TODO: IMPLEMENT THE PREREQ LOGIC
            boolean buttonAdded = false;
            final CourseClass course = theCourseObjects.get(i);
            final String url = course.getUrl();
            if (course.getDone()) {
                theClassListDone.add(true);
                theClassListInProgress.add(false);

                Button button = new Button(context);
                button.setText(course.getTitle());
                button.setTextColor(Color.BLUE);

                button.setLayoutParams(params);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("Button", ((Button) view).getText().toString());

                        setTheButtonPopup(course,view);
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

                button.setLayoutParams(params);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("ButtonIP", ((Button) view).getText().toString());
                        setTheButtonPopup(course,view);

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
                button.setLayoutParams(params);
                button.setTextColor(Color.RED);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("Button Red", ((Button) view).getText().toString());
                        setTheButtonPopup(course,view);
                    }
                });
                layout.addView(button);
                buttonOnPathway.add(button);

            }
        }



        TextView textView = (TextView) findViewById(R.id.textViewTemp);

        textView.setText("The Pathway");


 //       layout.post(new Runnable() {
 //           @Override
//            public void run() {
//                This method should implement a way to pause then increase the size of the buttons.
//                for (Button button : buttonOnPathway) {
//                    button.setTextSize(24);
//                    Log.e("String", "It's doing this");
//                }
 //           }
  //      });
    }

    public void setTheButtonPopup(CourseClass course, View view){
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.register_for_classes_popover, null, false);
        TextView textView = (TextView) inflatedView.findViewById(R.id.textView5);
        textView.setText(course.getTitle());
        Button button = (Button) inflatedView.findViewById(R.id.button);
        String buttonText = "Register for " + course.getTitle();
        button.setText(buttonText);
        final String url = course.getUrl();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url); //course.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = (int) size.x *2 /3;
        int startX =(int) size.x/5;
        int locx =(int) button.getX();
        int offset = (int) locx - startX;
        PopupWindow popWindow = new PopupWindow(inflatedView, x, 300, true );
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.gray));
        popWindow.showAsDropDown(view, offset, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
