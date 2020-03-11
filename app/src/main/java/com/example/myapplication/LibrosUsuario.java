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

public class LibrosUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_usuario);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        //Llamamos al fragment el cual recibira el id de un usuario por parametro
        FragmentB elotro=(FragmentB) getSupportFragmentManager().findFragmentById(R.id.fragmentB);
        int informacion= Integer.parseInt(getIntent().getStringExtra("idLibro"));

        Log.i("DEBUG", "onCreate 2a actividad");

        elotro.hacerAlgo(informacion);
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


