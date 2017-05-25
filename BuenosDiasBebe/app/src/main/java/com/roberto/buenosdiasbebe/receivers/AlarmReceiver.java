package com.roberto.buenosdiasbebe.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.content.Context;
import android.content.Intent;


import com.roberto.buenosdiasbebe.entidades.BuenosDias;
import com.roberto.buenosdiasbebe.https.DescargarMensajesBuenosDias;
import com.roberto.buenosdiasbebe.entidades.Notificacion;
import com.roberto.buenosdiasbebe.utils.Constantes;
import com.roberto.buenosdiasbebe.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(getClass().getCanonicalName(),"Ha saltado la alarma");
        //Nos conectamos para descargar el objeto BuenosDias.
        DescargarMensajesBuenosDias descargarMensajesBuenosDias=new DescargarMensajesBuenosDias();
        try {
            BuenosDias mensaje_buenosDias=descargarMensajesBuenosDias.execute().get();
            Log.d(getClass().getCanonicalName(),"Mensaje descargado del servidor:"+mensaje_buenosDias.toString());
            if(mensaje_buenosDias!=null){
                //Decodificamos la imagen en BASE-64.
                Bitmap imagen= Utils.decodificarImagen(mensaje_buenosDias);
                //Obtenemos la fecha de la imagen.
                String fecha_foto=Utils.getFechaFoto(mensaje_buenosDias);
               //Guardamos la imagen en memoria interna.
               guardarImagen(imagen,fecha_foto,context);
               //Lanzamos la notificación.
                Notificacion.lanzarNotificacion(imagen,context);
                //Reprogramamos la alarma para el día siguiente.
                reprogramarAlarma(context);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(getClass().getCanonicalName(),"Error",e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.e(getClass().getCanonicalName(),"Error",e);
        }



    }

    private void guardarImagen(Bitmap bitmap,String nombreImagen,Context context){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        //Obtenemos el array de byte.
        byte[] byteArray = stream.toByteArray();

        try {
            FileOutputStream outputStream = context.getApplicationContext().
                    openFileOutput(nombreImagen+".jpg", Context.MODE_PRIVATE);
            outputStream.write(byteArray);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

    }


    public void reprogramarAlarma(Context context){
        //Obtenemos la referencia al servicio del sistema del AlarmManager.
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //Recuperamos la configuración de la alarma.
        SharedPreferences parametros_alarma=context.getSharedPreferences("parametros_alarma", Context.MODE_PRIVATE);
        int hourOfDay=parametros_alarma.getInt("hourOfDay",0);
        int minute=parametros_alarma.getInt("minute",0);
        Log.d(getClass().getCanonicalName(),"Parámetros de la alarma:"+hourOfDay+":"+minute);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.add(Calendar.DAY_OF_WEEK,1);
        long tiempo_alarma=calendar.getTimeInMillis();
        Log.d(getClass().getCanonicalName(),"Calendar:"+calendar.toString());
        Log.d(getClass().getCanonicalName(),"Tiempo de alarma:"+tiempo_alarma);
        //Ponemos el estado del alarma en programada.
        SharedPreferences estado_alarma=context.getSharedPreferences("estado_alarma", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=estado_alarma.edit();
        //Guardamos el boolean del estado de la alarma.
        editor.putBoolean("estadoAlarma",true);
        editor.commit();
        //Preparamos el intent para ir al receiver que tratará la alarma.
        Intent intent=new Intent(context,AlarmReceiver.class);
        //Preparamos el pendingIntent.
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context, Constantes.ID_PENDING_INTENT_ALARMA,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        SimpleDateFormat dateformatter = new SimpleDateFormat("E dd/MM/yyyy 'a las' hh:mm:ss");
        Log.d(getClass().getCanonicalName(), "Alarma reprogramada para "+ dateformatter.format(calendar.getTime()));
        //Programamos la alarma.
        alarmManager.set(AlarmManager.RTC_WAKEUP,tiempo_alarma,pendingIntent);
    }

}
