package com.example.root.aplicacionturismo;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;

import static java.security.AccessController.getContext;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener
        , GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    ServicioWeb hiloConexion;
    List<Lista> ListLista;
    List<LatLng> ListLatLng;
    ArrayList<Lista> ArrayLista;
    ArrayList<LatLng> ArrayLatLng;
    List<String> latitudes;
    List<String> longitudes;
    Lista lista;
    Button btnInformacion, btnGuardar;
    String sitioSeleccionado;
    RequestQueue queue;
    StringRequest stringRequest;
    InputStreamReader lector;
    OutputStreamWriter escritor;
    String coordenadas="";
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        /*
        btnGuardar = (Button)findViewById(R.id.btn_guardarCsv);
        btnGuardar.setOnClickListener(this);

        File ruta= Environment.getExternalStorageDirectory();
        File archivo= new File(ruta,"coordenadas.csv");
        // File archivo= new File(ruta,"archivoB.txt"); //---es equivalente

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
            recreate();
            return;
        }
        try {
            escritor =new OutputStreamWriter(new FileOutputStream(archivo));

        } catch (FileNotFoundException e) {
            Toast.makeText(MapsActivity2.this, "ERROR:\n"+e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
*/



        String url = "https://proyetoturismov1.herokuapp.com/servicioweb/zona?nombreZona=gualel";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url, "1");

        latitudes = new ArrayList<String>();
        longitudes = new ArrayList<String>();

  /*
        for (int i = 0; i < ArrayLista.size(); i++){
            LatLng latLng = new LatLng(ArrayLista.get(i).getLat(),ArrayLista.get(i).getLon());
            Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title(ArrayLista.get(i).getNombre()));
            m.setTag(i);


            Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(-3.9846, -79.2355), new LatLng(-3.978343, -79.223854))
                .width(5)
                .color(Color.RED));

        }
*/

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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        LatLng latLng = new LatLng(-3.797199,-79.364088);
        Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title("Título").draggable(true));
        m.setTag(1);
        LatLng latLng2 = new LatLng(-3.779149,-79.367805);
        Marker m2 = mMap.addMarker(new MarkerOptions().position(latLng2).title("Título"));
        m2.setTag(2);
        for (int i=0; i<latitudes.size(); i++){
            LatLng latLng3 = new LatLng(Double.parseDouble(latitudes.get(i)),Double.parseDouble(longitudes.get(i)));
            //Toast.makeText(MapsActivity2.this, latLng.latitude+";"+latLng.longitude, Toast.LENGTH_SHORT).show();
            Marker m3 = mMap.addMarker(new MarkerOptions().position(latLng3).title(""+i));
            m.setTag(i);
        /*

            if(i+1<latitudes.size()){

            Polyline line = mMap.addPolyline(new PolylineOptions().add(new LatLng(Double.parseDouble(latitudes.get(i)),
                            Double.parseDouble(longitudes.get(i)))
                    , new LatLng(Double.parseDouble(latitudes.get(i+1)), Double.parseDouble(longitudes.get(i+1))))
                    .width(5)
                    .color(Color.RED));
            line.setGeodesic(true);

            }



        */
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
            recreate();
            return;
        }

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setMyLocationEnabled(true);


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.9f));
        mMap.setOnMarkerDragListener(this);
        /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker1) {

                final Marker marker = marker1;
                sitioSeleccionado = marker.getTitle();
                final Dialog dlgSitio = new Dialog(MapsActivity2.this);
                dlgSitio.setContentView(R.layout.dlg_presentar_sitio);

                String urlSitio = "https://proyetoturismov1.herokuapp.com/servicioweb/nombreSitio?nombreSitio="+marker.getTitle();
                final ImageView imagen = (ImageView) dlgSitio.findViewById(R.id.dlg_lista_urlImagen);

                final TextView txtNombre = (TextView) dlgSitio.findViewById(R.id.dlg_lista_nombre);
                final TextView txtDescripcion = (TextView) dlgSitio.findViewById(R.id.dlg_lista_informacion);
                final Button btnSeleccionar = (Button) dlgSitio.findViewById(R.id.btn_dlg_listar_seleccionar);
                final Button btnCancelar = (Button) dlgSitio.findViewById(R.id.btn_dlg_listar_cancelar);
                for (int i=0; i<ListLista.size(); i++){
                    lista = ListLista.get(i);
                    //Toast.makeText(MapsActivity2.this, lista.getNombre(), Toast.LENGTH_SHORT).show();
                    if (lista.getNombre().contains(marker.getTitle())){
                        Picasso.get()
                                .load(lista.getFoto()).into(imagen);
                        txtNombre.setText(lista.getNombre());
                        txtDescripcion.setText(lista.getDescripcion());
                        break;
                    }
                }

                btnSeleccionar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0; i<ListLista.size(); i++){
                            lista = ListLista.get(i);
                            Toast.makeText(MapsActivity2.this, lista.getNombre(), Toast.LENGTH_SHORT).show();

                            if (lista.getNombre()==marker.getTitle()){
                                Picasso.get()
                                        .load(ListLista.get(i).getFoto()).into(imagen);
                            }
                        }



                    }
                });
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dlgSitio.hide();
                    }
                });
                dlgSitio.show();
                return false;
            }
        });
