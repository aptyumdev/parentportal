package com.vu.parentportal;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
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

public class StudentTimetableAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private String selectedImageName;
    private String studentId;
    private TextView selectedImageTextView;
    private ImageView imagePreviewImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_timetable_add);

        studentId = getIntent().getStringExtra("studentId");
        selectedImageTextView = findViewById(R.id.tv_selected_image);
        imagePreviewImageView = findViewById(R.id.iv_image_preview);
        Button selectImageButton = findViewById(R.id.btn_select_image);
        Button uploadImageButton = findViewById(R.id.btn_upload_image);

        populateUI();

        // Check if the timetable image exists
        File timetableImage = new File(getFilesDir(), studentId + "_timetable.jpg");
        if (timetableImage.exists()) {
            selectedImageTextView.setText("File: " + timetableImage.getName());
            imagePreviewImageView.setImageURI(Uri.fromFile(timetableImage));
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

//        imagePreviewImageView.setOnClickListener(v -> {
//            if (selectedImageUri != null) {
//                Intent intent = new Intent(StudentTimetableAddActivity.this, FullScreenImageActivity.class);
//                intent.putExtra("imageUri", selectedImageUri);
//                startActivity(intent);
//            } else {
//                Toast.makeText(this, "No image to display", Toast.LENGTH_SHORT).show();
//            }
//        });

        imagePreviewImageView.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                Intent intent = new Intent(StudentTimetableAddActivity.this, FullScreenImageActivity.class);
                intent.putExtra("imageUri", selectedImageUri);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No image to display", Toast.LENGTH_SHORT).show();
            }
        });
