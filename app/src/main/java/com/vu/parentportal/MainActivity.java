package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button teacherButton = findViewById(R.id.btn_teacher);
        teacherButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
            startActivity(intent);
        });
        Button parentButton = findViewById(R.id.btn_parent);
        parentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ParentActivity.class);
            startActivity(intent);
        });
    }
}
