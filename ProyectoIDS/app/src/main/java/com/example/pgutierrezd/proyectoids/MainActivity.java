package com.example.pgutierrezd.proyectoids;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pgutierrezd.proyectoids.datasource.GetDataSource;
import com.example.pgutierrezd.proyectoids.models.DiaSemana;
import com.example.pgutierrezd.proyectoids.models.Recurso;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btnViewRegister;
    private ListView listView;
    private static final int ACTUALIZAR_ID=Menu.FIRST+1;

    private GetDataSource getDataSource;
    private MyRegisterDayWeekAdapter myRegisterDayWeekAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnViewRegister = (Button) findViewById(R.id.btnRegisterView);
        listView = (ListView) findViewById(R.id.listView);

        btnViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        getDataSource = new GetDataSource(this);
        fillListView();
        registerForContextMenu(listView);

    }

    public void fillListView(){
        getDataSource.open();
        List<DiaSemana> diaSemanas = getDataSource.findAll();
        myRegisterDayWeekAdapter = new MyRegisterDayWeekAdapter(this, R.layout.registro_dia_semana_row,diaSemanas);
        listView.setAdapter(myRegisterDayWeekAdapter);
        getDataSource.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        //super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE,ACTUALIZAR_ID,Menu.NONE,"Actualizar");
    }

    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        //return super.onContextItemSelected(item);
        switch(item.getItemId()){
            case ACTUALIZAR_ID:
                AdapterView.AdapterContextMenuInfo info1=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                actualizar(info1.id+1);
                break;

        }
        return true;
    }

    private void actualizar(final long rowid){
        LayoutInflater inflater= LayoutInflater.from(this);
        View actualizarView=inflater.inflate(R.layout.agregar_main, null);
        DiaSemana diaSemana = new DiaSemana();

        getDataSource.open();
        diaSemana = getDataSource.findUniqueActivity((int)rowid);
        getDataSource.close();

        final TextView idActivity = (TextView) actualizarView.findViewById(R.id.lblIdActivityUpdate);
        final TextView title = (TextView) actualizarView.findViewById(R.id.lblDateUpdate);
        final EditText hrs =(EditText)actualizarView.findViewById(R.id.txtHrsUpdate);
        final EditText coments =(EditText)actualizarView.findViewById(R.id.txtDescriptionUpdate);

        idActivity.setText(""+diaSemana.getIdRegistroDiaSemana());
        title.setText(diaSemana.getFecha());
        hrs.setText(diaSemana.getHrs());
        coments.setText(diaSemana.getComentario());
        if(rowid >0){
            new AlertDialog.Builder(this).setTitle("Actualizar")
                    .setView(actualizarView)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            procesarActualizar(idActivity.getText().toString(),title.getText().toString(),hrs.getText().toString(),
                                    coments.getText().toString());
                        }

                        private void procesarActualizar(String s,String s1, String s2, String s3) {
                            getDataSource.open();
                            if(getDataSource.updateActivity(s,s1,s2,s3) != null){
                                Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Error en los datos", Toast.LENGTH_LONG).show();
                            }

                            fillListView();
                            getDataSource.close();
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                        }
                    }).show();
        }
    }

    public class MyRegisterDayWeekAdapter extends ArrayAdapter {

        List dataDiaSemana;
        int resource;

        public MyRegisterDayWeekAdapter(Context context, int resource, List objects) {
            super(context, resource, objects);
            this.dataDiaSemana = objects;
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
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
}
