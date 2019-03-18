package com.example.cloudable;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class HandleContent {
    public void createFolder(ViewGroup parent){
        ImageButton test = new ImageButton(parent.getContext());
        Log.i("createButton","Attempting to create new button");
        parent.addView(test);
        Log.i("createImageButton", String.valueOf(parent.getTouchables().size()));
    }

}
