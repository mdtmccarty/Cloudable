package com.example.cloudable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class TestViewGroup {

    public void TestViewGroup() {

        }

    public void switchViewGroup(View v) {
        ViewGroup style = (ViewGroup) v;
        ViewGroup parent = (ViewGroup) v.getParent();
        int index = parent.indexOfChild(style);
        ViewGroup replacement = (ViewGroup) LayoutInflater.from(style.getContext()).inflate(R.layout.content_main_page,null);
//        ViewGroup replacement = (ViewGroup)style;
        ImageButton a = new ImageButton(style.getContext());
        replacement.removeAllViews();
        replacement.addView(a);
        replacement.setTag("RandoID");
        parent.removeView(v.findViewById(R.id.mainLayout));
        parent.addView(replacement);

    }

    public void switchViewGroupBack(View v,View OGChild){
        ViewGroup parent = (ViewGroup) v;
        parent.removeView(v.findViewWithTag("RandoID"));
        parent.addView(OGChild);
    }
}
