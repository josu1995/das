package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BusquedaUsuario extends AppCompatActivity implements FragmentA.listenerDelFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_usuario);
    }


    @Override
    public void seleccionarElemento(int id) {
        if (getSupportFragmentManager().findFragmentById(R.id.fragmentLibros)!=null){
            //EL OTRO FRAGMENT EXISTE
            FragmentB elotro=(FragmentB) getSupportFragmentManager().
                    findFragmentById(R.id.fragmentLibros);
            elotro.hacerAlgo(id);
        }
        else{
            Intent i= new Intent(this,LibrosUsuario.class);
            i.putExtra("idLibro",Integer.toString(id));
            startActivity(i);
        }
    }
}




