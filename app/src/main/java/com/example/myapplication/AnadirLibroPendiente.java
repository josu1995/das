package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AnadirLibroPendiente extends AppCompatActivity {
    boolean enc = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_libro_pendiente);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        //Miramos si el fichero esta creado, si no lo creamos
        try {
            BufferedReader fichero = new BufferedReader(new InputStreamReader(openFileInput("libros.txt")));
            fichero.close();
        }catch (IOException e){
            try {
                OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput("libros.txt", Context.MODE_PRIVATE));
                fichero.write("Libros Pendientes:");
                fichero.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        Button libro = findViewById(R.id.anadirLibroPendiente);
        final EditText lib = findViewById(R.id.lib);
        //Al añadir lo que hacemos es recoger lo que habia en el fichero y le añadimos al final el nuevo libro
        libro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BufferedReader fichero = new BufferedReader(new InputStreamReader(openFileInput("libros.txt")));
                    String linea = fichero.readLine();
                    String texto = "";
                    while (linea!=null){
                        //Si el libro esta dentro del fichero no lo añadimos y mostramos un dialogo
                        if(linea.equalsIgnoreCase(lib.getText().toString())) {
                            enc = true;
                            DialogFragment dialogo = new AlertDialogLibroPendiente();
                            dialogo.show(getSupportFragmentManager(),"libroPendiente");
                            lib.getText().clear();
                        }
                        texto +=linea+"\n" ;

                        linea = fichero.readLine();

                    }
                    fichero.close();
                    //Si no esta lo añadimos al final y mostramos una notificacion
                    if(!enc){
                        OutputStreamWriter fichero1 = new OutputStreamWriter(openFileOutput("libros.txt", Context.MODE_PRIVATE));
                        fichero1.append(texto+lib.getText().toString().toUpperCase());
                        fichero1.close();
                        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getBaseContext(), "LibroPendienteOk");

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel elCanal = new NotificationChannel("LibroPendienteOk", "NombreCanal", NotificationManager.IMPORTANCE_HIGH);
                            elCanal.setDescription("Descripción del canal");
                            elCanal.enableLights(true);
                            elCanal.setLightColor(Color.RED);
                            elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                            elCanal.enableVibration(true);
                            elManager.createNotificationChannel(elCanal);
                        }
                        elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                                .setContentTitle("Libro Pendiente")
                                .setContentText("Se ha añadido el libro: "+lib.getText().toString() +" a la lista de libros pendientes.")
                                .setVibrate(new long[]{0, 1000, 500, 1000})
                                .setAutoCancel(true);

                        elManager.notify(1, elBuilder.build());
                    }

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(getApplicationContext(),Login.class);
        startActivity(i);
        finish();
        return super.onOptionsItemSelected(item);
    }
}
