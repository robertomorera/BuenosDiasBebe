package com.roberto.buenosdiasbebe.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.roberto.buenosdiasbebe.R;
import com.roberto.buenosdiasbebe.activities.AjustesActivity;

/**
 * Created by Usr on 23/05/2017.
 */

public class CheckListener implements CheckBox.OnCheckedChangeListener
{

    private Context context;

    public CheckListener(Context context){
        this.context=context;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        AjustesActivity activity=(AjustesActivity) context;
        switch(buttonView.getId()){
            case R.id.check_habilitar_notif:
                Log.d(getClass().getCanonicalName(),"El usuario ha pulsado el checkbox de notificaciones");
                if(buttonView.isChecked()){
                    //Si esta chequeado habilitamos las notificaciones.
                    activity.habilitarNotificaciones();
                    Log.d(getClass().getCanonicalName(),"Notificaciones habilitadas: "+activity.isHabilitarNotificaciones());
                }else{
                    //Si no está marcado habilitamos las notificaciones.
                    activity.deshabilitarNotificaciones();
                    //Si la alarma estaba programada hay que desprogramarla.
                    //Obtenemos el estado de la alarma.
                    //Obtenemos las preferencias del usuario.
                    SharedPreferences estado_alarma=activity.getSharedPreferences("estado_alarma", Context.MODE_PRIVATE);
                    boolean estadoAlarma=estado_alarma.getBoolean("estadoAlarma",true);
                    if(estadoAlarma){
                    activity.desprogramarAlarma();
                    }
                    Log.d(getClass().getCanonicalName(),"Notificaciones habilitadas:"+activity.isHabilitarNotificaciones());
                }
             break;
            default:
        }
    }
}
