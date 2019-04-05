package com.example.cloudable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsedDirectory {

    String dirName;
    String dirPath;
    List<String> itemNames;
    Map<String, String> itemPaths;
    int numSubDir;

    public ParsedDirectory(String tempDirName, String tempDirPath) {
        dirName = tempDirName;
        dirPath = tempDirPath;
        itemNames = new ArrayList<>();
        itemPaths = new HashMap<>();
        numSubDir = 0;

    }

    public void addItem(String itemName, String itemPath, boolean subDir) {
        itemNames.add(itemName);
        itemPaths.put(itemName, itemPath);
        if (subDir == true){
            numSubDir += 1;
        }
    }

    public String getDirName() {
        return dirName;
    }

    public String getDirPath() {
        return dirPath;
    }

    public List<String> getItemNames() {
        return itemNames;
    }

    public Map<String, String> getItemPaths() {
        return itemPaths;
    }

    public int getNumSubDir() {
        return numSubDir;
    }
}
