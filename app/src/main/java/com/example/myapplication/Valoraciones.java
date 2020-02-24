package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class Valoraciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] nombres={"Bart Simpson","Edna Krabappel","Homer Simpson","Lisa Simpson","Seymour Skinner"};
        double[] valoracion={3.2,2.4,4.6,4.9,3.0};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoraciones);

        ListView libros = findViewById(R.id.listadoLibros);
        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),nombres,valoracion);
        libros.setAdapter(eladap);




    }
}
