package com.example.primerproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.primerproyecto.modelo.Producto;

public class ActividadAddProductoORM extends AppCompatActivity implements View.OnClickListener{

    EditText cajaNombre, cajaCodigo, cajaDescripcion, cajaExistencia, cajaPrecio;
    Button botonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_add_producto_orm);
        tomarControl();
    }

    private void tomarControl(){
        cajaCodigo = findViewById(R.id.txtCodigoORM);
        cajaNombre = findViewById(R.id.txtNombreORM);
        cajaDescripcion = findViewById(R.id.txtDescripcionORM);
        cajaExistencia = findViewById(R.id.txtExistenciaORM);
        cajaPrecio = findViewById(R.id.txtPrecioORM);
        botonGuardar = findViewById(R.id.btnGuardarProductoORM);
        botonGuardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardarProductoORM:
                Producto producto = new Producto();
                producto.setCodigo(cajaCodigo.getText().toString());
                producto.setNombre(cajaNombre.getText().toString());
                producto.setDescripcion(cajaDescripcion.getText().toString());
                producto.setExistencia(Integer.parseInt(cajaExistencia.getText().toString()));
                producto.setPrecio(Double.parseDouble(cajaPrecio.getText().toString()));
                producto.save();
                break;
        }
    }
}