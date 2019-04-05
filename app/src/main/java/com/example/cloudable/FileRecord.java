package com.example.cloudable;

class FileRecord {
    String fileName;
    String fileType;
    String filePath;
    String key;
    String adminKey;

    FileRecord(){

    }

    FileRecord( String name, String type, String path, String newKey, String newAdmin){
        fileName = name;
        fileType = type;
        filePath = path;
        key = newKey;
        adminKey = newAdmin;
    }
}