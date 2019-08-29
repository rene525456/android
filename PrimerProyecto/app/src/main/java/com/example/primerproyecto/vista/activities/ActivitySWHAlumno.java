package com.example.primerproyecto.vista.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.primerproyecto.R;
import com.example.primerproyecto.modelo.Alumno;
import com.example.primerproyecto.vista.adapter.AlumnoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    String INSERT = HOST.concat("/insertar_alumno.php");
    String UPDATE = HOST.concat("/actualizar_alumno.php");
    String DELETE = HOST.concat("/borrar_alumno.php");

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
            case R.id.btnAgregarAlumnoSWH:
                sw.execute(INSERT,"3",cajaNombre.getText().toString(),cajaDireccion.getText().toString());
                break;
            case R.id.btnModificarAlumnoSWH:
                sw.execute(UPDATE,"4", cajaId.getText().toString(),cajaNombre.getText().toString(),cajaDireccion.getText().toString());
                break;
            case R.id.btnEliminarAlumnoSWH:
                sw.execute(DELETE,"5", cajaId.getText().toString());
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
                    Toast.makeText(getApplicationContext(),"No hay estudiantes agregados", Toast
                            .LENGTH_SHORT).show();
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
        }else if(operacion == 3){
            JSONObject json = null;
            try {
                json = new JSONObject(cadena);
                String estado = json.getString("estado");
                if(estado.equals("1")){
                    Toast.makeText(getApplicationContext(),"Alumno insertado correctamente", Toast
                            .LENGTH_SHORT).show();
                    sw = new ServicioWeb();
                    sw.execute(GET,"1");
                }else{
                    Toast.makeText(getApplicationContext(),"El Alumno no se pudo insertar", Toast
                            .LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(operacion == 4){
            JSONObject json = null;
            try {
                json = new JSONObject(cadena);
                String estado = json.getString("estado");
                if(estado.equals("1")){
                    Toast.makeText(getApplicationContext(),"Alumno actualizado correctamente", Toast
                            .LENGTH_SHORT).show();
                    sw = new ServicioWeb();
                    sw.execute(GET,"1");
                }else{
                    Toast.makeText(getApplicationContext(),"El Alumno no se pudo actualizar", Toast
                            .LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(operacion == 5){
            JSONObject json = null;
            try {
                json = new JSONObject(cadena);
                String estado = json.getString("estado");
                if(estado.equals("1")){
                    Toast.makeText(getApplicationContext(),"Alumno eliminado correctamente", Toast
                            .LENGTH_SHORT).show();
                    sw = new ServicioWeb();
                    sw.execute(GET,"1");
                }else{
                    Toast.makeText(getApplicationContext(),"El Alumno no se pudo eliminar", Toast
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
                cajaId.setText(alumno.getIdAlumno() + "");
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
            }else if(parametros[1].equals("3")){
                try {
                    url = new URL(ruta);
                    URLConnection conexion = (HttpURLConnection) url.openConnection();
                    conexion.setDoInput(true);
                    conexion.setDoOutput(true);
                    conexion.setUseCaches(false);
                    conexion.setRequestProperty("Content-Type", "application/json");
                    conexion.setRequestProperty("Accept", "application/json");
                    conexion.connect();

                    //se crea el json
                    JSONObject json = new JSONObject();
                    json.put("nombre", parametros[2]);
                    json.put("direccion", parametros[3]);

                    // Envio los parámetros post.
                    OutputStream os = conexion.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(json.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = ((HttpURLConnection) conexion).getResponseCode();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        consulta += lector.readLine();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                consulta = parametros[1] + "-" + consulta;
            }else if(parametros[1].equals("4")){
                try {
                    url = new URL(ruta);
                    URLConnection conexion = (HttpURLConnection) url.openConnection();
                    conexion.setDoInput(true);
                    conexion.setDoOutput(true);
                    conexion.setUseCaches(false);
                    conexion.setRequestProperty("Content-Type", "application/json");
                    conexion.setRequestProperty("Accept", "application/json");
                    conexion.connect();

                    //se crea el json
                    JSONObject json = new JSONObject();
                    json.put("idalumno", parametros[2]);
                    json.put("nombre", parametros[3]);
                    json.put("direccion", parametros[4]);

                    // Envio los parámetros post.
                    OutputStream os = conexion.getOutputStream();
                    BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    escritor.write(json.toString());
                    escritor.flush();
                    escritor.close();

                    int respuesta = ((HttpURLConnection) conexion).getResponseCode();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        consulta += lector.readLine();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                consulta = parametros[1] + "-" + consulta;
            }else if(parametros[1].equals("5")){
                try {
                    url = new URL(ruta);
                    URLConnection conexion = (HttpURLConnection) url.openConnection();
                    conexion.setDoInput(true);
                    conexion.setDoOutput(true);
                    conexion.setUseCaches(false);
                    conexion.setRequestProperty("Content-Type", "application/json");
                    conexion.setRequestProperty("Accept", "application/json");
                    conexion.connect();

                    //se crea el json
                    JSONObject json = new JSONObject();
                    json.put("idalumno", parametros[2]);

                    // Envio los parámetros post.
                    OutputStream os = conexion.getOutputStream();
                    BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    escritor.write(json.toString());
                    escritor.flush();
                    escritor.close();

                    int respuesta = ((HttpURLConnection) conexion).getResponseCode();
                    if (respuesta == HttpURLConnection.HTTP_OK) {
                        BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                        consulta += lector.readLine();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                consulta = parametros[1] + "-" + consulta;
            }
            return consulta;
        }

        @Override
        protected void onPostExecute(String s) {
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