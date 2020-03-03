package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BusquedaUsuario extends AppCompatActivity implements FragmentA.listenerDelFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_usuario);
        DialogFragment dialogo = new AlertDialogBusquedaUsuario();
        dialogo.show(getSupportFragmentManager(),"busquedaUsuario");
    }

    //Dependiendo de como este la pantalla
    //Si esta en vertical solo mostrara los usuarios
    //Si esta horizontal se mostraran los usuarios y sus libros
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




