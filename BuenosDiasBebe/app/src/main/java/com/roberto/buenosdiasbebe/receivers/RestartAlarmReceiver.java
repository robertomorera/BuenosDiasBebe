package com.roberto.buenosdiasbebe.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.roberto.buenosdiasbebe.utils.Constantes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RestartAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            Log.d(getClass().getCanonicalName(),"Se ha recibido la señal de arranque del dispositivo" +
                    "toca reprogramar la alarma");
            //Reprogramamos la alarma.
            long tiempo=reprogramarTiempoAlarma(context);
            //Preparamos el intent para ir al receiver que tratará la alarma.
            Intent intent_alarma=new Intent(context,AlarmReceiver.class);
            //Preparamos el pendingIntent.
            PendingIntent pendingIntent=PendingIntent.getBroadcast(context, Constantes.ID_PENDING_INTENT_ALARMA,intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //Obtenemos la referencia al servicio del sistema del AlarmManager.
            AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP,tiempo,pendingIntent);

        }
    }


    public long reprogramarTiempoAlarma(Context context){
        //Recuperamos la configuración de la alarma.
        long tiempo_alarma=0;
        SharedPreferences parametros_alarma=context.getSharedPreferences("parametros_alarma", Context.MODE_PRIVATE);
        int hourOfDay=parametros_alarma.getInt("hourOfDay",0);
        int minute=parametros_alarma.getInt("minute",0);
        Log.d(getClass().getCanonicalName(),"Parámetros de la alarma:"+hourOfDay+":"+minute);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        tiempo_alarma=calendar.getTimeInMillis();
        long tiempo_actual=System.currentTimeMillis();
        Log.d(getClass().getCanonicalName(),"Tiempo actual:"+tiempo_actual);
        if(tiempo_actual>tiempo_alarma){
            //La hora de la alarma ya ha pasado se programa para el día siguiente.
            calendar.add(Calendar.DAY_OF_WEEK,1);
            //Actualizamos el tiempo de la alarma.
            tiempo_alarma=calendar.getTimeInMillis();
        }
        Log.d(getClass().getCanonicalName(),"Calendar:"+calendar.toString());
        Log.d(getClass().getCanonicalName(),"Tiempo de alarma:"+tiempo_alarma);
        //Ponemos el estado del alarma en programada.
        SharedPreferences estado_alarma=context.getSharedPreferences("estado_alarma", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=estado_alarma.edit();
        //Guardamos el boolean del estado de la alarma.
        editor.putBoolean("estadoAlarma",true);
        editor.commit();
        SimpleDateFormat dateformatter = new SimpleDateFormat("E dd/MM/yyyy 'a las' hh:mm:ss");
        Log.d(getClass().getCanonicalName(), "Alarma reprogramada para "+ dateformatter.format(calendar.getTime()));
        return tiempo_alarma;

    }
}
