package com.example.pgutierrezd.becaidspracticav2.dao;

import android.content.Context;
import android.database.Cursor;

import com.example.pgutierrezd.becaidspracticav2.database.ConnectDataBase;
import com.example.pgutierrezd.becaidspracticav2.database.DataBaseOpenHelper;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOTypeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgutierrezd on 18/08/2016.
 */
public class DAOTypeActivityImp extends ConnectDataBase implements DAOTypeActivity {

    protected static final String[] allColumnsTypeActivity = {
            DataBaseOpenHelper.COLUMN_ID_TIPO_ACTIVIDAD,
            DataBaseOpenHelper.COLUMN_CLAVE_TIPO_ACTIVIDAD,
            DataBaseOpenHelper.COLUMN_DESCRIPCION_TIPO_ACTIVIDAD
    };

    public DAOTypeActivityImp(Context context) {
        super(context);
        open();
    }

    @Override
    public List<String> getTipoActividad() {
        List<String> list = new ArrayList<>();
        list.add("Tipo actividad");
        Cursor cursor = database.query(DataBaseOpenHelper.TABLE_TIPO_ACTIVIDAD,allColumnsTypeActivity,null,null,null,null,null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex(allColumnsTypeActivity[2])));
            }
        }
        return list;
    }
}