*/
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_guardarCsv:
                try {
                    escritor.write(coordenadas);
                    Toast.makeText(MapsActivity2.this, "Creó el archivo-------", Toast.LENGTH_SHORT).show();
                    escritor.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {
            coordenadas = coordenadas+marker.getPosition().latitude+","+marker.getPosition().longitude+"\n";

     }

    @Override
    public void onMarkerDragEnd(Marker marker) {

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

        ArrayLatLng = new ArrayList<>();
        ListLatLng = obtenerLatLng(entrada);

        for (int i = 0; i < ListLatLng.size(); i++) {
            latLng = ListLatLng.get(i);
            //ArrayLatLng.add(latLng);

            if(i < ListLatLng.size()-1){
                Polyline line = mMap.addPolyline(new PolylineOptions().add(latLng
                        , ListLatLng.get(i+1))
                        .width(3)
                        .color(Color.RED));
                line.setGeodesic(true);
            }

            //Marker m = mMap.addMarker(new MarkerOptions().position(latLng).title("punto "+i));
            //m.setTag(i);
        }


    }
    private List<Lista> obtenerSitios(String str) throws JSONException {

        List<Lista> lista = new ArrayList<Lista>();
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {

            /*Lista lista1 = new Lista();

            lista1.setFoto(jsonArray.getJSONObject(i).getString("fotoSitio"));
            lista1.setNombre(jsonArray.getJSONObject(i).getString("nombreSitio"));
            lista1.setLat(jsonArray.getJSONObject(i).getDouble("latitudSitio"));
            lista1.setLon(jsonArray.getJSONObject(i).getDouble("longitudSitio"));
            lista1.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
            lista.add(lista1);
            */
        }
        return lista;

    }
    private List<LatLng> obtenerLatLng(String str) throws JSONException {

        List<LatLng> lista = new ArrayList<LatLng>();
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {

            LatLng list = new LatLng(Double.parseDouble(jsonArray.getJSONObject(i).getString("latitudZona"))
                    ,Double.parseDouble(jsonArray.getJSONObject(i).getString("longitudZona")));
            lista.add(list);
        }
        return lista;

    }

    private void cargarServicioWeb(String url, String nombre){

        queue=Volley.newRequestQueue(this);
        JSONObject json= new JSONObject();
        try {
            json.put("latitudZona", nombre);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //JsonArrayRequest jsonArrayRequest = new JsonArrayRequest();
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MapsActivity2.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //resultado.setText("Error");
                Toast.makeText(MapsActivity2.this, "Error: "+error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
