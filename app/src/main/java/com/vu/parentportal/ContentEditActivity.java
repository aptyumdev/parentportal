package com.vu.parentportal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Content;
import com.vu.parentportal.models.Student;
public class ContentEditActivity extends AppCompatActivity {
    String selectedAction;
    Student selectedStudent;
    ParentPortalApp app;
    AppDatabase db;
    Content selectedContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_edit);
        EditText contentTitleEditText = findViewById(R.id.et_content_title);
        EditText contentDetailEditText = findViewById(R.id.et_content_detail);
        Button saveButton = findViewById(R.id.btn_save);
        app = (ParentPortalApp) getApplication();
        db = DatabaseHelper.getDatabase(this);
        selectedContent = app.getSelectedContent();
        selectedStudent = app.getSelectedStudent();
        selectedAction = getIntent().getStringExtra("selectedAction");
        String studentId = selectedStudent.getStudentId();
        setTitle("Add New " + selectedAction);
        if (selectedContent != null) {
            contentTitleEditText.setText(selectedContent.getContentTitle());
            contentDetailEditText.setText(selectedContent.getContentDetail());
        }
        populateUI(studentId);
        saveButton.setOnClickListener(v -> {
            String contentTitle = contentTitleEditText.getText().toString().trim();
            String contentDetail = contentDetailEditText.getText().toString().trim();
            if (contentTitle.isEmpty() || contentDetail.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                return;
            }
            Content newContent = new Content();
            newContent.setContentTitle(contentTitle);
            newContent.setContentDetail(contentDetail);
            newContent.setContentType(selectedAction);
            newContent.setUserId(studentId); // Assuming userId is available in the app instance
            db.contentDao().insertContent(newContent);
            Toast.makeText(this, selectedAction + " added successfully", Toast.LENGTH_LONG).show();
            finish();
        });
    }

    void populateUI(String studentId) {
        Student updatedStudent = db.studentDao().getStudentByStudentId(studentId);
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
