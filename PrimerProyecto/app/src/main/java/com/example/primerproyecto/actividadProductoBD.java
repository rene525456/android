package com.example.primerproyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.primerproyecto.adapter.ProductoAdapter;
import com.example.primerproyecto.controlador.HelperProducto;
import com.example.primerproyecto.modelo.Producto;

public class actividadProductoBD extends AppCompatActivity implements View.OnClickListener {

    EditText cajaNombre, cajaDescripcion, cajaCodigo, cajaExistencia, cajaPrecio;
    Button botonGuardar, botonModificar, botonEliminar, botonBuscarTodos, botonBuscarCodigo;
    TextView datos;
    RecyclerView recyclerViewProducto;

    HelperProducto helperProducto;

    ProductoAdapter adapter;
    private List<Producto> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_producto_bd);
        cajaNombre = findViewById(R.id.txtNombreBD);
        cajaDescripcion = findViewById(R.id.txtDescripcionBD);
        cajaCodigo = findViewById(R.id.txtCodigoBD);
        cajaExistencia = findViewById(R.id.txtExistenciaBD);
        cajaPrecio = findViewById(R.id.txtPrecioBD);

        datos = findViewById(R.id.lblDatosBD);

        botonGuardar = findViewById(R.id.btnGuardarBD);
        botonModificar  = findViewById(R.id.btnModificarBD);
        botonEliminar = findViewById(R.id.btnEliminarBD);
        botonBuscarTodos = findViewById(R.id.btnBuscarTodosBD);
        botonBuscarCodigo = findViewById(R.id.btnBuscarCodigoBD);

        botonGuardar.setOnClickListener(this);
        botonModificar.setOnClickListener(this);
        botonEliminar.setOnClickListener(this);
        botonBuscarTodos.setOnClickListener(this);
        botonBuscarCodigo.setOnClickListener(this);


        // crear un objeto de tipo helper
        helperProducto = new HelperProducto(actividadProductoBD.this);
        list = new ArrayList<Producto>();
        list = helperProducto.getAllProductos();

        adapter = new ProductoAdapter(list);

        recyclerViewProducto = findViewById(R.id.recyclerProducto);
        recyclerViewProducto.setLayoutManager(new LinearLayoutManager(this));
        // generamos el evento
        adapter.setOnCLickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Producto producto = list.get(recyclerViewProducto.getChildAdapterPosition(v));
               viewDialog(producto)
           }
       }

        recyclerViewProducto.setAdapter(adapter);
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
                        deleteProducto(p.getCodigo());
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
                Producto producto = new Producto();
                producto.setNombre(cajaNombre.getText().toString());
                producto.setDescripcion(cajaDescripcion.getText().toString());
                producto.setCodigo(cajaCodigo.getText().toString());
                producto.setExistencia(Integer.parseInt(cajaExistencia.getText().toString()));
                producto.setPrecio(Double.parseDouble(cajaPrecio.getText().toString()));
                helperProducto.insertar(producto);
                break;
            case R.id.btnBuscarTodosBD:
                datos.setText(helperProducto.leerTodos());
                break;
            case R.id.btnBuscarCodigoBD:
                datos.setText(helperProducto.leerPorCodigo(cajaCodigo.getText().toString()));
                break;
        }
    }
}