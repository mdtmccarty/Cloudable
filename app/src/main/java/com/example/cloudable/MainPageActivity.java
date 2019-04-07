package com.example.cloudable;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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


public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton androidImageButton;
    SharedPreferences sp;
    ArrayList<ParsedDirectory> masterList;
    HandleContent master;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    File localFile;
    final String FILE_NAME = "audioFiles.mp3";
    String getKey;
    String getAdminKey;
    Context context;
    Gson gson = new Gson();
    Type token = new TypeToken<ArrayList<FileRecord>>(){}.getType();
    ArrayList<ParsedDirectory> parsedDirectories;

    //TODO set this groupName equal to the actual Group Name
    public String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mStorageRef = FirebaseStorage.getInstance().getReference();
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

        Bundle extras = getIntent().getExtras();
        groupName = extras.getString("group","");
        getKey = extras.getString("key", "");
        getAdminKey = extras.getString("admin", "");

        context = this;

        //TODO parse each JSON file into ParsedDirectory Objects, create an ArrayList of ParsedDirectory(s)
        //TODO starting with the Main directory folder, then it's first child, then it's first child's first child,
        //TODO then if it's first child's first child doesn't have any children, put it's first child's second child and so on
        //TODO i.e. 1, 1a, 1ai, 1aii, 1aiii, 1b, 1bi, 1bii, 1c, 1d, 1di, 1dii...
//        if (controller.getDirectories() != null) {
            //masterList = readGroup();

            //First initialization of main screen
            if (masterList != null) {
                master = new HandleContent(masterList.get(0), this);
            }
            //populate all folders.
            //master.recursDirectory(masterList, this);
//        }


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
                Intent intent = new Intent(MainPageActivity.this, AudioPlayer.class);
                startActivity(intent);
            }
        });
    }

    public void intentAdminControl(){
        Intent intent = new Intent(this, AdminControl.class);
        intent.putExtra("group", groupName);
        intent.putExtra("key", getKey);
        intent.putExtra("admin", getAdminKey);
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
            System.out.println("Refreshing...");
            masterList = readGroup();
            master = new HandleContent(masterList.get(0),this);
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

    public ArrayList<ParsedDirectory> readGroup(){
            parsedDirectories = new ArrayList<>();
            recurseDirectories(mStorageRef, groupName, groupName);
        return parsedDirectories;
    }

    public ParsedDirectory recurseDirectories(final StorageReference ref, String dirName, String dirPath){
        File localFile = null;
        final ParsedDirectory parsedDirectory = new ParsedDirectory(dirName, dirPath);
        final Gson gson = new Gson();
        try {
            localFile = File.createTempFile("data","json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final File finalLocalFile = localFile;
        ref.child("StorageData.json").getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                System.out.println("on complete");
        }});
            ArrayList<FileRecord> fileRecords;
            try {
                fileRecords = gson.fromJson(new FileReader(finalLocalFile), token);
                for (int i = 0; i < fileRecords.size(); i++){
                    FileRecord record = fileRecords.get(i);
                    parsedDirectory.itemNames.add(record.fileName);
                    parsedDirectory.itemPaths.put(record.fileName, record.filePath);
                    //if (record.fileType == "folder"){
                    parsedDirectory.numSubDir++;
                    parsedDirectories.add(recurseDirectories(ref.child(record.fileName), record.fileName, record.filePath));
                    //}
                }
            } catch (FileNotFoundException e) {
                System.out.println("Did we catch an error?");
                e.printStackTrace();
            }
        return parsedDirectory;
    }

    }