//        uploadImageButton.setOnClickListener(v -> {
//            if (selectedImageUri != null) {
//                File savedFile = saveImageToLocalStorage(selectedImageUri);
//                if (savedFile != null) {
//                    imagePreviewImageView.setImageURI(Uri.fromFile(savedFile));
//                    Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(this, "No image selected", Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            selectedImageName = getFileName(selectedImageUri);
            selectedImageTextView.setText("Selected File: " + selectedImageName);
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
            File file = new File(getFilesDir(), studentId + "_timetable.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            selectedImageTextView.setText("File: " + file.getName());
            return file;
        } catch (Exception e) {
            Toast.makeText(this, "Error saving image: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
    void populateUI() {
        AppDatabase db = DatabaseHelper.getDatabase(this);
        Student updatedStudent = db.studentDao().getStudentByStudentId(studentId);
        if (updatedStudent != null) {
            TextView studentNameTextView = findViewById(R.id.tv_student_name);
            TextView studentIdTextView = findViewById(R.id.tv_student_id);
            TextView studentClassTextView = findViewById(R.id.tv_student_class);
            studentNameTextView.setText("Student Name: " + updatedStudent.getStudentFullName());
            studentIdTextView.setText("ID: " + updatedStudent.getStudentId());
            studentClassTextView.setText("Class: " + updatedStudent.getStudentClass());
        }

        File timetableImage = new File(getFilesDir(), studentId + "_timetable.jpg");
        if (timetableImage.exists()) {
            selectedImageTextView.setText("File: " + timetableImage.getName());
            imagePreviewImageView.setImageURI(Uri.fromFile(timetableImage));
            selectedImageUri = Uri.fromFile(timetableImage);
        }
    }
}
//package com.vu.parentportal;
//
//import android.annotation.SuppressLint;
//import android.content.ContentResolver;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.pdf.PdfRenderer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.ParcelFileDescriptor;
//import android.provider.OpenableColumns;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//
//public class StudentTimetableAddActivity extends AppCompatActivity {
//    private static final int PICK_PDF_REQUEST = 1;
//    private Uri selectedPdfUri;
//    private String selectedPdfName;
//    private String studentId;
//    private TextView selectedPdfTextView;
//    private ImageView pdfPreviewImageView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student_timetable_add);
//
//        studentId = getIntent().getStringExtra("studentId");
//        selectedPdfTextView = findViewById(R.id.tv_selected_pdf);
//        pdfPreviewImageView = findViewById(R.id.iv_pdf_preview);
//        Button selectPdfButton = findViewById(R.id.btn_select_pdf);
//        Button uploadPdfButton = findViewById(R.id.btn_upload_pdf);
//
//        // Check if the timetable file exists
//        File timetableFile = new File(getFilesDir(), studentId + "_timetable.pdf");
//        if (timetableFile.exists()) {
//            selectedPdfTextView.setText("File: " + timetableFile.getName());
//            renderPdf(timetableFile);
//        }
//
//        selectPdfButton.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("application/pdf");
//            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
//        });
//
//        uploadPdfButton.setOnClickListener(v -> {
//            if (selectedPdfUri != null) {
//                File savedFile = savePdfToLocalStorage(selectedPdfUri);
//                if (savedFile != null) {
//                    renderPdf(savedFile);
//                    Toast.makeText(this, "PDF uploaded successfully", Toast.LENGTH_LONG).show();
//                }
//            } else {
//                Toast.makeText(this, "No PDF selected", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            selectedPdfUri = data.getData();
//            selectedPdfName = getFileName(selectedPdfUri);
//            selectedPdfTextView.setText("Selected File: " + selectedPdfName);
//        }
//    }
//
//    @SuppressLint("Range")
//    private String getFileName(Uri uri) {
//        String fileName = "unknown";
//        ContentResolver contentResolver = getContentResolver();
//        if (uri.getScheme().equals("content")) {
//            try (android.database.Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
//                if (cursor != null && cursor.moveToFirst()) {
//                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            }
//        } else if (uri.getScheme().equals("file")) {
//            fileName = new File(uri.getPath()).getName();
//        }
//        return fileName;
//    }
//
//    private File savePdfToLocalStorage(Uri uri) {
//        try {
//            InputStream inputStream = getContentResolver().openInputStream(uri);
//            File file = new File(getFilesDir(), studentId + "_timetable.pdf");
//            FileOutputStream outputStream = new FileOutputStream(file);
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, length);
//            }
//
//            outputStream.close();
//            inputStream.close();
//
//            selectedPdfTextView.setText("File: " + file.getName());
//            return file;
//        } catch (Exception e) {
//            Toast.makeText(this, "Error saving PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            return null;
//        }
//    }
//
//    private void renderPdf(File pdfFile) {
//        try {
//            ParcelFileDescriptor fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
//            PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);
//            PdfRenderer.Page page = pdfRenderer.openPage(0); // Render the first page
//
//            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
//            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//
//            pdfPreviewImageView.setImageBitmap(bitmap);
//
//            page.close();
//            pdfRenderer.close();
//            fileDescriptor.close();
//        } catch (Exception e) {
//            Toast.makeText(this, "Error rendering PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//}
////package com.vu.parentportal;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.widget.Button;
////import android.widget.TextView;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.vu.parentportal.database.AppDatabase;
////import com.vu.parentportal.database.DatabaseHelper;
////import com.vu.parentportal.models.Student;
////
////public class StudentTimetableAddActivity extends AppCompatActivity {
////    String studentId = "";
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_student_timetable_add);
////        studentId = getIntent().getStringExtra("studentId");
////        populateUI();
////        Button profileEditButton = findViewById(R.id.btn_student_profile);
////        profileEditButton.setOnClickListener(v -> {
////            Intent intent = new Intent(StudentTimetableAddActivity.this, StudentProfileActivity.class);
////            intent.putExtra("studentId", studentId);
////            startActivity(intent);
////        });
////    }
////
////    @Override
////    protected void onResume() {
////        super.onResume();
////        populateUI();
////    }
////
////    void populateUI() {
////        AppDatabase db = DatabaseHelper.getDatabase(this);
////        Student updatedStudent = db.studentDao().getStudentByStudentId(studentId);
////        if (updatedStudent != null) {
////            TextView studentNameTextView = findViewById(R.id.tv_student_name);
////            TextView studentIdTextView = findViewById(R.id.tv_student_id);
////            TextView studentClassTextView = findViewById(R.id.tv_student_class);
////            studentNameTextView.setText("Student Name: " + updatedStudent.getStudentFullName());
////            studentIdTextView.setText("ID: " + updatedStudent.getStudentId());
////            studentClassTextView.setText("Class: " + updatedStudent.getStudentClass());
////        }
////    }
////}
