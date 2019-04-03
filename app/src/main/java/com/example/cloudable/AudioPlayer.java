package com.example.cloudable;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.security.AccessController.getContext;

public class AudioPlayer extends AppCompatActivity {
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //StorageReference riversRef = mStorageRef.child("images/rivers.jpg");
    }

    public void download(View view) throws IOException {
        final File localFile = File.createTempFile("audio", "mp3");

        mStorageRef.child("recording.mp3").getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        MediaPlayer mp = new MediaPlayer();
                        try {
                            mp.setDataSource(localFile.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mp.start();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AudioPlayer.this, "didnt work buddy", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

//    public void download(View view){
//        StorageReference riversRef = mStorageRef.child("recording.mp3");
//        File localFile = null;
//        try {
//            localFile = File.createTempFile("recording", "mp3");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        riversRef.getFile(localFile)
//                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle failed download
//                // ...
//            }
//        });
//    }

//    private void downloadFiles() {
//    }

//    public void play(View view){
//
//        if (player == null){
//            player = MediaPlayer.create(this,R.raw.recording);
//        }
//
//        player.start();
//    }
}
