package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
        ArrayList<String> lineas = new ArrayList<String>();

        try {
            BufferedReader fichero = new BufferedReader(new InputStreamReader(openFileInput("libros.txt")));
            String linea = fichero.readLine();
            while (linea!=null){
                lineas.add(linea);
                Log.i("AA",linea);
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
        String[] arrayLineas = lineas.toArray(new String[lineas.size()]);
        ArrayAdapter eladaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayLineas);
        ListView lalista = (ListView) findViewById(R.id.miLista);
        lalista.setAdapter(eladaptador);
    }
}
