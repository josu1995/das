package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registro = findViewById(R.id.newRegistro);

        final EditText usuario = findViewById(R.id.newUsuario);
        final EditText pass = findViewById(R.id.newPass);
        final EditText pass2 = findViewById(R.id.newPass2);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AAA",usuario.getText().toString());
                Log.i("AAA",pass.getText().toString());
                Log.i("AAA",pass2.getText().toString());
            }
        });
    }
}
