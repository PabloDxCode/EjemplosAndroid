package com.example.pgutierrezd.lab1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private Button mButton;
    private TextView mTexto1, mTexto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.btn_texto);
        mTexto1 = (TextView) findViewById(R.id.lbl_texto1);
        mTexto2 = (TextView) findViewById(R.id.lbl_texto2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTexto2.setText(mTexto1.getText().toString());
                //mTexto1.setTextColor(Color.argb(180,200,0,0));
                Intent intent = new Intent(MainActivity.this,CalculadoraActivity.class);
                startActivity(intent);
            }
        });

        //mButton.setOnClickListener(new MyClickListener());
        //mButton.setOnClickListener(this);
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Log.d("Lab1", "Dentro del onClick()");
        }
    }

}
