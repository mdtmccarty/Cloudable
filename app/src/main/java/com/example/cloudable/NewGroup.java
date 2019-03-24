package com.example.cloudable;

import android.content.Intent;
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
    private String username, key;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //cloudable = FirebaseStorage.getInstance().getReference("StorageData.json");
    }

    /**
     * Takes the information from Activity to check, save, and ultimately go back to the login page.
     * @param view All Data on Current Activity
     */
    public void submitButton(View view) throws IOException {
//        File localFile = File.createTempFile("data","json");
//        cloudable.getFile(localFile);
//
//        JsonReader myJSON = new JsonReader(new FileReader(localFile));
//        FileRecord[] data = new Gson().fromJson(String.valueOf(myJSON), FileRecord[].class);
//
        EditText newKey = findViewById(R.id.newKey);
//
//        for (FileRecord file: data) {
//            if(newKey.getText().toString().equals(file.fileName)){
//                Toast.makeText(this, "Please make a different Key", Toast.LENGTH_LONG);
//                return;
//            }
//        }

        username = "dylanwaner93@gmail.com";
        key = newKey.getText().toString().trim();
        if (key.isEmpty()){
            newKey.setError("Key is required");
            newKey.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username, key).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("NewGroup", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI(user);
                }}});

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
