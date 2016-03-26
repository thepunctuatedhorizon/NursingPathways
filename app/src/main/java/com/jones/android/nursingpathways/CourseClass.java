package com.jones.android.nursingpathways;

/**
 * Created by jones on 3/23/2016.
 */
public class CourseClass {

    //These are the different types of information used by the course class.
    String title;
    String url;
    boolean done;
    boolean inProgress;
    boolean anyPreReqs;
    String preReqs;

    public CourseClass()
    {
        //Null constructor.  DO NOT use.
        super();
        title = null;
        url = null;
        done = false;
        inProgress = false;
        anyPreReqs = false;
        preReqs = null;
    }

    public CourseClass(String title, String url, boolean done, boolean inProgress, boolean anyPreReqs, String preReqs){
        super();
        //The proper constructor.
        //TODO: Nullable inputs?
        this.title = title;
        this.url = url;
        this.done = done;
        this.inProgress = inProgress;
        this.anyPreReqs = anyPreReqs;
        this.preReqs = preReqs;

    }

    //The getters and setters.
    public String getTitle(){
        return title;
    }
    public String getUrl(){
        return url;
    }
    public boolean getDone(){
        return done;
    }
    public boolean getInProgress(){
        return inProgress;
    }
    public boolean getAnyPreReqs(){
        return anyPreReqs;
    }
    public String getPreReqs(){
        return preReqs;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public void setDone(boolean done){
        this.done = done;
    }
    public void setInProgress(boolean inProgress){
        this.inProgress = inProgress;
    }
    public void setAnyPreReqs(boolean anyPreReqs){
        this.anyPreReqs = anyPreReqs;
    }
    public  void setPreReqs(String preReqs){this.preReqs = preReqs; }



}
