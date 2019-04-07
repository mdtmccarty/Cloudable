package com.example.cloudable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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

    public HandleContent(ParsedDirectory parsedDirectory, final Context context){
        directory = parsedDirectory.getDirName();
        numSubDir = parsedDirectory.getNumSubDir();
        for (int i = 0; i < parsedDirectory.getItemNames().size(); i++){
            final ImageButton imageButton = new ImageButton(context);
            imageButton.setTag(parsedDirectory.getItemNames().get(i));
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v = v.findViewById(R.id.mainLayout);
                    ((LinearLayout) v).removeAllViews();
                    String name = imageButton.getTag().toString();
                    HandleContent change = new HandleContent();
                    for (HandleContent content : handleContents){
                        if (content.directory == name){
                            change = content;
                        }
                    }
                    for (ImageButton button: change.imageButtons)
                    ((LinearLayout) v).addView(button);
                }
            });
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
}
