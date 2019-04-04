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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import static java.security.AccessController.getContext;

public class AudioPlayer extends AppCompatActivity {
    private StorageReference mStorageRef;
    final String FILE_NAME = "audioFiles.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    public void download(View view){
        StorageReference anotherStorRef = mStorageRef.child("recording.mp3");
        final long MAX_SIZE = 1024 * 1024;

        anotherStorRef.getBytes(MAX_SIZE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                FileOutputStream outputStream = null;
                try {
                    outputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    outputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (outputStream != null){
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void play(View view) throws IOException {
        FileInputStream fis = null;
        fis = openFileInput(FILE_NAME);
        InputStreamReader isr = new InputStreamReader(fis);
        MediaPlayer mp = new MediaPlayer();
        mp.reset();
        mp.setDataSource(fis.getFD());
        mp.prepare();
        mp.start();

    }

//    public void play(View view){
//
//        if (player == null){
//            player = MediaPlayer.create(this,R.raw.recording);
//        }
//
//        player.start();
//    }
}
