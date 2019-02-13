package com.edsel.bcscheduler;


import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

// bump version number if your schema changes
@Database(entities = {CourseRecord.class},version = 1)
public abstract class AppDatabaseMod extends RoomDatabase {

    // Declare your data access objects as abstract
    public abstract DatabaseInterface databaseInterface();
    // Database name to be used
    public static final String NAME = "MyCourses";

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}