package com.vu.parentportal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Student;

public class StudentProfileActivity extends AppCompatActivity {
    private EditText studentIdEditText, studentPasswordEditText, studentFullNameEditText, studentClassEditText, studentAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        studentIdEditText = findViewById(R.id.et_student_id);
        studentPasswordEditText = findViewById(R.id.et_student_password);
        studentFullNameEditText = findViewById(R.id.et_student_full_name);
        studentClassEditText = findViewById(R.id.et_student_class);
        studentAgeEditText = findViewById(R.id.et_student_age);
        Button saveButton = findViewById(R.id.btn_save_student_button);

        // Get the studentId from the intent
        String studentId = getIntent().getStringExtra("studentId");

        // Initialize the database
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME)
//                .allowMainThreadQueries()
//                .build();
        AppDatabase db = DatabaseHelper.getDatabase(this);

        // Query the student details using the studentId
        Student student = db.studentDao().getStudentByStudentId(studentId);

        if (student != null) {
            // Populate the text fields with the student details
            studentIdEditText.setText(student.getStudentId());
            studentPasswordEditText.setText(student.getStudentPassword());
            studentFullNameEditText.setText(student.getStudentFullName());
            studentClassEditText.setText(student.getStudentClass());
            studentAgeEditText.setText(student.getStudentAge());
        }

        saveButton.setOnClickListener(v -> {
            if (student != null) {
                // Retrieve updated values from the text fields
                student.setStudentPassword(studentPasswordEditText.getText().toString().trim());
                student.setStudentFullName(studentFullNameEditText.getText().toString().trim());
                student.setStudentClass(studentClassEditText.getText().toString().trim());
                student.setStudentAge(studentAgeEditText.getText().toString().trim());

                // Update the student record in the database
                db.studentDao().updateStudent(student);

                Toast.makeText(this, "Student details updated successfully", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Error: Student not found", Toast.LENGTH_LONG).show();
            }
        });
    }
}
