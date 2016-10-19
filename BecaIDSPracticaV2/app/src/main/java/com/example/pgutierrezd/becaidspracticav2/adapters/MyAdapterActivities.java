package com.example.pgutierrezd.becaidspracticav2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pgutierrezd.becaidspracticav2.R;
import com.example.pgutierrezd.becaidspracticav2.models.ActivitiesProjects;

import java.util.List;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public class MyAdapterActivities extends ArrayAdapter {

    List activitiesProjects;
    int resource;
    Context context;

    public MyAdapterActivities(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.activitiesProjects = objects;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        ActivitiesProjects u = (ActivitiesProjects) activitiesProjects.get(position);
        View row = inflater.inflate(resource, parent, false);

        TextView id = (TextView) row.findViewById(R.id.lblIdRow);
        TextView project = (TextView) row.findViewById(R.id.lblProjectRow);
        TextView comments = (TextView) row.findViewById(R.id.lblComentarioActivityRow);
        TextView typeActivity = (TextView) row.findViewById(R.id.lblTypeActivityRow);
        TextView hrs = (TextView) row.findViewById(R.id.lblHrsRow);

        id.setText(u.getId()+"");
        typeActivity.setText(u.getTipoActividad());
        project.setText(u.getClave());
        comments.setText(u.getComentario());
        hrs.setText(u.getHoras()+"");

        return row;
    }

}
