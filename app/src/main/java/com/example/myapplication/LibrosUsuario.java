package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class LibrosUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros_usuario);

        FragmentB elotro=(FragmentB) getSupportFragmentManager().findFragmentById(R.id.fragmentB);
        int informacion= Integer.parseInt(getIntent().getStringExtra("idLibro"));
        elotro.hacerAlgo(informacion);
    }

    }


