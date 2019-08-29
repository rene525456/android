package com.example.root.aplicacionturismo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.aplicacionturismo.Adapter.ListaAdapter;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback, View.OnClickListener {

    GoogleMap mMap;
    ServicioWeb hiloConexion;
    Frg_principal frgUno;

    List<LatLng> ListLatLng;
    ArrayList<LatLng> ArrayLatLng;
    LatLng latLng;

    Boolean isFABOpen = false;
    FloatingActionButton fab1, fab2, fab3, fab, fab4, fab5;
    TextView txtFab1, txtFab2, txtFab3, txtFab4, txtFab5;
    List<Lista> lista;
    String sitioSeleccionado;

    Button btn_corredor, btn_provincias, btn_mapa, btn_informacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent;

        frgUno = new Frg_principal();
        FragmentTransaction transactionUno = getSupportFragmentManager().beginTransaction();
        transactionUno.show(frgUno);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(this);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(this);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(this);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab4.setOnClickListener(this);
        fab5 = (FloatingActionButton) findViewById(R.id.fab5);
        fab5.setOnClickListener(this);
        txtFab1 = (TextView) findViewById(R.id.txtFab1);
        txtFab2 = (TextView) findViewById(R.id.txtFab2);
        txtFab3 = (TextView) findViewById(R.id.txtFab3);
        txtFab4 = (TextView) findViewById(R.id.txtFab4);
        txtFab5 = (TextView) findViewById(R.id.txtFab5);

        showFABMenu();


        String url = "https://proyetoturismov1.herokuapp.com/servicioweb/listaZonas";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url, "1");

