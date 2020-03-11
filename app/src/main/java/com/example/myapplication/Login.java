package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class Login extends AppCompatActivity {

    SQLiteDatabase bd = null;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Bd GestorBD = new Bd(this,"biblioteca",null,3);

        Toast.makeText(getApplication().getApplicationContext(),"Gracias por usar esta APP",Toast.LENGTH_LONG).show();
        Button login = findViewById(R.id.login);
        Button registro = findViewById(R.id.newRegistro);


        final EditText usuario = findViewById(R.id.usuario);
        final EditText pass = findViewById(R.id.password);

        //Si el usuario introduce bien su usuario y contraseña entrara al menu principal
        //si le saldra un dialogo
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] args = {usuario.getText().toString(),pass.getText().toString()};
                Cursor cu = Consultas.getLogin(args,GestorBD);
                if(cu.moveToNext()){
                    Singelton.setNombreUsuario(usuario.getText().toString());
                    Singelton.setIdUsuario(cu.getInt(0));
                    Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
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
        //Ir a la opcion de registrarse
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Registro.class);
                startActivity(i);

            }
        });
    }


}
