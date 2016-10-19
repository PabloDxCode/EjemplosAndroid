package com.example.pgutierrezd.lab11;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static String PENDIENTES="datos.txt";
    private EditText editor;
    String fileName;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editor=(EditText)findViewById(R.id.editor);
        boton=(Button)findViewById(R.id.close);
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                accesoSD();
                finish();
            }
        });


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        accesoSD();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        accesoSD();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void accesoSD(){
        Log.i("Activity2:","Entrando a accesoSD");
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        try {
            fileName=PENDIENTES;
            File sdDir=Environment.getExternalStorageDirectory();
            String state=Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mExternalStorageAvailable = mExternalStorageWriteable = true;
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                mExternalStorageAvailable = true;
                mExternalStorageWriteable = false;
            } else {
                mExternalStorageAvailable = mExternalStorageWriteable = false;
            }
            Log.i("Activity2","Entrando a accesoSD,mExternalStorageAvailable: "+mExternalStorageAvailable);
            Log.i("Activity2","Entrando a accesoSD,mExternalStorageWritable: "+mExternalStorageWriteable);
            Log.i("Activity2","Entrando a accesoSD, path absoluto:"+sdDir.getAbsolutePath());
            File rFile=new File("/sdcard/misdocs/"+fileName);
            boolean esVacio=editor.getText().toString().isEmpty();
            if(rFile.exists()&&rFile.canRead()&&esVacio){
                Log.i("Activity2:","Entrando a accesoSD-File exists and can read");
                BufferedReader br=null;
                try {

                    StringBuilder texto=new StringBuilder();
                    br=new BufferedReader(new FileReader(rFile));
                    String line;
                    while((line=br.readLine())!=null){
                        texto.append(line);
                        texto.append('\n');
                    }
                    editor.setText(texto.toString());
                } catch(Exception e){
                    Log.i("Activity2:","Entrando a accesoSD-File exists and can read, but EXCEPTION while reading");
                } finally {
                    if(br != null){
                        try {
                            Log.i("Activity2:","Entrando a accesoSD-File exists and can read-Closing");
                            br.close();
                        } catch (IOException e){
                            Log.i("Activity2:","Entrando a accesoSD-File exists and can read, but EXCEPTION while closing");
                        }
                    }
                }

            } else {
                if(mExternalStorageWriteable){
                    Log.i("Activity2","Entrando a accesoSD, canWrite");
                    File uadDir=new File(sdDir.getAbsolutePath()
                            +"/misdocs");
                    if(!uadDir.exists())
                        uadDir.mkdir();
                    if(uadDir.exists()&&uadDir.canWrite()){
                        Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists or created");
                        File file = new File(uadDir.getAbsolutePath()
                                +"/"+fileName);
                        try {
                            if (!file.exists())
                                file.createNewFile();
                            Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists-file created or exists");
                        } catch (IOException e){
                            Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists-EXCEPTION file NOT created");
                        }
                        if (file.exists()&& file.canWrite()){
                            Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists-file created, file can write");
                            FileOutputStream fos=null;
                            try {
                                fos = new FileOutputStream(file);
                                fos.write(editor.getText().toString()
                                        .getBytes());
                                Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists-file created, writing to file");

                            } catch(FileNotFoundException e){
                                Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists-file created, EXCEPTION FILE NOT FOUND while writing to file");
                            } catch(IOException e){
                                Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists-file created, EXCEPTION IO while writing to file");
                            } finally {
                                if (fos !=null){
                                    try {
                                        fos.flush();
                                        fos.close();
                                    } catch (Exception e){
                                        Log.i("Activity2","Entrando a accesoSD, canWrite,directory exists-file created, EXCEPTION writing to file");
                                    }
                                }
                            }
                        } else {
                            Log.i("Activity2","File does not exists or can not write");
                        }

                    } else {
                        Log.i("Activity2","Directory does not exists or can not write");
                    }

                } else {
                    Log.i("Activity2","External Storage directory does not exists");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}