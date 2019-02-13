package com.edsel.bcscheduler;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

public class DBInterface {

    @Query("SELECT * FROM CourseRecord")
    List<CourseRecord> getAllItems();

    @Insert
    void insertAll(CourseRecord...courseRecords);

    @Query("DELETE FROM CourseRecord where code == :code")
    public void deleteItems(String code);

    @Update
    public void updateItems(CourseRecord...courseRecords);

}
