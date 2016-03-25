package com.jones.android.nursingpathways;

import android.content.Context;
import android.content.SharedPreferences;

import com.jones.android.nursingpathways.CourseClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jones on 3/23/2016.
 */
public class CourseClassLoader {
    private List<CourseClass> coursesObject;

    CourseClassLoader(Context context){
        super();
        String[] courseLabels = context.getResources().getStringArray(R.array.AlliedHealthPathway);
        String[] coursePrereqs = context.getResources().getStringArray(R.array.Prereqs);
        SharedPreferences sharedPrefDone = context.getSharedPreferences("courses", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefInProgress = context.getSharedPreferences("coursesInProgress", Context.MODE_PRIVATE);

        coursesObject = new ArrayList<CourseClass>();
        for (int i = 0; i< courseLabels.length; i++)
        {
            boolean preReq = false;
            if (!coursePrereqs[i].equals("NONE")){
                preReq = true;
            }
            CourseClass course = new CourseClass(courseLabels[i],
                    "",
                    sharedPrefDone.getBoolean(courseLabels[i], false),
                    sharedPrefInProgress.getBoolean(courseLabels[i], false),
                    preReq,
                    coursePrereqs[i]);
            coursesObject.add(course);
        }
    }

    public List<CourseClass> loadClassObjects(){
        return coursesObject;
    }
}