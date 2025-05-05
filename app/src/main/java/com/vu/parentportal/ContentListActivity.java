package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Content;
import com.vu.parentportal.models.Student;

import java.util.List;

public class ContentListActivity extends AppCompatActivity {
    Student selectedStudent;
    AppDatabase db;
    List<Content> contentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);
        selectedStudent = ((ParentPortalApp) getApplication()).getSelectedStudent();
        String selectedAction = getIntent().getStringExtra("selectedAction");
        setTitle(selectedAction + " List");
        ListView listView = findViewById(R.id.list_view_content);
        db = DatabaseHelper.getDatabase(this);
        populateUI();
        if (selectedAction.equalsIgnoreCase("Feedback")) {
            contentList = db.contentDao().getContentsByContentTypeAndUserId(selectedAction, selectedStudent.getStudentId());
        }else {
            contentList = db.contentDao().getContentsByContentType(selectedAction);
        }
        ContentAdapter adapter = new ContentAdapter(this, contentList);
        listView.setAdapter(adapter);
    }

    void populateUI() {
        db = DatabaseHelper.getDatabase(this);
        Student updatedStudent = db.studentDao().getStudentByStudentId(selectedStudent.getStudentId());
        if (updatedStudent != null) {
            TextView studentNameTextView = findViewById(R.id.tv_student_name);
            TextView studentIdTextView = findViewById(R.id.tv_student_id);
            TextView studentClassTextView = findViewById(R.id.tv_student_class);
            studentNameTextView.setText("Student Name: " + updatedStudent.getStudentFullName());
            studentIdTextView.setText("ID: " + updatedStudent.getStudentId());
            studentClassTextView.setText("Class: " + updatedStudent.getStudentClass());
        }
    }
}
