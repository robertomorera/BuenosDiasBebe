package com.roberto.buenosdiasbebe.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import com.roberto.buenosdiasbebe.R;
import com.roberto.buenosdiasbebe.utils.Utils;

import java.util.Calendar;

/**
 * Created by Usr on 23/05/2017.
 */

public class DialogHora extends DialogFragment implements TimePickerDialog.OnTimeSetListener
{


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.d(getClass().getCanonicalName(),"La hora seleccionada por el usuario es: "+hourOfDay+":"+minute);
        //Guardamos la hora en la caja de texto.
        TextView caja_hora=(TextView)getActivity().findViewById(R.id.caja_hora_alarma);
        String horaSeleccionada=hourOfDay+":"+minute;
        //Guardamos los parámetros de la alarma.
        SharedPreferences parametros_alarma=getActivity().getSharedPreferences("parametros_alarma",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=parametros_alarma.edit();
        editor.putInt("hourOfDay",hourOfDay);
        editor.putInt("minute",minute);
        //Guardamos lo datos.
        editor.commit();
        //Formateamos la hora.
        String horaFormateada= Utils.formatearHora(horaSeleccionada);
        Log.d(getClass().getCanonicalName(),"Hora formateada: "+horaFormateada);
        //Seteamos la hora en el textView.
        caja_hora.setText(horaFormateada);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog timePicker=null;
        Calendar calendar=Calendar.getInstance();
        //Seleccionamos el formato de hora y minutos.
        int hora=calendar.get(Calendar.HOUR_OF_DAY);
        int minuto=calendar.get(Calendar.MINUTE);
        timePicker=new TimePickerDialog(getActivity(),this,hora,minuto,true);
        return timePicker;
    }
}
