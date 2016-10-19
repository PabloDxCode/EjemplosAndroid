package com.example.pgutierrezd.becaidspracticav2.helpers;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by pgutierrezd on 16/08/2016.
 */
public class CalendarHelper {

    public void validateDate(DatePicker view, int yearSelected, int monthOfYearSelected, int dayOfMonthSelected,
                             int myear, int mmonth, int mday) {
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
}
