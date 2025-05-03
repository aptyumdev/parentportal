package com.vu.parentportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {
    private static final int ADD_STUDENT_REQUEST = 1;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> studentNames = new ArrayList<>();
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        listView = findViewById(R.id.list_view_student_list);
        Button addStudentButton = findViewById(R.id.btn_add_new_student);
        db = DatabaseHelper.getDatabase(this);
        loadStudentList();
        addStudentButton.setOnClickListener(v -> {
            Intent intent = new Intent(StudentListActivity.this, StudentEditActivity.class);
            intent.putExtra("selectedAction", "Add");
            startActivityForResult(intent, ADD_STUDENT_REQUEST);
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Student selectedStudent = db.studentDao().getAllStudents().get(position);
            ((ParentPortalApp) getApplication()).setSelectedStudent(selectedStudent);
            Intent intent = new Intent(StudentListActivity.this, StudentDetailActivity.class);
            startActivity(intent);
        });
    }

    private void loadStudentList() {
        List<Student> studentList = db.studentDao().getAllStudents();
        studentNames.clear();
        for (Student student : studentList) {
            studentNames.add(student.getStudentFullName());
        }
        if (adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentNames);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_STUDENT_REQUEST && resultCode == RESULT_OK) {
            loadStudentList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudentList();
    }
}
