package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.florent37.parallax.ScrollView;

import java.util.ArrayList;
import java.util.List;


//This class displays the pathway for the user
public class PathWayDisplay extends AppCompatActivity {


    private List<Button> buttonOnPathway;
    private List<Boolean> theClassListDone;
    private List<Boolean> theClassListInProgress;
    private List<CourseClass> theCourseObjects;

    //This set of variables is important for the drawer layout to work.
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles = {"School Resources", "Help", "Settings"};  //Modify the strings here to change the categories.

    //This variable is used to prevent the drawer from opening on launch.  Only after things have finished loading will the drawer be initialized.
    private boolean operational = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_way_display);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //This sets up the drawer.
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
                //setDrawerListener(mDrawerToggle);

        //This seems to be what opens up the drawer prematurely. But it is necessary to display the pathway correctly?
        if (savedInstanceState == null) { selectItem(0); }




        //Some of these variables may be dispensed with.  I'm not sure we need them.  Some of them may be
        //from an older version of the code before the courseClassLoader was introduced.
        //TODO: Remove old unnecessary variables.
        theClassListDone = new ArrayList<>();
        theClassListInProgress = new ArrayList<>();
        theCourseObjects = new ArrayList<>();
        //THe button on pathway was designed to be a button container so you could come back to things later
        buttonOnPathway = new ArrayList<Button>();

        //This set of variables initializes critical layout components that the buttons will be added to.
        final LinearLayout layout = (LinearLayout) findViewById(R.id.content_path_way_display_linearLayout);
        final Context context = getApplicationContext();
        final ScrollView scroll = (ScrollView) findViewById(R.id.content_path_way_display_scrollview);


        //This section of the code loads the courses into memory.
        CourseClassLoader courseClassLoader = new CourseClassLoader(context);
        theCourseObjects = courseClassLoader.loadClassObjects();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getResources().getInteger(R.integer.pathway_app_button_width), LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,50,0,0);

        //This for loop gets the course objects out of the sorted object so it can color the buttons right.
        for (int i = 0; i < theCourseObjects.size(); i++) {
            boolean buttonAdded = false;
            final CourseClass course = theCourseObjects.get(i);

            //If the course is done, then we set up the button to be purple.
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

            //This if figures out if you are in progress. If in progress it colors it blue
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

            //This if figures out if you are able to take the course. If in possible to take it, it colors it green
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

            //This if figures out if you are not able to take the course. If in progress it colors it Red
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
        //The operational flag is turned true so that the drawer layout is used properly
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(View.FOCUS_DOWN);
                operational = true;
            }
        });

    }

    //The drawer click listener that starts the user on the other menus.
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    //This is where the operational click shield works its magic.
    //if you change the titles, be sure to change where the user is directed.
    private void selectItem(int position) {

        if (operational) {
            if (position == 0) {

                Intent intent = new Intent(getApplicationContext(), RegistrationDenied.class);
                startActivity(intent);
            }
            if (position == 1) {
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
            }
            if (position == 2) {
                Intent intent = new Intent(getApplicationContext(), Settings.class);
                startActivity(intent);
            }
        }
        //After the user has selected an item, it's good to close the drawer!
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    //As it sounds
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    //This sets up the pop up so that the user can find out information about the class.
    //This popup can be WILDLY better.
    //TODO: Make this popup wildly better.
    public void setTheButtonPopup(CourseClass course, View view){

        //This code sets up the layout inside of the popup.
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.pathway_popover, null, false);
        TextView textView = (TextView) inflatedView.findViewById(R.id.txtTitle);
        textView.setText(course.getTitle());
        TextView txtView = (TextView) inflatedView.findViewById(R.id.txtInfo);
        txtView.setText("See more information here:\n" + course.getUrl());
        //Linkifying the text allows the user to get to the course information if the need to.
        Linkify.addLinks(txtView, Linkify.WEB_URLS);
        TextView txtView2 = (TextView) inflatedView.findViewById(R.id.txtTaken);
        if (course.getDone()){
            txtView2.setText("");
        }else if (course.getInProgress())
        {
            txtView2.setText("");
        } else{
            txtView2.setText("");
        }

        //This is the code to show the popup
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //This may be entirely scrapped.  I would if I were you.
        /*
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_help) {
            Intent intent = new Intent(getApplicationContext(), Help.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_real_help) {
            Intent intent = new Intent(getApplicationContext(), RegistrationDenied.class);
            startActivity(intent);
            return true;
        }
        */
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);


    }



}
