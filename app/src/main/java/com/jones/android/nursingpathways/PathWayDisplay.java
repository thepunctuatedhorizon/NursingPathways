package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.jones.android.nursingpathways.CourseClass;

public class PathWayDisplay extends AppCompatActivity {

    //private String[] courseLabels;
    private List<Button> buttonOnPathway;
    private List<Boolean> theClassListDone;
    private List<Boolean> theClassListInProgress;
    private List<CourseClass> theCourseObjects;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_way_display);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        theClassListDone = new ArrayList<Boolean>();
        theClassListInProgress = new ArrayList<Boolean>();
        theCourseObjects = new ArrayList<CourseClass>();
        //courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        Intent intent = getIntent();

//        ImageView imageView = (ImageView) findViewById(R.id.qm);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PathWayDisplay.this, Help.class);
//                startActivity(intent);
//            }
//        });



        buttonOnPathway = new ArrayList<Button>();
        final LinearLayout layout = (LinearLayout) findViewById(R.id.content_path_way_display_linearLayout);
        Context context = getApplicationContext();

        CourseClassLoader courseClassLoader = new CourseClassLoader(context);
        theCourseObjects = courseClassLoader.loadClassObjects();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_app_button_width), LinearLayout.LayoutParams.WRAP_CONTENT);


        for (int i = 0; i < theCourseObjects.size(); i++) {
            boolean buttonAdded = false;
            final CourseClass course = theCourseObjects.get(i);
            final String url = course.getUrl();
            if (course.getDone()) {
                theClassListDone.add(true);
                theClassListInProgress.add(false);

                Button button = new Button(context);
                button.setText(course.getTitle());
                button.setTextColor(getResources().getColor(R.color.pathGreenDark));

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

            if (!buttonAdded&& course.getIsOpenForRegistration()){
                theClassListDone.add(false);
                theClassListInProgress.add(false);
                Button button = new Button(context);
                button.setText(course.getTitle());
                button.setLayoutParams(params);
                button.setTextColor(Color.GRAY);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("Button Gray", ((Button) view).getText().toString());
                        setTheButtonPopup(course,view);
                    }
                });
                layout.addView(button);
                buttonOnPathway.add(button);
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
        final View inflatedView = layoutInflater.inflate(R.layout.pathway_popover, null, false);
        TextView textView = (TextView) inflatedView.findViewById(R.id.txtTitle);
        textView.setText(course.getTitle());
        TextView txtView = (TextView) inflatedView.findViewById(R.id.txtInfo);
        txtView.setText("This course has " + course.getPreReqs() + " as a prerequisite.");
        TextView txtView2 = (TextView) inflatedView.findViewById(R.id.txtTaken);
        if (course.getDone()){
            txtView2.setText("You have completed this course.");
        }else if (course.getInProgress())
        {
            txtView2.setText("You are taking this course now.");
        } else{
            txtView2.setText("Your pathway requires this course.");
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = (int) size.x *2 /3;
        int startX =(int) size.x/5;
        int locx =(int) view.getX();
        int offset = (int) -startX;
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
        if (id == R.id.action_help){
            Intent intent = new Intent(getApplicationContext(),Help.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
