package com.example.pgutierrezd.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{

    TextView mTextView;
    String[] nombres = {"Pablo","Sarai","Alan","Viridiana","Leonardo","Miguel","Gerardo"};

    ListView listaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaContactos = (ListView) findViewById(android.R.id.list);
        mTextView = (TextView) findViewById(R.id.selected);

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nombres);
        listaContactos.setAdapter(arrayAdapter);

        listaContactos.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        mTextView.setText(nombres[i]);

    }
}
