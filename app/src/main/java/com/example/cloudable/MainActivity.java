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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    boolean isAdmin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isAdmin = false;
        SharedPreferences login = getSharedPreferences("loginInfo", MODE_PRIVATE);
        EditText userName = findViewById(R.id.editText);
        EditText key = findViewById(R.id.editText2);
        userName.setText(login.getString("userName", ""));
        key.setText(login.getString("key", ""));
        //this is a comment that I am adding
        // ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
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

    public void intentLogin(View view) {
        isAdmin = true;
        EditText userName = findViewById(R.id.editText);
        EditText key = findViewById(R.id.editText2);
        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
        sharedPreferences.edit().putString("userName", userName.getText().toString()).apply();
        sharedPreferences.edit().putString("key", key.getText().toString()).apply();
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
        System.out.println("creating intent!");
    }

    public void intentNewGroup(View view) {
        Intent intent = new Intent(this, NewGroup.class);
        startActivity(intent);
    }
}
