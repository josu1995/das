package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.paperdb.Paper;

public class Notas extends AppCompatActivity {
    Button guardar;
    EditText titulo,cuerpo;
    @Override

    //El usuario mete unos tetxto y se encarga de llamar a la actividad de widget donde se creara este mismo.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        guardar = findViewById(R.id.botonNota);
        titulo = findViewById(R.id.tituloNota);
        cuerpo = findViewById(R.id.cuerpoNota);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.init(getApplicationContext());
                Paper.book().write("titulo",titulo.getText().toString());
                Paper.book().write("cuerpo",cuerpo.getText().toString());
                Toast.makeText(Notas.this,"Nota guardada",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
