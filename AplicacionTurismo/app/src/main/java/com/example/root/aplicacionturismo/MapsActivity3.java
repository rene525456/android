package com.example.root.aplicacionturismo;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.root.aplicacionturismo.modelo.Lista;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

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

import static java.security.AccessController.getContext;

public class MapsActivity3 extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    ServicioWeb hiloConexion;
    ArrayList<Lista> ArrayLista;
    List<Lista> ListLista;
    Lista lista;
    Button btnInformacion;
    String sitioSeleccionado;
    RequestQueue queue;
    StringRequest stringRequest;
    TextView txtBuscar;
    ImageButton btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);




        txtBuscar = (TextView)findViewById(R.id.txt_buscar_mp);
        btnBuscar = (ImageButton)findViewById(R.id.btn_Buscar_m3);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayLista.clear();
                ListLista.clear();

                String url = "https://proyetoturismov1.herokuapp.com/servicioweb/grupo?nombreSitio="+txtBuscar.getText();
                hiloConexion = new ServicioWeb();
                hiloConexion.execute(url, "1");
            }
        });

        /*
        for (int i = 0; i < ArrayLista.size(); i++){
            LatLng latLng = new LatLng(ArrayLista.get(i).getLat(),ArrayLista.get(i).getLon());
            Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title(ArrayLista.get(i).getNombre()));
            m.setTag(i);
        }*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity3.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            recreate();
            return;
        }

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setMyLocationEnabled(true);


        LatLng latLng = new LatLng(-4.0027985,-79.197711);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.9f));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker1) {

                final Marker marker = marker1;
                sitioSeleccionado = marker.getTitle();
                final Dialog dlgSitio = new Dialog(MapsActivity3.this);
                dlgSitio.setContentView(R.layout.dlg_presentar_sitio);

                String urlSitio = "https://proyetoturismov1.herokuapp.com/servicioweb/nombreSitio?nombreSitio="+marker.getTitle();
                final ImageView imagen = (ImageView) dlgSitio.findViewById(R.id.dlg_lista_urlImagen);

                final TextView txtNombre = (TextView) dlgSitio.findViewById(R.id.dlg_lista_nombre);
                final TextView txtDescripcion = (TextView) dlgSitio.findViewById(R.id.dlg_lista_informacion);
                for (int i=0; i<ListLista.size(); i++){
                    lista = ListLista.get(i);
                    //Toast.makeText(MapsActivity3.this, lista.getNombre(), Toast.LENGTH_SHORT).show();
                    if (lista.getNombre().contains(marker.getTitle())){
                        Picasso.get()
                                .load(lista.getFoto()).into(imagen);
                        txtNombre.setText(lista.getNombre());
                        txtDescripcion.setText(lista.getDescripcion());
                        break;
                    }
                }

                        /*
                btnSeleccionar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0; i<ListLista.size(); i++){
                            lista = ListLista.get(i);
                            Toast.makeText(MapsActivity3.this, lista.getNombre(), Toast.LENGTH_SHORT).show();

                            if (lista.getNombre()==marker.getTitle()){
                                Picasso.get()
                                        .load(ListLista.get(i).getFoto()).into(imagen);
                            }
                        }


                        String urlSitio = "https://proyetoturismov1.herokuapp.com/servicioweb/nombreSitio?nombreSitio="+marker.getTitle();
                        hiloConexion = new ServicioWeb();
                        hiloConexion.execute(urlSitio, "1");
                        stringRequest = new StringRequest(Request.Method.GET, urlSitio,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ArrayLista = new ArrayList<>();
                                        try {
                                            List<Lista> aux = obtenerSitios(response);
                                            Picasso.get()
                                                    .load(aux.get(0).getFoto()).into(imagen);
                                        } catch (JSONException e) {

                                            Toast.makeText(MapsActivity3.this, "ERROR EN LA LECTURA", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MapsActivity3.this, "ERROR EN LA LECTURA", Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(stringRequest);
*/
                        //cargarServicioWeb(urlSitio, marker.getTitle());

                        /*
                        Picasso.get()
                                .load(ArrayLista.get(0).getFoto()).into(imagen);
                        txtNombre.setText(ArrayLista.get(0).getNombre());
                        txtNombre.setText(ArrayLista.get(0).getDescripcion());
                    }
                });
*/
                dlgSitio.show();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
        }
    }

    public class ServicioWeb2 extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String cadena = strings[0];
            String devuelve = "No devuelve nada";
            URL url;
            if (strings[1]=="1"){
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    int codigoConnection = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (codigoConnection==HttpURLConnection.HTTP_OK){
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        devuelve = br.readLine();
                        //JSONObject jsonObject = new JSONObject(devuelve);

                        return devuelve;
                        //return devuelve = jsonObject.getString("weather");

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //catch (JSONException e) {
                //  e.printStackTrace();
                //}
            }
            return devuelve;
        }

        @Override
        protected void onPostExecute(String s) {


            try {
                obtenerObjeros(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);

        }
    }

    public class ServicioWeb extends AsyncTask<String,Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String cadena = strings[0];
            String devuelve = "No devuelve nada";
            URL url;
            if (strings[1]=="1"){
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    int codigoConnection = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();
                    if (codigoConnection==HttpURLConnection.HTTP_OK){
                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        devuelve = br.readLine();
                        //JSONObject jsonObject = new JSONObject(devuelve);

                        return devuelve;
                        //return devuelve = jsonObject.getString("weather");

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //catch (JSONException e) {
                //  e.printStackTrace();
                //}
            }
            return devuelve;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                obtenerObjeros(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);

        }
    }
    private void obtenerObjeros(String entrada) throws JSONException {
        ArrayLista = new ArrayList<>();
        ListLista = obtenerSitios(entrada);

        for (int i = 0; i < ListLista.size(); i++) {
            lista = ListLista.get(i);
            ArrayLista.add(lista);
            LatLng latLng = new LatLng(lista.getLat(),lista.getLon());
            Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title(ArrayLista.get(i).getNombre()));
            m.setTag(i);
            m.setTitle(ArrayLista.get(i).getNombre());
        }


    }
    private List<Lista> obtenerSitios(String str) throws JSONException {

        List<Lista> lista = new ArrayList<Lista>();
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {
            Lista lista1 = new Lista();
            lista1.setFoto(jsonArray.getJSONObject(i).getString("fotoSitio"));
            lista1.setNombre(jsonArray.getJSONObject(i).getString("nombreSitio"));
            lista1.setLat(jsonArray.getJSONObject(i).getDouble("latitudSitio"));
            lista1.setLon(jsonArray.getJSONObject(i).getDouble("longitudSitio"));
            lista1.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
            lista.add(lista1);
        }
        return lista;

    }

    private void cargarServicioWeb(String url, String nombre){

        queue=Volley.newRequestQueue(this);
        JSONObject json= new JSONObject();
        try {
            json.put("nombreSitio", nombre);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //JsonArrayRequest jsonArrayRequest = new JsonArrayRequest();
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MapsActivity3.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //resultado.setText("Error");
                Toast.makeText(MapsActivity3.this, "Error: "+error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
