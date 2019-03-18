package com.example.cloudable;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class HandleContent {

    @SuppressLint("LongLogTag")
    public void Refresh(ViewGroup parent){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Log.d("Getting storageRef's name: ",storageRef.getName());
    }
    public void createFolder(ViewGroup parent){
        Refresh(parent);
        ImageButton test = new ImageButton(parent.getContext());
        Log.i("createButton","Attempting to create new button");
        parent.addView(test);
        Log.i("createImageButton", String.valueOf(parent.getTouchables().size()));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference TF1 = storageRef.child("Test Folder 1");
        Log.d("Testing Folder Name",TF1.getName());
        //System.out.println(storage.toString());
    }

}
