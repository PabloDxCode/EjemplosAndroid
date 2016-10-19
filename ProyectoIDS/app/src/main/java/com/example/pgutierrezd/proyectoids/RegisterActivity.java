package com.example.pgutierrezd.proyectoids;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pgutierrezd.proyectoids.datasource.GetDataSource;
import com.example.pgutierrezd.proyectoids.models.DiaSemana;
import com.example.pgutierrezd.proyectoids.models.Recurso;

public class RegisterActivity extends AppCompatActivity {

    private int myear,mmonth,mday;
    private Button btnDisplayCalendar, btnSaveActivity;
    private TextView lblSetDate;
    private EditText txtInputHrs, txtDescriptionActivityDay;
    static final int DATE_DIALOG_ID = 999;
    private Toolbar toolbar;

    private GetDataSource getDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        btnSaveActivity = (Button) findViewById(R.id.btnSaveActivityDay);
        txtInputHrs = (EditText) findViewById(R.id.txtInputHrs);
        txtDescriptionActivityDay = (EditText) findViewById(R.id.txtDescriptionActivityDay);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setCurrentDateOnView();
        addListenerOnButton();

        getDataSource = new GetDataSource(this);

        getDataSource.open();

        btnSaveActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields()) {
                    if (Integer.parseInt(txtInputHrs.getText().toString()) > 8 || Integer.parseInt(txtInputHrs.getText().toString()) < 1) {
                        Toast.makeText(getApplicationContext(), "El numero de horas es mayor  o menor al esperado", Toast.LENGTH_LONG).show();
                    } else {
                        DiaSemana diaSemana = getDataSource.validateDayActivity(getDataView());
                        if(diaSemana == null) {
                            Toast.makeText(getApplicationContext(), "¡Estas insertando mas horas de las permitadas!", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Actividad " + diaSemana.getFecha() + " Fue salvada", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        //getDataSource.close();
    }

    private DiaSemana getDataView(){
        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setFecha(lblSetDate.getText().toString());
        diaSemana.setHrs(txtInputHrs.getText().toString());
        diaSemana.setComentario(txtDescriptionActivityDay.getText().toString());
        return diaSemana;
    }

    public boolean validateFields(){
        boolean bandera = true;
        if(txtInputHrs.getText().toString().equals("")) {
            txtInputHrs.setError("Es necesario llenar este campo");
            bandera = false;
        }
        if(txtDescriptionActivityDay.getText().toString().equals("")) {
            txtDescriptionActivityDay.setError("Es necesario llenar este campo");
            bandera = false;
        }
        return bandera;
    }

    // display current date
    public void setCurrentDateOnView() {

        lblSetDate = (TextView) findViewById(R.id.lblSetDate);

        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        lblSetDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(mmonth + 1).append("-").append(mday).append("-")
                .append(myear));
    }

    public void addListenerOnButton() {
        btnDisplayCalendar = (Button) findViewById(R.id.btnDisplayCalendar);
        btnDisplayCalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }

        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, myear,mmonth,mday){
                    @Override
                    public void onDateChanged(DatePicker view, int yearSelected, int monthOfYearSelected, int dayOfMonthSelected){
                        //FECHA DEL SISTEMA
                        GregorianCalendar calSystem = new GregorianCalendar();
                        int weekMonthSystem = calSystem.get(Calendar.DAY_OF_WEEK_IN_MONTH);

                        //FECHA DEL DATEPICKER
                        Date dateInicio = new Date(yearSelected,monthOfYearSelected,dayOfMonthSelected);
                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setTime(dateInicio);
                        int dayWeekGC = cal.get(Calendar.DAY_OF_WEEK);
                        int weekMonthGC = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);

                        if (yearSelected > myear || yearSelected < myear) {
                            view.updateDate(myear, mmonth, mday);
                        }
                        if(dayWeekGC == 1 || dayWeekGC == 2) {
                            view.updateDate(myear, mmonth, mday);
                        }
                        if(weekMonthSystem < 4 && monthOfYearSelected == mmonth)
                            if(weekMonthSystem < weekMonthGC){
                                view.updateDate(myear, mmonth, mday);
                            }else if(weekMonthSystem > weekMonthGC+1){
                                view.updateDate(myear, mmonth, mday);
                            }
                        if(weekMonthSystem < 4 && monthOfYearSelected > mmonth)
                            view.updateDate(myear, mmonth, mday);
                        if(weekMonthSystem > 3 && monthOfYearSelected > mmonth)
                            if(weekMonthGC > 1){
                                view.updateDate(myear, mmonth, mday);
                            }
                        if(weekMonthSystem > 1 && weekMonthSystem < 4 && monthOfYearSelected != mmonth)
                            view.updateDate(myear, mmonth, mday);

                        if(monthOfYearSelected+1 < mmonth)
                            view.updateDate(myear, mmonth, mday);
                        if(weekMonthSystem == 1 && monthOfYearSelected == mmonth-1){
                            if(weekMonthGC < 4)
                                view.updateDate(myear, mmonth, mday);
                        }

                    }
                };
                return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;
            // set selected date into textview
            lblSetDate.setText(new StringBuilder().append(mday).append("-").append(mmonth + 1)
                    .append("-").append(myear)
                    .append(" "));
        }
    };

}
