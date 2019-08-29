package com.example.root.aplicacionturismo.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.aplicacionturismo.R;
import com.example.root.aplicacionturismo.modelo.Lista;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListaAdapter extends BaseAdapter {
    private Context contexto;
    private ArrayList<Lista> lista;

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(contexto, R.layout.display_lista, null);
        }
        ImageView imagen = (ImageView)convertView.findViewById(R.id.display_urlImagen);
        TextView textNombre = (TextView)convertView.findViewById(R.id.display_nombre);
        TextView textDescripcion = (TextView)convertView.findViewById(R.id.display_informacion);

        Lista arregloLista = lista.get(position);

        //para dar un nuevo tamaño en px -> .resize()
        Picasso.get()
                .load(arregloLista.getFoto()).into(imagen);
        textNombre.setText(arregloLista.getNombre());
        textDescripcion.setText(arregloLista.getDescripcion());

        return convertView;
    }



    public ListaAdapter(Context contexto, ArrayList<Lista> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }

    public ArrayList<Lista> getlista() {
        return lista;
    }

    public void setlista(ArrayList<Lista> lista) {
        this.lista = lista;
    }

    public Context getContexto() {
        return contexto;
    }

    public void setContexto(Context contexto) {
        this.contexto = contexto;
    }


}
