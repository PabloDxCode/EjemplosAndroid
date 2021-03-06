package com.example.pgutierrezd.becaidspractica.helpers;

import android.database.Cursor;

import com.example.pgutierrezd.becaidspractica.database.DataBaseOpenHelper;
import com.example.pgutierrezd.becaidspractica.models.DiaSemana;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public class ConvertCursorToList {

    public List<DiaSemana> cursorToList(Cursor cursor) {
        List<DiaSemana> diaSemanas = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                DiaSemana diaSemana = new DiaSemana();
                diaSemana.setIdRegistroDiaSemana(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_REGISTRO_DIASEMANA)));
                diaSemana.setFecha(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_FECHA)));
                diaSemana.setHrs(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_HORA)));
                diaSemana.setComentario(cursor.getString(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_COMENTARIO)));
                diaSemana.setIdRecursoFK(cursor.getInt(cursor.getColumnIndex(DataBaseOpenHelper.COLUMN_ID_RECURSO_FK)));
                diaSemanas.add(diaSemana);
            }
        }
        return diaSemanas;
    }
}
