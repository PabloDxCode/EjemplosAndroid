package com.example.pgutierrezd.lab14;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener{

    LocationManager locationManager;
    Geocoder geocoder;
    TextView textOut;
    String bestProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textOut=(TextView)findViewById(R.id.textOut);
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> providers=locationManager.getAllProviders();
        for(String prov:providers)
            Log.d("Proveedores: ",prov);
        Criteria criteria=new Criteria();
        bestProvider=locationManager.getBestProvider(criteria, false);
        Log.d("Proveedor seleccionado",bestProvider);
        try {
            geocoder = new Geocoder(this);
            Location lastLocation = locationManager.getLastKnownLocation(bestProvider);
            if (lastLocation != null)
                onLocationChanged(lastLocation);
        }catch (SecurityException ex){}

    }



    @Override
    protected void onPause() throws SecurityException{
        // TODO Auto-generated method stub
        super.onPause();
        locationManager.removeUpdates(this);
    }




    @Override
    protected void onResume() throws SecurityException{
        // TODO Auto-generated method stub
        super.onResume();
        locationManager.requestLocationUpdates(bestProvider,1000,10,this);
    }



    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        String texto=String.format("Lat:\t %f\nLong:\t %f\nAlt: \t %f\n",location.getLatitude(),
                location.getLongitude(),location.getAltitude());

        textOut.setText(texto);
        try {
            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 10);
            for(Address address:addresses){
                textOut.append("\n"+address.getAddressLine(0));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}