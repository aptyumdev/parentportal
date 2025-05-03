package com.vu.parentportal;

import android.app.Application;

import com.vu.parentportal.models.Student;

public class ParentPortalApp extends Application {
    private String userType;
    private Student selectedStudent;
    public Student getSelectedStudent() {
        return selectedStudent;
    }
    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
}