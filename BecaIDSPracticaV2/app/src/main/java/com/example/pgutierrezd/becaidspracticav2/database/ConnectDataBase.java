package com.example.pgutierrezd.becaidspracticav2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public abstract class ConnectDataBase {

    protected SQLiteOpenHelper dbHelper;
    protected SQLiteDatabase database;

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
