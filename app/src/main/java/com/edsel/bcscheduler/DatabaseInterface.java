package com.edsel.bcscheduler;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DatabaseInterface {

    @Query("SELECT * FROM CourseRecord")
    List<CourseRecord> getAllItems();
    //List<Course> getAllItems();

    @Insert
    void insertAll(CourseRecord... courseRecords);
    //void insertAll(CourseRecord... Course);

    //@Delete
    @Query("DELETE FROM CourseRecord where code == :code")
    public void deleteItems(String code);

    @Update
    public void updateItems(CourseRecord... courseRecords);

    // @Query("SELECT * FROM CourseRecord where magnitude > :mag")
    // public List<CourseRecord> getbyMag(double mag);

    @Query("DELETE FROM CourseRecord")
    public void dropTheTable();

    @RawQuery
    int vacuumDb(SupportSQLiteQuery supportSQLiteQuery);
}