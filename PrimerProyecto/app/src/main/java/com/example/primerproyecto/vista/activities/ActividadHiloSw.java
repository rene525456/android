package com.example.primerproyecto.vista.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primerproyecto.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ActividadHiloSw extends AppCompatActivity implements View.OnClickListener{

    Button boton;
    TextView datos;
    String ruta = "https://samples.openweathermap.org/data/2.5/weather?id=2172797&appid=b6907d289e10d714a6e88b30761fae22";
    ObtenerServicioWeb hiloConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_hilo_sw);
        boton = findViewById(R.id.btnCargarHSW);
        datos = findViewById(R.id.lblDatosHSW);
        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCargarHSW:
                hiloConexion = new ObtenerServicioWeb();
                hiloConexion.execute(ruta);
                break;
        }
    }

    class ObtenerServicioWeb extends AsyncTask<String, Void, String>{

        // método procesa la petición del servicio web
        @Override
        protected String doInBackground(String... parametros) {
            String rutaUrl = parametros[0];
            String consulta = null;
            try {
                URL url = new URL(rutaUrl);
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                int respuesta = conexion.getResponseCode();
                if (respuesta == HttpURLConnection.HTTP_OK){
                    InputStream in = new BufferedInputStream(conexion.getInputStream());
                    BufferedReader lector = new BufferedReader(new InputStreamReader(in));
                    consulta = lector.readLine();
                }else
                    consulta += "error :" + respuesta;
            }catch (Exception ex){

            }
            return consulta;
        }

        @Override
        protected void onPostExecute(String s){
            datos.setText(s);
        }

    }

}


