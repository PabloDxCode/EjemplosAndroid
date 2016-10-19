package com.example.pgutierrezd.lab17;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.pgutierrezd.lab17.services.Player;

public class MainActivity extends AppCompatActivity {

    VideoView mVideoView;
    MediaController mediaControls;
    private int position = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnInicio=(Button)findViewById(R.id.btn_Inicio);
        setVideo();

        btnInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intento=new Intent(getBaseContext(),Player.class);

                startService(intento);

            }
        });
        Button btnDetener=(Button)findViewById(R.id.btn_Fin);
        btnDetener.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(),Player.class));

            }
        });

    }

    private void setVideo(){
        mVideoView = (VideoView) findViewById(R.id.video);
        mediaControls = new MediaController(this);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Cargando video");
        dialog.setMessage("Espere...");
        dialog.setCancelable(false);
        dialog.show();


        mVideoView.setMediaController(mediaControls);
        //mVideoView.setVideoPath("/sdcard/Pictures/MOV_0124.mp4");
        String path = "android.resource://"+ getPackageName() + "/" + R.raw.video;
        mVideoView.setVideoURI(Uri.parse(path));


        mVideoView.requestFocus();

        Log.i("VideoM","Antes de listener");


        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                dialog.dismiss();
                Log.i("Video","iniciando");

                mVideoView.seekTo(position);
                mVideoView.start();

            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos",mVideoView.getCurrentPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        position = savedInstanceState.getInt("pos");
        mVideoView.seekTo(position);
    }
}