package com.example.pgutierrezd.becaidspracticav2.helpers;

import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public class ValidateFields {

    public boolean validateFieldsEditText(EditText[] campos){
        boolean bandera = true;
        for (EditText dato: campos) {
            if(dato.getText().toString().equals("")) {
                dato.setError("Es necesario llenar este campo");
                bandera = false;
            }
        }
        return bandera;
    }
    public boolean validateSpinners(Spinner[] spnn){
        boolean bandera = true;
        for (Spinner dato: spnn) {
            if(dato.getItemAtPosition(dato.getSelectedItemPosition()).toString().equals("Seleccioname")) {
                bandera = false;
            }
        }
        return bandera;
    }

    public int validateHrs(EditText hrs, Spinner tipoActividad){
        if(tipoActividad.getItemAtPosition(tipoActividad.getSelectedItemPosition()).toString().equals("Normal")){
            if (Double.parseDouble(hrs.getText().toString()) > 0 || Double.parseDouble(hrs.getText().toString()) < 9)
                return 1;
            else
                return 2;
        }else{
            if (Double.parseDouble(hrs.getText().toString()) == 4 || Double.parseDouble(hrs.getText().toString()) == 8)
                return 3;
            else
                return 2;
        }
    }
}
