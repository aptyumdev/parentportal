package com.vu.parentportal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vu.parentportal.models.Teacher;

import java.util.List;

@Dao
public interface TeacherDao {

    @Insert
    void insertTeacher(Teacher teacher);

    @Update
    void updateTeacher(Teacher teacher);

    @Delete
    void deleteTeacher(Teacher teacher);

    @Query("SELECT * FROM Teacher")
    List<Teacher> getAllTeachers();

    @Query("SELECT * FROM Teacher WHERE id = :id")
    Teacher getTeacherById(int id);

    @Query("SELECT * FROM Teacher WHERE teacherId = :teacherId")
    Teacher getTeacherByTeacherId(String teacherId);
}
