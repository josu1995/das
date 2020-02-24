package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnadirValoracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_valoracion);

        Button añadir = findViewById(R.id.anadirAnadir);
        final EditText nombre = findViewById(R.id.anadirNombreLibro);
        final EditText valoracion = findViewById(R.id.cambiarValoracion);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre.getText().toString();
                double val = Double.parseDouble(valoracion.getText().toString().replace(",","."));
                Log.i("AA", val + "");
                if(val < 0 || val > 10){
                    //Error
                    DialogFragment dialogo = new AlertDialogValoracion();
                    dialogo.show(getSupportFragmentManager(),"valoracion");
                    valoracion.getText().clear();
                }else {
                    Intent i = new Intent(getApplicationContext(),MenuPrincipal.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
