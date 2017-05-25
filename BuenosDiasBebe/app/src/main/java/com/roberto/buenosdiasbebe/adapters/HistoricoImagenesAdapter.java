package com.roberto.buenosdiasbebe.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roberto.buenosdiasbebe.R;
import com.roberto.buenosdiasbebe.activities.MostrarNotificacion;

import java.util.ArrayList;

/**
 * Created by Usr on 25/05/2017.
 */

public class HistoricoImagenesAdapter extends BaseAdapter {

    /**
     * Atributo referido al contexto de la aplicacion.
     */
    private Context context;

    /**
     * Atributo referido al historial de fotografias recibidas.
     */

    private ArrayList<Bitmap> listaImagenes;
    /**
     * Fechas asociadas a las fotografias.
     */
    private ArrayList<String> listaFechas;


    private Bitmap imagen_intent;

    /**
     * Constructor de la clase HistoricoImagenesAdapter.
     * @param context
     * @param listaImagenes
     * @param listaFechas
     */

    public HistoricoImagenesAdapter(Context context, ArrayList<Bitmap> listaImagenes,ArrayList<String> listaFechas){
        this.context=context;
        this.listaImagenes=listaImagenes;
        this.listaFechas=listaFechas;
    }

    /**
     * Método que devuelve el número de veces que se llamara al método
     * getView()
     * @return
     */
    @Override
    public int getCount() {
        return listaImagenes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Método que devuelve fila inflada para cada elemento
     * del histórico de fotografías recibidas..
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creamos la vista a devolver.
        View filaInflada=null;
        //Obtenemos una referencia de la actividad.
        Activity activity=(Activity)context;
        //Creamos el objeto LayoutInflater.
        LayoutInflater layoutInflater=activity.getLayoutInflater();

        //Inflamos la vista
        if(convertView==null){
            Log.d(getClass().getCanonicalName(),"Se infla la fila de la fotografía en la posicion "+position);
            //Obtenemos la fila inflada.
            filaInflada=layoutInflater.inflate(R.layout.fila,parent,false);

        }else{
            Log.d(getClass().getCanonicalName(),"Se recicla la fila en la posicion "+position);
            filaInflada=convertView;
        }

        //Obtenemos el ImageView.
        ImageView imagenDescargada=(ImageView)filaInflada.findViewById(R.id.imagen_recibida);
        //Seteamos la imagen del array del historial de fotográfias.
        Bitmap imagen=listaImagenes.get(position);
        imagen_intent=imagen;
        Log.d(getClass().getCanonicalName(),"Fotografia:"+imagen.toString());
        imagenDescargada.setImageBitmap(imagen);
        imagenDescargada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Pinchando sobre la imagen la mostramos.
                Intent intent=new Intent(context, MostrarNotificacion.class);
                intent.putExtra("imagen",imagen_intent);
                context.startActivity(intent);
            }
        });
        //Obtenemos el TextView.
        TextView nombreFichero=(TextView)filaInflada.findViewById(R.id.fechaFoto);
        //Seteamos la fecha de la fotografía.
        nombreFichero.setText(listaFechas.get(position));

        return filaInflada;




    }
}
