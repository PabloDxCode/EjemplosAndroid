package com.example.pgutierrezd.lab6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener{

    Button descargar;
    TextView status;
    ImageView imagen;

    private Handler handler = new Handler(this);
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descargar = (Button) findViewById(R.id.descarga);
        status = (TextView) findViewById(R.id.status);
        imagen = (ImageView) findViewById(R.id.imagen);

        descargar.setOnClickListener(this);

    }

    @Override
    public boolean handleMessage(Message message) {
        Bitmap img = (Bitmap) message.obj;
        String text = message.getData().getString("status");
        imagen.setImageBitmap(img);
        status.setText(text);
        return true;
    }

    private Runnable imageDownloader = new Runnable() {

        Bitmap image = null;

        private void enviarMensaje(String dato){
            Bundle bundle = new Bundle();
            bundle.putString("status", dato);
            Message message = new Message();
            message.setData(bundle);
            message.obj = image;
            handler.sendMessage(message);
        }

        @Override
        public void run() {
            try {
                URL imageURL = new URL("http://hacking-etico.com/wp-content/uploads/2016/04/ANDROID.png");
                image = BitmapFactory.decodeStream(imageURL.openStream());
                Log.i("Lab6",Thread.currentThread()+"");
                if(image != null){
                    Log.i("Lab6","Imagen descargada exitosamente");
                    enviarMensaje("Imagen descargada exitosamente");
                }else{
                    Log.i("Lab6","No se puede descargar la imagen");
                    enviarMensaje("No se puede descargar la imagen");
                }
            }catch (MalformedURLException ex){
                Log.i("Lab6","La URL de la imagen es incorrecta");
            }catch(IOException e){
                Log.i("Lab6","No se puede procesar la imagen");
            }
        }
    };

    @Override
    public void onClick(View view) {
        for (int i = 0; i < 7; i++){
            //new Thread(imageDownloader, "Hilo de descarga " + i).start();
            executor.execute(imageDownloader);
        }
        status.setText("Descarga iniciada...");
        try {
            Thread.sleep(5000);
            status.setText("Desacarga Finalizada");
        }catch(InterruptedException ex){

        }
    }
}

/*import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener{

    Button descargar;
    TextView status;
    ImageView imagen;

    private Handler handler = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descargar = (Button) findViewById(R.id.descarga);
        status = (TextView) findViewById(R.id.status);
        imagen = (ImageView) findViewById(R.id.imagen);

        descargar.setOnClickListener(this);

    }

    @Override
    public boolean handleMessage(Message message) {
        Bitmap img = (Bitmap) message.obj;
        String text = message.getData().getString("status");
        imagen.setImageBitmap(img);
        status.setText(text);
        return true;
    }

    private Runnable imageDownloader = new Runnable() {

        Bitmap image = null;

        private void enviarMensaje(String dato){
            Bundle bundle = new Bundle();
            bundle.putString("status", dato);
            Message message = new Message();
            message.setData(bundle);
            message.obj = image;
            handler.sendMessage(message);
        }

        @Override
        public void run() {
            try {
                URL imageURL = new URL("http://hacking-etico.com/wp-content/uploads/2016/04/ANDROID.png");
                image = BitmapFactory.decodeStream(imageURL.openStream());
                Log.i("Lab6",Thread.currentThread()+"");
                if(image != null){
                    Log.i("Lab6","Imagen descargada exitosamente");
                    enviarMensaje("Imagen descargada exitosamente");
                }else{
                    Log.i("Lab6","No se puede descargar la imagen");
                    enviarMensaje("No se puede descargar la imagen");
                }
            }catch (MalformedURLException ex){
                Log.i("Lab6","La URL de la imagen es incorrecta");
            }catch(IOException e){
                Log.i("Lab6","No se puede procesar la imagen");
            }
        }
    };

    @Override
    public void onClick(View view) {
        for (int i = 0; i < 4; i++){
            new Thread(imageDownloader, "Hilo de descarga " + i).start();
        }
        status.setText("Descarga iniciada...");
        try {
            Thread.sleep(5000);
            status.setText("Desacarga Finalizada");
        }catch(InterruptedException ex){

        }
    }
}*/
