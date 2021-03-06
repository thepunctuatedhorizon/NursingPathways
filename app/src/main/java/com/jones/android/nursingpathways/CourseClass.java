package com.jones.android.nursingpathways;

/**
 * Created by jones on 3/23/2016.
 */
public class CourseClass {

    //These are the different types of information used by the course class.
    //This will store the title of the course
    private String title;
    //This will store the url to the quick register link
    private String url;
    //This will store the status of whether this course has been completed
    private boolean done;
    //This will store the status of whether this course is in progress or not.
    private boolean inProgress;
    //This will store the fact that the course has a prerequisite or not.
    private boolean anyPreReqs;
    //This will store the boolean that determines whether this particular course is open for registration
    //This will be set by a calculation.
    private boolean isOpenForRegistration;
    //This will store the name of the prerequisite course (if there is one, else it will be "NONE")
    private String preReqs;

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

    public CourseClass(String title, String url, boolean done, boolean inProgress, boolean anyPreReqs, String preReqs, boolean isOpenForRegistration){
        super();
        //The proper constructor.
        this.title = title;
        this.url = url;
        this.done = done;
        this.inProgress = inProgress;
        this.anyPreReqs = anyPreReqs;
        this.preReqs = preReqs;
        this.isOpenForRegistration = isOpenForRegistration;

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
    public boolean getIsOpenForRegistration() {return isOpenForRegistration;}

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

    public void setIsOpenForRegistaration(boolean is) {this.isOpenForRegistration = is;}



}
