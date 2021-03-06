package com.edsel.bcscheduler;

public class Course{

    private String term;
    private String title;
    private String alert;
    private String coreq;
    private String day;
    private String location;
    private String time;
    private String code;
    private int credits;
    private String dept;
    private String faculties;

    public String getTerm() {
        return this.term;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAlert() {
        return this.alert;
    }

    public String getCoreq() {
        return this.coreq;
    }

    public String getDay() {
        return this.day;
    }

    public String getLocation() {
        return this.location;
    }

    public String getTime() {
        return this.time;
    }

    public String getCode() {
        return this.code;
    }

    public int getCredits() {
        return this.credits;
    }

    public String getDept() {
        return this.dept;
    }

    public String getFaculties() {
        return this.faculties;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public void setCoreq(String coreq) {
        this.coreq = coreq;
    }

    public void setDay(String Day) {
        this.day = Day;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setFaculties(String fact) {
        this.faculties = fact;
    }
}