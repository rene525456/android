package com.example.primerproyecto;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.primerproyecto.adapter.AlumnoAdapter;
import com.example.primerproyecto.modelo.Alumno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ActivitySWHAlumno extends AppCompatActivity implements View.OnClickListener{

    TextView datos;
    EditText cajaId, cajaNombre, cajaDireccion;
    Button botonGuardar, botonModificar, botonEliminar, botonBuscarTodos, botonBuscarCodigo;
    RecyclerView recyclerViewAlumno;
    AlumnoAdapter adapter;
    private List<Alumno> listaAlumno;
    ServicioWeb sw;
    String HOST = "http://reneguaman.000webhostapp.com";
    String GET = HOST.concat("/obtener_alumnos.php");
    String GET_BY_ID = HOST.concat("/obtener_alumno_por_id.php");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swhalumno);
        tomarControl();
    }

    @Override
    public void onClick(View v) {
        sw = new ServicioWeb();
        switch (v.getId()){
            case R.id.btnBuscarTodosAlumnosSWH:
                sw.execute(GET,"1"); // ejecuta al hilo, doInBackground
                break;
            case R.id.btnBuscarAlumnoCodigoSWH:
                sw.execute(GET_BY_ID + "?idalumno=" + cajaId.getText().toString(),"2");
                break;
        }
    }

    private void descomponerConsulta(int operacion, String cadena){
        if (operacion == 1){
            try {
                JSONObject json = new JSONObject(cadena);
                String estado = json.getString("estado");
                List<Alumno> lista = new ArrayList<Alumno>();
                if(estado.equals("1")){
                    JSONArray alumnosJson = json.getJSONArray("alumnos");
                    for (int i = 0; i <alumnosJson.length();i++){
                        Alumno alumno = new Alumno();
                        alumno.setIdAlumno(alumnosJson.getJSONObject(i).getInt("idalumno"));
                        alumno.setNombre(alumnosJson.getJSONObject(i).getString("nombre"));
                        alumno.setDireccion(alumnosJson.getJSONObject(i).getString("direccion"));
                        lista.add(alumno);
                    }
                    cargarLista(lista);
                }else{
                    // cuando no hay estudiantes
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(operacion == 2){
            JSONObject json = null;
            try {
                json = new JSONObject(cadena);
                String estado = json.getString("estado");
                List<Alumno> lista = new ArrayList<Alumno>();
                if(estado.equals("1")){
                    Alumno alumno = new Alumno();
                    alumno.setIdAlumno(json.getJSONObject("alumno").getInt("idAlumno"));
                    alumno.setNombre(json.getJSONObject("alumno").getString("nombre"));
                    alumno.setDireccion(json.getJSONObject("alumno").getString("direccion"));
                    lista.add(alumno);
                    cargarLista(lista);
                }else{
                    Toast.makeText(getApplicationContext(),"No existe ese estudiante", Toast
                    .LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                cajaId.setText(alumno.getIdAlumno());
                cajaNombre.setText(alumno.getNombre());
                cajaDireccion.setText(alumno.getDireccion());
            }
        });
        recyclerViewAlumno.setAdapter(adapter);
    }

    class ServicioWeb extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... parametros) {
            String consulta = "";
            URL url = null;
            String ruta = parametros[0];
            if (parametros[1].equals("1") || parametros[1].equals("2")){
                try {
                    url = new URL(ruta);
                    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                    int codigoRespuesta = conexion.getResponseCode();
                    if (codigoRespuesta == HttpURLConnection.HTTP_OK){
                        InputStream in = new BufferedInputStream(conexion.getInputStream());
                        BufferedReader lector = new BufferedReader(new InputStreamReader(in));
                        consulta += lector.readLine();
                    }else{
                        consulta += "no hubo conexión";
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                consulta = parametros[1] + "-" + consulta;
            }else {

            }
            return consulta;
        }

        @Override
        protected void onPostExecute(String s) {
            //datos.setText(s.substring(2,s.length()));
            descomponerConsulta(Integer.parseInt(s.charAt(0)+""),s.substring(2,s.length()));
        }
    }

    private void tomarControl(){
        cajaId = findViewById(R.id.txtIdAlumnoSWH);
        cajaNombre = findViewById(R.id.txtNombreAlumnoSWH);
        cajaDireccion = findViewById(R.id.txtDireccionAlumnoSWH);
        botonGuardar = findViewById(R.id.btnAgregarAlumnoSWH);
        botonModificar = findViewById(R.id.btnModificarAlumnoSWH);
        botonEliminar = findViewById(R.id.btnEliminarAlumnoSWH);
        botonBuscarTodos = findViewById(R.id.btnBuscarTodosAlumnosSWH);
        botonBuscarCodigo = findViewById(R.id.btnBuscarAlumnoCodigoSWH);
        recyclerViewAlumno = findViewById(R.id.recyclerAlumnoHilo);
        datos = findViewById(R.id.lbl);
        botonGuardar.setOnClickListener(this);
        botonModificar.setOnClickListener(this);
        botonEliminar.setOnClickListener(this);
        botonBuscarTodos.setOnClickListener(this);
        botonBuscarCodigo.setOnClickListener(this);
    }
}