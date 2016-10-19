package com.example.pgutierrezd.becaidspractica.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.pgutierrezd.becaidspractica.database.DataBaseOpenHelper;
import com.example.pgutierrezd.becaidspractica.helpers.ConvertCursorToList;
import com.example.pgutierrezd.becaidspractica.models.DiaSemana;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public class GetDataSource extends ConnectDataBase{

    ConvertCursorToList cCursorToList;

    public GetDataSource(Context context) {
        super(context);
    }

    public int findTotalHrs(String fecha){
        int totalHrs = 0;
        String query = "SELECT SUM("+ DataBaseOpenHelper.COLUMN_HORA+") AS Total FROM "+ DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA + "" +
                " WHERE "+DataBaseOpenHelper.COLUMN_FECHA + "='"+fecha+"';";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            totalHrs = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return totalHrs;
    }

    public List<DiaSemana> findAll(){
        cCursorToList = new ConvertCursorToList();
        Cursor cursor = database.query(DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA, allColumns, null,null,null,null,null);
        List<DiaSemana> diaSemana = cCursorToList.cursorToList(cursor);
        return diaSemana;
    }

    public DiaSemana findUniqueActivity(int id){
        Cursor cursor = database.rawQuery("SELECT * FROM "+DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA+" " +
                "WHERE "+DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA+"= ?;" ,new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setIdRegistroDiaSemana(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA)));
        diaSemana.setFecha(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_FECHA)));
        diaSemana.setHrs(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_HORA)));
        diaSemana.setComentario(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_COMENTARIO)));
        diaSemana.setIdRecursoFK(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_RECURSO_FK)));
        return diaSemana;
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
        values.put(DataBaseOpenHelper.COLUMN_FECHA, diaSemana.getFecha());
        values.put(DataBaseOpenHelper.COLUMN_HORA, diaSemana.getHrs());
        values.put(DataBaseOpenHelper.COLUMN_COMENTARIO, diaSemana.getComentario());
        values.put(DataBaseOpenHelper.COLUMN_ID_RECURSO_FK, 1);
        long insertid = database.insert(DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA, null,values);
        diaSemana.setIdRecursoFK(1);
        return diaSemana;
    }

    public DiaSemana updateActivity(String id, String fecha, String hrs, String comments) {
        int totalHrs = 0;
        String query = "SELECT SUM("+DataBaseOpenHelper.COLUMN_HORA+") AS Total FROM "+ DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA + "" +
                " WHERE "+DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA + "!="+id+" AND "+DataBaseOpenHelper.COLUMN_FECHA+"='"+fecha+"' ;";
        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        totalHrs = cursor.getInt(cursor.getColumnIndex("Total"));

        if((totalHrs+Integer.parseInt(hrs)) <= 8){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseOpenHelper.COLUMN_HORA,hrs);
            contentValues.put(DataBaseOpenHelper.COLUMN_COMENTARIO, comments);
            database.update(DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA,contentValues,
                    DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA+"="+id,null);

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

}
