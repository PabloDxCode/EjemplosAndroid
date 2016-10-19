package com.example.pgutierrezd.becaidspractica.datasources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pgutierrezd.becaidspractica.database.DataBaseOpenHelper;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public class ConnectDataBase {

    protected SQLiteOpenHelper dbHelper;
    protected SQLiteDatabase database;

    protected static final String[] allColumns = {
            DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA,
            DataBaseOpenHelper.COLUMN_FECHA,
            DataBaseOpenHelper.COLUMN_HORA,
            DataBaseOpenHelper.COLUMN_COMENTARIO,
            DataBaseOpenHelper.COLUMN_ID_RECURSO_FK
    };

    public ConnectDataBase(Context context){
        dbHelper = new DataBaseOpenHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

}
