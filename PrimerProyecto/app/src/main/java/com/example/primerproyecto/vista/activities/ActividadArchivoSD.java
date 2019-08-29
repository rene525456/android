package com.example.primerproyecto.vista.activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primerproyecto.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ActividadArchivoSD extends AppCompatActivity implements View.OnClickListener{

    EditText cajaNombre, cajaCodigo, cajaExistencia, cajaPrecio;
    Button botonEscribir, botonLeer;
    TextView datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_archivo_sd);
        cajaNombre = findViewById(R.id.txtNombreProductoSD);
        cajaCodigo = findViewById(R.id.txtPrecioProductoSD);
        cajaExistencia = findViewById(R.id.txtExistenciaProductoSD);
        cajaPrecio = findViewById(R.id.txtPrecioProductoSD);
        datos = findViewById(R.id.lblDatosSD);
        botonEscribir = findViewById(R.id.btnEscribirSD);
        botonLeer = findViewById(R.id.btnLeerSD);
        botonEscribir.setOnClickListener(this);
        botonLeer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEscribirSD:
                try {
                    /*File ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"archivo.txt");
                    OutputStreamWriter escritor = new OutputStreamWriter(new FileOutputStream(ruta));
                    escritor.write(cajaNombre.getText().toString() + ","
                            + cajaCodigo.getText().toString() + ","
                            + cajaExistencia.getText().toString() + ","
                            + cajaPrecio.getText().toString() + ";");
                    escritor.close();
                    */
                    File ruta = Environment.getExternalStorageDirectory();
//                    File ruta_sd = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File f = new File(ruta.getAbsolutePath(), "archivo.txt");

                    OutputStreamWriter fout =
                            new OutputStreamWriter(
                                    new FileOutputStream(f));

                    fout.write(cajaNombre.getText().toString() + ","
                            + cajaCodigo.getText().toString() + ","
                            + cajaExistencia.getText().toString() + ","
                            + cajaPrecio.getText().toString() + ";");
                    fout.close();

                }catch (Exception ex){
                    Log.e("Error Escritura SD",ex.getMessage());
                }
                break;

            case R.id.btnLeerSD:
                try {
                    /*File ruta = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),"archivo.txt");
                    BufferedReader lector = new BufferedReader(new InputStreamReader(new FileInputStream(ruta)));
                    datos.setText(lector.readLine());
                    lector.close();*/
                    File ruta = Environment.getExternalStorageDirectory();

                    File f = new File(ruta.getAbsolutePath(), "archivo.txt");

                    BufferedReader fin =
                            new BufferedReader(
                                    new InputStreamReader(
                                            new FileInputStream(f)));

                    String texto = fin.readLine();
                    datos.setText(texto);
                    fin.close();
                }catch (Exception ex){
                    Log.e("Error Escritura SD",ex.getMessage());
                }
                break;
        }
    }
}