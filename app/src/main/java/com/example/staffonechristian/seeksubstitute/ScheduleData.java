package com.example.staffonechristian.seeksubstitute;

/**
 * Created by staffonechristian on 2017-10-18.
 */

public class ScheduleData {

    private String subject;
    private String time;
    private String date;
    private String sirName;
    private String sirID;
    private String lectureId;
    private String country;
    private String schoolName;
    private boolean going;
    //by default going will be true


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public ScheduleData(){

        this.going = true;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSirName() {
        return sirName;
    }

    public void setSirName(String sirName) {
        this.sirName = sirName;
    }

    public String getSirID() {
        return sirID;
    }

    public void setSirID(String sirID) {
        this.sirID = sirID;
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isGoing() {
        return going;
    }

    public void setGoing(boolean going) {
        this.going = going;
    }
}
