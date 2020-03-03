package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LibrosPendientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_pendientes);

        Button anadirLibro = findViewById(R.id.anadirLibroPendiente);
        Button verLibro = findViewById(R.id.librosPendientes);
        //Menu donde pondemos añadir un libro a nuestra lista de pendientes
        //O mirar nuestra lista de pendientes
        anadirLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AnadirLibroPendiente.class);
                startActivity(i);
            }
        });

        verLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),VerLibrosPendientes.class);
                startActivity(i);
            }
        });
    }
}
