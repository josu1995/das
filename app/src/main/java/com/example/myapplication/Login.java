package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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

    SQLiteDatabase bd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Bd GestorBD = new Bd(this,"biblioteca",null,3);

        Button login = findViewById(R.id.login);
        Button registro = findViewById(R.id.newRegistro);

        final EditText usuario = findViewById(R.id.usuario);
        final EditText pass = findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {usuario.getText().toString(),pass.getText().toString()};
                Cursor cu = Consultas.getLogin(args,GestorBD);
                if(cu.moveToNext()){
                    Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
                    i.putExtra("user",usuario.getText().toString());
                    i.putExtra("id",cu.getInt(0));
                    startActivity(i);
                    cu.close();
                    finish();
                }else{
                    usuario.getText().clear();
                    pass.getText().clear();
                    DialogFragment dialogo = new AlertDialogLogin();
                    dialogo.show(getSupportFragmentManager(),"login");
                    cu.close();
                }


            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Registro.class);
                startActivity(i);

            }
        });
    }
}
