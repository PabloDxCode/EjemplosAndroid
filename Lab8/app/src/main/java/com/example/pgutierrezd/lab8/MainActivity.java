package com.example.pgutierrezd.lab8;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button loginBtn;
    private final int ACTIVITY_USER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 555-2368"));

        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TextView userET = (TextView) MainActivity.this.findViewById(R.id.user);
                final EditText passwordET = (EditText) MainActivity.this.findViewById(R.id.password);

                String user = userET.getText().toString();
                String password = passwordET.getText().toString();

                if( user.isEmpty() || password.isEmpty() ){
                    Toast.makeText(MainActivity.this,"Debes ingresar un usuario o contraseña", Toast.LENGTH_LONG).show();
                }else{
                    Intent intentImplicit = new Intent();
                    intentImplicit.setAction("com.example.pgutierrezd.MI_INTENTO");
                    intentImplicit.setType("text/plain");

                    Bundle datos = new Bundle();
                    datos.putString("usuario", user);
                    intentImplicit.putExtras(datos);

                    if(intentImplicit.resolveActivity(getPackageManager()) != null){
                        //startActivity(intentImplicit);
                        startActivityForResult(intentImplicit,ACTIVITY_USER); // Este dos es el requestCode
                    }
                }

                //Intent intent = new Intent(MainActivity.this, UserActivity.class);
                //startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_USER) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Date fecha = (Date) data.getSerializableExtra("fecha_salida");
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.US);
                    String legible = df.format(fecha);
                    Toast.makeText(this, legible, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

}
