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

public class PathWayDisplay extends AppCompatActivity {

    //private String[] courseLabels;
    private List<Button> buttonOnPathway;
    private List<Boolean> theClassListDone;
    private List<Boolean> theClassListInProgress;
    private List<CourseClass> theCourseObjects;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles = {"School Resources", "Help", "Settings"};

    private boolean operational = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_way_display);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
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

        if (savedInstanceState == null) {
            selectItem(0);
        }





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
                operational = true;
            }
        });

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

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
        // update the main content by replacing fragments
        /*
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
*/
        // update selected item and title, then close the drawer
        //mDrawerList.setItemChecked(position, true);
       // setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

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



    public void setTheButtonPopup(CourseClass course, View view){
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View inflatedView = layoutInflater.inflate(R.layout.pathway_popover, null, false);
        TextView textView = (TextView) inflatedView.findViewById(R.id.txtTitle);
        textView.setText(course.getTitle());
        TextView txtView = (TextView) inflatedView.findViewById(R.id.txtInfo);
        Resources res = getResources();
        /*if (course.getPreReqs().equals("NONE")){
            txtView.setText("");
        } else {
            //TODO: Verify placeholder works properly at runtime.
            txtView.setText(res.getString(R.string.coursePrereq, course.getPreReqs()));
        }*/
        txtView.setText("See more information here:\n" + course.getUrl());
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
