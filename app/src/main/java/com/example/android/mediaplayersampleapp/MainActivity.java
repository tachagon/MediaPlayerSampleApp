package com.example.android.mediaplayersampleapp;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import static com.example.android.mediaplayersampleapp.R.id.seekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private SeekBar seekBar;
    private ProgressBar volumeProgressBar;

    private float maxVolume = 10;
    private float currentVolume = maxVolume / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a Media Player object
        mediaPlayer = MediaPlayer.create(this, R.raw.music1);

        // Find Objects in XML
        Button playButton = (Button) findViewById(R.id.play);
        Button pauseButton = (Button) findViewById(R.id.pause);
        volumeProgressBar = (ProgressBar) findViewById(R.id.volume_progress_bar);
        Button decreaseVolumeButton = (Button) findViewById(R.id.decrease_volume);
        Button increaseVolumeButton = (Button) findViewById(R.id.increase_volume);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        // Initial for seek bar
        seekBar.setProgress(0);

        // Set initial volume
        mediaPlayer.setVolume(currentVolume/maxVolume, currentVolume/maxVolume);
        Log.i("Here-------------", "" + (currentVolume/maxVolume));
        volumeProgressBar.setProgress((int)((currentVolume/maxVolume) * 100));

        // Define behavior for play button
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Start to play music
                mediaPlayer.start();
                // call Runnable for the First time
                handler.postDelayed(UpdateSongProgress, 100);
            }
        });

        // Define behavior for pause button
        pauseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        // Define behavior for decrease volume button
        decreaseVolumeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                currentVolume -= 1;
                if (currentVolume <= 0)
                    currentVolume = 0;

                mediaPlayer.setVolume(currentVolume/maxVolume, currentVolume/maxVolume);
                Log.i("Here-------------", "" + (currentVolume/maxVolume));
                volumeProgressBar.setProgress((int)((currentVolume/maxVolume) * 100));
            }
        });

        // Define behavior for increase volume button
        increaseVolumeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                currentVolume += 1;
                if (currentVolume >= maxVolume)
                    currentVolume = maxVolume;

                mediaPlayer.setVolume(currentVolume/maxVolume, currentVolume/maxVolume);
                Log.i("Here-------------", "" + (currentVolume/maxVolume));
                volumeProgressBar.setProgress((int)((currentVolume/maxVolume) * 100));
            }
        });

    }

    private Runnable UpdateSongProgress = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mediaPlayer.getCurrentPosition() * 100 / mediaPlayer.getDuration());
            // Call itself every 100 milli seconds
            handler.postDelayed(this, 100);
        }
    };
}
