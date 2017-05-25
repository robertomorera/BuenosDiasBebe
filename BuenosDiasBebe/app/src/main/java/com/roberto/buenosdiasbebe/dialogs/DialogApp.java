package com.roberto.buenosdiasbebe.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.roberto.buenosdiasbebe.R;

/**
 * Created by Usr on 22/05/2017.
 */

public class DialogApp {

    /**
     * Atributo referido al contexto de la aplicación.
     */
    private Context context;

    /**
     * Constructor de la clase DialogApp.
     * @param context
     */
    public DialogApp(Context context){
        this.context=context;
    }


    /**
     * Método que construye el alertDialog para preguntar
     * al usuario si desea salir de la aplicacion
     */

    public void avisoSalidaApp(){
        //Creamos la referencia del objeto AlertDialog.
        final AlertDialog dialogSalir = new AlertDialog.Builder(context).create();
        //Seteamos el titulo del alertDialog.
        dialogSalir.setTitle(R.string.titulo_dialog_salir);
        //Seteamos el mensaje del alertDialog.
        String mensajeDialogSalir=context.getResources().getText(R.string.mensaje_dialog_salir).toString();
        dialogSalir.setMessage(mensajeDialogSalir);
        //Creamos el boton afirmativo
        CharSequence textoBotonPositivo=context.getResources().getText(R.string.literal_Si);
        dialogSalir.setButton(AlertDialog.BUTTON_POSITIVE, textoBotonPositivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(getClass().getCanonicalName(),"El usuario ha pulsado el botón Si y saldrá de la aplicacion");
                //Cuando pulsamos el boton "Si" se cierra la aplicación
                Activity activity=(Activity)context;
                activity.finish();
            }
        });

        //Creamos el botón negativo.
        CharSequence textoBotonNegativo=context.getResources().getText(R.string.literal_No);
        dialogSalir.setButton(AlertDialog.BUTTON_NEGATIVE, textoBotonNegativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Cuando pulsamos el boton "No" se cierra el alertDialog ya que el usuario cancela la acción.
                Log.d(getClass().getCanonicalName(),"El usuario ha pulsado el botón No y no saldrá de la aplicacion");
                dialogInterface.cancel();
            }
        });

        //Mostramos el alertDialog.
        dialogSalir.show();
    }

}



