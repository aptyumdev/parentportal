package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        setTitle(R.string.main_activity_title);

        Button teacherButton = findViewById(R.id.btn_teacher);
        Button parentButton = findViewById(R.id.btn_parent);

        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the TeacherActivity
                Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
                startActivity(intent);
            }
        });

        parentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ParentActivity
                Intent intent = new Intent(MainActivity.this, ParentActivity.class);
                startActivity(intent);
            }
        });
    }
}