package com.example.primerproyecto;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.primerproyecto.adapter.ProductoAdapter;
import com.example.primerproyecto.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

public class ActividadProductoORM extends AppCompatActivity implements View.OnClickListener {

    Button botonAgregar;
    RecyclerView recyclerView;
    List<Producto> listaProducto;
    ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_producto_orm);
        botonAgregar = findViewById(R.id.btnAgregarORM);
        recyclerView = findViewById(R.id.recyclerORM);
        botonAgregar.setOnClickListener(this);
        cargarLista();
    }

    private void cargarLista(){
        listaProducto = new ArrayList<Producto>();
        listaProducto = Producto.getAll();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(listaProducto);
        adapter.setOnCLickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Producto producto = listaProducto.get(recyclerView.getChildAdapterPosition(v));
                final Dialog dlgModificar = new Dialog(ActividadProductoORM.this);
                dlgModificar.setContentView(R.layout.dlg_modificar_producto_orm);

                TextView cCodigo = dlgModificar.findViewById(R.id.txtCodigoModificarORM);
                final TextView cNombre = dlgModificar.findViewById(R.id.txtNombreModificarORM);
                final TextView cDescripcion = dlgModificar.findViewById(R.id.txtDescripcionModificarORM);
                TextView cExistencia = dlgModificar.findViewById(R.id.txtExistenciaModificarORM);
                TextView cPrecio = dlgModificar.findViewById(R.id.txtPrecioModificarORM);

                cCodigo.setText(producto.getCodigo());
                cNombre.setText(producto.getNombre());
                cDescripcion.setText(producto.getDescripcion());
                //cExistencia.setText(producto.getExistencia());
                //cPrecio.setText(producto.getPrecio()+"");

                Button botonModificar = dlgModificar.findViewById(R.id.btnMoficiarORM);

                botonModificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        producto.setNombre(cNombre.getText().toString());
                        producto.setDescripcion(cDescripcion.getText().toString());

                        producto.save();
                        cargarLista();
                        dlgModificar.hide();

                    }
                });


                dlgModificar.show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregarORM:
                Intent intent = new Intent(ActividadProductoORM.this,ActividadAddProductoORM.class);
                startActivity(intent);
                break;
        }
    }
}
