package com.vu.parentportal.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(
    tableName = "Assignment",
    foreignKeys = @ForeignKey(
        entity = Student.class,
        parentColumns = "studentId",
        childColumns = "studentId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = {@Index(value = "studentId"), @Index(value = "assignmentId", unique = true)}
)
public class Assignment {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "assignmentId")
    private String assignmentId;

    @ColumnInfo(name = "studentId")
    private String studentId;

    @ColumnInfo(name = "assignmentName")
    private String assignmentName;
}