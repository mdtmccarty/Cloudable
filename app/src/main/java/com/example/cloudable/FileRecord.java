package com.example.cloudable;

class FileRecord {
    int recordNumber;
    String fileName;
    String fileType;
    String filePath;
    String key;
    String adminKey;

    FileRecord(){

    }

    FileRecord(int num, String name, String type, String path, String newKey, String newAdmin){
        recordNumber = num;
        fileName = name;
        fileType = type;
        filePath = path;
        key = newKey;
        adminKey = newAdmin;
    }
}