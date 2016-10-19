package com.example.pgutierrezd.becaidspracticav2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.pgutierrezd.becaidspracticav2.database.ConnectDataBase;
import com.example.pgutierrezd.becaidspracticav2.database.DataBaseOpenHelper;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOActivityDay;
import com.example.pgutierrezd.becaidspracticav2.models.DiaSemana;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by pgutierrezd on 18/08/2016.
 */
public class DAOActivityDayImp extends ConnectDataBase implements DAOActivityDay {

    protected static final String[] allColumnsDayWeek = {
            DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA,
            DataBaseOpenHelper.COLUMN_FECHA,
            DataBaseOpenHelper.COLUMN_HORA,
            DataBaseOpenHelper.COLUMN_COMENTARIO,
            DataBaseOpenHelper.COLUMN_ID_RECURSO_FK,
            DataBaseOpenHelper.COLUMN_ID_PROYECTO_FK,
            DataBaseOpenHelper.COLUMN_ID_TIPO_ACTIVIDAD_FK
    };

    public DAOActivityDayImp(Context context) {
        super(context);
        open();
    }

    @Override
    public boolean validateDayActivity(DiaSemana diaSemana) {
        if( findTotalHrs(diaSemana.getFecha().toString()) + Double.parseDouble(diaSemana.getHrs()) <= 8){
            createRegisterDayWeek(diaSemana);
            return true;
        }else{
            return false;
        }
    }

    public double findTotalHrs(String date) {
        double totalHrs = 0;
        String query = "SELECT SUM("+ DataBaseOpenHelper.COLUMN_HORA+") AS Total FROM "+ DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA + "" +
                " WHERE "+DataBaseOpenHelper.COLUMN_FECHA + "='"+date+"';";
        Cursor cursor = database.rawQuery(query,null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            totalHrs = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return totalHrs;
    }

    public void createRegisterDayWeek(DiaSemana diaSemana) {
        ContentValues values = new ContentValues();
        values.put(DataBaseOpenHelper.COLUMN_FECHA, diaSemana.getFecha());
        values.put(DataBaseOpenHelper.COLUMN_HORA, diaSemana.getHrs());
        values.put(DataBaseOpenHelper.COLUMN_COMENTARIO, diaSemana.getComentario());
        values.put(DataBaseOpenHelper.COLUMN_ID_RECURSO_FK, 1);
        values.put(DataBaseOpenHelper.COLUMN_ID_PROYECTO_FK, diaSemana.getIdProyectoFK());
        values.put(DataBaseOpenHelper.COLUMN_ID_TIPO_ACTIVIDAD_FK,diaSemana.getIdTipoActividadFK());
        database.insert(DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA, null,values);
    }

    @Override
    public boolean updateActivity(String id, String fecha, String hrs, String comments, int activity) {
        double totalHrs = 0;
        String query = "SELECT SUM("+DataBaseOpenHelper.COLUMN_HORA+") AS Total FROM "+ DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA + "" +
                " WHERE "+DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA + "!="+id+" AND "+DataBaseOpenHelper.COLUMN_FECHA+"='"+fecha+"' ;";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        totalHrs = cursor.getDouble(cursor.getColumnIndex("Total"));

        if((totalHrs+Double.parseDouble(hrs)) <= 8){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseOpenHelper.COLUMN_HORA,hrs);
            contentValues.put(DataBaseOpenHelper.COLUMN_COMENTARIO, comments);
            contentValues.put(DataBaseOpenHelper.COLUMN_ID_TIPO_ACTIVIDAD_FK, activity);
            database.update(DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA,contentValues,
                    DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA+"="+id,null);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public DiaSemana findUniqueActivity(int id) {
        Cursor cursor = database.rawQuery("SELECT * FROM "+DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA+" " +
                "WHERE "+DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA+"= ?;" ,new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setIdRegistroDiaSemana(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA)));
        diaSemana.setFecha(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_FECHA)));
        diaSemana.setHrs(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_HORA)));
        diaSemana.setComentario(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_COMENTARIO)));
        diaSemana.setIdRecursoFK(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_RECURSO_FK)));
        diaSemana.setIdProyectoFK(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_PROYECTO_FK)));
        diaSemana.setIdTipoActividadFK(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_TIPO_ACTIVIDAD_FK)));
        return diaSemana;
    }

    @Override
    public List<String> findAllDates() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT "+DataBaseOpenHelper.COLUMN_FECHA+" " +
                "FROM "+DataBaseOpenHelper.TABLE_REGISTRO_DIA_SEMANA+"" +
                " ORDER BY "+DataBaseOpenHelper.COLUMN_FECHA+" DESC",null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_FECHA)));
            }
        }
        return list;
    }

    public boolean validateNotification(){
        boolean bandera = false;
        //Fecha actual
        GregorianCalendar calSystem = new GregorianCalendar();
        int today = calSystem.get(Calendar.DAY_OF_WEEK);

        String fechaAnterior = getFechaAnterior(today);
        String fechaActual = calSystem.get(Calendar.DAY_OF_MONTH)+"-"+(calSystem.get(Calendar.MONTH)+1)+"-"+calSystem.get(Calendar.YEAR);

        Cursor cursor = database.rawQuery("SELECT DISTINCT fecha FROM RegistroDiaSemana WHERE fecha < '"+fechaActual+"'",null);
        if(cursor.getCount() > 1){
            if(today > 1 && today < 7){
                cursor = database.rawQuery("SELECT SUM(horas) AS sumHora FROM RegistroDiaSemana WHERE fecha LIKE '%"+fechaAnterior+"%'",null);
                cursor.moveToFirst();

                if(cursor.getCount() == 0) bandera = true;
                else if(cursor.getDouble(cursor.getColumnIndex("sumHora")) < 8 ) bandera = true;
            }
        }
        return bandera;
    }

    public String getFechaAnterior(int today){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if(today == 2 || today == 3) calendar.add(Calendar.DAY_OF_YEAR,-4);
        else calendar.add(Calendar.DAY_OF_YEAR,-2);

        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        String fecha=formato.format(calendar.getTime());
        String[] aux = fecha.split("-");
        fecha = aux[0]+"-"+Integer.parseInt(aux[1])+"-"+aux[2];
        return fecha;
    }

}
