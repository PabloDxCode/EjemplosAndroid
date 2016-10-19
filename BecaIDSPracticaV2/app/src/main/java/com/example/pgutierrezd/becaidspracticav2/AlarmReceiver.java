package com.example.pgutierrezd.becaidspracticav2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.example.pgutierrezd.becaidspracticav2.dao.DAOActivityDayImp;
import com.example.pgutierrezd.becaidspracticav2.interfaces.DAOActivityDay;

/**
 * Created by joules on 24/06/16.
 */
public class AlarmReceiver extends BroadcastReceiver{

    private Context context;
    private DAOActivityDay daoActivityDay;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,"TAG");
        wl.acquire();

        daoActivityDay = new DAOActivityDayImp(context);
        if(daoActivityDay.validateNotification()){
            notification();
        }

        wl.release();
    }

    private void notification(){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_android_white_36dp)
                        .setContentTitle("Alerta!")
                        .setContentText("¡Actividades de hace dos días no estan completas o no estan registradas!")
                        .setAutoCancel(true)
                        .setSound(alarmSound);

        Intent resultIntent = new Intent(context,MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());
    }


}
