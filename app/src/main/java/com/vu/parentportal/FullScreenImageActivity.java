package com.vu.parentportal;

import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenImageActivity extends AppCompatActivity {
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView imageView;
    private Matrix matrix = new Matrix();
    private float scale = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        imageView = findViewById(R.id.fullscreen_image_view);

        // Get the image URI from the Intent
        Intent intent = getIntent();
        Uri imageUri = intent.getParcelableExtra("imageUri");
        if (imageUri != null) {
            imageView.setImageURI(imageUri);
        }

        // Initialize ScaleGestureDetector
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(1f, Math.min(scale, 5f)); // Limit zoom levels
            matrix.setScale(scale, scale, detector.getFocusX(), detector.getFocusY());
            imageView.setImageMatrix(matrix);
            return true;
        }
    }
}