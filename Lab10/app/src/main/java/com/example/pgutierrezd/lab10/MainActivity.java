package com.example.pgutierrezd.lab10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editor;
    private Button boton;
    private final static String PENDIENTES="pendientes.txt";

    private EditText dir;
    private Button btnTexto, guardarTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editor=(EditText)findViewById(R.id.editor);
        boton=(Button)findViewById(R.id.close);

        btnTexto = (Button) findViewById(R.id.btnTexto);
        guardarTexto = (Button) findViewById(R.id.guardarTexto);

        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

        btnTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dir = (EditText) findViewById(R.id.url);
                String direccion = dir.getText().toString();

                try {
                    InputStream in=openFileInput(direccion);
                    InputStreamReader tmp=new InputStreamReader(in);
                    BufferedReader reader=new BufferedReader(tmp);
                    String str;
                    StringBuffer buf=new StringBuffer();
                    while((str=reader.readLine())!=null)
                        buf.append(str+"\n");
                    in.close();
                    editor.setText(buf.toString());

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        guardarTexto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dir = (EditText) findViewById(R.id.url);
                String direccion = dir.getText().toString();
                try {
                    OutputStreamWriter out=new OutputStreamWriter(openFileOutput(direccion,0));
                    out.write(editor.getText().toString());
                    out.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    /*
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        try {
            InputStream in=openFileInput(PENDIENTES);
            InputStreamReader tmp=new InputStreamReader(in);
            BufferedReader reader=new BufferedReader(tmp);
            String str;
            StringBuffer buf=new StringBuffer();
            while((str=reader.readLine())!=null)
                buf.append(str+"\n");
            in.close();
            editor.setText(buf.toString());

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        try {
            OutputStreamWriter out=new OutputStreamWriter(openFileOutput(PENDIENTES,0));
            out.write(editor.getText().toString());
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/


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
}