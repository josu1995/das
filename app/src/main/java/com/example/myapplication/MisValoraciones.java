package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MisValoraciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] nombres={"Bart Simpson","Edna Krabappel","Homer Simpson","Lisa Simpson","Seymour Skinner"};
        double[] valoracion={3.2,2.4,4.6,4.9,3.0};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_valoraciones);
        Button email = findViewById(R.id.botonEmail);

        ListView libros = findViewById(R.id.listadoMisValoraciones);
        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),nombres,valoracion);
        libros.setAdapter(eladap);

        libros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.i("AA", ((TextView)view.findViewById(R.id.nombreLibro)).getText().toString());
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MandarMail.class);
                startActivity(i);
            }
        });
    }
}
