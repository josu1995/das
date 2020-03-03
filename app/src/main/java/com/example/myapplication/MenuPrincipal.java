package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {
    int id= Singelton.getIdUsuario();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        TextView user = findViewById(R.id.bnv);

        user.setText(user.getText().toString()+" "+Singelton.getNombreUsuario());

        Button añadir = findViewById(R.id.anadir);
        Button valoraciones = findViewById(R.id.valoraciones);
        Button misValoraciones = findViewById(R.id.misValoraciones);
        Button pendientes = findViewById(R.id.pendientes);
        Button busquedaUsuario = findViewById(R.id.buscarUsuario);

        //El menu principal donde podemos elegir que hacer en nuestra app
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),AnadirValoracion.class);
            startActivity(i);
            }
        });

        valoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Valoraciones.class);
                startActivity(i);
            }
        });

        misValoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MisValoraciones.class);
                startActivity(i);
            }
        });
        pendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),LibrosPendientes.class);
                startActivity(i);
            }
        });

        busquedaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),BusquedaUsuario.class);
                startActivity(i);
            }
        });



    }
}
