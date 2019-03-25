package com.example.cloudable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Activity for creating a New Group and starting that groups folders and setting up Admin Access.
 */
public class NewGroup extends AppCompatActivity {
    private StorageReference cloudable;
    private String username, key;
    private FirebaseAuth mAuth;

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
        List<FileRecord> files = Arrays.asList(data);

        EditText newGroup = findViewById(R.id.groupName);
        EditText newKey = findViewById(R.id.newKey);
        EditText verifyKey = findViewById(R.id.verifyKey);
        EditText newAdminKey = findViewById(R.id.adminKey);
        EditText verifyAdminKey = findViewById(R.id.verifyAdminKey);

        if(!(newKey.getText().toString().equals(verifyKey.getText().toString())) ||
                !(newAdminKey.getText().toString().equals(verifyAdminKey.getText().toString()))){
            Toast.makeText(this, "Your Key or Admin Key do not match. Please re-enter"
            , Toast.LENGTH_LONG).show();
            return;
        }

        for (FileRecord file: files) {
            if(newKey.getText().toString().equals(file.fileName)){
                Toast.makeText(this, "Please make a different Key", Toast.LENGTH_LONG).show();
                return;
            }
        }

        files.add(new FileRecord(files.size() + 1, newGroup.getText().toString(),
                "Folder", newGroup.getText().toString(), newKey.getText().toString(),
                newAdminKey.getText().toString()));

        new Gson().toJson(files, new FileWriter(localFile));
        cloudable.putFile(Uri.fromFile(localFile));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
