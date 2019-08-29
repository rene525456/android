package com.example.primerproyecto.vista.activities;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primerproyecto.R;

public class ActividadSesorProximidad extends AppCompatActivity implements SensorEventListener {

    SensorManager manejador;
    Sensor proximidad;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_sesor_proximidad);
        manejador = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximidad = manejador.getDefaultSensor(Sensor.TYPE_PROXIMITY);

    }


    @Override
    protected void onResume(){
        super.onResume();
        manejador.registerListener(this, proximidad, manejador.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manejador.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        if(x < proximidad.getMaximumRange()){
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        }
        else{
            getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
