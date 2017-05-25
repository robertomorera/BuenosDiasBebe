package com.roberto.buenosdiasbebe.https;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.roberto.buenosdiasbebe.entidades.BuenosDias;
import com.roberto.buenosdiasbebe.utils.Constantes;
import com.roberto.buenosdiasbebe.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Usr on 24/05/2017.
 */

public class DescargarMensajesBuenosDias extends AsyncTask<Void,Void,BuenosDias> {
    @Override
    protected BuenosDias doInBackground(Void... params) {
        BuenosDias mensajeBuenosDias=null;
        //Construimos la ruta del servidor.
        Date fecha_actual=new Date();
        String fecha_formateada= Utils.formatearFecha(fecha_actual);
        String url_servidor= Constantes.URL_SERVIDOR+fecha_formateada;
        Log.d(getClass().getCanonicalName(),"URL de conexión: "+url_servidor);
        URL url=null;
        HttpURLConnection request=null;
        InputStream inputStream=null;
        InputStreamReader inputStreamReader=null;
        int codigoRespuesta=-1;
        String mensaje_devuelto=null;
        try{
            //Creamos la URL de conexión.
            url=new URL(url_servidor);
            //Abrimos la conexión.
            request=(HttpURLConnection)url.openConnection();
            //Recuperamos el código de respuesta.
            codigoRespuesta=request.getResponseCode();
            if(codigoRespuesta==HttpURLConnection.HTTP_OK){
                inputStream=request.getInputStream();
                inputStreamReader=new InputStreamReader(inputStream);
                //Creamos un bufferedReader.
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                //Obtenemos el mensaje devuelto por el servidor.
                mensaje_devuelto=bufferedReader.readLine();
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                //Parseamos el mensaje.
                Gson gson=new Gson();
                //Lo transformamos en un objeto de tipo BuenosDias.
                mensajeBuenosDias=gson.fromJson(mensaje_devuelto,BuenosDias.class);
            }

        }catch(Exception e){
            Log.e(getClass().getCanonicalName(),"La descarga del mensaje fue erronea",e);
        }finally{
            if(request!=null){
                //Cerramos la conexión.
                request.disconnect();
            }
        }
        return mensajeBuenosDias;

    }
}

