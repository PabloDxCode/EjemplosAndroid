package com.example.pgutierrezd.becaidspractica.helpers;

import android.widget.EditText;

/**
 * Created by pgutierrezd on 12/08/2016.
 */
public class ValidateFields {

    public boolean validateFields(EditText hrs, EditText comments){
        boolean bandera = true;
        if(hrs.getText().toString().equals("")) {
            hrs.setError("Es necesario llenar este campo");
            bandera = false;
        }
        if(comments.getText().toString().equals("")) {
            comments.setError("Es necesario llenar este campo");
            bandera = false;
        }
        return bandera;
    }

}
