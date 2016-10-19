package com.example.pgutierrezd.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculadoraActivity extends AppCompatActivity {

    private TextView mResultado;
    private EditText mMetros, mPeso;
    private Button mCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        mMetros = (EditText) findViewById(R.id.entrada1);
        mPeso = (EditText) findViewById(R.id.entrada2);
        mCalcular = (Button) findViewById(R.id.btnEnviar);
        mResultado = (TextView) findViewById(R.id.resultado);

        mCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    double peso = Double.parseDouble(mPeso.getText().toString());
                    double altura = Double.parseDouble(mMetros.getText().toString());
                    double r = (peso / Math.pow(altura,2));
                    mResultado.setText(String.format("IMC: %.2f",r));
                    if(r < 18.50){
                        Toast.makeText(getApplicationContext(), "Delgado" ,Toast.LENGTH_LONG).show();
                    }else  if(r >= 18.50 && r <= 24.99){
                        Toast.makeText(getApplicationContext(), "Normal" ,Toast.LENGTH_LONG).show();
                    }else if(r >= 25 && r < 30){
                        Toast.makeText(getApplicationContext(), "Sobre peso" ,Toast.LENGTH_LONG).show();
                    }else if(r > 40){
                        Toast.makeText(getApplicationContext(), "Obeso" ,Toast.LENGTH_LONG).show();
                    }

                }catch(NumberFormatException ex){
                    Toast.makeText(getApplicationContext(), "Algún campo esta vacío" ,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
