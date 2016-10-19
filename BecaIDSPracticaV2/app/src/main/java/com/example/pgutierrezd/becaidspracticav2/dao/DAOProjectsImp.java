package com.example.pgutierrezd.becaidspracticav2.dao;

import android.content.Context;
import android.database.Cursor;

import com.example.pgutierrezd.becaidspracticav2.database.ConnectDataBase;
import com.example.pgutierrezd.becaidspracticav2.database.DataBaseOpenHelper;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOProjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgutierrezd on 18/08/2016.
 */
public class DAOProjectsImp  extends ConnectDataBase implements DAOProjects{

    protected static final String[] allColumnsProjects = {
            DataBaseOpenHelper.COLUMN_ID_PROYECTO,
            DataBaseOpenHelper.COLUMN_NOMBRE_PROYECTO,
            DataBaseOpenHelper.COLUMN_CLAVE_PROYECTO,
            DataBaseOpenHelper.COLUMN_COMENTARIO_PROYECTO
    };

    public DAOProjectsImp(Context context) {
        super(context);
        open();
    }

    @Override
    public List<String> getProjects() {
        List<String> list = new ArrayList<>();
        list.add("Proyecto");
        Cursor cursor = database.query(DataBaseOpenHelper.TABLE_PROYECTO,allColumnsProjects,null,null,null,null,null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex(allColumnsProjects[2])));
            }
        }
        return list;
    }
}
