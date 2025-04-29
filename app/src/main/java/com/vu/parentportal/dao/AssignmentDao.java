package com.vu.parentportal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vu.parentportal.models.Assignment;

import java.util.List;

@Dao
public interface AssignmentDao {

    @Insert
    void insertAssignment(Assignment assignment);

    @Update
    void updateAssignment(Assignment assignment);

    @Delete
    void deleteAssignment(Assignment assignment);

    @Query("SELECT * FROM Assignment")
    List<Assignment> getAllAssignments();

    @Query("SELECT * FROM Assignment WHERE id = :id")
    Assignment getAssignmentById(int id);

    @Query("SELECT * FROM Assignment WHERE assignmentId = :assignmentId")
    Assignment getAssignmentByAssignmentId(String assignmentId);
}
