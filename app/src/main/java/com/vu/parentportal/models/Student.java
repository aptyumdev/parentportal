package com.vu.parentportal.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String studentId;
    private String studentPassword;
    private String studentFullName;
    private String studentClass;
    private String studentAge;
}
