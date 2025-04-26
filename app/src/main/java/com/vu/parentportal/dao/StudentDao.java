package com.vu.parentportal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vu.parentportal.models.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Query("SELECT * FROM Student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM Student WHERE studentId = :id")
    Student getStudentById(int id);
}
