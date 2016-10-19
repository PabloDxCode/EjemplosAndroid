package com.example.pgutierrezd.becaidspractica.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pgutierrezd.becaidspractica.R;
import com.example.pgutierrezd.becaidspractica.models.DiaSemana;

import java.util.List;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public class MyAdapterActivities extends ArrayAdapter {

    List dataDiaSemana;
    int resource;
    Context context;

    public MyAdapterActivities(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.dataDiaSemana = objects;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //LayoutInflater inflater = context.getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        DiaSemana u = (DiaSemana) dataDiaSemana.get(position);
        View row = inflater.inflate(resource, parent, false);

        TextView idRegister = (TextView) row.findViewById(R.id.idRegisterRow);
        TextView date = (TextView) row.findViewById(R.id.lblDateRow);
        TextView hrs = (TextView) row.findViewById(R.id.lblHrsRow);

        idRegister.setText(String.valueOf(u.getIdRegistroDiaSemana()));
        date.setText(u.getFecha());
        hrs.setText(u.getHrs());

        return row;
    }

}
