package com.vu.parentportal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ParentActivity extends AppCompatActivity {
    private EditText studentIdEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        studentIdEditText = findViewById(R.id.et_student_id);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(v -> {
            String studentId = studentIdEditText.getText().toString();
            String studentPassword = passwordEditText.getText().toString();
            Toast.makeText(this, "Student ID: " + studentId + "\nPassword: " + studentPassword, Toast.LENGTH_LONG).show();
        });
    }
}