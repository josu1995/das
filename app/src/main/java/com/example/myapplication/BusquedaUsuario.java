package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BusquedaUsuario extends AppCompatActivity implements FragmentA.listenerDelFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_usuario);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        DialogFragment dialogo = new AlertDialogBusquedaUsuario();
        dialogo.show(getSupportFragmentManager(),"busquedaUsuario");
    }

    //Dependiendo de como este la pantalla
    //Si esta en vertical solo mostrara los usuarios
    //Si esta horizontal se mostraran los usuarios y sus libros
    @Override
    public void seleccionarElemento(int id) {

        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            FragmentB elotro=(FragmentB) getSupportFragmentManager().
                    findFragmentById(R.id.fragmentLibros);
            elotro.hacerAlgo(id);

        }
        else
        {

            Intent i= new Intent(this,LibrosUsuario.class);
            i.putExtra("idLibro",Integer.toString(id));
            startActivity(i);

        }
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




