package com.example.primerproyecto.vista.activities;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primerproyecto.R;

public class ActividadSensorAcelerometro extends AppCompatActivity implements SensorEventListener {

    EditText campoX, campoY, campoZ;
    SensorManager manejador;
    Sensor acelerometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_sensor_acelerometro);
        campoX = findViewById(R.id.txtAcelerometroX);
        campoY = findViewById(R.id.txtAcelerometroY);
        campoZ = findViewById(R.id.txtAcelerometroZ);
        manejador = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = manejador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x,y,z;
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];
        campoX.setText("");
        campoX.append(x + "");
        campoY.setText("");
        campoY.append(y + "");
        campoZ.setText("");
        campoZ.append(z + "");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        manejador.registerListener(this, acelerometro, manejador.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manejador.unregisterListener(this);
    }
}