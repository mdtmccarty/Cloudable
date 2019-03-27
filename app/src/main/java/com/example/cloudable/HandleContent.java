package com.example.cloudable;

import android.annotation.SuppressLint;
import android.content.Context;
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

    public int getNumSubDir() {
        return numSubDir;
    }

    int numSubDir;
    String directory;
    List<HandleContent> handleContents;

    public HandleContent(){
        imageButtons = new ArrayList<ImageButton>();
        String directory = null;
        handleContents = new ArrayList<HandleContent>();
    }

    public HandleContent(ParsedDirectory parsedDirectory, Context context){
        directory = parsedDirectory.getDirName();
        numSubDir = parsedDirectory.getNumSubDir();
        for (int i = 0; i < parsedDirectory.getItemNames().size(); i++){
            ImageButton imageButton = new ImageButton(context);
            imageButton.setTag(parsedDirectory.getItemNames().get(i));
            imageButtons.add(imageButton);
        }
    }

    public void addSubDirectory(HandleContent handleContent){
        this.handleContents.add(handleContent);
    }

    public HandleContent recursDirectory(ArrayList<ParsedDirectory> list, Context context){
        HandleContent handleContent = new HandleContent(list.get(0),context);
        list.remove(0);
        for (int i = 0; i < handleContent.getNumSubDir(); i++){
            handleContent.addSubDirectory(recursDirectory(list,context));
        }
        return handleContent;
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
