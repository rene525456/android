package com.example.primerproyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText cajaNombres, cajaCedula;
    Button botonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // se toma el control de los componentes gráficos
        cajaNombres = (EditText) findViewById(R.id.txtNombres);
        cajaCedula = (EditText) findViewById(R.id.txtCedula);
        botonRegistrar = (Button) findViewById(R.id.btnRegistrar);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,cajaNombres.getText() + " "
                        // + cajaCedula.getText(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ActividadRecibirParametros.class);

                Bundle bundle = new Bundle();
                bundle.putString("nombres", cajaNombres.getText().toString());
                bundle.putString("dni", cajaCedula.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}
