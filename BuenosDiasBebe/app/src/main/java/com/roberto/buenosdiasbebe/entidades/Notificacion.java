package com.roberto.buenosdiasbebe.entidades;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

import com.roberto.buenosdiasbebe.R;
import com.roberto.buenosdiasbebe.activities.MostrarNotificacion;
import com.roberto.buenosdiasbebe.utils.Constantes;

/**
 * Created by Usr on 24/05/2017.
 */

public class Notificacion {

    public static void lanzarNotificacion(Bitmap bitmap, Context context){

        //Builder y notification manager y preparo el aspecto de la notificación.
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.icono);
        builder.setContentTitle(Constantes.TITULO_NOTIFICACION);
        builder.setContentText(Constantes.BUENOS_DIAS_BEBE);
        //Cuando pulso sobre la notificación se elimina de forma automática.
        builder.setAutoCancel(true);
        //Aquí iré cuando el usuario toque la notificación.
        Intent resultIntent=new Intent(context,MostrarNotificacion.class);
        resultIntent.putExtra("imagen",bitmap);
        //Se usa para acceder desde el exterior a la app y envuelve al intent normal, le añade una capa de seguridad.
        PendingIntent pendingIntent=PendingIntent.getActivity(context,(int)System.currentTimeMillis(),resultIntent,PendingIntent.FLAG_ONE_SHOT);
        //Asociamos el pendingIntent a la notificación.
        builder.setContentIntent(pendingIntent);
        //La imagen adjunta se ve en el cuerpo de la notificación
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        //Lanzamos la notificacion.
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Construimos la notificación.
        Notification notification=builder.build();
        //Notificamos la notificación.
        notificationManager.notify(350,notification);
    }
}
