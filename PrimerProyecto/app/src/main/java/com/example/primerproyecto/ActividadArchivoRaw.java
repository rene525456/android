package com.example.primerproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ActividadArchivoRaw extends AppCompatActivity implements View.OnClickListener{

    TextView datos;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_archivo_raw);
        datos = findViewById(R.id.lblDatosRaw);
        boton = findViewById(R.id.btnArchivoLeerRaw);
        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnArchivoLeerRaw:
                try {
                    InputStream input = getResources().openRawResource(R.raw.archivo_raw);
                    BufferedReader lector = new BufferedReader(new InputStreamReader(input));
                    datos.setText(lector.readLine());
                    input.close();
                    lector.close();
                }catch (Exception ex){
                    Log.e("Error Raw Leer",ex.getMessage());
                }
                break;
        }
    }
}
