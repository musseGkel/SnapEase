package com.example.snapease;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class settings_one extends AppCompatActivity {
    private static final String LOG_TAG = settings_one.class.getSimpleName();
    EditText saveInputText;
    Button saveButton, readTextButton;
    TextView readTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_one);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.click_event), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button eventTestButton = findViewById(R.id.eventTestButton);
        TextView clickEvent= findViewById(R.id.clickEvent);
        saveInputText = findViewById(R.id.saveInputText);
        saveButton=findViewById(R.id.saveButton);
        readTextButton=findViewById(R.id.readTextButton);
        readTextView=findViewById(R.id.readTextView);

        eventTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.setText("Click Event");
            }
        });

        eventTestButton.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        clickEvent.setText("Long Click Event");
                        return true;
                    }
                }
        );


        Button settingsTwoButton = findViewById(R.id.settingsTwoButton);
        settingsTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(settings_one.this, settings_two.class);
                startActivity(intent);
            }
        });

        Button returnButton = findViewById(R.id.returnButton);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Log.d(LOG_TAG, "onCreate");

        Context context = getApplicationContext();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText= String.valueOf(saveInputText.getText());
                System.out.println("inputText: "+ inputText);
                System.out.println("inputText.length: "+ inputText.length());
                if(inputText.length()>0){
                    try {
                        saveTextToStorage(inputText, context);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        readTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String readText= readTextFromStorage(context);
                    if(readText.length()>0){
                        readTextView.setText(readText);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void saveTextToStorage(String data, Context context) throws IOException {
        File path = context.getFilesDir();
        File file = new File(path, "snapEase.txt");
        FileOutputStream stream = new FileOutputStream(file);
        try{
            stream.write(data.getBytes());
        }  finally{
            stream.close();
        }
    }

    private String readTextFromStorage(Context context) throws IOException {
        File path = context.getFilesDir();
        File file = new File(path, "snapEase.txt");
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream stream = new FileInputStream(file);
        try{
            stream.read(bytes);
        }  finally{
            stream.close();
        }
        String contents = new String(bytes);
        return contents;
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