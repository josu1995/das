package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        TextView user = findViewById(R.id.bnv);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String usuario= extras.getString("user");
            user.setText(user.getText().toString() + usuario);
        }

        Button añadir = findViewById(R.id.anadir);
        Button valoraciones = findViewById(R.id.valoraciones);
        Button misValoraciones = findViewById(R.id.misValoraciones);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),AnadirValoracion.class);
            startActivity(i);
            }
        });

        valoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Valoraciones.class);
                startActivity(i);
            }
        });

        misValoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MisValoraciones.class);
                startActivity(i);
            }
        });



    }
}
