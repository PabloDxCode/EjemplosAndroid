package com.example.pgutierrezd.lab7;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    // Menu
    private static final int AGREGAR_ID=Menu.FIRST+1;
    private static final int BORRAR_ID=Menu.FIRST+2;
    private static final int CERRAR_ID= Menu.FIRST+3;
    private static final int ACTUALIZAR_ID=Menu.FIRST+4;
    private ListAdapter adapter;
    private Cursor cursor;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DBHelper(this).getWritableDatabase();
        cursor = db.rawQuery("SELECT _id,title FROM regions ORDER BY _id",null);
        adapter=new SimpleCursorAdapter(this,R.layout.row,cursor,new String[]{"_id","title"}, new int[]{R.id.id,R.id.title},0);
        mListView = (ListView) findViewById(R.id.lista);
        mListView.setAdapter(adapter);
        registerForContextMenu(mListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main_activity8, menu);
        //return true;
        menu.add(Menu.NONE,AGREGAR_ID,Menu.NONE,"Agregar");//(Grupo,ID del elemento,Orden|,Texto)
        menu.add(Menu.NONE,CERRAR_ID,Menu.NONE,"Cerrar");
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE,BORRAR_ID,Menu.NONE,"Borrar");
        menu.add(Menu.NONE,ACTUALIZAR_ID,Menu.NONE,"Actualizar");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case CERRAR_ID:
                finish();
                return true;
            case AGREGAR_ID:
                agregar();
                return true;

        }
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        //return super.onContextItemSelected(item);
        switch(item.getItemId()){
            case BORRAR_ID:
                AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                borrar(info.id);
                break;
            case ACTUALIZAR_ID:
                AdapterView.AdapterContextMenuInfo info1=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                Log.i("Id","id "+ info1.id);
                actualizar(info1.id);
                break;

        }
        return true;
    }

    private void actualizar(final long rowid){
        Cursor c1=db.rawQuery("SELECT title FROM regions WHERE _id=?", new String[]{String.valueOf(rowid)});
        c1.moveToFirst();
        String titulo=c1.getString(c1.getColumnIndex("title"));
        LayoutInflater inflater= LayoutInflater.from(this);
        View actualizarView=inflater.inflate(R.layout.agregar, null);
        final EditText texto=(EditText)actualizarView.findViewById(R.id.texto);
        texto.setText(titulo);
        if(rowid >0){
            new AlertDialog.Builder(this).setTitle("Actualizar")
                    .setView(actualizarView)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            procesarActualizar(texto);

                        }

                        public void procesarActualizar(EditText texto){
                            ContentValues cv=new ContentValues();
                            cv.put("title", texto.getText().toString());
                            db.update("regions", cv,"_ID = ?", new String[]{String.valueOf(rowid)});
                            cursor=db.rawQuery("SELECT _id,title FROM regions ORDER BY _id", null);
                            ((SimpleCursorAdapter)adapter).changeCursor(cursor);

                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    })
                    .show();
        }
    }


    private void borrar(final long rowid){
        if(rowid>0){
            new AlertDialog.Builder(this).setTitle("Estas seguro de Borrar?")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            procesarBorrar(rowid);

                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    }).show();
        }
    }


    private void agregar(){
        LayoutInflater inflater=LayoutInflater.from(this);
        View agregarView=inflater.inflate(R.layout.agregar, null);
        final WrapperDatos wrapper=new WrapperDatos(agregarView);
        new AlertDialog.Builder(this).setTitle("Agregar").setView(agregarView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        procesarAgregar(wrapper);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void procesarAgregar(WrapperDatos datos){
        ContentValues cv=new ContentValues();
        cv.put("title", datos.getText());
        db.insert("regions","title", cv);
        cursor=db.rawQuery("SELECT _id,title FROM regions ORDER BY _id", null);
        ((SimpleCursorAdapter)adapter).changeCursor(cursor);

    }

    public void procesarBorrar(final long rowid){
        String[] args={String.valueOf(rowid)};
        db.delete("regions","_ID=?", args);
        cursor=db.rawQuery("SELECT _id,title FROM regions ORDER BY _id", null);
        ((SimpleCursorAdapter)adapter).changeCursor(cursor);
    }


    class WrapperDatos{
        EditText id=null;
        EditText text=null;
        View base=null;

        public WrapperDatos(View base){
            this.base=base;
        }

        private EditText getIdField(){
            if(id==null)
                id=(EditText)base.findViewById(R.id.clave);
            return id;
        }

        private EditText getTextField(){
            if(text==null)
                text=(EditText)base.findViewById(R.id.texto);
            return text;
        }

        public int getId(){
            return new Integer(getIdField().getText().toString()).intValue();
        }

        public String getText(){
            return getTextField().getText().toString();
        }
        
    }

}
