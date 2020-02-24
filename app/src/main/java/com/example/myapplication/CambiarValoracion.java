package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CambiarValoracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_valoracion);

        Button cambiar = findViewById(R.id.cambiarCambiar);
        EditText nombre = findViewById(R.id.cambiarNombre);
        final EditText valoracion = findViewById(R.id.cambiarValoracion);

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double val = Double.parseDouble(valoracion.getText().toString().replace(",","."));
                Log.i("AA", val + "");
                if(val < 0 || val > 10){
                    //Error
                    DialogFragment dialogo = new AlertDialogValoracion();
                    dialogo.show(getSupportFragmentManager(),"cambiarValoracion");
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
