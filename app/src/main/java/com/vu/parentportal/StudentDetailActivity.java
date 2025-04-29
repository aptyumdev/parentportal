package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Student;

public class StudentDetailActivity extends AppCompatActivity {
    String studentId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        studentId = getIntent().getStringExtra("studentId");
        populateUI();
        Button profileEditButton = findViewById(R.id.btn_student_profile);
        profileEditButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDetailActivity.this, StudentProfileActivity.class);
            intent.putExtra("studentId", studentId);
            startActivity(intent);
        });

        Button timetableButton = findViewById(R.id.btn_student_timetable);
        timetableButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDetailActivity.this, StudentTimetableAddActivity.class);
            intent.putExtra("studentId", studentId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateUI();
    }

    void populateUI() {
        AppDatabase db = DatabaseHelper.getDatabase(this);
        Student updatedStudent = db.studentDao().getStudentByStudentId(studentId);
        if (updatedStudent != null) {
            TextView studentNameTextView = findViewById(R.id.tv_student_name);
            TextView studentIdTextView = findViewById(R.id.tv_student_id);
            TextView studentClassTextView = findViewById(R.id.tv_student_class);
            studentNameTextView.setText("Student Name: " + updatedStudent.getStudentFullName());
            studentIdTextView.setText("ID: " + updatedStudent.getStudentId());
            studentClassTextView.setText("Class: " + updatedStudent.getStudentClass());
        }
    }
}
