package com.example.primerproyecto.controlador;

import android.content.Context;
import android.util.Log;
import com.example.primerproyecto.modelo.Producto;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ArchivoProducto {

    Context context;

    public ArchivoProducto(Context context) {
        this.context = context;
    }

    public boolean guardarProducto(Producto producto){
            //fos = openFileOutput(fichero,Context.MODE_PRIVATE);
        try {
            OutputStreamWriter escritor = new OutputStreamWriter(context.openFileOutput(
                    "archivo.txt", Context.MODE_APPEND));
            escritor.write(producto.toString());
            escritor.close();
            return true;
        }catch (Exception ex){
            Log.e("Error de Escritura",ex.getMessage());
            return false;
        }
    }

    public String leerProductos(){
        String datos = null;
        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(context.openFileInput("archivo.txt")));
            datos = lector.readLine();
            lector.close();
        }catch (Exception ex){
            Log.e("Error de Lectura",ex.getMessage());
        }
        return datos;
    }
}