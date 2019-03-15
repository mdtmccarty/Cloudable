package com.example.cloudable;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton androidImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        androidImageButton = (ImageButton) findViewById(R.id.webinarArchive);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v){
               Toast.makeText(MainPageActivity.this, "It works", Toast.LENGTH_LONG).show();

               ViewGroup parent = findViewById(R.id.mainLayout);
               createImageButton(parent);
           }
        });
    }

    public void createImageButton(ViewGroup parent){

        ImageButton test = new ImageButton(parent.getContext());
        Log.i("createButton","Attempting to create new button");
        parent.addView(test);
        Log.i("createImageButton", String.valueOf(parent.getTouchables().size()));
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //TODO Handle Refresh here
        if (id == R.id.action_settings) {
            System.out.println("Refreshing...");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //TODO handle navigation through folders here
        if (id == R.id.nav_Home) {
            System.out.println("Going Home!");
            // go Home
        } else if (id == R.id.nav_l1) {
            System.out.println("Going to Level 1!");
            // go to Level 2
        } else if (id == R.id.nav_l2) {
            System.out.println("Going to Level 2!");
            // go to Level 2
        } else if (id == R.id.nav_l3) {
            System.out.println("Going to Level 3!");
            // go to Level 3
        } else if (id == R.id.nav_l4) {
            System.out.println("Going to Level 4!");
            // go to Level 4
        } else if (id == R.id.nav_l5) {
            System.out.println("Going to Level 5!");
            // go to Level 5
        } else if (id == R.id.nav_wa) {
            System.out.println("Going to Webinar Archive!");
            // go to Webinar Archive
        } else if (id == R.id.nav_ac) {
            System.out.println("Going to Admin Control!");
            // go to Admin Control
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
