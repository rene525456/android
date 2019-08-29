package com.example.primerproyecto.vista.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primerproyecto.R;

public class ActividadRecibirParametros extends AppCompatActivity {

    TextView datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_recibir_parametros);
        datos = (TextView)findViewById(R.id.lblDatos);
        Bundle bundle = this.getIntent().getExtras();
        datos.setText("Bienvenido: " + bundle.getString("nombres") +  " "+ bundle.getString("dni"));
    }
}
