package com.example.cloudable;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Controller {
    Gson gson = new Gson();
    Type token;
    public ArrayList<FileRecord> files;

    FirebaseAuth firebaseAuth;

    ArrayList<ParsedDirectory> directories;

    ArrayList<ParsedDirectory> parsedDirectories = new ArrayList<>();


    int index = 0;



    private StorageReference cloudable = FirebaseStorage.getInstance().getReference();

    Controller(String group) {
        directories = new ArrayList<>();
        directories.add(new ParsedDirectory(group, group));
        cloudable = cloudable.child(group);
        token = new TypeToken<ArrayList<FileRecord>>(){}.getType();
        firebaseAuth = FirebaseAuth.getInstance();
        //makeDirectoryList(cloudable);
    }

//    private void readJson(StorageReference reference){
//        File localFile = null;
//        try {
//            localFile = File.createTempFile("data", "json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        final File finalLocalFile = localFile;
//        reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                try {
//                    files = gson.fromJson(new FileReader(finalLocalFile), token);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
//
//    private void makeDirectoryList(StorageReference reference){
//        readJson(reference.child("StorageData.json"));
//
//            for(FileRecord file: files){
//                System.out.println("within for");
//                if(file.fileType.equals("Folder")){
//                    directories.get(index).numSubDir++;
//                    directories.add(new ParsedDirectory(file.fileName, file.filePath));
//                    StorageReference folder = reference.child(file.fileName);
//                    index++;
//                    makeDirectoryList(folder);
//                }
//                directories.get(index).itemNames.add(file.fileName);
//                directories.get(index).itemPaths.put(file.fileName, file.filePath);
//            }
//            index--;
//    }
//
//    public ArrayList<ParsedDirectory> getDirectories() {
//        return directories;
//    }

    public ParsedDirectory recurseController(final StorageReference ref, String dirName, String dirPath, Context context) {

        final ParsedDirectory parsedDirectory = new ParsedDirectory(dirName, dirPath);
        File localFile = null;
        try {
            localFile = File.createTempFile("data", "json");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        final File finalLocalFile = localFile;
//        System.out.println("before getfile");
//            ref.child("StorageData.json").getFile(finalLocalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                    System.out.println("onSuccess");
//                    ArrayList<FileRecord> fileRecords = new ArrayList<>();
//                    try {
//                        FileReader fileReader = new FileReader(finalLocalFile);
//                        fileRecords = gson.fromJson(fileReader, token);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                    for (FileRecord record : fileRecords) {
//                        if (record.fileType.equals("folder")) {
//                            parsedDirectory.numSubDir++;
//                        }
//                        parsedDirectory.itemNames.add(record.fileName);
//                        parsedDirectory.itemPaths.put(record.fileName, record.filePath);
//                        parsedDirectories.add(recurseController(ref.child(record.fileName), record.fileName, record.filePath));
//                    }
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    System.out.println("onFailure!!!!");
//                }
//            });

        //ArrayList<FileRecord> fileRecords = null;
        StorageReference ref2 = FirebaseStorage.getInstance().getReference().child("randogroup");
        final File finalLocalFile = localFile;
        final Context finalContext = context;
        ref2.child("StorageData.json").getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                System.out.println("SUCCESSS!!!!!");
                try {
                    FileReader fileReader = new FileReader(finalLocalFile);
                    System.out.println("fileReader:" + fileReader);
                    System.out.println("is localFile null: " + finalLocalFile.getTotalSpace());
                    System.out.println("empty space: " + finalLocalFile.getFreeSpace());
                    System.out.println("is gson returning null:" + gson.fromJson(fileReader,token));
                    ArrayList<FileRecord> fileRecords = gson.fromJson(fileReader, token);
                    System.out.println("before gson:" + fileRecords);
                    for (int i = 0; i < fileRecords.size(); i++) {
                        System.out.println("in for loop");
                        FileRecord record = fileRecords.get(i);
                        if (record.fileType.equals("folder")) {
                            parsedDirectory.numSubDir++;
                        }
                        parsedDirectory.itemNames.add(record.fileName);
                        parsedDirectory.itemPaths.put(record.fileName, record.filePath);
                        parsedDirectories.add(recurseController(ref.child(record.fileName), record.fileName, record.filePath, finalContext));

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("FAILURE!!!!");
            }
        });


        return parsedDirectory;
    }
}
