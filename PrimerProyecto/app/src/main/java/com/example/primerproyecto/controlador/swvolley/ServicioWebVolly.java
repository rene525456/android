package com.example.primerproyecto.controlador.swvolley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.primerproyecto.modelo.Alumno;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServicioWebVolly {
    String host = "http://reneguaman.000webhostapp.com/";
    String insertar_url = "insertar_alumno.php";
    String GET = "obtener_alumnos.php";
    Context context;


    final public List<Alumno> listaAlumnos;

    public static final int MY_DEFAULT_TIMEOUT = 25000;

    boolean estado;
    String consulta;

    public ServicioWebVolly(Context context) {
        this.context = context;
        listaAlumnos = new ArrayList<>();
    }

    public boolean insertar(Alumno alumno){
        String url = host + insertar_url;
        JSONObject json = new JSONObject();
        try {
            json.put("nombre",alumno.getNombre());
            json.put("direccion", alumno.getDireccion());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        estado = true;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                estado = false;
            }
        }
        );
        VolleyAlumnoSingleton.getInstance(context).addToRequestque(request);
        return estado;
    }

    public String obtenerTodosAlumnos(){
        String url = host + GET;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                consulta = "xxxxxxxxxxxxxxxxxx";
                Toast.makeText(context,"xxx:" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progress.hide();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_DEFAULT_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleyAlumnoSingleton.getInstance(context).addToRequestque(request);
        return consulta;
    }

    public String estaAutenticado(){
        String url = "http://10.20.55.14:8089/usuarios/users";
        JSONObject json = new JSONObject();
        try {
            json.put("username","rene");
            json.put("password", "micarrera");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        consulta = response.toString();

                        Log.e("json",response.toString()+" xxxxxxxxxxxxxxxxxxxxx");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("json",error.toString());
            }
        }
        );
        VolleyAlumnoSingleton.getInstance(context).addToRequestque(request);
        return consulta;
    }
}