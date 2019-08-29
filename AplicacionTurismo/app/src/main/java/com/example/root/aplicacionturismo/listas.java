package com.example.root.aplicacionturismo;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.aplicacionturismo.Adapter.ListaAdapter;
import com.example.root.aplicacionturismo.modelo.Lista;

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

public class listas extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    ServicioWeb hiloConexion;

    LocationManager locManager;
    Location loc;
    ListView listView;
    ArrayList<Lista> ArrayLista;
    List<Lista> ListLista;
    Lista lista;
    ListaAdapter listaAdapter;
    String mylocation = "Vacio";
    Boolean sitio = false;
    ImageButton btnBuscar;
    EditText txtBuscar;
    Frg_principal frgUno;
    Button btn_corredor, btn_provincias, btn_mapa, btn_informacion;
    //List view



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        String urlId = "https://proyetoturismov1.herokuapp.com/servicioweb/Provincias";
        hiloConexion = new ServicioWeb();
        hiloConexion.execute(urlId, "1");

        listView = (ListView) findViewById(R.id.lista_datos);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombre = ArrayLista.get(position).getNombre();
                String url = "";
                    Toast.makeText(listas.this, "Click", Toast.LENGTH_SHORT).show();
                if (ArrayLista.get(position).getDescripcion().contains("cantones")) {
                    url = "https://proyetoturismov1.herokuapp.com/servicioweb/canton?nombreProvincia=" + nombre;
                } else if (ArrayLista.get(position).getDescripcion().contains("parroquias")) {
                    url = "https://proyetoturismov1.herokuapp.com/servicioweb/sitio?nombreCanton=" + nombre;
                    sitio=true;
                }else if (sitio){
                    MapsActivity.definirCoordenadas(ArrayLista.get(position).getLat(),ArrayLista.get(position).getLon(), ArrayLista.get(position).getNombre());
                    Intent intent = new Intent(listas.this, MapsActivity.class);
                    startActivity(intent);
                }
                hiloConexion = new ServicioWeb();
                hiloConexion.execute(url, "1");
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*
            case R.id.btn_Buscar:
                String urlId = "https://proyetoturismov1.herokuapp.com/servicioweb/grupo?nombreSitio="+txtBuscar.getText();
                hiloConexion = new ServicioWeb();
                hiloConexion.execute(urlId, "1");
                txtBuscar.setText("");
                break;
            */
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
                mostrarListView(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);

        }
    }
    private void mostrarListView(String entrada) throws JSONException {
        ArrayLista = new ArrayList<>();

        if (entrada.contains("provincia_id")){
            ListLista = obtenerProvincias(entrada);
        }else if (entrada.contains("canton_id")){
            ListLista = obtenerCantones(entrada);
        }else if (entrada.contains("sitio_id")){
            ListLista = obtenerSitios(entrada);

        }

        for (int i = 0; i < ListLista.size(); i++) {
            lista = ListLista.get(i);
            ArrayLista.add(lista);
        }
        listaAdapter = new ListaAdapter(listas.this, ArrayLista);
        listView.setAdapter(listaAdapter);
        listaAdapter.notifyDataSetChanged();
    }
    private List<Lista> obtenerProvincias(String str) throws JSONException {

        List<Lista> lista = new ArrayList<Lista>();
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {
            Lista lista1 = new Lista();
            lista1.setFoto(jsonArray.getJSONObject(i).getString("foto"));
            lista1.setNombre(jsonArray.getJSONObject(i).getString("nombreProvincia"));
            lista1.setLat(jsonArray.getJSONObject(i).getDouble("latitudProvincia"));
            lista1.setLon(jsonArray.getJSONObject(i).getDouble("longitudProvincia"));
            lista1.setDescripcion("Capital: "+jsonArray.getJSONObject(i).getString("capital")
                    +"\nNúmero de cantones: "+jsonArray.getJSONObject(i).getString("cantones")
                    +"\nNúmero de habitantes: "+jsonArray.getJSONObject(i).getString("poblacionProvincia"));
            lista.add(lista1);
        }
        return lista;

    }
    private List<Lista> obtenerCantones(String str) throws JSONException {

        List<Lista> lista = new ArrayList<Lista>();
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {
            Lista lista1 = new Lista();
            lista1.setFoto(jsonArray.getJSONObject(i).getString("fotoCanton"));
            lista1.setNombre(jsonArray.getJSONObject(i).getString("nombreCanton"));
            lista1.setLat(jsonArray.getJSONObject(i).getDouble("latitudCanton"));
            lista1.setLon(jsonArray.getJSONObject(i).getDouble("longitudCanton"));
            lista1.setDescripcion("Provincia a la pertenece: "+jsonArray.getJSONObject(i).getString("nombreProvincia")
                    +"\nNúmero de parroquias: "+jsonArray.getJSONObject(i).getString("parroquias")
                    +"\nNúmero de habitantes: "+jsonArray.getJSONObject(i).getString("poblacionCanton")
                    +"\nLugares turísticos: "+jsonArray.getJSONObject(i).getString("lugaresTuristicos"));
            lista.add(lista1);
        }
        return lista;

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


}
