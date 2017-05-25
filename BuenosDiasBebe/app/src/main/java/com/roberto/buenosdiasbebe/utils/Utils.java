package com.roberto.buenosdiasbebe.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.roberto.buenosdiasbebe.entidades.BuenosDias;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Usr on 23/05/2017.
 */

public class Utils {

   public static String formatearHora(String horaSeleccionada){
       String horaFormateada=null;
       String[] cadenas=horaSeleccionada.split(":");
       String hora=cadenas[0];
       String minuto=cadenas[1];
       if(hora.length()==1){
           hora="0"+hora;
       }

       if(minuto.length()==1){
           minuto="0"+minuto;
       }

       horaFormateada=hora+":"+minuto;

       return horaFormateada;
   }

   public static String formatearFecha(Date fecha){
       String fecha_formateada=null;
       DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
       fecha_formateada=formatter.format(fecha);
       String[] cadenasFecha=fecha_formateada.split("/");
       fecha_formateada=cadenasFecha[0]+cadenasFecha[1]+cadenasFecha[2];
       Log.d("Utils.java","Fecha formateada:"+fecha_formateada);
       return fecha_formateada;
   }

   public static String getFechaFoto(BuenosDias buenosDias){
       String fechaFoto=null;
       fechaFoto=buenosDias.getDia()+"-"+buenosDias.getMes()+"-"+buenosDias.getAnio();
       return fechaFoto;
   }

   public static Bitmap decodificarImagen(BuenosDias buenosDias){
       Bitmap imagen=null;
       String fotoMensaje=buenosDias.getFoto();
       byte[] decodedString = Base64.decode(fotoMensaje, Base64.DEFAULT);
       imagen= BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
       return imagen;
   }

}
