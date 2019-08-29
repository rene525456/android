package com.example.primerproyecto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapaPruebas extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap; // es el objeto q maneja, componente del mapa
    private Button botonSatelite, botonTerreno, botonHibrido, botonZoom, botonMiUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_pruebas);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        cargarComponentes();
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng loja = new LatLng(-4.003826, -79.207229);
        mMap.addMarker(new MarkerOptions().position(loja).title("Loja"));

        /*mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-4.019563, -79.207739))
                .title("Inicio").icon(BitmapDescriptorFactory.fromResource(R.drawable.common_google_signin_btn_text_light))
        );
        */
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(-4.019563, -79.207739))
                .title("Inicio")
                .snippet("Es el lugar donde inicio mi recorrido diário hacia la universidad")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.casa))
        );

        PolylineOptions lineas = new PolylineOptions();
        lineas.add(new LatLng(-4.019563, -79.207739)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.019712, -79.207267)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.019430, -79.207245)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.019006, -79.207025)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.018915, -79.207005)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.018733, -79.206875)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.018610, -79.206703)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.018535, -79.206442)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.018406, -79.206190)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.017641, -79.205723)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.017336, -79.205487)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.017240, -79.205261)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.017111, -79.203845)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.032742, -79.202394)).width(10).color(Color.BLUE);

        lineas.add(new LatLng(-4.032763, -79.200195)).width(10).color(Color.BLUE);
        lineas.add(new LatLng(-4.030221, -79.199444)).width(10).color(Color.BLUE);
        mMap.addPolyline(lineas);

        CircleOptions circleInicio = new CircleOptions()
                .center(new LatLng(-4.019563, -79.207739))
                .radius(20)
                .strokeColor(Color.parseColor("#0D47A1"))
                .strokeWidth(4)
                .fillColor(Color.argb(32, 33, 150, 243));
        mMap.addCircle(circleInicio);

        CircleOptions circleFin = new CircleOptions()
                .center(new LatLng(-4.030221, -79.199444))
                .radius(20)
                .strokeColor(Color.parseColor("#0D47A1"))
                .strokeWidth(4)
                .fillColor(Color.argb(62, 150, 50, 143));
        mMap.addCircle(circleFin);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(loja));

        mMap.getUiSettings().setZoomControlsEnabled(true);   // se activan controles de aumentar y reducir

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-4.019563, -79.207739),15));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSatelite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btnTerreno:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.btnHibrido:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.btnZoom:
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-4.003826, -79.207229),18));
                break;
            case R.id.btnUbicacion:

                break;
        }
    }

    private void cargarComponentes(){
        botonSatelite = findViewById(R.id.btnSatelite);
        botonTerreno = findViewById(R.id.btnTerreno);
        botonHibrido = findViewById(R.id.btnHibrido);
        botonZoom = findViewById(R.id.btnZoom);
        botonMiUbicacion = findViewById(R.id.btnUbicacion);
        botonSatelite.setOnClickListener(this);
        botonHibrido.setOnClickListener(this);
        botonTerreno.setOnClickListener(this);
        botonZoom.setOnClickListener(this);
        botonMiUbicacion.setOnClickListener(this);
    }
}
