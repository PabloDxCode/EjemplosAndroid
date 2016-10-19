package com.example.pgutierrezd.becaidspracticav2.interfaces;

import com.example.pgutierrezd.becaidspracticav2.models.ActivitiesProjects;
import com.example.pgutierrezd.becaidspracticav2.models.DiaSemana;

import java.util.List;

/**
 * Created by pgutierrezd on 18/08/2016.
 */
public interface DAOActivityDay {

    public boolean validateDayActivity(DiaSemana diaSemana);

    public boolean updateActivity(String id, String fecha, String hrs, String comments, int activity);
    public DiaSemana findUniqueActivity(int id);

    public List<String> findAllDates();

    public boolean validateNotification();

}
