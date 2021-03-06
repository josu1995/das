package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class Valoraciones extends AppCompatActivity {
    Bd GestorBD = new Bd(this,"biblioteca",null,4);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> nombreLibros = new ArrayList<String>();
        ArrayList<Double> valoraciones = new ArrayList<Double>();

        //Buscaremos en base de datos todas las valoraciones que hay
        //Por cada valoracion cogeremos su libro y su nota
        //Si un libro esta valorado por mas de un usuario, se hara una media
        Cursor cu = Consultas.getValoraciones(GestorBD);
        while(cu.moveToNext()){
            int idLibro = cu.getInt(1);
            double val = cu.getDouble(2);
            String [] args = {Integer.toString(idLibro)};
            Cursor c = Consultas.getLibroById(args,GestorBD);
            c.moveToNext();
            String nombreLibro = c.getString(1);
            if(nombreLibros.contains(nombreLibro)){
                double temp = valoraciones.get(nombreLibros.indexOf(nombreLibro));
                valoraciones.remove(nombreLibros.indexOf(nombreLibro));
                valoraciones.add(nombreLibros.indexOf(nombreLibro),(temp+val)/2);
            }else {
                nombreLibros.add(nombreLibro);
                valoraciones.add(val);

            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valoraciones);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        //Añadiremos los libros a la lista personalizada
        //Como nuestro ratingBar se muestra 5 estrellas haremos la nota que tiene ese libro entre 2
        String[] arrayLibros = nombreLibros.toArray(new String[nombreLibros.size()]);
        double [] d = new double[valoraciones.size()];
        for(int i =0;i<d.length;i++){
            d[i]=valoraciones.get(i) / 2;
        }

        ListView libros = findViewById(R.id.listadoLibros);
        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),arrayLibros,d);
        libros.setAdapter(eladap);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(getApplicationContext(),Login.class);
        startActivity(i);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
