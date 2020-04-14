package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.work.BackoffPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final Bd GestorBD = new Bd(this,"biblioteca",null,4);

        firebaseAuth = FirebaseAuth.getInstance();

        //toast para dar la bienvenida a la aplicaciion
        Toast.makeText(getApplication().getApplicationContext(),"Gracias por usar esta LYBRETA",Toast.LENGTH_LONG).show();
        Button login = findViewById(R.id.login);
        Button registro = findViewById(R.id.newRegistro);

        /*PeriodicWorkRequest trabajoRepetitivo =
                new PeriodicWorkRequest.Builder(Tarea.class,15, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(this).enqueue(trabajoRepetitivo);*/



        final EditText usuario = findViewById(R.id.usuario);
        final EditText pass = findViewById(R.id.password);

        //Si el usuario introduce bien su usuario y contraseña entrara al menu principal
        //si no le saldra un dialogo
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signInWithEmailAndPassword(usuario.getText().toString(),pass.getText().toString()).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String[] args = {usuario.getText().toString(),pass.getText().toString()};
                            Cursor cu = Consultas.getLogin(args,GestorBD);
                            cu.moveToNext();
                            Singelton.setNombreUsuario(usuario.getText().toString());
                            Singelton.setIdUsuario(cu.getInt(0));
                            //Intent i = new Intent(getApplicationContext(), Mapa.class);
                            Intent i = new Intent(getApplicationContext(), ListadoFotos.class);
                            startActivity(i);
                            finish();
                        }else{
                            usuario.getText().clear();
                            pass.getText().clear();
                            DialogFragment dialogo = new AlertDialogLogin();
                            dialogo.show(getSupportFragmentManager(),"login");
                        }
                    }
                });
                /*String[] args = {usuario.getText().toString(),pass.getText().toString()};
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
                }*/



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
