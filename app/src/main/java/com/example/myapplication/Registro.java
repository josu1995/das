package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

                if(usuario.getText().toString().length() == 0 || pass.getText().toString().length() == 0 ){
                    DialogFragment dialogo = new AlertDialogDatos();
                    dialogo.show(getSupportFragmentManager(),"datos");
                    usuario.getText().clear();
                    pass.getText().clear();
                    pass2.getText().clear();
                }else if(!pass.getText().toString().equals(pass2.getText().toString())){
                    DialogFragment dialogo = new AlertDialogPass();
                    dialogo.show(getSupportFragmentManager(),"registro");
                    pass.getText().clear();
                    pass2.getText().clear();
                }else {

                    NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getApplicationContext(), "RegistroOk");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal", NotificationManager.IMPORTANCE_DEFAULT);
                        elCanal.setDescription("Descripción del canal");
                        elCanal.enableLights(true);
                        elCanal.setLightColor(Color.RED);
                        elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                        elCanal.enableVibration(true);
                        elManager.createNotificationChannel(elCanal);
                    }
                    elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                            .setContentTitle("Registro")
                            .setContentText("El registro se complento correctamente.")
                            .setVibrate(new long[]{0, 1000, 500, 1000})
                            .setAutoCancel(true);

                    elManager.notify(1, elBuilder.build());


                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
