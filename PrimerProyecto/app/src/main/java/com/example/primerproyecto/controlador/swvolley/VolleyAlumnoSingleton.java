package com.example.primerproyecto.controlador.swvolley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyAlumnoSingleton{

    private RequestQueue queue;
    private Context context;
    private static VolleyAlumnoSingleton miInstancia;

    public VolleyAlumnoSingleton(Context contexto){
        context = contexto;
        queue = getRequestQue();
    }

    public RequestQueue getRequestQue(){
        if (queue == null){
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }

    public static synchronized VolleyAlumnoSingleton getInstance(Context context){
        if (miInstancia == null){
            miInstancia = new VolleyAlumnoSingleton(context);
        }
        return miInstancia;
    }

    public <T> void addToRequestque(Request request){
        queue.add(request);
    }
}