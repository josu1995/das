package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class LibrosUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_usuario);
        //Llamamos al fragment el cual recibira el id de un usuario por parametro
        FragmentB elotro=(FragmentB) getSupportFragmentManager().findFragmentById(R.id.fragmentB);
        int informacion= Integer.parseInt(getIntent().getStringExtra("idLibro"));

        Log.i("DEBUG", "onCreate 2a actividad");

        elotro.hacerAlgo(informacion);
    }

    }


