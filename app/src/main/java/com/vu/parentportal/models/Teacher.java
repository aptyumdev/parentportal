package com.vu.parentportal.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Teacher {
    @PrimaryKey
    private int id;
    private String teacherId;
    private String teacherPassword;
}
