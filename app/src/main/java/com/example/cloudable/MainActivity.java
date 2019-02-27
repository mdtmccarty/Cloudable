package com.example.cloudable;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this is a comment that I am adding
    }
    public void intentLogin(View view) {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
        System.out.println("creating intent!");
    }
}
