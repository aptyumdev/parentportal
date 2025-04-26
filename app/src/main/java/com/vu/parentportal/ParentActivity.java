package com.vu.parentportal;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ParentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent); // This line sets the layout

        // Set the title (optional, but recommended)
//        setTitle(R.string.parent_activity_title);
    }
}