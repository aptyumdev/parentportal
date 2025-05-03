package com.vu.parentportal;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vu.parentportal.database.AppDatabase;
import com.vu.parentportal.database.DatabaseHelper;
import com.vu.parentportal.models.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class StudentActionActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private String selectedImageName;
    private String selectedAction;
    private Student student;
    private String imageFileName;
    private ImageView imagePreviewImageView;
    Button selectImageButton;
    Button uploadImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_action);
        student = ((ParentPortalApp) getApplication()).getSelectedStudent();
        selectedAction = getIntent().getStringExtra("selectedAction");
        setTitle("Student " + selectedAction);
        imageFileName = student.getStudentId() + "_" + selectedAction + ".jpg";
        imagePreviewImageView = findViewById(R.id.iv_image_preview);
        selectImageButton = findViewById(R.id.btn_select_image);
        uploadImageButton = findViewById(R.id.btn_upload_image);

        populateUI();

        // Check if the image exists
        File imgeFile = new File(getFilesDir(), imageFileName);
        if (imgeFile.exists()) {
            imagePreviewImageView.setImageURI(Uri.fromFile(imgeFile));
        }

        selectImageButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
        });

        uploadImageButton.setOnClickListener(v -> {
            if (selectedImageUri == null) {
                Toast.makeText(this, "Please select an image before uploading.", Toast.LENGTH_LONG).show();
                return;
            }

            // Validate file type
            String fileType = getContentResolver().getType(selectedImageUri);
            if (fileType == null || (!fileType.equals("image/jpeg") && !fileType.equals("image/png"))) {
                Toast.makeText(this, "Invalid file type. Please select a JPG or PNG image.", Toast.LENGTH_LONG).show();
                return;
            }

            // Optional: Validate file size
            try (InputStream inputStream = getContentResolver().openInputStream(selectedImageUri)) {
                if (inputStream != null && inputStream.available() > 5 * 1024 * 1024) { // 5 MB limit
                    Toast.makeText(this, "File size exceeds 5 MB. Please select a smaller image.", Toast.LENGTH_LONG).show();
                    return;
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error validating file size: " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            // Save the image if validation passes
            File savedFile = saveImageToLocalStorage(selectedImageUri);
            if (savedFile != null) {
                imagePreviewImageView.setImageURI(Uri.fromFile(savedFile));
                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
            }
        });
        imagePreviewImageView.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                Intent intent = new Intent(StudentActionActivity.this, FullScreenImageActivity.class);
                intent.putExtra("imageUri", selectedImageUri);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No image to display", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            selectedImageName = getFileName(selectedImageUri);
//            selectedImageTextView.setText("Selected File: " + selectedImageName);
            imagePreviewImageView.setImageURI(selectedImageUri);
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String fileName = "unknown";
        ContentResolver contentResolver = getContentResolver();
        if (uri.getScheme().equals("content")) {
            try (android.database.Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        } else if (uri.getScheme().equals("file")) {
            fileName = new File(uri.getPath()).getName();
        }
        return fileName;
    }

    private File saveImageToLocalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File file = new File(getFilesDir(), imageFileName);
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();
            return file;
        } catch (Exception e) {
            Toast.makeText(this, "Error saving image: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    void populateUI() {
        AppDatabase db = DatabaseHelper.getDatabase(this);
        Student updatedStudent = db.studentDao().getStudentByStudentId(student.getStudentId());
        if (updatedStudent != null) {
            TextView studentNameTextView = findViewById(R.id.tv_student_name);
            TextView studentIdTextView = findViewById(R.id.tv_student_id);
            TextView studentClassTextView = findViewById(R.id.tv_student_class);
            studentNameTextView.setText("Student Name: " + updatedStudent.getStudentFullName());
            studentIdTextView.setText("ID: " + updatedStudent.getStudentId());
            studentClassTextView.setText("Class: " + updatedStudent.getStudentClass());
        }

        File imageFile = new File(getFilesDir(), imageFileName);
        if (imageFile.exists()) {
            imagePreviewImageView.setImageURI(Uri.fromFile(imageFile));
            selectedImageUri = Uri.fromFile(imageFile);
        }

        if(((ParentPortalApp) getApplication()).getUserType().equalsIgnoreCase("Teacher")) {
            selectImageButton.setVisibility(View.VISIBLE);
            uploadImageButton.setVisibility(View.VISIBLE);
        } else {
            selectImageButton.setVisibility(View.GONE);
            uploadImageButton.setVisibility(View.GONE);
        }

    }
}
