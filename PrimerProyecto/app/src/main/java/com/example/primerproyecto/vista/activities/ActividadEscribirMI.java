package com.example.primerproyecto.vista.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.primerproyecto.R;
import com.example.primerproyecto.controlador.ArchivoProducto;
import com.example.primerproyecto.modelo.Producto;

public class ActividadEscribirMI extends AppCompatActivity implements View.OnClickListener{

    EditText cajaCodigo, cajaNombre, cajaPrecio, cajaExistencia;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_escribir_mi);
        cajaCodigo = findViewById(R.id.txtCodigoProducto);
        cajaNombre = findViewById(R.id.txtNombreProducto);
        cajaPrecio = findViewById(R.id.txtPrecioProducto);
        cajaExistencia = findViewById(R.id.txtExistenciaProducto);
        boton = findViewById(R.id.btnRegistrarProducto);

        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistrarProducto:
                try {
                    ArchivoProducto archivoProducto = new ArchivoProducto(ActividadEscribirMI.this);
                    Producto producto = new Producto();
                    producto.setNombre(cajaNombre.getText().toString());
                    producto.setCodigo(cajaCodigo.getText().toString());
                    producto.setExistencia(Integer.parseInt(cajaExistencia.getText().toString()));
                    producto.setPrecio(Double.parseDouble(cajaPrecio.getText().toString()));
                    archivoProducto.guardarProducto(producto);
                    Toast.makeText(ActividadEscribirMI.this, "Producto Agregado", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Log.e("Error de Escritura",ex.getMessage());
                }
                break;
        }
    }
}
