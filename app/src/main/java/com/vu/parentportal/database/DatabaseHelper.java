package com.vu.parentportal.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseHelper {
    private static AppDatabase instance;

    private DatabaseHelper() {
        // Private constructor to prevent instantiation
    }

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, AppDatabase.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return instance;
    }
}