/*
        String url1 = "https://proyetoturismov1.herokuapp.com/servicioweb/zona?nombreZona=Taquil";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url1, "1");

        String url2 = "https://proyetoturismov1.herokuapp.com/servicioweb/zona?nombreZona=Cisne";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url2, "1");

        String url3 = "https://proyetoturismov1.herokuapp.com/servicioweb/zona?nombreZona=Gualel";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url3, "1");

        String url4 = "https://proyetoturismov1.herokuapp.com/servicioweb/zona?nombreZona=Chuquiribamba";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(url4, "1");
*/


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



        //List view

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        fab4.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        fab5.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab1.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab2.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab3.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab4.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
        txtFab5.animate().translationY(-getResources().getDimension(R.dimen.standard_155));
    }
    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
        fab4.animate().translationY(0);
        fab5.animate().translationY(0);
        txtFab1.animate().translationY(0);
        txtFab2.animate().translationY(0);
        txtFab3.animate().translationY(0);
        txtFab4.animate().translationY(0);
        txtFab5.animate().translationY(0);
    }

    @Override
    public void onBackPressed() {

        if(!isFABOpen){
            super.onBackPressed();
        }else{
            closeFABMenu();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //Intent intent;
        //intent = new Intent(MenuIzquierdo.this, Luz.class);
        //startActivity(intent);
        switch (item.getItemId()) {
            case R.id.nav_seleccionar:
                Intent seleccionar = new Intent(MainActivity.this, listas.class);
                startActivity(seleccionar);
                break;
            case R.id.nav_chantaco:
                Intent Chantaco = new Intent(MainActivity.this, Chantaco.class);
                startActivity(Chantaco);
                break;
            case R.id.nav_chuquiribamba:
                Intent Chuquiribamba = new Intent(MainActivity.this, Chuquiribamba.class);
                startActivity(Chuquiribamba);
                break;
            case R.id.nav_cisne:
                Intent Cisne = new Intent(MainActivity.this, Cisne.class);
                startActivity(Cisne);
                break;
            case R.id.nav_gualel:
                Intent Gualel = new Intent(MainActivity.this, Gualel.class);
                startActivity(Gualel);
                break;
            case R.id.nav_taquil:
                Intent Taquil = new Intent(MainActivity.this, Taquil.class);
                startActivity(Taquil);
                break;

            case R.id.nav_codigo:
                Dialog dlgCodigo = new Dialog(MainActivity.this);
                dlgCodigo.setContentView(R.layout.dlg_ingresar_codigo);
                final EditText txt_id = (EditText) dlgCodigo.findViewById(R.id.txt_ingresarCodigo_codigo);
                Button button = (Button) dlgCodigo.findViewById(R.id.btn_ingresarCodigo_codigo);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "No se han definido acción", Toast.LENGTH_SHORT).show();
                    }
                });

                dlgCodigo.show();
                break;
            case R.id.nav_mapa:
                Intent intent3 = new Intent(MainActivity.this, MapsActivity2.class);
                startActivity(intent3);
                break;
            case R.id.nav_mapaRutas:
                Intent intent4 = new Intent(MainActivity.this, MapsActivity3.class);
                startActivity(intent4);
                break;
            case R.id.nav_coordenadas:
            MapsActivity.definirCoordenadas(-3.9846, -79.2355, "Sandro House");
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
                /*
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(MainActivity.this, "No se han definido los permisos necesarios", Toast.LENGTH_SHORT).show();
                }else {
                    locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    Toast.makeText(MainActivity.this, "Latitud: "+loc, Toast.LENGTH_SHORT).show();
                }
                */
            //Toast.makeText(MainActivity.this, mylocation, Toast.LENGTH_SHORT).show();
            break;


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        Marker Inicio1 = mMap.addMarker(new MarkerOptions().position(new LatLng(-3.949519, -79.269697)).title("Taquil")
                .icon(getMarkerIcon("#fc1ffc0b"))
                .snippet("Corredor Taquil"));
        Inicio1.setTag(1);

        Marker Inicio2 = mMap.addMarker(new MarkerOptions().position(new LatLng(-3.899213, -79.305632)).title("Chantaco")
                .icon(getMarkerIcon("#f002be9f"))
                .snippet("Corredor Chantaco"));
        Inicio2.setTag(1);

        Marker Inicio3 = mMap.addMarker(new MarkerOptions().position(new LatLng(-3.845300, -79.344618)).title("Chuquiribamba")
                .icon(getMarkerIcon("#f6ff4608"))
                .snippet("Corredor Chuquiribamba"));
        Inicio3.setTag(1);

        Marker Inicio4 = mMap.addMarker(new MarkerOptions().position(new LatLng(-3.797199 , -79.364088)).title("Gualel")
                .icon(getMarkerIcon("#f9ba0970"))
                .snippet("Corredor Gualel"));
        Inicio4.setTag(1);

        Marker Inicio5 = mMap.addMarker(new MarkerOptions().position(new LatLng(-3.788181 , -79.383097)).title("El Cisne")
                .icon(getMarkerIcon("#f75f26b4"))
                .snippet("Corredor El Cisne"));
        Inicio5.setTag(1);



    }
        /*

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        Marker Inicio = mMap.addMarker(new MarkerOptions().position(ArrayLatLng.get(0)).title("Inicio")
                .icon(getMarkerIcon("#2AB202")));
        Inicio.setTag(1);
        Marker Fin = mMap.addMarker(new MarkerOptions().position(ArrayLatLng.get(ArrayLatLng.size() - 1)).title("Fin")
                .icon(getMarkerIcon("#001DA1")));
        Fin.setTag(2);

            Log.i("sandro", "Marcó la linea  "+i);
            Log.i("sandro", "Valores  "+latLng+ "valores2 "+ListLatLng.get(i + 1));
            Polyline line = mMap.addPolyline(new PolylineOptions().add(latLng
                    , ListLatLng.get(i + 1))
                    .width(5)
                    .color(Color.parseColor("#FF4930")));
            line.setGeodesic(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ArrayLatLng.get(0), 13.9f));
    }
        */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab1:
                Intent Taquil = new Intent(MainActivity.this, Taquil.class);
                startActivity(Taquil);
                break;
            case R.id.fab2:
                Intent Chantaco = new Intent(MainActivity.this, Chantaco.class);
                startActivity(Chantaco);

                break;
            case R.id.fab3:
                Intent Chuquiribamba = new Intent(MainActivity.this, Chuquiribamba.class);
                startActivity(Chuquiribamba);
                break;
            case R.id.fab4:
                Intent Gualel = new Intent(MainActivity.this, Gualel.class);
                startActivity(Gualel);
                break;
            case R.id.fab5:
                Intent Cisne = new Intent(MainActivity.this, Cisne.class);
                startActivity(Cisne);
                break;

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
    private void obtenerObjeros(String entrada) throws JSONException {

        ArrayLatLng = new ArrayList<>();
        ListLatLng = obtenerLatLng(entrada);

        for (int i = 0; i < ListLatLng.size(); i++) {
            latLng = ListLatLng.get(i);
            ArrayLatLng.add(latLng);
            if (i < ListLatLng.size() - 1) {
                Polyline line = mMap.addPolyline(new PolylineOptions().add(latLng
                        , ListLatLng.get(i + 1))
                        .width(5)
                        .color(Color.parseColor("#FF4930")));
                line.setGeodesic(true);
            }
        }
        Marker Inicio = mMap.addMarker(new MarkerOptions().position(ArrayLatLng.get(0)).title("Inicio")
                .icon(getMarkerIcon("#2AB202"))
                .snippet("Inicia Corredor"));
        Inicio.setTag(1);
        /*
        Marker miMaker=mMap.addMarker(new MarkerOptions()
                .position(ArrayLatLng.get(0))
                .title(sitioSeleccionado)
                .snippet(sitioSeleccionado)
                .icon(getMarkerIcon("#2AB202"))
                .anchor(0.5f,0.5f));

        miMaker.showInfoWindow();
*/
        Marker Fin = mMap.addMarker(new MarkerOptions().position(ArrayLatLng.get(ArrayLatLng.size() - 1)).title("Fin")
                .icon(getMarkerIcon("#001DA1"))
                .snippet("Termina Corredor Corredor"));
        Fin.setTag(2);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ArrayLatLng.get(0), 10.2f));

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
        sitioSeleccionado = jsonArray.getJSONObject(i).getString("nombreZona");
        }
        return lista;

    }



}
