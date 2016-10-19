package com.example.pgutierrezd.lab0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lab0","En onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lab0","En onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lab0","En onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lab0","En onRestart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lab0","En onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lab0","En onDestroy()");
    }
}
