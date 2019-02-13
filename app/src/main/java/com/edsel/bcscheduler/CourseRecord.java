package com.edsel.bcscheduler;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class CourseRecord implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String term;

    @ColumnInfo
    public String title;

    @ColumnInfo
    public String alert;

    @ColumnInfo
    public String coreq;

    @ColumnInfo
    public String day;

    @ColumnInfo
    public String location;

    @ColumnInfo
    public String time;

    @ColumnInfo
    public String code;

    @ColumnInfo
    public int credits;

    @ColumnInfo
    public String dept;

    @ColumnInfo
    public String faculties;

    public CourseRecord() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getCoreq() {
        return coreq;
    }

    public void setCoreq(String coreq) {
        this.coreq = coreq;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getFaculties() {
        return faculties;
    }

    public void setFaculties(String faculties) {
        this.faculties = faculties;
    }

    @Override
    public String toString() {
        String msg = "Term: " + this.term + ", Title: " + this.title + ", Alert: " + this.alert + "Coreq: " + this.coreq;
        return msg;
    }
}
