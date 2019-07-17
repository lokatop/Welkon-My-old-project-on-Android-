package com.example.welkon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import static com.example.welkon.BasicScan.KEY_FOR_NUMBER_OF_QUIZ;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;

    private int key = 0;
    private String videoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);


        //---------Получаем данные из сканера
        Intent intent = getIntent();
        String keyQuestion = intent.getStringExtra(KEY_FOR_NUMBER_OF_QUIZ);
        key = Integer.parseInt(keyQuestion);
        videoView = (VideoView) findViewById(R.id.videoView);
        //------------------------------------
        ;
        String nameVideo = "";
        //------------------------------------
        String path = Environment.getExternalStorageDirectory().toString();
        String videoPath = path + "/AudioArmy/video/";

        if(key != 0) {
            int forNumberName = key - 25;
            String forName = forNumberName + "";
            nameVideo = "v" + forName + ".mp4";
            videoFile = path + "/AudioArmy/" + nameVideo;

            // Set the media controller buttons
            if (mediaController == null) {
                mediaController = new MediaController(VideoActivity.this);
                // Set the videoView that acts as the anchor for the MediaController.
                mediaController.setAnchorView(videoView);
                // Set MediaController for VideoView
                videoView.setMediaController(mediaController);


                //String SrcPath = "/mnt/sdcard/final.mp4";
                //videoView1.setVideoPath(SrcPath);
            }

            videoView.setVideoURI(Uri.parse(videoPath+nameVideo));

            videoView.setMediaController(new MediaController(this));
            videoView.requestFocus();

            // When the video file ready for playback.
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer) {

                    videoView.seekTo(position);
                    if (position == 0) {
                        videoView.start();
                    }

                    // When video Screen change size.
                    mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                            // Re-Set the videoView that acts as the anchor for the MediaController
                            mediaController.setAnchorView(videoView);
                        }
                    });
                }
            });
        }
    }
    // Find ID corresponding to the name of the resource (in the directory raw).
    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }


    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }

}
