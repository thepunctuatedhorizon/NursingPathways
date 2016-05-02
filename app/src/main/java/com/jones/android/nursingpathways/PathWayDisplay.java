package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
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

import com.github.florent37.parallax.ScrollView;

import java.util.ArrayList;
import java.util.List;

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

        theClassListDone = new ArrayList<>();
        theClassListInProgress = new ArrayList<>();
        theCourseObjects = new ArrayList<>();
        //courseLabels = getResources().getStringArray(R.array.AlliedHealthPathway);
        //Intent intent = getIntent();

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
        final Context context = getApplicationContext();
        final ScrollView scroll = (ScrollView) findViewById(R.id.content_path_way_display_scrollview);

        CourseClassLoader courseClassLoader = new CourseClassLoader(context);
        theCourseObjects = courseClassLoader.loadClassObjects();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_app_button_width), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,50,0,0);


        for (int i = 0; i < theCourseObjects.size(); i++) {
            boolean buttonAdded = false;
            final CourseClass course = theCourseObjects.get(i);
            final String url = course.getUrl();
            if (course.getDone()) {
                theClassListDone.add(true);
                theClassListInProgress.add(false);

                Button button = new Button(context);
                button.setText(course.getTitle());
                button.setBackground(getResources().getDrawable(R.drawable.bttn_purple));
                button.setTextColor(getResources().getColor(R.color.pathBlack));
                button.setTextSize(24);
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
                button.setBackground(getResources().getDrawable(R.drawable.bttn_blue));
                button.setTextColor(getResources().getColor(R.color.pathBlack));
                button.setTextSize(24);
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
                button.setBackground(getResources().getDrawable(R.drawable.bttn_green));
                button.setTextColor(getResources().getColor(R.color.pathBlack));
                button.setTextSize(24);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("Button Gray", ((Button) view).getText().toString());
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
                button.setBackground(getResources().getDrawable(R.drawable.bttn_red));
                button.setTextColor(getResources().getColor(R.color.pathBlack));
                button.setTextSize(24);
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

        //Makes the bottom of the scrollview visible on load instead of top.
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

    }



    public void setTheButtonPopup(CourseClass course, View view){
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.pathway_popover, null, false);
        TextView textView = (TextView) inflatedView.findViewById(R.id.txtTitle);
        textView.setText(course.getTitle());
        TextView txtView = (TextView) inflatedView.findViewById(R.id.txtInfo);
        Resources res = getResources();
        if (course.getPreReqs().equals("NONE")){
            txtView.setText(R.string.courseNoPrereq);
        } else {
            //TODO: Verify placeholder works properly at runtime.
            txtView.setText(res.getString(R.string.coursePrereq, course.getPreReqs()));
        }
        TextView txtView2 = (TextView) inflatedView.findViewById(R.id.txtTaken);
        if (course.getDone()){
            txtView2.setText("");
        }else if (course.getInProgress())
        {
            txtView2.setText("");
        } else{
            txtView2.setText("");
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int x = size.x *2/3;
        int startX = size.x /17;
        int locx =(int) view.getX();
        int offset = -startX;
        PopupWindow popWindow = new PopupWindow(inflatedView, x, getResources().getInteger(R.integer.pathway_checkbox_width), true );
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bttn_green));
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

        if (id == R.id.action_real_help){
            Intent intent = new Intent(getApplicationContext(),RegistrationDenied.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
