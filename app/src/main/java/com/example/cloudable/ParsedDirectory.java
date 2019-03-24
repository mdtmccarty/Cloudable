package com.example.cloudable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedDirectory {
    String dirName;
    String dirPath;
    List<String> itemNames;
    Map<String,String> itemPaths;

    public ParsedDirectory(String tempDirName, String tempDirPath){
        dirName = tempDirName;
        dirPath = tempDirPath;
        itemNames = new ArrayList<>();
        itemPaths = new HashMap<>();
    }

    public void addItem(String itemName, String itemPath){
        itemNames.add(itemName);
        itemPaths.put(itemName, itemPath);
    }
}
