package com.example.pgutierrezd.becaidspracticav2.interfaces;

import com.example.pgutierrezd.becaidspracticav2.models.ActivitiesProjects;

import java.util.List;

/**
 * Created by pgutierrezd on 18/08/2016.
 */
public interface DAOActivitiesProjects {

    public List<ActivitiesProjects> findAllInnerJoin(String date);

}
