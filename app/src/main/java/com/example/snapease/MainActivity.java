package com.example.snapease;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    ImageView lastSavedimageView, lastSavedExternal, lastSavedSDCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.click_event), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadImageFromInternalStorage();
        loadImageFromExternalStorage();
        loadImageFromExternalSDCard();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button cameraSettingsButton = findViewById(R.id.cameraSettingsButton);
        lastSavedimageView= findViewById(R.id.lastSavedimageView);
        cameraSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Actions to perform when the button is clicked
                // For example, display a toast message
                Intent intent = new Intent(MainActivity.this, settings_one.class);
                startActivity(intent);
            }
        });

        Button takePicButton = findViewById(R.id.takePicButton);
        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, take_picture.class);
                startActivity(intent);
            }
        });
        Log.d(LOG_TAG, "onCreate");
    }
    private void loadImageFromInternalStorage()
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File path = cw.getDir("imageDir", Context.MODE_PRIVATE);
        try {
            File f=new File(path, "last_picture.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.lastSavedimageView);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    private void loadImageFromExternalStorage()
    {
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), Environment.DIRECTORY_PICTURES);
        File pathPrimaryExternalStorage = externalStorageVolumes[0];
        try {
            File fileExternal = new File(pathPrimaryExternalStorage, "last_picture.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(fileExternal));
            ImageView img=(ImageView)findViewById(R.id.lastSavedExternal);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void loadImageFromExternalSDCard()
    {
        File[] externalStorageVolumes = ContextCompat.getExternalFilesDirs(getApplicationContext(), Environment.DIRECTORY_PICTURES);
        File pathSecondaryExternalStorage = externalStorageVolumes[1];
        try {
            File fileExternalSDCard = new File(pathSecondaryExternalStorage, "last_picture.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(fileExternalSDCard));
            ImageView img=(ImageView)findViewById(R.id.lastSavedSDCard);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
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