package com.roberto.buenosdiasbebe.activities;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.roberto.buenosdiasbebe.R;

public class MostrarNotificacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_notificacion);
        //Ponemos la toolbar personalizada
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Obtenemos la imagen que se adjunto a la notificación.
        Bitmap bitmap=getIntent().getParcelableExtra("imagen");
        ImageView imagen=(ImageView)findViewById(R.id.imagen_descargada);
        imagen.setImageBitmap(bitmap);
    }
}
