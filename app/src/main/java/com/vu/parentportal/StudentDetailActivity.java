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
    Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        student = ((ParentPortalApp) getApplication()).getSelectedStudent();
        populateUI();
        Button profileEditButton = findViewById(R.id.btn_student_profile);
        profileEditButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentDetailActivity.this, StudentEditActivity.class);
            intent.putExtra("selectedAction", "Profile");
            startActivity(intent);
        });
        Button timetableButton = findViewById(R.id.btn_student_timetable);
        timetableButton.setOnClickListener(v -> {
            goToStudentActionActivity("Timetable");
        });
        Button syllabusButton = findViewById(R.id.btn_student_syllabus);
        syllabusButton.setOnClickListener(v -> {
            goToStudentActionActivity("Syllabus");
        });
        Button resultButton = findViewById(R.id.btn_student_results);
        resultButton.setOnClickListener(v -> {
            goToStudentActionActivity("Result");
        });

        Button datesheetButton = findViewById(R.id.btn_student_date_sheet);
        datesheetButton.setOnClickListener(v -> {
            goToStudentActionActivity("Datesheet");
        });
        Button feevoucherButton = findViewById(R.id.btn_student_fee_voucher);
        feevoucherButton.setOnClickListener(v -> {
            goToStudentActionActivity("FeeVoucher");
        });
    }

    private void goToStudentActionActivity(String action) {
        Intent intent = new Intent(StudentDetailActivity.this, StudentActionActivity.class);
        intent.putExtra("selectedAction", action);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateUI();
    }

    void populateUI() {
        AppDatabase db = DatabaseHelper.getDatabase(this);
        Student updatedStudent = db.studentDao().getStudentByStudentId(student.getStudentId());
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
