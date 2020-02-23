package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button login = findViewById(R.id.login);
        Button registro = findViewById(R.id.newRegistro);

        final EditText usuario = findViewById(R.id.usuario);
        final EditText pass = findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AAA","Login");
                Log.i("AAA",usuario.getText().toString());
                Log.i("AAA",pass.getText().toString());
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AAA","registro");
                Intent i = new Intent(getApplicationContext(), Registro.class);
                startActivity(i);
            }
        });
    }
}
