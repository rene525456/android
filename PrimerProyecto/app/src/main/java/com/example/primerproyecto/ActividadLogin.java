package com.example.primerproyecto;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primerproyecto.controlador.swvolley.ServicioWebVolly;

public class ActividadLogin extends AppCompatActivity {

    TextView datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_login);
        datos = findViewById(R.id.lblLogin);
        ServicioWebVolly sw = new ServicioWebVolly(ActividadLogin.this);
        datos.setText("hola " + sw.estaAutenticado() + " todos");
        /*
        if(sw.estaAutenticado())

            datos.setText("autenticado");
        else
            datos.setText("no autenticado");
        */
    }
}
