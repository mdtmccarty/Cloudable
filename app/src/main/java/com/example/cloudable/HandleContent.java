package com.example.cloudable;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HandleContent {

    List<ImageButton> imageButtons;
    Integer numContents;
    String directory;
    List<HandleContent> handleContents;

    public HandleContent(){
        imageButtons = new ArrayList<ImageButton>();
        String directory = null;
        handleContents = new ArrayList<HandleContent>();
        numContents = 0;
    }

    public HandleContent(String name, Integer tempNumContents){
        imageButtons = new ArrayList<ImageButton>();
        for (Integer i = 0; i < tempNumContents; i++){
//            ImageButton imageButton = new ImageButton();
//            imageButtons.add()
        }
    }

    @SuppressLint("LongLogTag")
    public void Refresh(ViewGroup parent){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Log.d("Getting storageRef's name: ",storageRef.getName());
    }

    /**
     * creates an image button within a view group, to mimic the viewgroup's
     * content and be displayed when the viewgroup is being used.
     * @param parent The viewgroup that is being edited
     */
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

    public void createScrollView(View parent){

    }

    /**
     * Create a new viewgroup, in representation of viewing the contents of an
     * existing folder on FireBase
     */
    public void createViewGroup(){
    }

}
