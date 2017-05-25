package com.roberto.buenosdiasbebe.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.roberto.buenosdiasbebe.R;
import com.roberto.buenosdiasbebe.activities.AjustesActivity;
import com.roberto.buenosdiasbebe.activities.HistoricoActivity;

/**
 * Created by Usr on 22/05/2017.
 */

public class ButtonListener implements View.OnClickListener {

    /**
     * Atributo del contexto de la aplicación.
     */
    private Context context;

    /**
     * Constructor de la clase ButtonListener
     * @param context
     */
    public ButtonListener(Context context){
        this.context=context;
    }

    @Override
    public void onClick(View v) {
        //Miramos el botón de la aplicación que ha tocado el usuario.
        Intent intent=null;
        Activity activity=(Activity)context;
        switch(v.getId()){

            case R.id.boton_ajustes:
                Log.d(getClass().getCanonicalName(),"El usuario ha tocado el botón de ajustes");
                //Creamos el intent para ir a la activity de ajustes.
                intent=new Intent(context, AjustesActivity.class);
                activity.startActivity(intent);
                break;
            case R.id.boton_historico:
                Log.d(getClass().getCanonicalName(),"El usuario ha pulsado el botón de histórico de fotos");
                intent=new Intent(context, HistoricoActivity.class);
                activity.startActivity(intent);
                break;
            default:
        }
    }
}
