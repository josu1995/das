package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnadirValoracion extends AppCompatActivity {

    double val = 0;
    int id= Singelton.getIdUsuario();
    int idLibro =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_valoracion);

        final Bd GestorBD = new Bd(this,"biblioteca",null,3);
        Toolbar barra = findViewById(R.id.toolbar);
        setSupportActionBar(barra);
        Button añadir = findViewById(R.id.anadirAnadir);
        final EditText nombre = findViewById(R.id.anadirNombreLibro);
        final EditText valoracion = findViewById(R.id.cambiarValoracion);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre.setText(extras.getString("libro"));
        }
        //Hacemos varias comprobaciones antes de añadir la valoracion
        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si el usuario introduce algo que no sea numero o numero coma/punto numero mostrara un dialog
                if(valoracion.getText().toString().matches("^[0-9]+([,.][0-9]+)?$") && valoracion.getText().toString().length() > 0) {
                     val = Double.parseDouble(valoracion.getText().toString().replace(",", "."));
                    if (val < 0 || val > 10) {
                        DialogFragment dialogo = new AlertDialogValoracion();
                        dialogo.show(getSupportFragmentManager(), "valoracion");
                        valoracion.getText().clear();
                    } else {
                        //Obtenemos el id del libro que hemos introducido si no existe es que nadie lo ha valorado
                        String[] args = {nombre.getText().toString().toUpperCase()};
                        Cursor cu = Consultas.getLibro(args, GestorBD);
                        if (cu.moveToNext()) {
                            idLibro = cu.getInt(0);
                            cu.close();
                            String[] argumentos = {Integer.toString(id), Integer.toString(idLibro)};
                            //Como el libro existe buscamos haber si ese usuario con ese libro tiene una valoracion
                            //Si lo tiene le mostramos un dialogo
                            Cursor cur = Consultas.getValoracionUsuarioLibro(argumentos, GestorBD);
                            if (cur.moveToNext()) {
                                DialogFragment dialogo = new AlertDialogNewValoracion();
                                dialogo.show(getSupportFragmentManager(), "valoracionRepetida");
                                cur.close();
                            } else {
                                //Añadimos la valoracion
                                Consultas.anadirValoracion(id, idLibro, Double.toString(val), GestorBD);
                                Intent i = new Intent(getApplicationContext(), MenuPrincipal.class);
                                startActivity(i);
                                finish();
                            }
                        } else {
                            //Inserto el libro ya que no existe
                            cu.close();
                            Consultas.anadirLibro(nombre.getText().toString().toUpperCase(), GestorBD);

                            //Sacamos el id del libro
                            Cursor c = Consultas.getIdLibro(args, GestorBD);
                            c.moveToNext();
                            idLibro = c.getInt(0);
                            c.close();

                            //Añadimos la valoracion y mostramos una notificacion
                            Consultas.anadirValoracion(id, idLibro, Double.toString(val), GestorBD);
                            NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getBaseContext(), "ValoracionOk");

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel elCanal = new NotificationChannel("ValoracionOk", "NombreCanal", NotificationManager.IMPORTANCE_HIGH);
                                elCanal.setDescription("Descripción del canal");
                                elCanal.enableLights(true);
                                elCanal.setLightColor(Color.RED);
                                elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                                elCanal.enableVibration(true);
                                elManager.createNotificationChannel(elCanal);
                            }
                            elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning)
                                    .setContentTitle("Libro añadido")
                                    .setContentText("El libro: "+nombre.getText().toString() +" se añadió correctamente.")
                                    .setVibrate(new long[]{0, 1000, 500, 1000})
                                    .setAutoCancel(true);

                            elManager.notify(1, elBuilder.build());
                        }
                    }
                }else{
                    valoracion.getText().clear();
                    DialogFragment dialogo = new AlertDialogNotNumero();
                    dialogo.show(getSupportFragmentManager(),"notNumero");

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
