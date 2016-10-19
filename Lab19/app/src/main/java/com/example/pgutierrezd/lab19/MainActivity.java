package com.example.pgutierrezd.lab19;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Mi Toolbar");



        MainFragment mf = MainFragment.newInstance("Hola","Mundo");

        getSupportFragmentManager().beginTransaction().replace(R.id.container,mf,"Main Fragment").commit();
    }

    @Override
    public void onFragmentInteraction() {
        Toast.makeText(this,"Interacción con el fragment",Toast.LENGTH_SHORT).show();
    }
}
