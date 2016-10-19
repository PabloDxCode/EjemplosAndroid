package com.example.pgutierrezd.proyectoids.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pgutierrezd.proyectoids.database.DataBase;
import com.example.pgutierrezd.proyectoids.models.DiaSemana;
import com.example.pgutierrezd.proyectoids.models.Recurso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgutierrezd on 11/08/2016.
 */
public class GetDataSource {

    SQLiteOpenHelper dbHelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DataBase.COLUMN_ID_REGISTRO_DIASEMANA,
            DataBase.COLUMN_FECHA,
            DataBase.COLUMN_HORA,
            DataBase.COLUMN_COMENTARIO,
            DataBase.COLUMN_ID_RECURSO_FK
    };

    public GetDataSource(Context context){
        dbHelper = new DataBase(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public int findTotalHrs(String fecha){
        int totalHrs = 0;
        String query = "SELECT SUM("+DataBase.COLUMN_HORA+") AS Total FROM "+ DataBase.TABLE_REGISTRO_DIA_SEMANA + "" +
                " WHERE "+DataBase.COLUMN_FECHA + "='"+fecha+"';";
        Cursor cursor = database.rawQuery(query,null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            totalHrs = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return totalHrs;
    }

    public List<DiaSemana> findAll(){
        Cursor cursor = database.query(DataBase.TABLE_REGISTRO_DIA_SEMANA, allColumns, null,null,null,null,null);
        List<DiaSemana> diaSemana = cursorToList(cursor);
        return diaSemana;
    }

    public DiaSemana findUniqueActivity(int id){
        Cursor cursor = database.rawQuery("SELECT * FROM "+DataBase.TABLE_REGISTRO_DIA_SEMANA+" " +
                "WHERE "+DataBase.COLUMN_ID_REGISTRO_DIASEMANA+"= ?;" ,new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setIdRegistroDiaSemana(cursor.getInt(cursor.getColumnIndex(DataBase.COLUMN_ID_REGISTRO_DIASEMANA)));
        diaSemana.setFecha(cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_FECHA)));
        diaSemana.setHrs(cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_HORA)));
        diaSemana.setComentario(cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_COMENTARIO)));
        diaSemana.setIdRecursoFK(cursor.getInt(cursor.getColumnIndex(DataBase.COLUMN_ID_RECURSO_FK)));
        return diaSemana;
    }

    private List<DiaSemana> cursorToList(Cursor cursor) {
        List<DiaSemana> diaSemanas = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdRegistroDiaSemana(cursor.getInt(cursor.getColumnIndex(DataBase.COLUMN_ID_REGISTRO_DIASEMANA)));
                diaSemana.setFecha(cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_FECHA)));
                diaSemana.setHrs(cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_HORA)));
                diaSemana.setComentario(cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_COMENTARIO)));
                diaSemana.setIdRecursoFK(cursor.getInt(cursor.getColumnIndex(DataBase.COLUMN_ID_RECURSO_FK)));
                diaSemanas.add(diaSemana);
            }
        }
        return diaSemanas;
    }

    public DiaSemana validateDayActivity(DiaSemana diaSemana) {

        boolean bandera = true;
        List<DiaSemana> datosDB = new ArrayList<>();
        datosDB = findAll();

        for (DiaSemana d: datosDB) {
            if(diaSemana.getFecha().toString().equals(d.getFecha())){
                if( findTotalHrs(diaSemana.getFecha().toString()) + Integer.parseInt(diaSemana.getHrs()) <= 8){
                    createRegisterDayWeek(diaSemana);
                    bandera = false;
                    return diaSemana;
                }else{
                    bandera = false;
                    return null;
                }
            }
        }
        if(bandera){
            createRegisterDayWeek(diaSemana);
            return diaSemana;
        }
        return null;
    }

    public DiaSemana createRegisterDayWeek(DiaSemana diaSemana){
        ContentValues values = new ContentValues();
        values.put(DataBase.COLUMN_FECHA, diaSemana.getFecha());
        values.put(DataBase.COLUMN_HORA, diaSemana.getHrs());
        values.put(DataBase.COLUMN_COMENTARIO, diaSemana.getComentario());
        values.put(DataBase.COLUMN_ID_RECURSO_FK, 1);
        long insertid = database.insert(DataBase.TABLE_REGISTRO_DIA_SEMANA, null,values);
        diaSemana.setIdRecursoFK(1);
        return diaSemana;
    }

    public DiaSemana updateActivity(String id, String fecha, String hrs, String comments) {
        int totalHrs = 0;
        String query = "SELECT SUM("+DataBase.COLUMN_HORA+") AS Total FROM "+ DataBase.TABLE_REGISTRO_DIA_SEMANA + "" +
                " WHERE "+DataBase.COLUMN_ID_REGISTRO_DIASEMANA + "!="+id+" AND "+DataBase.COLUMN_FECHA+"='"+fecha+"' ;";
        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        totalHrs = cursor.getInt(cursor.getColumnIndex("Total"));

        if((totalHrs+Integer.parseInt(hrs)) <= 8){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBase.COLUMN_HORA,hrs);
            contentValues.put(DataBase.COLUMN_COMENTARIO, comments);
            database.update(DataBase.TABLE_REGISTRO_DIA_SEMANA,contentValues,
                    DataBase.COLUMN_ID_REGISTRO_DIASEMANA+"="+id,null);

            DiaSemana diaSemana = new DiaSemana();
            diaSemana.setIdRegistroDiaSemana(Integer.parseInt(id));
            diaSemana.setFecha(fecha);
            diaSemana.setHrs(hrs);
            diaSemana.setComentario(comments);
            diaSemana.setIdRecursoFK(1);
            return diaSemana;
        }else{
            return null;
        }
    }

    public Recurso createRecurso(Recurso r) {
        ContentValues values = new ContentValues();
        values.put(DataBase.COLUMN_FECHA, r.getNombre());
        values.put(DataBase.COLUMN_APATERNO, r.getaPaterno());
        values.put(DataBase.COLUMN_AMATERNO, r.getaPaterno());
        values.put(DataBase.COLUMN_EMAIL, r.getEmail());
        long insertid = database.insert(DataBase.TABLE_RECURSOS, null,values);
        return r;
    }

    public String getRecurso(){
        Cursor c1=database.rawQuery("SELECT "+DataBase.COLUMN_NOMBRE+" FROM "+DataBase.TABLE_RECURSOS+" " +
                "WHERE "+DataBase.COLUMN_ID_RECURSO+"=1; ",null);
        c1.moveToFirst();
        String nombre = c1.getString(c1.getColumnIndex(DataBase.COLUMN_NOMBRE));

        return nombre;
    }
}
