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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CambiarValoracion extends AppCompatActivity {
    Bd GestorBD = new Bd(this,"biblioteca",null,3);
    int id=0;
    int idLibro=0;
    String nombreLibro = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_valoracion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String usuario= extras.getString("user");
            id = extras.getInt("id");
            idLibro = extras.getInt("idLibro");
            nombreLibro = extras.getString("NombreLibro");

        }
        Button cambiar = findViewById(R.id.cambiarCambiar);
        EditText nombre = findViewById(R.id.cambiarNombre);
        nombre.setText(nombreLibro);
        final EditText valoracion = findViewById(R.id.cambiarValoracion);

        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double val = Double.parseDouble(valoracion.getText().toString().replace(",","."));
                Log.i("AA", val + "");
                if(val < 0 || val > 10){
                    //Error
                    DialogFragment dialogo = new AlertDialogValoracion();
                    dialogo.show(getSupportFragmentManager(),"cambiarValoracion");
                    valoracion.getText().clear();
                }else {
                    String [] args = {Integer.toString(id),Integer.toString(idLibro)};
                    Consultas.updateValoracion(args,Double.parseDouble(valoracion.getText().toString()),GestorBD);

                    NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getBaseContext(), "CambioValoracion");

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel elCanal = new NotificationChannel("CambioValoracion", "NombreCanal", NotificationManager.IMPORTANCE_HIGH);
                        elCanal.setDescription("Descripción del canal");
                        elCanal.enableLights(true);
                        elCanal.setLightColor(Color.RED);
                        elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                        elCanal.enableVibration(true);
                        elManager.createNotificationChannel(elCanal);
                    }
                    elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                            .setContentTitle("Valoracion")
                            .setContentText("Su valoracion se modifico correctamente.")
                            .setVibrate(new long[]{0, 1000, 500, 1000})
                            .setAutoCancel(true);

                    elManager.notify(1, elBuilder.build());

                    Intent i = new Intent(getApplicationContext(),MenuPrincipal.class);
                    i.putExtra("id",id);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
