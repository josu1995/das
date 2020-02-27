package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {
    int id= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        TextView user = findViewById(R.id.bnv);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String usuario= extras.getString("user");
            user.setText(user.getText().toString() + usuario);
            id = extras.getInt("id");
        }

        Button añadir = findViewById(R.id.anadir);
        Button valoraciones = findViewById(R.id.valoraciones);
        Button misValoraciones = findViewById(R.id.misValoraciones);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent i = new Intent(getApplicationContext(),AnadirValoracion.class);
            i.putExtra("id",id);
            startActivity(i);
            }
        });

        valoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Valoraciones.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        misValoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MisValoraciones.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });



    }
}
