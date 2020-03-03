package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {
    Bd GestorBD = new Bd(this,"biblioteca",null,3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registro = findViewById(R.id.newRegistro);

        final EditText usuario = findViewById(R.id.newUsuario);
        final EditText pass = findViewById(R.id.newPass);
        final EditText pass2 = findViewById(R.id.newPass2);

        //Al darle al boton registrar se hacen una serie de comprobaciones
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si alguno de los campos esta vacio mostramos un dialogo.
                if(usuario.getText().toString().length() == 0 || pass.getText().toString().length() == 0 ){
                    DialogFragment dialogo = new AlertDialogDatos();
                    dialogo.show(getSupportFragmentManager(),"datos");
                    usuario.getText().clear();
                    pass.getText().clear();
                    pass2.getText().clear();
                    //Si las contraseñas no coinciden mostramos un dialogo
                }else if(!pass.getText().toString().equals(pass2.getText().toString())){
                    DialogFragment dialogo = new AlertDialogPass();
                    dialogo.show(getSupportFragmentManager(),"registro");
                    pass.getText().clear();
                    pass2.getText().clear();
                }else{
                    //Si las comprobaciones son correctas miramos que el usuario no este ya registrado, con
                    //ese nombre de usuario
                    String[] args = {usuario.getText().toString(),};
                    Cursor cu = Consultas.getUserRegistro(args,GestorBD);
                    if(cu.moveToNext()) {
                        DialogFragment dialogo = new AlertDialogUsuario();
                        dialogo.show(getSupportFragmentManager(),"usuario");
                        usuario.getText().clear();
                        pass.getText().clear();
                        pass2.getText().clear();
                    }else{
                        //Si no esta registrado los añadimos a la base de datos y mostramos una notificacion
                        Consultas.registrarUsuario(usuario.getText().toString(), pass.getText().toString(),GestorBD);

                    NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getBaseContext(), "RegistroOk");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel elCanal = new NotificationChannel("RegistroOk", "NombreCanal", NotificationManager.IMPORTANCE_HIGH);
                        elCanal.setDescription("Descripción del canal");
                        elCanal.enableLights(true);
                        elCanal.setLightColor(Color.RED);
                        elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                        elCanal.enableVibration(true);
                        elManager.createNotificationChannel(elCanal);
                    }
                    elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                            .setContentTitle("Registro")
                            .setContentText("El registro se completó correctamente.")
                            .setVibrate(new long[]{0, 1000, 500, 1000})
                            .setAutoCancel(true);

                    elManager.notify(1, elBuilder.build());

                    //Volvemos a la pantalla de login
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                    finish();
                    }
                }
            }
        });
    }
}
