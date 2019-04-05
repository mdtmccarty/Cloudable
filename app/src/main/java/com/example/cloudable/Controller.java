package com.example.cloudable;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class Controller {
    Gson gson = new Gson();
    Type token = new TypeToken<ArrayList<FileRecord>>(){}.getType();
    ArrayList<ArrayList<FileRecord>> files;
    ArrayList<ParsedDirectory> directories;
    File localFile;
    int index = 0;

    {
        try {
            localFile = File.createTempFile("data", "json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StorageReference cloudable = FirebaseStorage.getInstance().getReference();

    Controller(String group){
        directories = new ArrayList<>();
        directories.add(new ParsedDirectory(group, group));
        files = new ArrayList<>();
        cloudable = cloudable.child(group);
        makeDirectoryList(cloudable);
    }

    private void readJson(StorageReference reference){
        reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                try {
                    files.add((ArrayList<FileRecord>) gson.fromJson(new FileReader(localFile), token));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void makeDirectoryList(StorageReference reference){
        readJson(reference.child("StorageData.json"));
        for(FileRecord file: files.get(index)){
            if(file.fileType.equals("Folder")){
                directories.get(index).numSubDir++;
                directories.add(new ParsedDirectory(file.fileName, file.filePath));
                StorageReference folder = reference.child(file.fileName);
                index++;
                makeDirectoryList(folder);
            }
            directories.get(index).itemNames.add(file.fileName);
            directories.get(index).itemPaths.put(file.fileName, file.filePath);
        }
        index--;
    }
}
