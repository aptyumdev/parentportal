package com.vu.parentportal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Student;

public class StudentEditActivity extends AppCompatActivity {
    private EditText studentIdEditText, studentPasswordEditText, studentFullNameEditText, studentClassEditText, studentAgeEditText;
    AppDatabase db;
//    String studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_edit);
        db = DatabaseHelper.getDatabase(this);
        String selectedAction = getIntent().getStringExtra("selectedAction");
        studentIdEditText = findViewById(R.id.et_student_id);
        studentPasswordEditText = findViewById(R.id.et_student_password);
        studentFullNameEditText = findViewById(R.id.et_student_full_name);
        studentClassEditText = findViewById(R.id.et_student_class);
        studentAgeEditText = findViewById(R.id.et_student_age);
        if (selectedAction != null && selectedAction.equals("Profile")) {
            setTitle("Edit Student Profile");
            studentIdEditText.setEnabled(false);
            loadStudentData();
        } else {
            setTitle("Add New Student");
        }
        Button saveStudentButton = findViewById(R.id.btn_save_student_button);

        saveStudentButton.setOnClickListener(v -> {
            String studentId = studentIdEditText.getText().toString().trim();
            String studentPassword = studentPasswordEditText.getText().toString().trim();
            String studentFullName = studentFullNameEditText.getText().toString().trim();
            String studentClass = studentClassEditText.getText().toString().trim();
            String studentAge = studentAgeEditText.getText().toString().trim();

            if (studentId.isEmpty() || studentPassword.isEmpty() || studentFullName.isEmpty() || studentClass.isEmpty() || studentAge.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                if (selectedAction != null && selectedAction.equals("Profile")) {
                    // Update existing student
                    Student updatedStudent = db.studentDao().getStudentByStudentId(studentId);
                    updatedStudent.setStudentId(studentId);
                    updatedStudent.setStudentPassword(studentPassword);
                    updatedStudent.setStudentFullName(studentFullName);
                    updatedStudent.setStudentClass(studentClass);
                    updatedStudent.setStudentAge(studentAge);
                    db.studentDao().updateStudent(updatedStudent);
                    Toast.makeText(this, "Student updated successfully", Toast.LENGTH_LONG).show();
                } else {
                    Student newStudent = new Student();
                    newStudent.setStudentId(studentId);
                    newStudent.setStudentPassword(studentPassword);
                    newStudent.setStudentFullName(studentFullName);
                    newStudent.setStudentClass(studentClass);
                    newStudent.setStudentAge(studentAge);
                    // Check if studentId already exists
                    Student existingStudent = db.studentDao().getStudentByStudentId(studentId);
                    if (existingStudent != null) {
                        Toast.makeText(this, "Student with this ID already exists", Toast.LENGTH_LONG).show();
                        return;
                    }
                    // Add new student
                    db.studentDao().insertStudent(newStudent);
                    Toast.makeText(this, "Student added successfully", Toast.LENGTH_LONG).show();
                }
//                db.studentDao().insertStudent(newStudent);
//                Toast.makeText(this, "Student added successfully", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK);
                finish();
            } catch (android.database.sqlite.SQLiteConstraintException e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
//            db.studentDao().insertStudent(newStudent);
//            Toast.makeText(this, "Student added successfully", Toast.LENGTH_LONG).show();
//
//            setResult(RESULT_OK);
//            finish();
        });
    }

    private void loadStudentData() {
        Student selectedStudentBeforeUpdate = ((ParentPortalApp) getApplication()).getSelectedStudent();
        Student selectedStudent = db.studentDao().getStudentByStudentId(selectedStudentBeforeUpdate.getStudentId());
        if (selectedStudent != null) {
            studentIdEditText.setText(selectedStudent.getStudentId());
            studentPasswordEditText.setText(selectedStudent.getStudentPassword());
            studentFullNameEditText.setText(selectedStudent.getStudentFullName());
            studentClassEditText.setText(selectedStudent.getStudentClass());
            studentAgeEditText.setText(selectedStudent.getStudentAge());
        } else {
            Toast.makeText(this, "No student data available", Toast.LENGTH_LONG).show();
        }
    }
}
