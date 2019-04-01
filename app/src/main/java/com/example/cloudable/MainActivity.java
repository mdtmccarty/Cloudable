package com.example.cloudable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences login = getSharedPreferences("loginInfo", MODE_PRIVATE);
        EditText userName = findViewById(R.id.editText);
        EditText key = findViewById(R.id.editText2);
        userName.setText(login.getString("userName", ""));
        key.setText(login.getString("key", ""));
        //this is a comment that I am adding
        // ...
    }

    @Override
    public void onStart() {
        super.onStart();
        // Initialize Firebase Auth
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }

    public void intentLogin(View view) throws IOException {
        EditText userName = findViewById(R.id.editText);
        EditText key = findViewById(R.id.editText2);
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        sharedPreferences.edit().putString("userName", userName.getText().toString()).apply();
        sharedPreferences.edit().putString("key", key.getText().toString()).apply();
        String group = readGroup(key);
        if(group == null){
            Toast.makeText(this,"Incorrect Key! Please enter again", Toast.LENGTH_LONG).show();
            //return;
        }
        Intent intent = new Intent(this, MainPageActivity.class);
        intent.putExtra("group", group);
        startActivity(intent);
        System.out.println("creating intent!");
    }

    public void intentNewGroup(View view) {
        Intent intent = new Intent(this, NewGroup.class);
        startActivity(intent);
    }

    public String readGroup(final EditText password) throws IOException {
        final Gson gson = new Gson();
        final File localFile = File.createTempFile("data","json");
        final String[] result = new String[1];
        result[0] = null;

        StorageReference groups = FirebaseStorage.getInstance().getReference();
        groups.child("StorageData.json");
        groups.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        FileReader data = null;
                        try {
                            data = new FileReader(localFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Type token = new TypeToken<ArrayList<FileRecord>>(){}.getType();
                        ArrayList<FileRecord> files = gson.fromJson(data, token);

                        for(FileRecord file: files){
                            if(file.key.equals(password.getText().toString())){
                                result[0] = file.fileName;
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
        });
        return result[0];
    }
}
