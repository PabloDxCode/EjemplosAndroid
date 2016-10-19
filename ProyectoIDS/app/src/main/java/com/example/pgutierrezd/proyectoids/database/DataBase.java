package com.example.pgutierrezd.proyectoids.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pgutierrezd on 10/08/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "registroactividades.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_RECURSOS = "Recurso";
    public static final String COLUMN_ID_RECURSO = "idRecurso";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APATERNO = "aPaterno";
    public static final String COLUMN_AMATERNO = "aMaterno";
    public static final String COLUMN_EMAIL = "email";

    public static final String TABLE_REGISTRO_DIA_SEMANA = "RegistroDiaSemana";
    public static final String COLUMN_ID_REGISTRO_DIASEMANA = "idRegistroDiaSemana";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "horas";
    public static final String COLUMN_COMENTARIO = "comentario";
    public static final String COLUMN_ID_RECURSO_FK = "idRecursoDiaSemana";

    public static final String CREATE_TABLE_RECURSOS =
            "CREATE TABLE " + TABLE_RECURSOS +"(" +
            COLUMN_ID_RECURSO +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOMBRE + " TEXT, " +
            COLUMN_APATERNO + " TEXT, " +
            COLUMN_AMATERNO + " TEXT, " +
            COLUMN_EMAIL + " TEXT" +
            ");";
    public static final String CREATE_TABLE_DIASEMANA = "" +
            "CREATE TABLE " + TABLE_REGISTRO_DIA_SEMANA + "(" +
            COLUMN_ID_REGISTRO_DIASEMANA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FECHA + " DATE, " +
            COLUMN_HORA + " TEXT, " +
            COLUMN_COMENTARIO + " TEXT," +
            COLUMN_ID_RECURSO_FK + " INTEGER, " +
            "FOREIGN KEY("+COLUMN_ID_RECURSO_FK+") REFERENCES "+TABLE_RECURSOS+"("+COLUMN_ID_RECURSO+")" +
            ");";

    public DataBase(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECURSOS);
        db.execSQL(CREATE_TABLE_DIASEMANA);

        db.execSQL("INSERT INTO "+TABLE_RECURSOS+"('"+COLUMN_NOMBRE+"','"+COLUMN_APATERNO+"','"+COLUMN_AMATERNO+"','"+COLUMN_EMAIL+"') " +
                "VALUES('Pablo','Gutierrez','Diaz','dxpablo2012@hotmail.com');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_RECURSOS);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_REGISTRO_DIA_SEMANA);
        onCreate(db);
    }
}