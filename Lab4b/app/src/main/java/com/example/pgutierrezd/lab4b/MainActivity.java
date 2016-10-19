package com.example.pgutierrezd.lab4b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView selected;
    private Button desplegar;
    private ListView lista;

    private Contacto[] contactos = {
            new Contacto("Pablo",22),
            new Contacto("Emmanuel",31),
            new Contacto("Bernardo",25),
            new Contacto("Sarai", 22)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected = (TextView) findViewById(R.id.selected);
        desplegar = (Button) findViewById(R.id.desplegar);
        lista = (ListView) findViewById(android.R.id.list);

        lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<Contacto> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, contactos);
        lista.setAdapter(adapter);

        desplegar.setOnClickListener(new ClickListener());
    }

    private class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String datos = "";
            int opciones = lista.getCount();
            SparseBooleanArray sel = lista.getCheckedItemPositions();
            for(int i = 0; i < opciones; i++){
                if(sel.get(i)){
                    datos+= lista.getItemAtPosition(i).toString() + ", ";
                }
            }
            selected.setText(datos);
        }
    }
}
