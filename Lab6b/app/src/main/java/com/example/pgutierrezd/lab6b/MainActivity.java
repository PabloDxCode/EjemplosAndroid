package com.example.pgutierrezd.lab6b;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button descargar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        descargar = (Button) findViewById(R.id.descarga);

        descargar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        new Descargar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                "http://hacking-etico.com/wp-content/uploads/2016/04/ANDROID.png");
        new Descargar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                "http://o.aolcdn.com/hss/storage/midas/f48e9dfc9e564fc2d0588688e86a243d/202487399/10%2Bam%2BOfficial%2BAnnounce_G_FB%2Bcopy.jpg");
        new Descargar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                "https://lh6.ggpht.com/B6ePDjUR8H_6G9YWC_ZG1yutDCPker8Dw3QSWcCh8c9X6HWChBVLbZjkTDcC9ZZEyeJWiA9o7-g=w300");
        new Descargar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                "http://www.zaresdeluniverso.com/wp-content/uploads/2015/02/android-pissin-on-apple-decal.jpg");
    }

    private class Descargar extends AsyncTask<String, Void, Bitmap>{

        TextView status = (TextView) findViewById(R.id.status);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            status.setText("Inicia Descarga");
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL imgUrl = new URL(strings[0]);
                Bitmap imagen = BitmapFactory.decodeStream(imgUrl.openStream());
                return imagen;
            }catch(Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ImageView target = (ImageView) findViewById(R.id.imagen);
            target.setImageBitmap(bitmap);
            status.setText("Descarga finalizada");
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            status.setText("Descargando...");
        }
    }

}
