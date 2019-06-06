package com.example.primerproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ActividadLeerMI extends AppCompatActivity implements View.OnClickListener{

    Button boton;
    TextView datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_leer_mi);

        datos = findViewById(R.id.lblDatosMI);
        boton = findViewById(R.id.btnLeerMI);
        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLeerMI:
                try {
                    BufferedReader lector = new BufferedReader(new InputStreamReader(openFileInput("archivo.txt")));
                    datos.setText(lector.readLine());
                    lector.close();
                }catch (Exception ex){
                    Log.e("Error de Lectura",ex.getMessage());
                }
                break;
        }
    }
}
