package com.example.pgutierrezd.becaidspractica;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pgutierrezd.becaidspractica.datasources.GetDataSource;
import com.example.pgutierrezd.becaidspractica.helpers.ValidateFields;
import com.example.pgutierrezd.becaidspractica.models.DiaSemana;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RegisterActivitiesActivity extends AppCompatActivity {

    private int myear,mmonth,mday;
    private Button btnDisplayCalendar, btnSaveActivity;
    private TextView lblSetDate;
    private EditText txtInputHrs, txtDescriptionActivityDay;
    static final int DATE_DIALOG_ID = 999;

    private GetDataSource getDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activities);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSaveActivity = (Button) findViewById(R.id.btnSaveActivityDay);
        txtInputHrs = (EditText) findViewById(R.id.txtInputHrs);
        txtDescriptionActivityDay = (EditText) findViewById(R.id.txtDescriptionActivityDay);

        setCurrentDateOnView();
        addListenerOnButton();

        getDataSource = new GetDataSource(this);
        btnSaveActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateFields validateFields = new ValidateFields();
                if(validateFields.validateFields(txtInputHrs, txtDescriptionActivityDay)) {
                    if (Integer.parseInt(txtInputHrs.getText().toString()) > 8 || Integer.parseInt(txtInputHrs.getText().toString()) < 1) {
                        Snackbar.make(view, "El numero de horas es mayor  o menor al esperado", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        getDataSource.open();
                        DiaSemana diaSemana = getDataSource.validateDayActivity(getDataView());
                        getDataSource.close();
                        if(diaSemana == null) {
                            Toast.makeText(getApplicationContext(), "¡Estas insertando mas horas de las permitadas!", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Actividad " + diaSemana.getFecha() + " Fue salvada", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivitiesActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }

    private DiaSemana getDataView(){
        DiaSemana diaSemana = new DiaSemana();
        diaSemana.setFecha(lblSetDate.getText().toString());
        diaSemana.setHrs(txtInputHrs.getText().toString());
        diaSemana.setComentario(txtDescriptionActivityDay.getText().toString());
        return diaSemana;
    }

    // display current date
    private void setCurrentDateOnView() {

        lblSetDate = (TextView) findViewById(R.id.lblSetDate);

        final Calendar c = Calendar.getInstance();
        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);

        lblSetDate.setText(new StringBuilder()

                .append(mmonth + 1).append("-").append(mday).append("-")
                .append(myear));
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
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;

            lblSetDate.setText(new StringBuilder().append(mday).append("-").append(mmonth + 1)
                    .append("-").append(myear)
                    .append(" "));
        }
    };

}
