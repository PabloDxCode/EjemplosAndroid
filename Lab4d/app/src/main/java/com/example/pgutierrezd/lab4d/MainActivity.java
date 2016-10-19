package com.example.pgutierrezd.lab4d;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView selected;
    private ListView lista;
    private List<Usuario> usuarios;
    private int lastSelected;

    private MiAdaptador adapter;

    private void llenarUsuarios(){
        for (int i = 0; i < 15; i++){
            usuarios.add(new Usuario(i,"Usuario " + i, "user"+i+"@gmail.com"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected = (TextView) findViewById(R.id.texto);
        lista = (ListView) findViewById(R.id.listv);

        usuarios = new ArrayList<>();
        llenarUsuarios();

        adapter = new MiAdaptador(this, R.layout.usuario_row, usuarios);
        lista.setAdapter(adapter);
        registerForContextMenu(lista);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(usuarios.get(info.position).getNombre());
        lastSelected = info.position;
        String[] menuOpcions = getResources().getStringArray(R.array.menu_options);
        for (int i = 0; i < menuOpcions.length; i++){
            menu.add(Menu.NONE, i, i, menuOpcions[i]);//menu.add(grupo al que pertenece,id de la opcion, el orden, el contenido)
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int option = item.getItemId();
        switch (option){
            case 0:
                break;
            case 1:
                Log.d("Lab4d","Eliminar");
                usuarios.remove(lastSelected);
                adapter.notifyDataSetChanged();
                break;
            case 2:
                break;
        }
        return super.onContextItemSelected(item);
    }

    class MiAdaptador extends ArrayAdapter{

        List users;
        int resource;

        public MiAdaptador(Context context, int resource, List objects) {
            super(context, resource, objects);
            this.users = objects;
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            Usuario u = (Usuario)users.get(position);
            View row = inflater.inflate(resource, parent, false);

            TextView idUser = (TextView) row.findViewById(R.id.idUsuario);
            TextView nombre = (TextView) row.findViewById(R.id.nombreUsuario);
            TextView correo = (TextView) row.findViewById(R.id.correoUsuario);

            idUser.setText(String.valueOf(u.getId()));
            nombre.setText(u.getNombre());
            correo.setText(u.getCorreo());

            return row;
        }
    }
}
