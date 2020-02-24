package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        Bd GestorBD = new Bd(this,"biblioteca",null,2);
        SQLiteDatabase bd  = GestorBD.getWritableDatabase();

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
                /*Cursor c = bd.rawQuery("SELECT Nombre,Pass FROM USUARIOS ",null);
                while (c.moveToNext()){
                    int od = c.getInt(0);
                    String om = c.getString(1);
                    String pass = c.getString(2);
                    Log.i("AA",od+om+pass);
                 }*/

                Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
                i.putExtra("user",usuario.getText().toString());
                startActivity(i);
                finish();
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
