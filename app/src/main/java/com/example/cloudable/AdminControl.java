package com.example.cloudable;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AdminControl extends AppCompatActivity {

    Button createFolderButton;

    private String m_Text = "";
    private String folderLocation = "";
    private String folderPath = "";
    private StorageReference mainFolder;
    private Uri filePath;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);
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

    }

    public void createFolder(View v) {
        AlertDialog.Builder folderBuilder = new AlertDialog.Builder(this);
        folderBuilder.setTitle("In which folder would you like to create the new folder?");

        final Context context = this;

        final EditText inputFolder = new EditText(this);

        inputFolder.setInputType(InputType.TYPE_CLASS_TEXT);
        folderBuilder.setView(inputFolder);

        folderBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                folderLocation = inputFolder.getText().toString();
                mainFolder = FirebaseStorage.getInstance().getReference();
                MainPageActivity mpa = new MainPageActivity();



                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Folder Name");

                final EditText input = new EditText(context);

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        mainFolder = FirebaseStorage.getInstance().getReference();
                        MainPageActivity mpa = new MainPageActivity();


                final Gson gson = new Gson();
                JsonArray jo = new JsonArray();
                int recordNumber = 0;
                String path;
                File localFile = null;

                        try {
                            localFile = File.createTempFile("data","json");
                            System.out.println("M_TEXT: " + m_Text);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FileWriter fileWriter = null;
                        try {
                            fileWriter = new FileWriter(localFile, false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            fileWriter.write(gson.toJson(jo));
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (folderLocation.equals("main")){
                            path = mpa.groupName + "/" + m_Text;
                            mainFolder.child(mpa.groupName + "/" + m_Text + "/StorageData.json").putFile(Uri.fromFile(localFile));
                        }
                        else{
                            path = mpa.groupName + "/" + folderLocation + "/" + m_Text;
                            mainFolder.child(mpa.groupName + "/" + folderLocation + "/" + m_Text + "/StorageData.json").putFile(Uri.fromFile(localFile));
                        }
                        //TODO place this FileRecord in the parent's JSON File.
                        FileRecord newRecord = new FileRecord(recordNumber, m_Text, "folder", path, "0", "0");
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        folderBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        folderBuilder.show();
    }

    public void uploadPicture(View v){

        mainFolder = FirebaseStorage.getInstance().getReference();
        //MainPageActivity mpa = new MainPageActivity();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 71);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 71){
            if (resultCode == RESULT_OK){
                filePath = data.getData();
                mainFolder.child("TestKey/").putFile(filePath);
            }
        }
    }
}
