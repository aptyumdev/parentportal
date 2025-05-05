package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Teacher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertDefaultTeacher();
        Button teacherButton = findViewById(R.id.btn_teacher);
        teacherButton.setOnClickListener(v -> {
            goToLoginActivity("Teacher");
        });
        Button parentButton = findViewById(R.id.btn_parent);
        parentButton.setOnClickListener(v -> {
            goToLoginActivity("Parent");
        });
    }
    private void goToLoginActivity(String userType) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        ((ParentPortalApp) getApplication()).setUserType(userType);
        startActivity(intent);
    }
    private void insertDefaultTeacher(){
        AppDatabase db = DatabaseHelper.getDatabase(this);
        if (db.teacherDao().getTeacherByTeacherId("teacher") == null) { // Assuming teacherId=1 for the default record
            Teacher defaultTeacher = new Teacher();
            defaultTeacher.setTeacherId("teacher"); // Use 1 as the ID for the default teacher
            defaultTeacher.setTeacherPassword("123");
            db.teacherDao().insertTeacher(defaultTeacher);
        }
    }
}
