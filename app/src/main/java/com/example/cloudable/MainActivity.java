package com.example.cloudable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    boolean isAdmin;

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
