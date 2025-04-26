package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {
    String studentId = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        TextView studentNameTextView = findViewById(R.id.tv_student_name);
        TextView studentIdTextView = findViewById(R.id.tv_student_id);
        TextView studentClassTextView = findViewById(R.id.tv_student_class);

        Intent intentStudent = getIntent();
        String studentName = intentStudent.getStringExtra("studentName");
        studentId = intentStudent.getStringExtra("studentId");
        String studentClass = intentStudent.getStringExtra("studentClass");

        studentNameTextView.setText("Student Name: " + studentName);
        studentIdTextView.setText("ID: " + studentId);
        studentClassTextView.setText("Class: " + studentClass);

        Button profileButton = findViewById(R.id.btn_student_profile);
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDetailActivity.this, TeacherActivity.class);
            startActivity(intent);
        });

    }
}
