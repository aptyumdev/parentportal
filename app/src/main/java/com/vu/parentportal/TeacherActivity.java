package com.vu.parentportal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherActivity extends AppCompatActivity {
    private EditText teacherIdEditText;
    private EditText teacherPasswordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher); // This line sets the layout

        // Set the title (optional, but recommended)
        setTitle(R.string.teacher_activity_title);

        // Initialize views
        teacherIdEditText = findViewById(R.id.teacherid);
        teacherPasswordEditText = findViewById(R.id.teacherpassword);
        loginButton = findViewById(R.id.loginButton);

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText fields
                String teacherId = teacherIdEditText.getText().toString();
                String teacherPassword = teacherPasswordEditText.getText().toString();

                // You can now use the teacherId and teacherPassword variables
                // For example, you can display them in a Toast
                String message = "Teacher ID: " + teacherId + "\nPassword: " + teacherPassword;
                Toast.makeText(TeacherActivity.this, message, Toast.LENGTH_LONG).show();

                // Here, you would typically perform your login logic
                // (e.g., check against a database, send to a server, etc.)
            }
        });
    }
}