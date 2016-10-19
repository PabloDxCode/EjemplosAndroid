package com.example.pgutierrezd.becaidspracticav2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pgutierrezd.becaidspracticav2.dao.DAOActivityDayImp;
import com.example.pgutierrezd.becaidspracticav2.dao.DAOProjectsImp;
import com.example.pgutierrezd.becaidspracticav2.dao.DAOTypeActivityImp;
import com.example.pgutierrezd.becaidspracticav2.helpers.CalendarHelper;
import com.example.pgutierrezd.becaidspracticav2.helpers.ValidateFields;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOActivityDay;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOProjects;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOTypeActivity;
import com.example.pgutierrezd.becaidspracticav2.models.DiaSemana;

public class RegisterActivitiesActivity extends AppCompatActivity {

    private Button btnDisplayCalendar, btnSaveActivity;
    private TextView lblSetDate,lblTitleHrs;
    private EditText txtInputHrs, txtDescriptionActivityDay;
    private Spinner spnrProyecto, spnrTipoActividad;

    private int myear,mmonth,mday;

    static final int DATE_DIALOG_ID = 999;

    private CalendarHelper calendarHelper;
    private ValidateFields validateFields;

    private DAOActivityDay daoDayWeek;
    private DAOProjects daoProjects;
    private DAOTypeActivity daoTypeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSaveActivity = (Button) findViewById(R.id.btnSaveActivityDay);
        txtInputHrs = (EditText) findViewById(R.id.txtInputHrs);
        txtDescriptionActivityDay = (EditText) findViewById(R.id.txtDescriptionActivityDay);
        lblSetDate = (TextView) findViewById(R.id.lblSetDate);
        spnrProyecto = (Spinner) findViewById(R.id.spnrProyecto);
        spnrTipoActividad = (Spinner) findViewById(R.id.spnrActivityType);
        lblTitleHrs = (TextView) findViewById(R.id.lbl_title_hrs);

        calendarHelper = new CalendarHelper();

        daoDayWeek = new DAOActivityDayImp(this);
        daoProjects = new DAOProjectsImp(this);
        daoTypeActivity = new DAOTypeActivityImp(this);

        setCurrentDateOnView();
        addListenerOnButton();
        fillProjects();
        fillTypeActivities();

        spnrTipoActividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 1)lblTitleHrs.setText("Horas (4 ú 8)");
                else lblTitleHrs.setText("Horas");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSaveActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields = new ValidateFields();
                if(validateFields.validateSpinners(new Spinner[]{spnrProyecto,spnrTipoActividad})) {
                    if (validateFields.validateFieldsEditText(new EditText[]{txtInputHrs, txtDescriptionActivityDay})) {
                        if(validateFields.validateHrs(txtInputHrs,spnrTipoActividad) == 1 || validateFields.validateHrs(txtInputHrs,spnrTipoActividad) == 3){
                            boolean bandera = daoDayWeek.validateDayActivity(getDataView(lblSetDate, txtInputHrs, txtDescriptionActivityDay,spnrProyecto,spnrTipoActividad));
                            if(bandera == false) {
                                Toast.makeText(getApplicationContext(), "¡Estas insertando mas horas de las permitadas!", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Actividad fue salvada", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivitiesActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }else{
                            Snackbar.make(view, "El numero de horas es mayor o menor al esperado", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }else{
                    Snackbar.make(view, "No has seleccionado un tipo de actividad o Proyecto", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public DiaSemana getDataView(TextView lblSetDate, EditText txtInputHrs, EditText txtDescriptionActivityDay, Spinner spnrProyecto, Spinner spnrTipoActividad) {
        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setFecha(lblSetDate.getText().toString());
        diaSemana.setHrs(txtInputHrs.getText().toString());
        diaSemana.setComentario(txtDescriptionActivityDay.getText().toString());
        diaSemana.setIdProyectoFK(spnrProyecto.getSelectedItemPosition());
        diaSemana.setIdTipoActividadFK(spnrTipoActividad.getSelectedItemPosition());
        return diaSemana;
    }

    private void addListenerOnButton() {
        btnDisplayCalendar = (Button) findViewById(R.id.btnDisplayCalendar);
        btnDisplayCalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    private void fillProjects(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, daoProjects.getProjects());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrProyecto.setAdapter(adapter);
    }

    private void fillTypeActivities(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, daoTypeActivity.getTipoActividad());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrTipoActividad.setAdapter(adapter);
    }

    // display current date
    public void setCurrentDateOnView() {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        myear = c.get(java.util.Calendar.YEAR);
        mmonth = c.get(java.util.Calendar.MONTH);
        mday = c.get(java.util.Calendar.DAY_OF_MONTH);
        lblSetDate.setText(new StringBuilder().append(mday).append("-").append(mmonth + 1).append("-").append(myear));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, myear,mmonth,mday){
                    @Override
                    public void onDateChanged(DatePicker view, int yearSelected, int monthOfYearSelected, int dayOfMonthSelected){
                        calendarHelper.validateDate(view,yearSelected,monthOfYearSelected,dayOfMonthSelected, myear,mmonth,mday);
                    }
                };
                return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;

            lblSetDate.setText(new StringBuilder().append(mday).append("-").append(mmonth + 1)
                    .append("-").append(myear));
        }
    };

}
