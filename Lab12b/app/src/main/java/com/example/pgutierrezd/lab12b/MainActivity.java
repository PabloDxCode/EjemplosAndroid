package com.example.pgutierrezd.lab12b;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    /** Called when the activity is first created. */
    Button botonEnviarSMS;
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsEnviar, smsEntregado;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), PendingIntent.FLAG_ONE_SHOT);//FLAG_ONE_SHOT = para que solo se intente mandar una vez
        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED),0);

        botonEnviarSMS=(Button)findViewById(
                R.id.btnEnviarSMS);
        botonEnviarSMS.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        enviarSMS("5520438758","Estoy en curso");

                    }
                });
    }

    private void enviarSMS(String tel,String msg){
        SmsManager sms= SmsManager.getDefault();
        // destinationAddress - the address to send the message to
        // scAddress - is the service center address or null to use the current default SMSC
        // text - the body of the message to send
        // sentIntent -  if not NULL this PendingIntent is broadcast when the message is successfully
        //               sent, or failed. The result code will be Activity.RESULT_OK for success,
        //               or one of these errors: RESULT_ERROR_GENERIC_FAILURE
        //               RESULT_ERROR_RADIO_OFF, RESULT_ERROR_NULL_PDU.
        //               The per-application based SMS control checks sentIntent.
        //               If sentIntent is NULL the caller will be checked against all unknown
        //               applications, which cause smaller number of SMS to be sent in checking period.
        // deliveryIntent - if not NULL this PendingIntent is broadcast when the message is delivered
        //               to the recipient. The raw pdu of the status report is in the extended data ("pdu").
        sms.sendTextMessage(tel,null,msg,sentPI,deliveredPI);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsEnviar);
        unregisterReceiver(smsEntregado);
    }

    @Override
    protected void onResume() {
        super.onResume();

        smsEnviar = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case AppCompatActivity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:

                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:

                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:

                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:

                        break;
                }
            }
        };

        smsEntregado = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case AppCompatActivity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "Mensaje Entregado",Toast.LENGTH_SHORT).show();
                        break;
                    case AppCompatActivity.RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "Mensaje no se pudo entregar",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        registerReceiver(smsEnviar,new IntentFilter(SENT));
        registerReceiver(smsEntregado, new IntentFilter(DELIVERED));
    }
}