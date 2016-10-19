package com.example.pgutierrezd.becaidspracticav2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pgutierrezd.becaidspracticav2.adapters.MyAdapterActivities;
import com.example.pgutierrezd.becaidspracticav2.dao.DAOActivitiesProjectsImp;
import com.example.pgutierrezd.becaidspracticav2.dao.DAOActivityDayImp;
import com.example.pgutierrezd.becaidspracticav2.dao.DAOTypeActivityImp;
import com.example.pgutierrezd.becaidspracticav2.helpers.ValidateFields;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOActivitiesProjects;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOActivityDay;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOTypeActivity;
import com.example.pgutierrezd.becaidspracticav2.models.ActivitiesProjects;
import com.example.pgutierrezd.becaidspracticav2.models.DiaSemana;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fab;
    private Spinner spnrDate;

    private MyAdapterActivities myAdapterActivities;
    private DAOActivityDay daoDayWeek;
    private DAOTypeActivity daoTypeActivity;
    private DAOActivitiesProjects daoActivitiesProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.listView);
        spnrDate = (Spinner) findViewById(R.id.spnrSource);

        daoDayWeek = new DAOActivityDayImp(this);
        daoTypeActivity = new DAOTypeActivityImp(this);
        daoActivitiesProjects = new DAOActivitiesProjectsImp(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivitiesActivity.class);
                startActivity(intent);
            }
        });

        spnrDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillListView(spnrDate.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fillSpinnerDate();
        notificacion();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivitiesProjects activitiesProjects = (ActivitiesProjects) adapterView.getItemAtPosition(i);
                actualizar(activitiesProjects.getId());
            }
        });
    }

    public void fillSpinnerDate(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, daoDayWeek.findAllDates());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrDate.setAdapter(adapter);
    }

    public void fillListView(String date){
        List<ActivitiesProjects> list = daoActivitiesProjects.findAllInnerJoin(date);
        myAdapterActivities = new MyAdapterActivities(this,R.layout.registro_dia_semana_row,list);
        listView.setAdapter(myAdapterActivities);
    }

    private void actualizar(final int rowid){
        LayoutInflater inflater= LayoutInflater.from(this);
        View actualizarView=inflater.inflate(R.layout.actualizar_main, null);
        DiaSemana diaSemana = new DiaSemana();

        final TextView idActivity = (TextView) actualizarView.findViewById(R.id.lblIdActivityUpdate);
        final TextView title = (TextView) actualizarView.findViewById(R.id.lblDateUpdate);
        final EditText hrs =(EditText)actualizarView.findViewById(R.id.txtHrsUpdate);
        final EditText coments =(EditText)actualizarView.findViewById(R.id.txtDescriptionUpdate);
        final Spinner spnTipoActividad = (Spinner) actualizarView.findViewById(R.id.spnrActivityTypeUpdate);

        diaSemana = daoDayWeek.findUniqueActivity((int)rowid);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, daoTypeActivity.getTipoActividad());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoActividad.setAdapter(adapter);

        idActivity.setText(""+diaSemana.getIdRegistroDiaSemana());
        title.setText(diaSemana.getFecha());
        hrs.setText(diaSemana.getHrs());
        coments.setText(diaSemana.getComentario());
        spnTipoActividad.setSelection(diaSemana.getIdTipoActividadFK());

        if(rowid >0){
            new AlertDialog.Builder(this).setTitle("Actualizar")
                    .setView(actualizarView)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ValidateFields validateFields = new ValidateFields();

                            if(validateFields.validateSpinners(new Spinner[]{spnTipoActividad})){
                                if(validateFields.validateFieldsEditText(new EditText[]{hrs, coments})){
                                    if(validateFields.validateHrs(hrs,spnTipoActividad) == 1 ||
                                            validateFields.validateHrs(hrs,spnTipoActividad) == 3){
                                        procesarActualizar(idActivity.getText().toString(),title.getText().toString(),hrs.getText().toString(),
                                                coments.getText().toString(),spnTipoActividad.getSelectedItemPosition());
                                    }else{
                                        Toast.makeText(getApplicationContext(),"El numero de horas es mayor o menor al esperado",Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"Faltan datos por llenar",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"No seleccionaste Tipo de actividad",Toast.LENGTH_LONG).show();
                            }
                        }

                        private void procesarActualizar(String s,String s1, String s2, String s3, int activity) {
                            if(daoDayWeek.updateActivity(s,s1,s2,s3, activity) == true)
                                Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Datos no actualizados", Toast.LENGTH_LONG).show();
                            fillListView(spnrDate.getItemAtPosition(spnrDate.getSelectedItemPosition()).toString()+"");
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }
            }).show();
        }
    }

    private void notificacion(){
        Context context = getApplicationContext();
        PendingIntent alarmIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 1);

        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_HOUR, alarmIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
