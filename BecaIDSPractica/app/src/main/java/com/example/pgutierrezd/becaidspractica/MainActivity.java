package com.example.pgutierrezd.becaidspractica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pgutierrezd.becaidspractica.adapters.MyAdapterActivities;
import com.example.pgutierrezd.becaidspractica.datasources.GetDataSource;
import com.example.pgutierrezd.becaidspractica.helpers.ValidateFields;
import com.example.pgutierrezd.becaidspractica.models.DiaSemana;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fab;
    private CollapsingToolbarLayout collapsing;

    private MyAdapterActivities myAdapterActivities;
    private GetDataSource getDataSource;

    private static final int ACTUALIZAR_ID=Menu.FIRST+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        getDataSource = new GetDataSource(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivitiesActivity.class);
                startActivity(intent);
            }
        });

        fillListView();
        registerForContextMenu(listView);
    }

    public void fillListView(){
        getDataSource.open();
        List<DiaSemana> diaSemanas = getDataSource.findAll();
        myAdapterActivities = new MyAdapterActivities(this, R.layout.registro_dia_semana_row,diaSemanas);
        listView.setAdapter(myAdapterActivities);
        getDataSource.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE,ACTUALIZAR_ID,Menu.NONE,"Actualizar");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case ACTUALIZAR_ID:
                AdapterView.AdapterContextMenuInfo info1=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                actualizar(info1.id+1);
                break;

        }
        return true;
    }

    private void actualizar(final long rowid){
        LayoutInflater inflater= LayoutInflater.from(this);
        View actualizarView=inflater.inflate(R.layout.agregar_main, null);
        DiaSemana diaSemana = new DiaSemana();

        getDataSource.open();
        diaSemana = getDataSource.findUniqueActivity((int)rowid);
        getDataSource.close();

        final TextView idActivity = (TextView) actualizarView.findViewById(R.id.lblIdActivityUpdate);
        final TextView title = (TextView) actualizarView.findViewById(R.id.lblDateUpdate);
        final EditText hrs =(EditText)actualizarView.findViewById(R.id.txtHrsUpdate);
        final EditText coments =(EditText)actualizarView.findViewById(R.id.txtDescriptionUpdate);

        idActivity.setText(""+diaSemana.getIdRegistroDiaSemana());
        title.setText(diaSemana.getFecha());
        hrs.setText(diaSemana.getHrs());
        coments.setText(diaSemana.getComentario());
        if(rowid >0){
            new AlertDialog.Builder(this).setTitle("Actualizar")
                    .setView(actualizarView)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ValidateFields validateFields = new ValidateFields();
                            if(validateFields.validateFields(hrs, coments)){
                                if(Integer.parseInt(hrs.getText().toString()) > 0 && Integer.parseInt(hrs.getText().toString()) < 9){
                                    procesarActualizar(idActivity.getText().toString(),title.getText().toString(),hrs.getText().toString(),
                                            coments.getText().toString());
                                }else{
                                    Toast.makeText(getApplicationContext(),"No puedes guardar mas de 8 o menos de 1 hrs",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Faltan datos por llenar",Toast.LENGTH_LONG).show();
                            }
                        }

                        private void procesarActualizar(String s,String s1, String s2, String s3) {
                            getDataSource.open();
                            if(getDataSource.updateActivity(s,s1,s2,s3) != null)
                                Toast.makeText(getApplicationContext(), "Datos Actualizados", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(getApplicationContext(), "Datos no actualizados", Toast.LENGTH_LONG).show();
                            fillListView();
                            getDataSource.close();
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                }
            }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
