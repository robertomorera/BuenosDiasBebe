package com.roberto.buenosdiasbebe.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.roberto.buenosdiasbebe.adapters.HistoricoImagenesAdapter;
import com.roberto.buenosdiasbebe.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class HistoricoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        //Ponemos la toolbar personalizada
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Obtenemos los datoa para el histórico de fotográfias recibidas.
        ArrayList<Bitmap> listaImagenes=new ArrayList<Bitmap>();
        ArrayList<String> listaFechas=new ArrayList<String>();
        listaImagenes=obtenerImagenesGuardadas();
        listaFechas=obtenerFechasImagenes();
        Log.d(getClass().getCanonicalName(),"Historico de imagenes:"+listaImagenes.toString());
        Log.d(getClass().getCanonicalName(),listaFechas.toString());
        //Obtenemos el ListView del historial.
        ListView listaFotografias=(ListView)findViewById(R.id.lista_fotos_recibidas);
        //Creamos el adaptador del ListView para pasarle los datis y qu infle las distintas filas.
        HistoricoImagenesAdapter historicoImagenesAdapter=new HistoricoImagenesAdapter(this,listaImagenes,listaFechas);
        //Asociamos listener y adapter.
        listaFotografias.setAdapter(historicoImagenesAdapter);
    }

    /**
     * Método que obtiene las imagenes del histórico.
     * @return
     */
    private ArrayList<Bitmap>  obtenerImagenesGuardadas(){
        ArrayList<Bitmap> listaImagenes=new ArrayList<Bitmap>();
        //Obtenemos las imagenes guardadas por la aplicación.
        File[] listaFicheros=getApplicationContext().getFilesDir().listFiles();
        for(int i=0;i< listaFicheros.length;i++){
            //Obtenemos el nombre del fichero.
            String nombreFichero=listaFicheros[i].getName();
            try {
                FileInputStream fileInputStream = new FileInputStream(getApplicationContext().getFilesDir().getPath()+ "/"+nombreFichero);
                //Decodificamos a bitmap.
                Bitmap imagen= BitmapFactory.decodeStream(fileInputStream);
                //Añadimos la imagen al arrayList.
                listaImagenes.add(imagen);
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e(getClass().getCanonicalName(),"Error",e);
            }
        }
        return listaImagenes;
    }

    private ArrayList<String>  obtenerFechasImagenes(){
        ArrayList<String> listaFechas=new ArrayList<String>();
        //Obtenemos las imagenes guardadas por la aplicación.
        File[] listaFicheros=getApplicationContext().getFilesDir().listFiles();
        for(int i=0;i< listaFicheros.length;i++){
            //Obtenemos el nombre del fichero.
            String nombreFichero=listaFicheros[i].getName();
            listaFechas.add(nombreFichero);
        }
        return listaFechas;
    }
}

