package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jones.android.nursingpathways.CourseClass;

import java.util.ArrayList;
import java.util.List;


public class CourseClassLoader {
    private List<CourseClass> coursesObject;
    private List<CourseClass> sortedObject;
    final private int ALLIED_HEALTH = 100;

    String[] courseLabels;
    String[] coursePrereqs;
    String[] courseURLs;
    //This class will load the courses into memory.

    CourseClassLoader(Context context){
        super();


        SharedPreferences sharedPrefDone = context.getSharedPreferences("courses", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefInProgress = context.getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);
        SharedPreferences pathwayPref = context.getSharedPreferences("pathway", Context.MODE_PRIVATE);
        int pathway;
        if (pathwayPref.contains("PathwayChoice"))
        {
            pathway = pathwayPref.getInt("PathwayChoice", 100);
        } else { pathway = 100;}

        switch (pathway){
            case ALLIED_HEALTH:
            {
                courseLabels = context.getResources().getStringArray(R.array.AlliedHealthPathway);
                coursePrereqs = context.getResources().getStringArray(R.array.AlliedHealthPrereqs);
                courseURLs = context.getResources().getStringArray(R.array.AlliedHealthURLS);
                break;
            }
            default:
            {
                courseLabels = context.getResources().getStringArray(R.array.AlliedHealthPathway);
                coursePrereqs = context.getResources().getStringArray(R.array.AlliedHealthPrereqs);
                courseURLs = context.getResources().getStringArray(R.array.AlliedHealthURLS);
            }
        }


        coursesObject = new ArrayList<CourseClass>();
        sortedObject = new ArrayList<CourseClass>();
        List<CourseClass> courseDone = new ArrayList<CourseClass>();
        List<CourseClass> courseInProgress = new ArrayList<CourseClass>();
        List<CourseClass> courseTop = new ArrayList<CourseClass>();
        List<CourseClass> courseAvailable = new ArrayList<CourseClass>();

        for (int i = courseLabels.length-1; i>=0; i--)
        {
            boolean preReq = false;
            if (!coursePrereqs[i].equals("NONE")){
                preReq = true;
            }

            boolean isCourseAvailableForRegistration = false;

            String iCoursePrereq = coursePrereqs[i];
            boolean done = sharedPrefDone.getBoolean(courseLabels[i], false);
            boolean inProgress = sharedPrefInProgress.getBoolean(courseLabels[i], false);

            if (!done&&!inProgress){

                for (int j =0; j<courseLabels.length-1; j++)
                {

                    String courseString = courseLabels[j];
                    boolean prereqDone = sharedPrefDone.getBoolean(courseLabels[j],false);
                    if (courseString.equals(iCoursePrereq)&&prereqDone){
                        isCourseAvailableForRegistration = true;

                    }
                }
                if (!isCourseAvailableForRegistration && !done && !inProgress &&!preReq){isCourseAvailableForRegistration = true;}
            }
            CourseClass course = new CourseClass(courseLabels[i],
                    courseURLs[i],
                    done,
                    inProgress,
                    preReq,
                    coursePrereqs[i],
                    isCourseAvailableForRegistration);


            boolean added = false;
            if (done){
                courseDone.add(course);
                added = true;
            }
            if (inProgress && !added){
                courseInProgress.add(course);
                added = true;
            }
            if (isCourseAvailableForRegistration && !added){
                courseAvailable.add(course);
                added = true;
            }
            if (!added){
                courseTop.add(course);
            }
            coursesObject.add(course);
        }


            for (CourseClass course : courseTop) {
                sortedObject.add(course);

            }
        for (CourseClass course : courseAvailable){
            sortedObject.add(course);
        }

        for (CourseClass course : courseInProgress){
            sortedObject.add(course);

        }
        for (CourseClass course : courseDone){
            sortedObject.add(course);

        }

    }

    //TODO: What if the list is NULL?
    public List<CourseClass> loadClassObjects(){
        return sortedObject;
    }
}
