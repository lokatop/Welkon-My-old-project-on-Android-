package com.example.welkon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.example.welkon.BasicScan.KEY_FOR_NUMBER_OF_QUIZ;

public class AudioActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl{
    private static final String TAG = "AudioPlayer";

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private String audioFile;

    private Handler handler = new Handler();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        //---------Получаем данные из сканера
        Intent intent = getIntent();
        String keyQuestion = intent.getStringExtra(KEY_FOR_NUMBER_OF_QUIZ);
        int key = Integer.parseInt(keyQuestion);
        String nameAudio = "";
        String namePhoto = "";
        //------------------------------------
        String path = Environment.getExternalStorageDirectory().toString();
        String imagePath = path + "/AudioArmy/";

        ImageView imageView = (ImageView) findViewById(R.id.ivAudio);

        switch (key){
            case 11:
                nameAudio = "a1.mp3";
                namePhoto = "a1.jpg";
                imageView.setImageURI(Uri.parse(imagePath+namePhoto));

                break;
            case 12:
                nameAudio = "a2.mp3";
                namePhoto = "a2.jpg";
                imageView.setImageURI(Uri.parse(imagePath+namePhoto));
                break;
            case 13:
                nameAudio = "a3.mp3";
                namePhoto = "a3.jpg";
                imageView.setImageURI(Uri.parse(imagePath+namePhoto));
                break;
            case 14:
                nameAudio = "a4.mp3";
                namePhoto = "a4.jpg";
                imageView.setImageURI(Uri.parse(imagePath+namePhoto));
                break;
        }

        audioFile =  path + "/AudioArmy/"+nameAudio;
        ((TextView)findViewById(R.id.now_playing_text)).setText(nameAudio);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        mediaController = new MediaController(this){
            @Override
            public void show() {
                super.show(0);//Default no auto hide timeout
            }
        };
        mediaController.requestFocus();


        try {
            mediaPlayer.setDataSource(audioFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "Could not open file " + audioFile + " for playback.", e);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaController.hide();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //the MediaController will hide after 3 seconds - tap the screen to make it appear again
        mediaController.show();
        return false;
    }

    //--MediaPlayerControl methods----------------------------------------------------
    public void start() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
    //--------------------------------------------------------------------------------

    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPrepared");
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.main_audio_view));

        handler.post(new Runnable() {
            @Override
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show(0);
            }
        });
    }
}