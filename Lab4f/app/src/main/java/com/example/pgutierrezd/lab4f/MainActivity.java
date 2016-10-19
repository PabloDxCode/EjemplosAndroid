package com.example.pgutierrezd.lab4f;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView listaContactos;
    private CheckBox checkFavoritos;
    private TextView seleccionado;
    private boolean isFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //explicito -bloques internos, implicito - tanto bloques internos como de otras aplicaciones

        listaContactos = (ListView) findViewById(R.id.listaContactos);
        checkFavoritos = (CheckBox) findViewById(R.id.checkFavoritos);
        seleccionado = (TextView) findViewById(R.id.selected);
        isFavorites = false;

        fillContactList();
        checkFavoritos.setOnCheckedChangeListener(new CheckChangeListener());

        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view.findViewById(R.id.contactoItem);
                String nombre = tv.getText().toString();
                seleccionado.setText(nombre);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fillContactList();
                }
                break;
        }
    }

    private void fillContactList(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
            return;
        }

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        };
        String[] selection = null;
        String sort = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
        String where = isFavorites? ContactsContract.Contacts.STARRED + " = 1": null;
        Cursor cursor = getContentResolver().query(uri,
                projection,
                where,
                selection,
                sort);
        String[] fields = new String[]{ContactsContract.Data.DISPLAY_NAME};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.contacto_row, cursor, fields,
                                                                new int[]{R.id.contactoItem},0);
        listaContactos.setAdapter(adapter);
        //cursor.close();

    }

    private class CheckChangeListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            isFavorites = b;
            fillContactList();
        }

    }

}
