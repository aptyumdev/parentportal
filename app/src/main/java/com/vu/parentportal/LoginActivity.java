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

public class LoginActivity extends AppCompatActivity {
    private EditText userIdEditText, userPasswordEditText;
    private Button loginButton;

    String userType;
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userType = ((ParentPortalApp) getApplication()).getUserType();
        setTitle(userType + " Login");
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        userIdEditText = findViewById(R.id.user_id);
        userPasswordEditText = findViewById(R.id.user_password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String userIdInput = userIdEditText.getText().toString().trim();
            String userPasswordInput = userPasswordEditText.getText().toString().trim();
            if (userIdInput.isEmpty() || userPasswordInput.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                return;
            }
            String userType = ((ParentPortalApp) getApplication()).getUserType();
            if (userType.equals("Teacher")) {
                loginAsTeacher(userIdInput, userPasswordInput);
            }
            else {
                loginAsParent(userIdInput, userPasswordInput);
            }

        });
    }

    private void loginAsParent(String userIdInput, String userPasswordInput) {
        // Implement parent login logic here
        // For example, check the credentials against the database
        // If successful, navigate to the parent dashboard
        Toast.makeText(this, "Parent login not implemented yet", Toast.LENGTH_LONG).show();
        // Intent intent = new Intent(LoginActivity.this, ParentDashboardActivity.class);
        // startActivity(intent);
    }

    private void loginAsTeacher(String userIdInput,  String userPasswordInput) {
        Teacher teacher = db.teacherDao().getTeacherByTeacherId(userIdInput);
        if (teacher != null && teacher.getTeacherPassword().equals(userPasswordInput)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, StudentListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Login Failed: Invalid credentials", Toast.LENGTH_LONG).show();
        }
    }
}
