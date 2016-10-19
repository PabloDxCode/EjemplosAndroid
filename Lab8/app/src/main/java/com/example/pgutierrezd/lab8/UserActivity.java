package com.example.pgutierrezd.lab8;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserActivity extends AppCompatActivity {

    TextView usuario;
    Button logout;
    ImageView imagen;
    Button share;

    private final int CAMARA_REQUEST = 3;
    private final int FILE_REQUEST = 4;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri pathMM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        usuario = (TextView) findViewById(R.id.userFits);
        logout = (Button) findViewById(R.id.logout);
        imagen = (ImageView) findViewById(R.id.imagen);
        share = (Button) findViewById(R.id.share);

        imagen.setClickable(true);

        final Intent intent = getIntent();
        Bundle datos = intent.getExtras();

        if(datos != null){
            usuario.setText(datos.getString("usuario"));
        }

        //Bundle bundle = getIntent().getExtras();
        //Toast.makeText(this,""+bundle.get("usuario"),Toast.LENGTH_LONG).show();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date actual = new Date();
                Intent intent = new Intent();
                intent.putExtra("fecha_salida",actual);//Si el objeto no es serializable no se puede guardar en el intent ni en un bundle
                //setResult(2,intent);
                setResult(Activity.RESULT_OK,intent);// El primer parametro es el resultCode
                finish();
            }
        });

        imagen.setOnClickListener(new ImageListener());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM,pathMM);
                startActivity(Intent.createChooser(intent,"Compartir en..."));
            }
        });

    }

    private Uri getMediaUri() {
        File mediaDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getString(R.string.app_name));
        return Uri.fromFile(mediaDir);
    }

    private class ImageListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Toast.makeText(UserActivity.this,"Hola", Toast.LENGTH_LONG).show();
            final CharSequence[] items = {"Camara","Biblioteca","Cancelar"};

            new AlertDialog.Builder(UserActivity.this).setTitle("Compartir Foto")
                    .setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(items[i].equals("Cancelar")){
                                dialogInterface.dismiss();
                            }else if(items[i].equals("Camara")){

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                pathMM = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                                //intent.putExtra(MediaStore.EXTRA_OUTPUT,pathMM);
                                startActivityForResult(intent, CAMARA_REQUEST);
                            }else if(items[i].equals("Biblioteca")){
                                Intent intent = new Intent();
                                intent.setType("image/* video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Escoger Archivo"),FILE_REQUEST);
                            }
                        }
                    }).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bm;
        switch(requestCode){
            case CAMARA_REQUEST:
                if(resultCode == Activity.RESULT_OK){
                    bm = (Bitmap) data.getExtras().get("data");
                    pathMM = data.getData();
                    imagen.setImageBitmap(bm);
                }
                break;
            case FILE_REQUEST:
                if(resultCode == Activity.RESULT_OK){
                    if(data != null){
                        try {
                            bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                            imagen.setImageBitmap(bm);
                            pathMM = data.getData();
                        }catch(IOException ex){

                        }
                    }
                }
                break;
        }
    }

    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES),"Lab8");
        //Create the storage directory if it does not exist
        if( !mediaStorageDir.exists()){
            if( !mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp","failed to create directory");
                return null;
            }
        }
        //Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        }else if(type == MEDIA_TYPE_VIDEO){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        }else{
            return null;
        }
        return mediaFile;
    }


}
