package com.example.root.aplicacionturismo;

import android.app.Dialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.aplicacionturismo.modelo.Lista;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

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

public class Chuquiribamba extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener{

    private GoogleMap mMap;
    ServicioWeb hiloConexion;
    List<LatLng> ListLatLng;
    ArrayList<LatLng> ArrayLatLng;
    List<String> latitudes;
    List<String> longitudes;
    LatLng latLng;


    int aux = 0;
    Boolean isFABOpen = false;
    FloatingActionButton fab1, fab2, fab3, fab;
    TextView txtFab1, txtFab2, txtFab3;
    List<Lista> lista;
    String sitioSeleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuquiribamba);

        fab = (FloatingActionButton) findViewById(R.id.Chuquiribambafab);
        fab1 = (FloatingActionButton) findViewById(R.id.Chuquiribambafab1);
        fab1.setOnClickListener(this);
        fab2 = (FloatingActionButton) findViewById(R.id.Chuquiribambafab2);
        fab2.setOnClickListener(this);
        fab3 = (FloatingActionButton) findViewById(R.id.Chuquiribambafab3);
        fab3.setOnClickListener(this);
        txtFab1 = (TextView) findViewById(R.id.txtFab1);
        txtFab2 = (TextView) findViewById(R.id.txtFab2);
        txtFab3 = (TextView) findViewById(R.id.txtFab3);
        showFABMenu();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        String url = "https://proyetoturismov1.herokuapp.com/servicioweb/zona?nombreZona=Chuquiribamba";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url, "1");


        String url2 = "https://proyetoturismov1.herokuapp.com/servicioweb/parada?nombreCorredor=Chuquiribamba";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url2, "1");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab1.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab2.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }
    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        txtFab1.animate().translationY(0);
        txtFab2.animate().translationY(0);
        txtFab3.animate().translationY(0);
    }

    @Override
    public void onBackPressed() {
        if(!isFABOpen){
            super.onBackPressed();
        }else{
            closeFABMenu();
        }
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker1) {

                final Marker marker = marker1;
                sitioSeleccionado = marker.getTitle();
                final Dialog dlgSitio = new Dialog(Chuquiribamba.this);
                dlgSitio.setContentView(R.layout.dlg_presentar_sitio);

                String urlSitio = "https://proyetoturismov1.herokuapp.com/servicioweb/nombreSitio?nombreSitio="+marker.getTitle();
                final ImageView imagen = (ImageView) dlgSitio.findViewById(R.id.dlg_lista_urlImagen);

                final TextView txtNombre = (TextView) dlgSitio.findViewById(R.id.dlg_lista_nombre);
                final TextView txtDescripcion = (TextView) dlgSitio.findViewById(R.id.dlg_lista_informacion);
                for (int i=0; i<lista.size(); i++){
                    //Toast.makeText(MapsActivity2.this, lista.getNombre(), Toast.LENGTH_SHORT).show();
                    if (lista.get(i).getNombre().contains(marker.getTitle())){
                        Picasso.get()
                                .load(lista.get(i).getFoto()).into(imagen);
                        txtNombre.setText(lista.get(i).getNombre());
                        txtDescripcion.setText(lista.get(i).getDescripcion());
                        break;
                    }
                }

                imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dlgSitio2 = new Dialog(Chuquiribamba.this);
                        dlgSitio2.setContentView(R.layout.dlg_imagen);
                        final ImageView imagen2 = (ImageView) dlgSitio2.findViewById(R.id.dlg_imagen_imagen);
                        for (int i=0; i<lista.size(); i++){
                            //Toast.makeText(MapsActivity2.this, lista.getNombre(), Toast.LENGTH_SHORT).show();
                            if (lista.get(i).getNombre().contains(marker.getTitle())){
                                Picasso.get()
                                        .load(lista.get(i).getFoto()).into(imagen2);
                                break;
                            }
                        }
                        dlgSitio2.show();
                    }
                });
                dlgSitio.show();
                return false;
            }
        });
    }
    public void llamarImg(){
        final Dialog llamarImg = new Dialog(Chuquiribamba.this);
        llamarImg.setContentView(R.layout.dlg_galeria);
        ImageView imagen2 = (ImageView) llamarImg.findViewById(R.id.dlg_galeria_imagen);
        Button btnIzq = (Button) llamarImg.findViewById(R.id.btn_izq);
        btnIzq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux--;
                llamarImg.hide();
                llamarImg();
            }
        });
        Button btnDer = (Button) llamarImg.findViewById(R.id.btn_der);
        btnDer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux++;
                llamarImg.hide();
                llamarImg();
            }
        });
        if (aux< lista.size()&& aux>=0){
            Picasso.get()
                    .load(lista.get(aux).getFoto()).into(imagen2);
            llamarImg.show();

        }
        else if (aux<0){
            aux=lista.size()-1;
            llamarImg();
        }else if (aux==lista.size()){
            aux=0;llamarImg();
        }

    }
    private void colocarMarcadores(){
        for (int i = 0; i < lista.size(); i++) {
            LatLng latLng = new LatLng(lista.get(i).getLat(), lista.get(i).getLon());
            Marker Inicio = mMap.addMarker(new MarkerOptions().position(latLng).title(lista.get(i).getNombre())
                    .icon(getMarkerIcon("#ff2299")));
            //.icon(BitmapDescriptorFactory.fromFile(lista.get(i).getFoto()))
            Inicio.setTag(i);

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
                        return devuelve;
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Chuquiribambafab1:
                colocarMarcadores();
                break;

            case R.id.Chuquiribambafab2:
                final Dialog dlgSitio = new Dialog(Chuquiribamba.this);
                dlgSitio.setContentView(R.layout.dlg_presentar_sitio);
                final ImageView imagen = (ImageView) dlgSitio.findViewById(R.id.dlg_lista_urlImagen);
                final TextView txtNombre = (TextView) dlgSitio.findViewById(R.id.dlg_lista_nombre);
                final TextView txtDescripcion = (TextView) dlgSitio.findViewById(R.id.dlg_lista_informacion);
                Picasso.get()
                        .load(lista.get(0).getFoto()).into(imagen);
                txtNombre.setText("Corredor Chuquiribamba");
                final TextView txt_dlg_sitio = (TextView) dlgSitio.findViewById(R.id.txt_dlg_sitio);
                txt_dlg_sitio.setText("Chuquiribamba");
                txtDescripcion.setText(lista.get(0).getDescripcion());

                imagen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dlgSitio2 = new Dialog(Chuquiribamba.this);
                        dlgSitio2.setContentView(R.layout.dlg_imagen);
                        final ImageView imagen2 = (ImageView) dlgSitio2.findViewById(R.id.dlg_imagen_imagen);
                        Picasso.get()
                                .load(lista.get(0).getFoto()).into(imagen2);

                        dlgSitio2.show();
                    }
                });
                dlgSitio.show();
                break;

            case R.id.Chuquiribambafab3:
                llamarImg();
                break;

        }
    }
    private void obtenerObjeros(String entrada) throws JSONException {

        ArrayLatLng = new ArrayList<>();
        if (entrada.contains("Parada")) {
            obtenerParadas(entrada);
        } else {
            ListLatLng = obtenerLatLng(entrada);

            for (int i = 0; i < ListLatLng.size(); i++) {
                latLng = ListLatLng.get(i);
                ArrayLatLng.add(latLng);
                if (i < ListLatLng.size() - 1) {
                    Polyline line = mMap.addPolyline(new PolylineOptions().add(latLng
                            , ListLatLng.get(i + 1))
                            .width(3)
                            .color(Color.parseColor("#FF4930")));
                    line.setGeodesic(true);
                }
            }
            Marker Inicio = mMap.addMarker(new MarkerOptions().position(ArrayLatLng.get(0)).title("Inicio")
                    .icon(getMarkerIcon("#2AB202")));
            Inicio.setTag(1);
            Marker Fin = mMap.addMarker(new MarkerOptions().position(ArrayLatLng.get(ArrayLatLng.size() - 1)).title("Fin")
                    .icon(getMarkerIcon("#001DA1")));
            Fin.setTag(2);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ArrayLatLng.get(0), 13.9f));
        }
    }
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
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
    private void obtenerParadas(String str) throws JSONException {

        lista = new ArrayList<Lista>();
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {
            Lista lista1 = new Lista();
            lista1.setFoto(jsonArray.getJSONObject(i).getString("fotoParada"));
            lista1.setNombre(jsonArray.getJSONObject(i).getString("nombreParada"));
            lista1.setLat(jsonArray.getJSONObject(i).getDouble("latitudParada"));
            lista1.setLon(jsonArray.getJSONObject(i).getDouble("longitudParada"));
            lista1.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
            lista.add(lista1);
        }

    }
}
