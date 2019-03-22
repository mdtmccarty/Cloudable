package com.example.cloudable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Activity for creating a New Group and starting that groups folders and setting up Admin Access.
 */
public class NewGroup extends AppCompatActivity {
    private StorageReference cloudable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cloudable = FirebaseStorage.getInstance().getReference("StorageData.json");
    }

    /**
     * Takes the information from Activity to check, save, and ultimately go back to the login page.
     * @param view All Data on Current Activity
     */
    public void submitButton(View view) throws IOException {
        File localFile = File.createTempFile("data","json");
        cloudable.getFile(localFile);

        JsonReader myJSON = new JsonReader(new FileReader(localFile));
        FileRecord[] data = new Gson().fromJson(String.valueOf(myJSON), FileRecord[].class);

        EditText newKey = findViewById(R.id.newKey);

        for (FileRecord file: data) {
            if(newKey.getText().toString().equals(file.fileName)){
                Toast.makeText(this, "Please make a different Key", Toast.LENGTH_LONG);
                return;
            }
        }


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
