package com.example.pgutierrezd.becaidspracticav2.dao;

import android.content.Context;
import android.database.Cursor;

import com.example.pgutierrezd.becaidspracticav2.database.ConnectDataBase;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOActivitiesProjects;
import com.example.pgutierrezd.becaidspracticav2.models.ActivitiesProjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pgutierrezd on 18/08/2016.
 */
public class DAOActivitiesProjectsImp extends ConnectDataBase implements DAOActivitiesProjects {

    public DAOActivitiesProjectsImp(Context context) {
        super(context);
        open();
    }

    @Override
    public List<ActivitiesProjects> findAllInnerJoin(String date) {
        List<ActivitiesProjects> listActivitiesProjects = new ArrayList<>();
        ActivitiesProjects activitiesProjects;
        Cursor cursor = database.rawQuery("SELECT r._id, p.clave, r.comentario, ta.descripcion, r.horas FROM RegistroDiaSemana AS r " +
                "INNER JOIN Proyecto AS p ON r.idProyectoDiaSemana = p._id " +
                "INNER JOIN TipoActividad AS ta ON r.idTipoActividadDiaSemana = ta._id " +
                "WHERE fecha='"+date+"';",null);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                activitiesProjects = new ActivitiesProjects();
                activitiesProjects.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                activitiesProjects.setClave(cursor.getString(cursor.getColumnIndex("clave")));
                activitiesProjects.setComentario(cursor.getString(cursor.getColumnIndex("comentario")));
                activitiesProjects.setTipoActividad(cursor.getString(cursor.getColumnIndex("descripcion")));
                activitiesProjects.setHoras(cursor.getDouble(cursor.getColumnIndex("horas")));
                listActivitiesProjects.add(activitiesProjects);
            }
        }
        return listActivitiesProjects;
    }
}
