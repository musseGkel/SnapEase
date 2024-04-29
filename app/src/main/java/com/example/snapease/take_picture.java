package com.example.snapease;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class take_picture extends AppCompatActivity {
    private static final String LOG_TAG = take_picture.class.getSimpleName();
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 101;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 102;
    private ImageView imageView;
    private Bitmap imageBitmap;

    String imageDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_take_picture);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.click_event), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(LOG_TAG, "onCreate");

        imageView = findViewById(R.id.imageView);
        // Request camera permissions if not granted

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Context context = getApplicationContext();
                CharSequence text = "Click Event!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast =
                        Toast.makeText(context, imageDate, duration);
                toast.show();
                return true;
            }
        });

        Button takeAPicButton= findViewById(R.id.takeAPicButton);
        takeAPicButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(take_picture.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(take_picture.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSION);
                        } else {
                            dispatchTakePictureIntent();
                        }
                    }
                }
        );
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                 imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                imageDate = dateFormat.format(new Date());
            } else {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getImageDateFromMetadata(Bitmap imageBitmap) {
        try {
            // Create a temporary file to save the Bitmap
            File tempFile = createTempFile(imageBitmap);

            // Create an ExifInterface instance to read the metadata
            ExifInterface exifInterface = new ExifInterface(tempFile.getAbsolutePath());

            // Get the date and time the image was taken
            String dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);

            // Format the date and time if available
            if (dateTime != null) {
                // dateTime format may vary, you may need to parse it accordingly
                return dateTime;
            } else {
                return "Date not available";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading metadata";
        }
    }

    private File createTempFile(Bitmap bitmap) throws IOException {
        // Create a temporary file in the app's cache directory
        File tempFile = new File(getApplicationContext().getCacheDir(), "temp_image.jpg");

        // Write the Bitmap to the temporary file
        FileOutputStream out = new FileOutputStream(tempFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        out.flush();
        out.close();

        return tempFile;
    }

    public void takeAPic(View v){
        System.out.println("Taking a PIC!!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }
}