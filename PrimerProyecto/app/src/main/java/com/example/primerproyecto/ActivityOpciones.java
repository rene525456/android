package com.example.primerproyecto;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityOpciones extends AppCompatActivity implements View.OnClickListener{

    Button botonLogin, botonSensores, botonArchivos, botonSql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        // se toma el control de los componentes de la aplicación
        botonLogin = (Button) findViewById(R.id.btnLogin);
        botonSensores = (Button) findViewById(R.id.btnSensores);
        botonArchivos = (Button) findViewById(R.id.btnArchivos);
        botonSql = (Button) findViewById(R.id.btnSql);
        botonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.btnLogin:
                intent = new Intent(ActivityOpciones.this, MainActivity.class);
                break;
        }
        startActivity(intent);
    }

    // método para agregar un menú en la activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.opcionDialogo:
                Dialog dialogoSumar = new Dialog(ActivityOpciones.this);
                dialogoSumar.setContentView(R.layout.dlg_suma);

                final EditText n1 = dialogoSumar.findViewById(R.id.txtNumero1);
                final EditText n2 = dialogoSumar.findViewById(R.id.txtNumero2);
                Button botonSuma = dialogoSumar.findViewById(R.id.btnSumar);
                botonSuma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int resultado;
                        resultado = Integer.parseInt(n1.getText().toString()) + Integer.parseInt(n2.getText().toString());
                        Toast.makeText(ActivityOpciones.this,"Suma = " + resultado, Toast.LENGTH_SHORT).show();
                    }
                });

                dialogoSumar.show();
                break;
            case R.id.opcionEscribirMI:
                intent = new Intent(ActivityOpciones.this,ActividadEscribirMI.class);
                startActivity(intent);
                break;
            case R.id.opcionLeerMI:
                intent = new Intent(ActivityOpciones.this,ActividadLeerMI.class);
                startActivity(intent);
                break;
            case R.id.opcionRaw:
                intent = new Intent(ActivityOpciones.this,ActividadArchivoRaw.class);
                startActivity(intent);
                break;
            case R.id.opcionSD:
                intent = new Intent(ActivityOpciones.this,ActividadArchivoSD.class);
                startActivity(intent);
                break;
            case R.id.opcionHelper:
                intent = new Intent(ActivityOpciones.this,actividadProductoBD.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}





















