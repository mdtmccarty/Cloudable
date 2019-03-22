package com.example.cloudable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
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


public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton androidImageButton;
    SharedPreferences sp;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        androidImageButton = findViewById(R.id.webinarArchive);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Toast.makeText(MainPageActivity.this, "It works", Toast.LENGTH_LONG).show();

                ViewGroup parent = findViewById(R.id.mainLayout);
                createImageButton(parent);
            }
        });


        androidImageButton = findViewById(R.id.imageButton2);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                setContentView(R.layout.activity_audio_player);
                //Intent intent = new Intent(this, AudioPlayer.class);
            }
        });
    }

    public void play(View view){

        if (player == null){
            player = MediaPlayer.create(this,R.raw.recording);
        }

        player.start();
    }

    public void intentAdminControl(){
        Intent intent = new Intent(this, AdminControl.class);
        startActivity(intent);
    }

    public void switchViewGroup(){
        TestViewGroup test = new TestViewGroup();
        test.switchViewGroup(findViewById(R.id.mainLayout));
    }

    public void createImageButton(ViewGroup parent){
        ImageButton test = new ImageButton(parent.getContext());
        Log.i("createButton","Attempting to create new button");
        parent.addView(test);
        Log.i("createImageButton", String.valueOf(parent.getTouchables().size()));
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            ViewGroup parent = findViewById(R.id.mainLayout);
            HandleContent folders = new HandleContent();
            folders.createFolder(parent);
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
            switchViewGroup();
            // go Home
        } else if (id == R.id.nav_l1) {
            System.out.println("Going to Level 1!");
            TestViewGroup test = new TestViewGroup();
            test.switchViewGroupBack(findViewById(R.id.evilParent),findViewById(R.id.mainLayout));
            // go to Level 2
        } else if (id == R.id.nav_l2) {
            System.out.println("Going to Level 2!");
            ScrollViewChild test = new ScrollViewChild();
            test.switchScrollView((ViewGroup) findViewById(R.id.).getParent());
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
            Log.i("Admin Control", "Going to Admin Control");
            intentAdminControl();
            // go to Admin Control
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}