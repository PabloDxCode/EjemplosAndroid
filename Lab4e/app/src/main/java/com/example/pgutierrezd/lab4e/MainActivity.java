package com.example.pgutierrezd.lab4e;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView seleccionado;
    AutoCompleteTextView edit;
    String[] items = {
            "Pablo","Arcadio","Ivan","Paty","Josue","Emmanuel","Bernardo","Virginia",
            "Jose","Manuel","Virgilio","Emma","Arcelia"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seleccionado = (TextView) findViewById(R.id.seleccionado);
        edit = (AutoCompleteTextView) findViewById(R.id.edit);

        edit.addTextChangedListener(new EditWatcher());
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,items);
        edit.setAdapter(adapter);

    }



    private class EditWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            seleccionado.setText(edit.getText());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

}
