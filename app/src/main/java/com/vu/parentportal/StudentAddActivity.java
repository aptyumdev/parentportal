package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Student;

public class StudentAddActivity extends AppCompatActivity {
    private EditText studentIdEditText, studentPasswordEditText, studentFullNameEditText, studentClassEditText, studentAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);

        studentIdEditText = findViewById(R.id.et_student_id);
        studentPasswordEditText = findViewById(R.id.et_student_password);
        studentFullNameEditText = findViewById(R.id.et_student_full_name);
        studentClassEditText = findViewById(R.id.et_student_class);
        studentAgeEditText = findViewById(R.id.et_student_age);
        Button addStudentButton = findViewById(R.id.btn_save_student_button);

//        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME)
//                .allowMainThreadQueries()
//                .build();

        AppDatabase db = DatabaseHelper.getDatabase(this);

        addStudentButton.setOnClickListener(v -> {
            String studentId = studentIdEditText.getText().toString().trim();
            String studentPassword = studentPasswordEditText.getText().toString().trim();
            String studentFullName = studentFullNameEditText.getText().toString().trim();
            String studentClass = studentClassEditText.getText().toString().trim();
            String studentAge = studentAgeEditText.getText().toString().trim();

            if (studentId.isEmpty() || studentPassword.isEmpty() || studentFullName.isEmpty() || studentClass.isEmpty() || studentAge.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                return;
            }
            // Check if studentId already exists
            Student existingStudent = db.studentDao().getStudentByStudentId(studentId);
            if (existingStudent != null) {
                Toast.makeText(this, "Student with this ID already exists", Toast.LENGTH_LONG).show();
                return;
            }

            Student newStudent = new Student();
            newStudent.setStudentId(studentId);
            newStudent.setStudentPassword(studentPassword);
            newStudent.setStudentFullName(studentFullName);
            newStudent.setStudentClass(studentClass);
            newStudent.setStudentAge(studentAge);
            try {
                db.studentDao().insertStudent(newStudent);
                Toast.makeText(this, "Student added successfully", Toast.LENGTH_LONG).show();
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
}
