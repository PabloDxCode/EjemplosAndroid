package com.example.pgutierrezd.lab12;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText numero=(EditText)findViewById(R.id.numero);
        Button marcar=(Button)findViewById(R.id.marcar);



        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CALL_PHONE},1);


        marcar.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                try {
                    String aMarcar = "tel:" + numero.getText().toString();
                    //startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(aMarcar)));
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(aMarcar)));
                }catch(SecurityException ex){

                }

            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}