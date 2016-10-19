package com.example.pgutierrezd.becaidspracticav2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pgutierrezd on 10/08/2016.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "registroactividades.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_RECURSOS = "Recurso";
    public static final String COLUMN_ID_RECURSO = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APATERNO = "aPaterno";
    public static final String COLUMN_AMATERNO = "aMaterno";
    public static final String COLUMN_EMAIL = "email";

    public static final String TABLE_PROYECTO = "Proyecto";
    public static final String COLUMN_ID_PROYECTO = "_id";
    public static final String COLUMN_NOMBRE_PROYECTO = "nombre";
    public static final String COLUMN_CLAVE_PROYECTO = "clave";
    public static final String COLUMN_COMENTARIO_PROYECTO = "comentarios";

    public static final String TABLE_TIPO_ACTIVIDAD = "TipoActividad";
    public static final String COLUMN_ID_TIPO_ACTIVIDAD = "_id";
    public static final String COLUMN_CLAVE_TIPO_ACTIVIDAD = "clave";
    public static final String COLUMN_DESCRIPCION_TIPO_ACTIVIDAD = "descripcion";

    public static final String TABLE_REGISTRO_DIA_SEMANA = "RegistroDiaSemana";
    public static final String COLUMN_ID_REGISTRO_DIASEMANA = "_id";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "horas";
    public static final String COLUMN_COMENTARIO = "comentario";
    public static final String COLUMN_ID_RECURSO_FK = "idRecursoDiaSemana";
    public static final String COLUMN_ID_PROYECTO_FK = "idProyectoDiaSemana";
    public static final String COLUMN_ID_TIPO_ACTIVIDAD_FK = "idTipoActividadDiaSemana";

    public static final String CREATE_TABLE_RECURSOS =
            "CREATE TABLE " + TABLE_RECURSOS +"(" +
            COLUMN_ID_RECURSO +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NOMBRE + " VARCHAR(20), " +
            COLUMN_APATERNO + " VARCHAR(25), " +
            COLUMN_AMATERNO + " VARCHAR(25), " +
            COLUMN_EMAIL + " VARCHAR(50)" +
            ");";

    public static final String CREATE_TABLE_PROYECTO =
            "CREATE TABLE " + TABLE_PROYECTO +"(" +
                    COLUMN_ID_PROYECTO +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE_PROYECTO + " VARCHAR(50), " +
                    COLUMN_CLAVE_PROYECTO + " VARCHAR(50), " +
                    COLUMN_COMENTARIO_PROYECTO + " TEXT " +
                    ");";

    public static final String CREATE_TABLE_TIPO_ACTIVIDAD =
            "CREATE TABLE " + TABLE_TIPO_ACTIVIDAD +"(" +
                    COLUMN_ID_TIPO_ACTIVIDAD +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CLAVE_TIPO_ACTIVIDAD + " VARCHAR(50), " +
                    COLUMN_DESCRIPCION_TIPO_ACTIVIDAD + " VARCHAR(50) " +
                    ");";

    public static final String CREATE_TABLE_DIASEMANA = "" +
            "CREATE TABLE " + TABLE_REGISTRO_DIA_SEMANA + "(" +
            COLUMN_ID_REGISTRO_DIASEMANA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FECHA + " DATE, " +
            COLUMN_HORA + " VARCHAR(2), " +
            COLUMN_COMENTARIO + " TEXT," +
            COLUMN_ID_RECURSO_FK + " INTEGER, " +
            COLUMN_ID_PROYECTO_FK + " INTEGER, " +
            COLUMN_ID_TIPO_ACTIVIDAD_FK + " INTEGER, " +
            "FOREIGN KEY("+COLUMN_ID_RECURSO_FK+") REFERENCES "+TABLE_RECURSOS+"("+COLUMN_ID_RECURSO+")," +
            "FOREIGN KEY("+COLUMN_ID_PROYECTO_FK+") REFERENCES "+TABLE_PROYECTO+"("+COLUMN_ID_PROYECTO+")," +
            "FOREIGN KEY("+COLUMN_ID_TIPO_ACTIVIDAD_FK+") REFERENCES "+TABLE_TIPO_ACTIVIDAD+"("+COLUMN_ID_TIPO_ACTIVIDAD+")" +
            ");";

    public DataBaseOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECURSOS);
        db.execSQL(CREATE_TABLE_PROYECTO);
        db.execSQL(CREATE_TABLE_TIPO_ACTIVIDAD);
        db.execSQL(CREATE_TABLE_DIASEMANA);

        db.execSQL("INSERT INTO "+TABLE_RECURSOS+"("+COLUMN_NOMBRE+","+COLUMN_APATERNO+","+COLUMN_AMATERNO+","+COLUMN_EMAIL+") " +
                "VALUES('Pablo','Gutierrez','Diaz','dxpablo2012@hotmail.com');");

        db.execSQL("INSERT INTO "+TABLE_PROYECTO+"("+COLUMN_NOMBRE_PROYECTO+","+COLUMN_CLAVE_PROYECTO+","+COLUMN_COMENTARIO_PROYECTO+") " +
                "VALUES('Proyecto Desarrollo App Banorte','PR_DAB','Desarrollo de aplicación android para Banorte.');");
        db.execSQL("INSERT INTO "+TABLE_PROYECTO+"("+COLUMN_NOMBRE_PROYECTO+","+COLUMN_CLAVE_PROYECTO+","+COLUMN_COMENTARIO_PROYECTO+") " +
                "VALUES('Proyecto Capacitación Android/IOS','PR_CAI','Capacitación a nuevo personal.');");
        db.execSQL("INSERT INTO "+TABLE_PROYECTO+"("+COLUMN_NOMBRE_PROYECTO+","+COLUMN_CLAVE_PROYECTO+","+COLUMN_COMENTARIO_PROYECTO+") " +
                "VALUES('Proyecto Sistema Web para Nestle','PR_SWN','Sistema de control de productos Nestle');");

        db.execSQL("INSERT INTO "+TABLE_TIPO_ACTIVIDAD+"("+COLUMN_CLAVE_TIPO_ACTIVIDAD+","+COLUMN_DESCRIPCION_TIPO_ACTIVIDAD+") " +
                "VALUES('DDSC01','Normal');");
        db.execSQL("INSERT INTO "+TABLE_TIPO_ACTIVIDAD+"("+COLUMN_CLAVE_TIPO_ACTIVIDAD+","+COLUMN_DESCRIPCION_TIPO_ACTIVIDAD+") " +
                "VALUES('DDSC02','Vacaciones');");
        db.execSQL("INSERT INTO "+TABLE_TIPO_ACTIVIDAD+"("+COLUMN_CLAVE_TIPO_ACTIVIDAD+","+COLUMN_DESCRIPCION_TIPO_ACTIVIDAD+") " +
                "VALUES('DDSC03','Falta por Enfermedad');");
        db.execSQL("INSERT INTO "+TABLE_TIPO_ACTIVIDAD+"("+COLUMN_CLAVE_TIPO_ACTIVIDAD+","+COLUMN_DESCRIPCION_TIPO_ACTIVIDAD+") " +
                "VALUES('DDSC04','Falta con permiso');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_RECURSOS);
        db.execSQL("DROP TABLE IF EXIST " + TABLE_REGISTRO_DIA_SEMANA);
        onCreate(db);
    }
}