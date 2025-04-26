package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.models.Teacher;

public class TeacherActivity extends AppCompatActivity {
    private EditText teacherIdEditText, teacherPasswordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        // Initialize the database using the static constant for the database name
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries() // For simplicity, allow queries on the main thread (not recommended for production)
                .build();

        // Check and insert default teacher record
        if (db.teacherDao().getTeacherByTeacherId("teacher") == null) { // Assuming teacherId=1 for the default record
            Teacher defaultTeacher = new Teacher();
            defaultTeacher.setTeacherId("teacher"); // Use 1 as the ID for the default teacher
            defaultTeacher.setTeacherPassword("123");
            db.teacherDao().insertTeacher(defaultTeacher);
        }

        teacherIdEditText = findViewById(R.id.teacherid);
        teacherPasswordEditText = findViewById(R.id.teacherpassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String teacherIdInput = teacherIdEditText.getText().toString().trim();
            String teacherPasswordInput = teacherPasswordEditText.getText().toString().trim();

            // Validate for blank fields
            if (teacherIdInput.isEmpty() || teacherPasswordInput.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                return;
            }

            // Validate credentials
            try {
//                int teacherIdInput = Integer.parseInt(teacherIdInput);
                Teacher teacher = db.teacherDao().getTeacherByTeacherId(teacherIdInput);

                if (teacher != null && teacher.getTeacherPassword().equals(teacherPasswordInput)) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TeacherActivity.this, StudentListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Login Failed: Invalid credentials", Toast.LENGTH_LONG).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Login Failed: Invalid Teacher ID", Toast.LENGTH_LONG).show();
            }
        });
    }
}
