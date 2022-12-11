package com.example.musicplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Buttons
    Button forward , back, play, stop;
    TextView time_txt , title_txt;
    SeekBar seekBar;

    // media player
    MediaPlayer mediaPlayer;

    // Handlers
    Handler handler = new Handler();
    // variables
    double startTime = 0;
    double finalTime = 0;
    int forwardTime = 10000;
    int backwardTime = 10000;
    static  int oneTimeOnly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // fidning id's
        findIds();
        // media player
        mediaPlayer = MediaPlayer.create(
                this,
                R.raw.phut_hon
        );
        seekBar.setClickable(false);

        title_txt.setText("2 phút hơn");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayMusic();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;
                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }else{
                    Toast.makeText(MainActivity.this, "Can't make Jump Forward", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;
                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }else {
                    Toast.makeText(MainActivity.this, "Can't Go back", Toast.LENGTH_SHORT).show();
                }
            }
        });







    }

    @SuppressLint("DefaultLocale")
    private  void PlayMusic(){
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if(oneTimeOnly==0){
            seekBar.setMax((int)finalTime);
            oneTimeOnly = 1;
        }

        time_txt.setText(String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long)finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long)finalTime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)finalTime))
        ));

        seekBar.setProgress((int)startTime);
        handler.postDelayed(UpdateSongTime,100);

    }

    // creating the Runnable
    private  Runnable UpdateSongTime = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            time_txt.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                    -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))

            ));
            seekBar.setProgress((int) startTime);
            handler.postDelayed(this, 100);
        }
    };


    private void findIds() {
        forward = findViewById(R.id.fastforward);
        back = findViewById(R.id.fastrewined);
        play = findViewById(R.id.play_btn);
        stop = findViewById(R.id.pause_btn);

        title_txt = findViewById(R.id.song_title);
        time_txt = findViewById(R.id.time_text);
        seekBar = findViewById(R.id.seekbar);

    }
}