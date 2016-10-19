package com.example.pgutierrezd.lab7;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

    public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "northwind";
    private static final int DATABASE_VERSION = 3;
    private static final String TITLE = "title";
    private static final String POB = "poblacion";
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = TITLE + "," + POB;
        // TODO Auto-generated method stub
        String tabla = "CREATE TABLE regions(_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT, poblacion INTEGER)";
        db.execSQL(tabla);
        ContentValues cv = new ContentValues();
        cv.put(TITLE, "America");
        cv.put(POB, 2000000);
        db.insert("regions", str, cv);

        cv.put(TITLE, "Europe");
        cv.put(POB, 1000000);
        db.insert("regions", str, cv);

        cv.put(TITLE, "Africa");
        cv.put(POB, 3000000);
        db.insert("regions", str, cv);

        cv.put(TITLE, "Asia");
        cv.put(POB, 10000000);
        db.insert("regions", str, cv);

        cv.put(TITLE, "Oceania");
        cv.put(POB, 500000);
        db.insert("regions", str, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS regions");
        onCreate(db);
        Toast.makeText(mContext, "Actualizando", Toast.LENGTH_SHORT).show();

    }
}