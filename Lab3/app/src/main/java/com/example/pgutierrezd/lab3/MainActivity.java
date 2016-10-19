package com.example.pgutierrezd.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(getApplication());
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mensaje = "";
        switch (item.getItemId()){
            case R.id.opcion1:
                mensaje = "Opción 1";
                break;
            case R.id.opcion2:
                mensaje = "Opción 2";
                break;
        }

        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();

        return super.onOptionsItemSelected(item);
    }
}
