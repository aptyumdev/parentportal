package com.vu.parentportal.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vu.parentportal.models.Student;
import com.vu.parentportal.models.Teacher;
import com.vu.parentportal.dao.StudentDao;
import com.vu.parentportal.dao.TeacherDao;

@Database(entities = {Student.class, Teacher.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "ParentPortal.db"; // Static constant for database name

    public abstract StudentDao studentDao();
    public abstract TeacherDao teacherDao();
}
