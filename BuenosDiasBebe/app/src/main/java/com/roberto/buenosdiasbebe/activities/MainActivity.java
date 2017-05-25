package com.roberto.buenosdiasbebe.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.roberto.buenosdiasbebe.receivers.AlarmReceiver;
import com.roberto.buenosdiasbebe.R;
import com.roberto.buenosdiasbebe.listeners.ButtonListener;
import com.roberto.buenosdiasbebe.dialogs.DialogApp;
import com.roberto.buenosdiasbebe.utils.Constantes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ponemos la toolbar personalizada
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Obtenemos los botones del menu principal de la app.
        Button boton_ajustes=(Button)findViewById(R.id.boton_ajustes);
        Button boton_historico=(Button)findViewById(R.id.boton_historico);
        Button boton_programar_alarma=(Button)findViewById(R.id.boton_programar_alarma);
        //Creamos los listener de los botones.
        ButtonListener listener_ajustes=new ButtonListener(this);
        ButtonListener listener_historico=new ButtonListener(this);
        //Asignamos los listeners a los botones.
        boton_ajustes.setOnClickListener(listener_ajustes);
        boton_historico.setOnClickListener(listener_historico);
        //Obtenemos las preferencias del usuario.
        SharedPreferences ajustes_usuario=getSharedPreferences("ajustes_usuario", Context.MODE_PRIVATE);
        boolean habilitarNotificaciones=ajustes_usuario.getBoolean("habilitarNotificaciones",true);
        Log.d(getClass().getCanonicalName(),"El valor recuperado es:"+habilitarNotificaciones);
        if(!habilitarNotificaciones){
            //Si las notificaciones están desactivadas deshabilitamos la programación de la alarma.
            boton_programar_alarma.setEnabled(false);
        }else{
            boton_programar_alarma.setEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
        Log.d(getClass().getCanonicalName(),"El usuario ha pulsado el botón de atrás se le pregunta si quiere cerrrar la app.");
        //Se muestra el AlertDialog para preguntar si queremos cerrar la aplicación.
        DialogApp dialog_salir_app=new DialogApp(this);
        dialog_salir_app.avisoSalidaApp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Button boton_programar_alarma=(Button)findViewById(R.id.boton_programar_alarma);
        //Obtenemos las preferencias del usuario.
        SharedPreferences ajustes_usuario=getSharedPreferences("ajustes_usuario", Context.MODE_PRIVATE);
        boolean habilitarNotificaciones=ajustes_usuario.getBoolean("habilitarNotificaciones",true);
        Log.d(getClass().getCanonicalName(),"El valor recuperado es:"+habilitarNotificaciones);
        if(!habilitarNotificaciones){
            //Si las notificaciones están desactivadas deshabilitamos la programación de la alarma.
            boton_programar_alarma.setEnabled(false);
        }else{
            boton_programar_alarma.setEnabled(true);
        }

    }

    public void programarAlarma(View v){
        //Obtenemos la referencia al servicio del sistema del AlarmManager.
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //Recuperamos la configuración de la alarma.
        SharedPreferences parametros_alarma=getSharedPreferences("parametros_alarma", Context.MODE_PRIVATE);
        int hourOfDay=parametros_alarma.getInt("hourOfDay",0);
        int minute=parametros_alarma.getInt("minute",0);
        Log.d(getClass().getCanonicalName(),"Parámetros de la alarma:"+hourOfDay+":"+minute);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        long tiempo_alarma=calendar.getTimeInMillis();
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
        SharedPreferences estado_alarma=getSharedPreferences("estado_alarma", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=estado_alarma.edit();
        //Guardamos el boolean del estado de la alarma.
        editor.putBoolean("estadoAlarma",true);
        editor.commit();
        //Preparamos el intent para ir al receiver que tratará la alarma.
        Intent intent=new Intent(this,AlarmReceiver.class);
        //Preparamos el pendingIntent.
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, Constantes.ID_PENDING_INTENT_ALARMA,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        SimpleDateFormat dateformatter = new SimpleDateFormat("E dd/MM/yyyy 'a las' hh:mm:ss");
        Log.d(getClass().getCanonicalName(), "Alarma programada para "+ dateformatter.format(calendar.getTime()));
        Toast.makeText(this,"Alarma programada para "+ dateformatter.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
        //Programamos la alarma.
        alarmManager.set(AlarmManager.RTC_WAKEUP,tiempo_alarma,pendingIntent);
        }


    }



