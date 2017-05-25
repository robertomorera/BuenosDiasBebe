package com.roberto.buenosdiasbebe.activities;

import android.app.AlarmManager;
import android.app.DialogFragment;
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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.roberto.buenosdiasbebe.receivers.AlarmReceiver;
import com.roberto.buenosdiasbebe.listeners.CheckListener;
import com.roberto.buenosdiasbebe.R;
import com.roberto.buenosdiasbebe.dialogs.DialogHora;
import com.roberto.buenosdiasbebe.utils.Constantes;

public class AjustesActivity extends AppCompatActivity {

    private boolean habilitarNotificaciones=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        //Ponemos la toolbar personalizada
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        recuperarAjustesUsuario();
        //Obtenemos los elementos de la activity.
        CheckBox check_notificaciones=(CheckBox)findViewById(R.id.check_habilitar_notif);
        TextView caja_hora_alarma=(TextView)findViewById(R.id.caja_hora_alarma);
        //Listener del checkbox.
        CheckListener checkListener=new CheckListener(this);
        check_notificaciones.setOnCheckedChangeListener(checkListener);
        //Obtenemos las referencias de los botones de la activity de ajustes de usuario.
        Button boton_guardar_ajustes=(Button)findViewById(R.id.boton_guardar_ajustes);
        Button boton_programar_alarma=(Button)findViewById(R.id.boton_programar_alarma);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CheckBox check_notificaciones=(CheckBox)findViewById(R.id.check_habilitar_notif);
        if(check_notificaciones.isChecked()){
            setHabilitarNotificaciones(true);
        }else{
            setHabilitarNotificaciones(false);
        }

        //Guardamos los ajustes seleccionados por el usuario en un sharedPreferences.
        SharedPreferences ajustes_usuario=getSharedPreferences("ajustes_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=ajustes_usuario.edit();
        //Guardamos el boolean de habilitacion de notificaciones.
        editor.putBoolean("habilitarNotificaciones",habilitarNotificaciones);
        //Guardamos la hora seleccionada por el usuario.
        TextView caja_hora_alarma=(TextView)findViewById(R.id.caja_hora_alarma);
        String horaSeleccionada=caja_hora_alarma.getText().toString();
        editor.putString("horaSeleccionada",horaSeleccionada);
        editor.commit();

    }

    public void mostrarDialogHora(View v){
        Log.d(getClass().getCanonicalName(),"El usuario ha pulsado la caja de texto para configurar la hora");
        DialogFragment fragmentHora=new DialogHora();
        fragmentHora.show(getFragmentManager(),"horario");
    }

    public void guardarAjustes(View v){
        //Guardamos los ajustes seleccionados por el usuario en un sharedPreferences.
        SharedPreferences ajustes_usuario=getSharedPreferences("ajustes_usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=ajustes_usuario.edit();
        //Guardamos el boolean de habilitacion de notificaciones.
        editor.putBoolean("habilitarNotificaciones",habilitarNotificaciones);
        //Guardamos la hora seleccionada por el usuario.
        TextView caja_hora_alarma=(TextView)findViewById(R.id.caja_hora_alarma);
        String horaSeleccionada=caja_hora_alarma.getText().toString();
        editor.putString("horaSeleccionada",horaSeleccionada);
        //Guardamos los cambios en el sharedPreferences.
        editor.commit();
        //Notificamos al usuario con un Toast que se han guardado los ajustes de forma correcta.
        Toast.makeText(this, Constantes.MENSAJE_TOAST_GUARDAR_AJUSTES,Toast.LENGTH_LONG).show();
    }

    public void recuperarAjustesUsuario(){
        //Recuperamos los valores de ajustes del usuario.
        //Guardamos los ajustes seleccionados por el usuario.
        SharedPreferences ajustes_usuario=getSharedPreferences("ajustes_usuario", Context.MODE_PRIVATE);
        String horaSeleccionada=ajustes_usuario.getString("horaSeleccionada","----");
        TextView caja_hora_alarma=(TextView)findViewById(R.id.caja_hora_alarma);
        caja_hora_alarma.setText(horaSeleccionada);
        boolean habilitarNotificaciones=ajustes_usuario.getBoolean("habilitarNotificaciones",false);
        CheckBox check_notificaciones=(CheckBox)findViewById(R.id.check_habilitar_notif);
        if(habilitarNotificaciones){
            check_notificaciones.setChecked(true);
            Log.d("Mensaje","sdasd");
        }else{
            check_notificaciones.setChecked(false);
            Log.d("Mensaje","hola");
        }

    }

    public void desprogramarAlarma(){

        Intent intentAlarm= new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,Constantes.ID_PENDING_INTENT_ALARMA,intentAlarm,PendingIntent.FLAG_NO_CREATE);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //Ponemos el estado de la alarma como desprogramada.
        SharedPreferences estado_alarma=getSharedPreferences("estado_alarma", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=estado_alarma.edit();
        //Guardamos el boolean del estado de la alarma.
        editor.putBoolean("estadoAlarma",false);
        editor.commit();
        alarmManager.cancel(pendingIntent);
        Log.d("MENSAJE","Alarma desprogramada");

    }

    public void habilitarNotificaciones(){
        setHabilitarNotificaciones(true);
    }

    public void deshabilitarNotificaciones(){
        setHabilitarNotificaciones(false);
    }

    public boolean isHabilitarNotificaciones() {
        return habilitarNotificaciones;
    }

    public void setHabilitarNotificaciones(boolean habilitarNotificaciones) {
        this.habilitarNotificaciones = habilitarNotificaciones;
    }




}
