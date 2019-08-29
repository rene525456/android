package com.example.primerproyecto.vista.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primerproyecto.R;
import com.example.primerproyecto.controlador.HelperProducto;
import com.example.primerproyecto.modelo.Producto;
import com.example.primerproyecto.vista.adapter.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;

public class actividadProductoBD extends AppCompatActivity implements View.OnClickListener {

    EditText cajaNombre, cajaDescripcion, cajaCodigo, cajaExistencia, cajaPrecio;
    Button botonGuardar, botonModificar, botonEliminar, botonEliminarCodigo, botonBuscarTodos,
            botonBuscarCodigo;
    RecyclerView recyclerViewProducto;
    HelperProducto helperProducto;
    ProductoAdapter adapter;
    private List<Producto> listaProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_producto_bd);
        tomarControl();
        helperProducto = new HelperProducto(actividadProductoBD.this);
    }

    public void cargarLista(List<Producto> lista){
        listaProducto = new ArrayList<Producto>();
        listaProducto = lista;
        recyclerViewProducto.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductoAdapter(listaProducto);
        adapter.setOnCLickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto producto = listaProducto.get(recyclerViewProducto.getChildAdapterPosition(v));
                cajaCodigo.setText(producto.getCodigo());
                cajaNombre.setText(producto.getNombre());
                cajaDescripcion.setText(producto.getDescripcion());
            }
        });
        recyclerViewProducto.setAdapter(adapter);
    }

    private void tomarControl(){
        cajaNombre = findViewById(R.id.txtNombreBD);
        cajaDescripcion = findViewById(R.id.txtDescripcionBD);
        cajaCodigo = findViewById(R.id.txtCodigoBD);
        cajaExistencia = findViewById(R.id.txtExistenciaBD);
        cajaPrecio = findViewById(R.id.txtPrecioBD);
        botonGuardar = findViewById(R.id.btnGuardarBD);
        botonModificar  = findViewById(R.id.btnModificarBD);
        botonEliminar = findViewById(R.id.btnEliminarBD);
        botonEliminarCodigo = findViewById(R.id.btnEliminarCodigoBD);
        botonBuscarTodos = findViewById(R.id.btnBuscarTodosBD);
        botonBuscarCodigo = findViewById(R.id.btnBuscarCodigoBD);
        botonGuardar.setOnClickListener(this);
        botonModificar.setOnClickListener(this);
        botonEliminar.setOnClickListener(this);
        botonEliminarCodigo.setOnClickListener(this);
        botonBuscarTodos.setOnClickListener(this);
        botonBuscarCodigo.setOnClickListener(this);
        recyclerViewProducto = findViewById(R.id.recyclerProducto);
    }

    private void viewDialog(final Producto p) {
        final CharSequence[] opt = {"Editar", "Eliminar", "Caneclar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliga una opcion");
        builder.setItems(opt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opt[i].equals("Editar")) {
                    //editProduccto(p);
                } else {
                    if (opt[i].equals("Eliminar")) {
                        //deleteProducto(p.getCodigo());
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardarBD:
                Producto p1 = new Producto();
                p1.setNombre(cajaNombre.getText().toString());
                p1.setDescripcion(cajaDescripcion.getText().toString());
                p1.setCodigo(cajaCodigo.getText().toString());
                p1.setExistencia(Integer.parseInt(cajaExistencia.getText().toString()));
                p1.setPrecio(Double.parseDouble(cajaPrecio.getText().toString()));
                helperProducto.insertar(p1);
                cargarLista(helperProducto.getAllProductos());
                break;
            case R.id.btnModificarBD:
                Producto p2 = new Producto();
                p2.setNombre(cajaNombre.getText().toString());
                p2.setDescripcion(cajaDescripcion.getText().toString());
                p2.setCodigo(cajaCodigo.getText().toString());
                p2.setExistencia(Integer.parseInt(cajaExistencia.getText().toString()));
                p2.setPrecio(Double.parseDouble(cajaPrecio.getText().toString()));
                helperProducto.modificar(p2);
                cargarLista(helperProducto.getAllProductos());
                break;
            case R.id.btnEliminarCodigoBD:
                helperProducto.eliminarPorCodigo(cajaCodigo.getText().toString());
                cargarLista(helperProducto.getAllProductos());
                break;
            case R.id.btnEliminarBD:
                helperProducto.eliminarTodos();
                cargarLista(helperProducto.getAllProductos());
                break;
            case R.id.btnBuscarTodosBD:
                cargarLista(helperProducto.getAllProductos());
                break;
            case R.id.btnBuscarCodigoBD:
                cargarLista(helperProducto.getByCode(cajaCodigo.getText().toString()));
                break;
        }
    }
}