package com.vu.parentportal.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@Entity
@Entity(tableName = "Student", indices = {@Index(value = "studentId", unique = true)})
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "studentId")
    private String studentId;
    private String studentPassword;
    private String studentFullName;
    private String studentClass;
    private String studentAge;
}
