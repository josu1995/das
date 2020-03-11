package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class VerLibrosPendientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_libros_pendientes);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        ArrayList<String> lineas = new ArrayList<String>();
        //Al crear la actividad abrimos el fichero
        //En caso de que no este lo crearemos.
        //Si lo hemos podido abrir cargaremos linea por linea los libros que tenemos apuntados como pendientes
        try {
            BufferedReader fichero = new BufferedReader(new InputStreamReader(openFileInput("libros.txt")));
            String linea = fichero.readLine();
            while (linea!=null){
                lineas.add(linea);
                linea = fichero.readLine();
            }
            fichero.close();
        }catch (IOException e){
            try {
                OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput("libros.txt", Context.MODE_PRIVATE));
                fichero.write("Libros Pendientes:");
                fichero.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        //Añadiremos los libros a una lista que es la que se mostrara por pantalla.
        String[] arrayLineas = lineas.toArray(new String[lineas.size()]);
        ArrayAdapter eladaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayLineas);
        ListView lalista = (ListView) findViewById(R.id.miLista);
        lalista.setAdapter(eladaptador);
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
