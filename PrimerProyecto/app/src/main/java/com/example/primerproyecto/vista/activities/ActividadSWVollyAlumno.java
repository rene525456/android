package com.example.primerproyecto.vista.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primerproyecto.R;
import com.example.primerproyecto.controlador.swvolley.ServicioWebVolly;
import com.example.primerproyecto.modelo.Alumno;
import com.example.primerproyecto.vista.adapter.AlumnoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActividadSWVollyAlumno extends AppCompatActivity implements View.OnClickListener {

    Button botonGuardar, botonListar;
    EditText cajaId, cajaNombre, cajaDireccion;
    ServicioWebVolly swv;
    RecyclerView recyclerViewAlumno;
    AlumnoAdapter adapter;
    private List<Alumno> listaAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_swvolly_alumno);
        cargarComponentes();
        swv = new ServicioWebVolly(ActividadSWVollyAlumno.this);
        //cargarLista(swv.obtenerTodosAlumnos());
    }

    private void cargarComponentes(){
        cajaId = findViewById(R.id.txtIdAlumnoVolly);
        cajaNombre = findViewById(R.id.txtNombreAlumnoVolly);
        cajaDireccion = findViewById(R.id.txtDireccionAlumnoVolly);
        botonGuardar = findViewById(R.id.btnAgregarAlumnoVolly);
        botonListar = findViewById(R.id.btnBuscarAlumnosVolly);
        recyclerViewAlumno = findViewById(R.id.recyclerAlumnoVolly);
        botonGuardar.setOnClickListener(this);
        botonListar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregarAlumnoVolly:
                Alumno alumno = new Alumno();
                alumno.setNombre(cajaNombre.getText().toString());
                alumno.setDireccion(cajaDireccion.getText().toString());
                boolean estado = swv.insertar(alumno);

                if(estado)
                    Toast.makeText(ActividadSWVollyAlumno.this,"Alumno Registrado", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ActividadSWVollyAlumno.this,"El Alumno no se registró", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnBuscarAlumnosVolly:
                Toast.makeText(ActividadSWVollyAlumno.this,swv.obtenerTodosAlumnos(), Toast.LENGTH_SHORT).show();
                //cargarLista(swv.obtenerTodosAlumnos());
                break;
        }
    }

    public void cargarLista(List<Alumno> lista){
        listaAlumno = new ArrayList<Alumno>();
        listaAlumno = lista;
        recyclerViewAlumno.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlumnoAdapter(listaAlumno);
        adapter.setOnCLickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alumno alumno = listaAlumno.get(recyclerViewAlumno.getChildAdapterPosition(v));
                cajaId.setText(alumno.getIdAlumno() + "");
                cajaNombre.setText(alumno.getNombre());
                cajaDireccion.setText(alumno.getDireccion());
            }
        });
        recyclerViewAlumno.setAdapter(adapter);
    }
}